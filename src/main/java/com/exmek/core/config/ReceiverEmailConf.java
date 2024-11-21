package com.exmek.core.config;

import lombok.Data;

@Data
public class ReceiverEmailConf {

	private String[] to;

	private String[] cc;

	private String[] bcc;
}
