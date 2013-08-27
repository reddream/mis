package org.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="tr_privilege")
public class Privilege extends ActionResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4236791610577747368L;

	@Id
	@Column(name="f_privilege_id") 	
	@GeneratedValue(strategy = GenerationType.TABLE,generator="privilege_gen")
    @TableGenerator(
    	name = "privilege_gen",
        table="ts_generator",
        pkColumnName="f_generator_name",
        valueColumnName="f_value",
        pkColumnValue="tr_privilege",
        initialValue=1000,
        allocationSize=1
    )
	private Integer privilegeID;
	
	@ManyToOne(cascade={CascadeType.REFRESH},optional=false,fetch =FetchType.LAZY)  
	@JoinColumn(name="f_role_id",insertable = false, updatable = false)
	private Role role;
	
	@ManyToOne(cascade={CascadeType.REFRESH},optional=false,fetch =FetchType.LAZY)  
	@JoinColumn(name="f_function_id",insertable = false, updatable = false)
	private Function function;
	
	@Column(name="f_role_id")
	private Integer roleId;
	
	@Column(name="f_function_id")
	private Integer functionId;
	
	@Column(name="f_readonly")
	private Integer readonly;

	public Integer getReadonly() {
		return readonly;
	}

	public void setReadonly(Integer readonly) {
		this.readonly = readonly;
	}

	public Integer getPrivilegeID() {
		return privilegeID;
	}

	public void setPrivilegeID(Integer privilegeID) {
		this.privilegeID = privilegeID;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
	}
	
}
