package com.exmek.core.rest;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DownloadTrackingResponse {

	private String status;
}
