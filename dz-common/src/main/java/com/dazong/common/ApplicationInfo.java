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

	@Value("${system_code}")
	public void setApplicationInfo(Integer systemCode) {
		info.systemCode = systemCode;
	}

	private static final ApplicationInfo info = new ApplicationInfo();

	public static ApplicationInfo instance() {
		return info;
	}

}
