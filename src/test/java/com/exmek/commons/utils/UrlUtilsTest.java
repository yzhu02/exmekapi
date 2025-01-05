package com.exmek.commons.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlUtilsTest {

	@Test
	void testConcatURL() {
		Assertions.assertNull(UrlUtils.concatURL((String[]) null));
		Assertions.assertNull(UrlUtils.concatURL((String) null));
		Assertions.assertEquals("", UrlUtils.concatURL());

		Assertions.assertEquals("base/path/resource", UrlUtils.concatURL(new String[] {"base", "/path", "resource"}));
		Assertions.assertEquals("base/path/resource", UrlUtils.concatURL(new String[] {"base", "path", "resource"}));
		Assertions.assertEquals("/base/path/resource", UrlUtils.concatURL(new String[] {"/base", "/path", "/resource"}));
		Assertions.assertEquals("/base/path/resource/", UrlUtils.concatURL(new String[] {"/base", "/path", "/resource/"}));
		Assertions.assertEquals("/base/path/resource/", UrlUtils.concatURL(new String[] {"/base/", "/path", "/resource/"}));
		Assertions.assertEquals("/base/path/resource/", UrlUtils.concatURL(new String[] {"/base/", "/path/", "/resource/"}));
		Assertions.assertEquals("/base/path/resource", UrlUtils.concatURL(new String[] {"/base", "path", "resource"}));
		Assertions.assertEquals("/base/path/resource", UrlUtils.concatURL(new String[] {"/", "base", "path", "resource"}));
		
		Assertions.assertEquals("/images/**", UrlUtils.concatURL(new String[] {"/images", "**"}));
		Assertions.assertEquals("/images/**", UrlUtils.concatURL(new String[] {"/images", "/**"}));
		Assertions.assertEquals("/images/**", UrlUtils.concatURL(new String[] {"/", "images", "**"}));
		Assertions.assertEquals("/materials/**/techdoc", UrlUtils.concatURL(new String[] {"/materials", "**", "techdoc"}));
		Assertions.assertEquals("classpath*:/static/images/motor/mechanical", UrlUtils.concatURL(new String[] {"classpath*:", "static", "images", "motor", "mechanical"}));
		Assertions.assertEquals("classpath:/newsrepo/", UrlUtils.concatURL(new String[] {"classpath:", "newsrepo", "/"}));
		

		Assertions.assertEquals("base/path/resource", UrlUtils.concatURL("base", "/path", "resource"));
		Assertions.assertEquals("base/path/resource", UrlUtils.concatURL("base", "path", "resource"));
		Assertions.assertEquals("/base/path/resource", UrlUtils.concatURL("/base", "/path", "/resource"));
		Assertions.assertEquals("/base/path/resource/", UrlUtils.concatURL("/base", "/path", "/resource/"));
		Assertions.assertEquals("/base/path/resource/", UrlUtils.concatURL("/base/", "/path", "/resource/"));
		Assertions.assertEquals("/base/path/resource/", UrlUtils.concatURL("/base/", "/path/", "/resource/"));
		Assertions.assertEquals("/base/path/resource", UrlUtils.concatURL("/base", "path", "resource"));

		Assertions.assertEquals("/base/path/resource", UrlUtils.concatURL("/", "base", "path", "resource"));
		
		Assertions.assertEquals("/images/**", UrlUtils.concatURL("/images", "**"));
		Assertions.assertEquals("/images/**", UrlUtils.concatURL("/images", "/**"));
		Assertions.assertEquals("/images/**", UrlUtils.concatURL("/", "images", "**"));
		Assertions.assertEquals("/materials/**/techdoc", UrlUtils.concatURL("/materials", "**", "techdoc"));		
		Assertions.assertEquals("classpath*:/static/images/motor/mechanical", UrlUtils.concatURL("classpath*:", "static", "images", "motor", "mechanical"));
		Assertions.assertEquals("classpath:/newsrepo/", UrlUtils.concatURL("classpath:", "newsrepo", "/"));
	}

}
