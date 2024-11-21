package com.exmek.core.config;

import java.util.Map;

import com.exmek.commons.net.Protocol;

import lombok.Data;

@Data
public class SmtpConf {

	private String host;

	private Integer port;

	private Protocol protocol;

	private String user;

	private String password;

	private Map<String, String> properties;
}
