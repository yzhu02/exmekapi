package com.exmek.core.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class News {

	private String id;
	private String title;
	private String content;
	private String publishTime;
	private List<String> picturePaths;
  private List<String> videoURLs;
}
