package com.exmek.core.resource;

public abstract class AbstractResourceManager implements ResourceManager {

	// Example:
	// EF045AS100.jpg
	// EF045AS100[0].jpg
	static final String RESOURCE_FILENAME_REGEX 		= "([\\w-]+)(\\[(\\d+)\\])?";
	
	// Example:
	// MPC023-[Implication For Name].jpg
	// MPC023-[Implication For Name][0].jpg
	static final String INDEXED_RESOURCE_FILENAME_REGEX	= "([\\w-]+)(\\[([\\w\\s]+)\\])?(\\[(\\d+)\\])?";

}
