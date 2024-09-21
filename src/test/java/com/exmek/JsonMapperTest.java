package com.exmek;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapperTest {

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		ObjectMapper m = new ObjectMapper();
		m.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		String s = "[{\"name\": \"Tcont\", \"x\": \"Speed(KRPM)\", \"y\": \"Torque(oz-in)\"}, {\"name\": \"Tpeak\", \"x\": \"Speed(KRPM)\", \"y\": \"Torque(Ncm)\"}]";
		
		Coordinate[] c = m.readValue(s, new TypeReference<Coordinate[]>() {});
		System.out.println(c);
	}

}

class Coordinate {
	private String name;
	private String x;
	private String y;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
}
