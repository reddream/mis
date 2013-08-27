package org.models.operator;

import java.util.ArrayList;
import java.util.List;

import org.models.constants.ShowType;

public class OperatorRequest {

	private Integer id;
	private Integer roleId;
	private String operatorName;
	private String loginName;
	private String queryOrderBy;
	private Integer pageNo;
	private Integer showType=ShowType.Valid;
	private List<Integer> idlist=new ArrayList<Integer>();
	
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
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
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
