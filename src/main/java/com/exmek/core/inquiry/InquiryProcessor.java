package com.exmek.core.inquiry;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.exmek.commons.net.ContentType;
import com.exmek.commons.utils.UrlUtils;
import com.exmek.core.config.AppConfigProvider;
import com.exmek.core.config.ReceiverEmailConf;
import com.exmek.core.consts.RequestHeaderConsts;
import com.exmek.core.email.MailSenderService;
import com.exmek.core.exception.ErrorCode;
import com.exmek.core.exception.ValidationException;
import com.exmek.core.external.CountryLookupService;
import com.exmek.core.mapper.InquiryMapper;
import com.exmek.core.model.Inquiry;
import com.exmek.core.persistence.entity.InquiryEntity;
import com.exmek.core.persistence.repository.InquiryRepository;
import com.exmek.core.rest.InquiryRequest;
import com.exmek.core.rest.InquiryResponse;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InquiryProcessor {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private AppConfigProvider appConfigProvider;

//	@Autowired
//	private TemplateEngine templateEngine;

	@Autowired
	private InquiryRepository inquiryRepository;
	
	@Autowired
	private CountryLookupService countryLookupService;

	@Autowired
	private InquiryMapper inquiryMapper;

	@Autowired
	private MailSenderService mailSenderService;

	public InquiryResponse processInquiry(@NotNull @RequestBody InquiryRequest reqInquiryPayload,
			@RequestHeader(name = RequestHeaderConsts.CLIENT_IP, required = false) String headerClientIp) {
		if (reqInquiryPayload == null) {
			throw new ValidationException("inquiry request payload cannot be null. ", ErrorCode.ERR_CODE_INQUIRY_MISSING_REQUEST_PAYLOAD);
		}
		InquiryResponse response = new InquiryResponse();
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		InquiryEntity entity = inquiryMapper.mapInquiryToEntity(reqInquiryPayload.getInquiry());
		String clientIpAddr = headerClientIp;
		if (ObjectUtils.isEmpty(clientIpAddr)) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			clientIpAddr = request.getRemoteAddr();
		}
		entity.setClientIpAddress(clientIpAddr);
		entity.setClientCountryOrRegion(getCountryOrRegionName(clientIpAddr));
		try {
			entity = inquiryRepository.save(entity);
			response.setStatus("SAVED");
		} catch (Exception ex) {
			log.error("Failed to save InquiryEntity to db.", ex);
			response.setStatus("SAVE_FAILED");
		}
		Inquiry inquiry = inquiryMapper.mapInquiryToModel(entity);
		response.setInquiry(inquiry);
		if (sendInquiryEmailToExmekSys(inquiry)) {
			response.setStatus("KICKED_INQUIRY_EMAIL");
		} else {
			response.setStatus("KICK_INQUIRY_EMAIL_FAILED");
		}
		return response;
	}
	
	private String getCountryOrRegionName(String ipAddr) {
		if (ObjectUtils.isEmpty(ipAddr)) {
			return null;
		}
		String countryOrRegionCode = countryLookupService.getCountryOrRegionCodeByIP(ipAddr);
		if (countryOrRegionCode == null) {
			return null;
		}
		if (countryOrRegionCode.length() <= 2) {
			return new Locale("", countryOrRegionCode).getDisplayCountry();
		} else {
			return countryOrRegionCode;
		}
	}

	private boolean sendInquiryEmailToExmekSys(Inquiry inquiry) {
		String htmlContent = resolveInquiryContent(inquiry);
	    String subject = "Inquiry for " + inquiry.getRefModel();
	    ReceiverEmailConf irEmailConf = appConfigProvider.getInquiryReceiverEmailConf();
		try {
			mailSenderService.sendMail(irEmailConf.getTo(), irEmailConf.getCc(), irEmailConf.getBcc(), subject, htmlContent, ContentType.TEXT_HTML);
			return true;
		} catch (MessagingException ex) {
			log.error("Failed to send email of inquiry for {} ", inquiry.getRefModel());
			return false;
		}
	}
	
//	String resolveInquiryContentByThymeleaf(Inquiry inquiry) {
//		Context templContext = new Context();
//		templContext.setVariable("inquiry", inquiry);
//	    String htmlContent = templateEngine.process("inquiry", templContext);
//	}
	
	String resolveInquiryContent(Inquiry inquiry) {
		if (inquiry == null) {
			return null;
		}
		String htmlLocation = UrlUtils.concatURL(ResourceLoader.CLASSPATH_URL_PREFIX, "templates", "inquiry.html");
		String htmlContent = "";
		Resource htmlResource = null;
		try {
			htmlResource = ResourcePatternUtils.getResourcePatternResolver(applicationContext).getResource(htmlLocation);
		} catch (Exception ex) {
			log.error("Failed to load inquiry.html template resource from {} ", htmlLocation, ex);
			return htmlContent;
		}
		if (htmlResource == null) {
			log.error("Unable to load inquiry.html template resource from {} ", htmlLocation);
			return htmlContent;
		}
		try {
			htmlContent = StreamUtils.copyToString(htmlResource.getInputStream(), StandardCharsets.UTF_8);
		} catch (Exception ex) {
			log.error("Failed to read inquiry.html template content from {} ", htmlLocation, ex);
			return htmlContent;
		}
		log.debug("Loaded inquiry.html template content from {}: {} ", htmlLocation, htmlContent);
		String varTokenStart = "${inquiry.";
		String varTokenEnd = "}";
		int foundStartInx = htmlContent.indexOf(varTokenStart);
		while (foundStartInx >= 0) {
			int varEndInx = htmlContent.indexOf(varTokenEnd, foundStartInx + 1);
			if (varEndInx < 0) {
				break;
			}
			String varPropName = htmlContent.substring(foundStartInx + varTokenStart.length(), varEndInx);
			String varPropValue = null;
			try {
				Object propObj = PropertyUtils.getProperty(inquiry, varPropName);
				if (propObj != null) {
					varPropValue = propObj.toString();
				} else {
					log.warn("Failed to resolve inquiry property of {} ", varPropName);
				}
			} catch (Exception ex) {
				log.warn("Failed to resolve inquiry property of {} ", varPropName, ex);
			}
			if (varPropValue != null) {
				htmlContent = htmlContent.replace(varTokenStart + varPropName + varTokenEnd, varPropValue);
			} else {
				htmlContent = htmlContent.replace(varTokenStart + varPropName + varTokenEnd, "?");
			}
			foundStartInx = htmlContent.indexOf(varTokenStart);
		}
		log.debug("Resolved inquiry.html: {} ", htmlContent);
		return htmlContent;
	}

}
