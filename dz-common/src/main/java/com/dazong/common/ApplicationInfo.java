package com.dazong.common;

import org.springframework.beans.factory.annotation.Value;

/**
 * 系统信息
 * 
 * @author luobinwen
 *
 */
public class ApplicationInfo {

	private int systemCode = 0;

	public int getSystemCode() {
		return systemCode;
	}

	@Value("${system_code:0}")
	public void setApplicationInfo(Integer systemCode) {
		INFO.systemCode = systemCode;
	}

	private static final ApplicationInfo INFO = new ApplicationInfo();

	public static ApplicationInfo instance() {
		return INFO;
	}

}
