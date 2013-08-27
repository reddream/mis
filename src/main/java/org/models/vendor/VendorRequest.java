package org.models.vendor;

import java.util.ArrayList;
import java.util.List;

import org.models.constants.ShowType;

public class VendorRequest {
	private String vendorName;
	private String queryOrderBy;
	private Integer areaid;
	private Integer levelid;
	private Integer payConditionid;
	private Integer pageNo;
	private Integer showType=ShowType.Valid;
	private List<Integer> idlist=new ArrayList<Integer>();
	
	public Integer getAreaid() {
		return areaid;
	}

	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}

	public Integer getLevelid() {
		return levelid;
	}

	public void setLevelid(Integer levelid) {
		this.levelid = levelid;
	}

	public Integer getPayConditionid() {
		return payConditionid;
	}

	public void setPayConditionid(Integer payConditionid) {
		this.payConditionid = payConditionid;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getQueryOrderBy() {
		return queryOrderBy;
	}

	public void setQueryOrderBy(String queryOrderBy) {
		this.queryOrderBy = queryOrderBy;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
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
