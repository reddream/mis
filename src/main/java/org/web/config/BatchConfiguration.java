package org.web.config;

import java.util.List;

public class BatchConfiguration extends BaseConfiguration {
	
	private List<String> keywords;

	private String[] productColumnCodes;
	
	private String[] productColumnNames;
	
	private String[] vendorColumnCodes;
	
	private String[] vendorColumnNames;
	
	private String productSize;
	
	private String vendorSize;
	
	public String getProductSize() {
		return productSize;
	}

	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}

	public String getVendorSize() {
		return vendorSize;
	}

	public void setVendorSize(String vendorSize) {
		this.vendorSize = vendorSize;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public String[] getVendorColumnCodes() {
		return vendorColumnCodes;
	}

	public void setVendorColumnCodes(String[] vendorColumnCodes) {
		this.vendorColumnCodes = vendorColumnCodes;
	}

	public String[] getVendorColumnNames() {
		return vendorColumnNames;
	}

	public void setVendorColumnNames(String[] vendorColumnNames) {
		this.vendorColumnNames = vendorColumnNames;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public String[] getProductColumnCodes() {
		return productColumnCodes;
	}

	public void setProductColumnCodes(String[] productColumnCodes) {
		this.productColumnCodes = productColumnCodes;
	}

	public String[] getProductColumnNames() {
		return productColumnNames;
	}

	public void setProductColumnNames(String[] productColumnNames) {
		this.productColumnNames = productColumnNames;
	}
	
}
