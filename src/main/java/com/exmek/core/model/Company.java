package com.exmek.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class Company extends AbstractModel.Namable {

	private String description;
	private String mission;
	private String phoneNumber;
	private String email;
	private String address;
	private String youtubeLink;
	private String facebookLink;
	private String linkedinLink;
	private String twitterLink;
	private String instagramLink;
	private String tiktokLink;
}
