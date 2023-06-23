package com.remainder.service.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ControllerUtils {
	
	private static ControllerUtils builder;
	
	static {
		builder = new ControllerUtils();
	}
	
	public static ControllerUtils build() {
		return builder;
	}

	public void secured(Map<String, Object> result, HttpServletRequest request) {
		boolean token_update = (boolean) request.getAttribute("token_update");
		if (token_update) {
			result.put("token", request.getAttribute("token_updated"));
		}	
	}

}