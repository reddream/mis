package org.web.config;

public class PasswordConfiguration extends BaseConfiguration {

	private String defaultPassword;
	private Integer minilength;

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

	public Integer getMinilength() {
		return minilength;
	}

	public void setMinilength(Integer minilength) {
		this.minilength = minilength;
	}

}
