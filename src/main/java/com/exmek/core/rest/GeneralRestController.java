package com.exmek.core.rest;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.exmek.commons.net.ContentType;
import com.exmek.core.config.AppConfigProvider;
import com.exmek.core.config.ReceiverEmailConf;
import com.exmek.core.consts.EndpointConsts;
import com.exmek.core.consts.RequestHeaderConsts;
import com.exmek.core.email.MailSenderService;
import com.exmek.core.error.ErrorCode;
import com.exmek.core.error.ValidationException;
import com.exmek.core.external.CountryLookupService;
import com.exmek.core.mapper.InquiryMapper;
import com.exmek.core.model.Company;
import com.exmek.core.model.Inquiry;
import com.exmek.core.model.News;
import com.exmek.core.news.NewsRepo;
import com.exmek.core.persistence.entity.InquiryEntity;
import com.exmek.core.persistence.repository.InquiryRepository;

import jakarta.mail.MessagingException;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(EndpointConsts.ENDPOINT_API_PREFIX)
public class GeneralRestController {

	private static final Logger logger = LoggerFactory.getLogger(GeneralRestController.class);

	@Autowired
	private AppConfigProvider appConfigProvider; 

	@Autowired
	private NewsRepo newsRepo;

	@Autowired
	private InquiryRepository inquiryRepository;
	
	@Autowired
	private CountryLookupService countryLookupService;

	@Autowired
	private InquiryMapper inquiryMapper;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private MailSenderService mailSenderService;
	
	@GetMapping("/company/exmek")
	public Company getExmekCompany() {
		return appConfigProvider.getExmekCompany();
	}
		
	@GetMapping("/news/{idOrTitle}")
	public News getNews(@NotNull @PathVariable("idOrTitle") String idOrTitle) {
		if (idOrTitle == null || idOrTitle.isBlank()) {
			return null;
		}
		return newsRepo.loadNewsByIdOrTitle(idOrTitle);
	}
	
	@GetMapping("/news")
	public List<News> getAllNews() {
		return newsRepo.loadAllNews();
	}

	@PostMapping("/inquiries")
	public InquiryResponse createInquiry(@NotNull @RequestBody InquiryRequest reqInquiryPayload,
			@RequestHeader(RequestHeaderConsts.CLIENT_IP) String clientIpAddr) {
		if (reqInquiryPayload == null) {
			throw new ValidationException("inquiry request payload cannot be null. ", ErrorCode.ERR_CODE_INQUIRY_MISSING_REQUEST_PAYLOAD);
		}
		InquiryResponse response = new InquiryResponse();
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		InquiryEntity entity = mapToEntity(reqInquiryPayload.getInquiry());
		entity.setClientIpAddress(clientIpAddr);
		entity.setClientCountryOrRegion(getCountryOrRegionName(clientIpAddr));
		try {
			entity = inquiryRepository.save(entity);
			response.setStatus("SAVED");
		} catch (Exception ex) {
			logger.error("Failed to save InquiryEntity to db.", ex);
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

	private InquiryEntity mapToEntity(InquiryRequest.Inquiry inquiry) {
		InquiryEntity entity = new InquiryEntity();
		entity.setContactName(inquiry.getContactName());
		entity.setContactEmail(inquiry.getContactEmail());
		entity.setContactPhone(inquiry.getContactPhone());
		entity.setRefModel(inquiry.getRefModel());
		entity.setQuantity(inquiry.getQuantity());
		entity.setContent(inquiry.getContent());
		entity.setRefLink(inquiry.getRefLink());
		return entity;
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
		Context templContext = new Context();
		templContext.setVariable("inquiry", inquiry);
	    String htmlContent = templateEngine.process("inquiry", templContext);
	    String subject = "Inquiry for " + inquiry.getRefModel();
	    ReceiverEmailConf irEmailConf = appConfigProvider.getInquiryReceiverEmailConf();
		try {
			mailSenderService.sendMail(irEmailConf.getTo(), irEmailConf.getCc(), irEmailConf.getBcc(), subject, htmlContent, ContentType.TEXT_HTML);
			return true;
		} catch (MessagingException ex) {
			logger.error("Failed to send email of inquiry for {} ", inquiry.getRefModel());
			return false;
		}
	}

}
