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
@Table(name="tr_data_function")
public class DataFunction extends ActionResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5484155407691642875L;

	@Id
	@Column(name="f_data_function_id") 	
	@GeneratedValue(strategy = GenerationType.TABLE,generator="datafunction_gen")
    @TableGenerator(
    	name = "datafunction_gen",
        table="ts_generator",
        pkColumnName="f_generator_name",
        valueColumnName="f_value",
        pkColumnValue="tr_data_function",
        initialValue=1000,
        allocationSize=1
    )
	private Integer id;
	
	@ManyToOne(cascade={CascadeType.REFRESH},optional=false,fetch =FetchType.LAZY)  
	@JoinColumn(name="f_role_id",insertable = false, updatable = false)
	private Role role;
	
	@Column(name="f_table_code")
	private String tableCode;
	
	@Column(name="f_actions")
	private String actions;
	
	@Column(name="f_id")
	private String tableid;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}	
}
