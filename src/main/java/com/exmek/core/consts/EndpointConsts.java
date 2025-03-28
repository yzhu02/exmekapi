package com.exmek.core.consts;

public class EndpointConsts {

	public static final String API			= "api";
	public static final String MOTORS		= "motors";
	public static final String GEARBOXES	= "gearboxes";
	public static final String BRAKES		= "brakes";
	
	public static final String ENDPOINT_API_PREFIX		= "/" + API;
	public static final String ENDPOINT_API_MOTORS		= ENDPOINT_API_PREFIX + "/" + MOTORS;
	public static final String ENDPOINT_API_GEARBOXES	= ENDPOINT_API_PREFIX + "/" + GEARBOXES;
	public static final String ENDPOINT_API_BRAKES		= ENDPOINT_API_PREFIX + "/" + BRAKES;
	
	public static final String ENDPOINT_ADMIN			= "/admin";
	
	private EndpointConsts() {
	}
}
