package org.models.product;

import java.util.ArrayList;
import java.util.List;

import org.models.constants.ShowType;

public class ProductRequest {
	private String productName;
	private String queryOrderBy;
	private Integer osid;
	private Integer modelid;
	private Integer brandid;
	private Integer pageNo;
	private Integer vendorid;
	private String productCode;
	private String productFeatures;
	private Integer showType=ShowType.Valid;
	private List<Integer> idlist=new ArrayList<Integer>();
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getQueryOrderBy() {
		return queryOrderBy;
	}
	public void setQueryOrderBy(String queryOrderBy) {
		this.queryOrderBy = queryOrderBy;
	}
	public Integer getOsid() {
		return osid;
	}
	public void setOsid(Integer osid) {
		this.osid = osid;
	}
	public Integer getModelid() {
		return modelid;
	}
	public void setModelid(Integer modelid) {
		this.modelid = modelid;
	}
	public Integer getBrandid() {
		return brandid;
	}
	public void setBrandid(Integer brandid) {
		this.brandid = brandid;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getVendorid() {
		return vendorid;
	}
	public void setVendorid(Integer vendorid) {
		this.vendorid = vendorid;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductFeatures() {
		return productFeatures;
	}
	public void setProductFeatures(String productFeatures) {
		this.productFeatures = productFeatures;
	}
	public Integer getShowType() {
		return showType;
	}
	public void setShowType(Integer showType) {
		this.showType = showType;
	}
	public List<Integer> getIdlist() {
		return idlist;
	}
	public void setIdlist(List<Integer> idlist) {
		this.idlist = idlist;
	}
	public void addList(Integer ids[]){
		if(ids!=null){
			for(Integer i:ids){
				idlist.add(i);
			}
		}
	}
}
