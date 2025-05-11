package com.exmek.core.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exmek.core.config.AppConfigProvider;
import com.exmek.core.consts.EndpointConsts;
import com.exmek.core.consts.RequestHeaderConsts;
import com.exmek.core.error.ErrorCode;
import com.exmek.core.error.ValidationException;
import com.exmek.core.gensearch.GeneralSearchItem;
import com.exmek.core.gensearch.GeneralSearcher;
import com.exmek.core.inquiry.InquiryProcessor;
import com.exmek.core.model.Company;
import com.exmek.core.model.News;
import com.exmek.core.resource.CompositeResourceManager;
import com.exmek.core.resource.ResourceInfo;
import com.exmek.core.resource.UserResourceManager;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(EndpointConsts.ENDPOINT_API_PREFIX)
public class GeneralRestController {

	public static final String QRY_PARAM_NAME_KEYWORD	= "keyword";

	@Autowired
	private AppConfigProvider appConfigProvider;

	@Autowired
	private UserResourceManager userResourceManager;

	@Autowired
	private CompositeResourceManager compositeResourceManager;

	@Autowired
	private InquiryProcessor inquiryProcessor;

	@Autowired
	private GeneralSearcher generalSearcher;

	@GetMapping("/company/exmek")
	public Company getExmekCompany() {
		return appConfigProvider.getExmekCompany();
	}
		
	@GetMapping("/news/{idOrTitle}")
	public News getNews(@NotNull @PathVariable("idOrTitle") String idOrTitle) {
		if (idOrTitle == null || idOrTitle.isBlank()) {
			return null;
		}
		return userResourceManager.getNewsByIdOrTitle(idOrTitle);
	}
	
	@GetMapping("/news")
	public List<News> getAllNews() {
		return userResourceManager.getAllNews();
	}

	@PostMapping("/inquiries")
	public InquiryResponse createInquiry(@NotNull @RequestBody InquiryRequest reqInquiryPayload,
			@RequestHeader(name = RequestHeaderConsts.CLIENT_IP, required = false) String headerClientIp) {
		
		return inquiryProcessor.processInquiry(reqInquiryPayload, headerClientIp);
	}
	
	@GetMapping("/tech-docs")
	public List<ResourceInfo> getTechDocInfos() {
		List<ResourceInfo> commonTechDocInfos = userResourceManager.getCommonTechDocInfos();
		List<ResourceInfo> allPerModelTechDocInfos = compositeResourceManager.getTechDocInfos();
		if (commonTechDocInfos == null) {
			return allPerModelTechDocInfos;
		} else if (allPerModelTechDocInfos == null) {
			return commonTechDocInfos;
		} else {
			return Stream.concat(commonTechDocInfos.stream(), allPerModelTechDocInfos.stream())
					.distinct()
					.collect(Collectors.toList());
		}
	}

	@GetMapping("/gensearch")
	public PageableListDataResponse<GeneralSearchItem> generalSearch(
			@RequestParam(value = QRY_PARAM_NAME_KEYWORD) String keyword,
			@RequestParam(value = BaseProductRestController.QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = BaseProductRestController.QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {
		
		if (pageNumber != null && pageSize == null || pageNumber == null && pageSize != null) {
			throw new ValidationException("Must have both 'pageNumber' and 'pageSize' parameters for pagination",
					ErrorCode.ERR_CODE_REQUIRE_BOTH_OR_NONE_PAGE_PARAMS);
		}
		PageableListDataResponse<GeneralSearchItem> response = new PageableListDataResponse<>();
		List<GeneralSearchItem> searchResult = generalSearcher.priorityProductSearch(keyword);
		if (ObjectUtils.isNotEmpty(searchResult)) {
			populateGeneralSearchResponse(response, searchResult, pageNumber, pageSize);
		} else {
			searchResult = generalSearcher.alternativeProductSearch(keyword);
			if (ObjectUtils.isNotEmpty(searchResult)) {
				populateGeneralSearchResponse(response, searchResult, pageNumber, pageSize);
			}
		}
		return response;
	}
	
	private void populateGeneralSearchResponse(PageableListDataResponse<GeneralSearchItem> response, 
			List<GeneralSearchItem> searchResult, Integer pageNumber, Integer pageSize) {

		if (pageNumber != null && pageSize != null) {
			response.setPageNumber(pageNumber);
			response.setPageSize(pageSize);
			if (ObjectUtils.isEmpty(searchResult)) {
				response.setTotalPages(0);
				response.setTotalElementsOfAllPages(0);
				response.setTotalElementsOfCurrPage(0);
				response.setData(new ArrayList<>());
			} else {
				int totalAll = searchResult.size();
				response.setTotalElementsOfAllPages(totalAll);
				response.setTotalPages((int) Math.ceil((double) totalAll / pageSize));
				List<GeneralSearchItem> currPageData = new ArrayList<>();
				int currPageStartInx = pageNumber * pageSize;
				int currPageExclusiveEndInx = currPageStartInx + pageSize;
				for (int i = currPageStartInx; i < currPageExclusiveEndInx && i < totalAll; i++) {
					currPageData.add(searchResult.get(i));
				}
				response.setTotalElementsOfCurrPage(currPageData.size());
				response.setData(currPageData);
			}
		} else {
			response.setData(searchResult);
			response.setTotalElementsOfAllPages(searchResult.size());
			response.setTotalElementsOfCurrPage(searchResult.size());
		}
	}
}
