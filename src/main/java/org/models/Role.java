package org.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="ts_role")
public class Role extends ActionResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8821570186546172630L;
	
	@Id
	@Column(name="f_role_id")	
	@GeneratedValue(strategy = GenerationType.TABLE,generator="role_gen")
    @TableGenerator(
    	name = "role_gen",
        table="ts_generator",
        pkColumnName="f_generator_name",
        valueColumnName="f_value",
        pkColumnValue="ts_role",
        initialValue=1000,
        allocationSize=1
    )
	private Integer id;
	
	@Column(name="f_code")
	private String code;

	@Column(name="f_role_name")
	private String name;
//	
	@ManyToMany(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY) 
	@JoinTable(name = "tr_operator_role", 
	joinColumns = { @JoinColumn(name = "f_role_id",insertable = false, updatable = false) }, 
	inverseJoinColumns = { @JoinColumn(name = "f_operator_id",insertable = false, updatable = false) })
	private Set<Operator> operators = new HashSet<Operator> ();

	
	@OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name="f_role_id",insertable = false, updatable = false)
	private Set<Privilege> privileges = new HashSet<Privilege>();
		
	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	public Set<Operator> getOperators() {
		return operators;
	}

	public void setOperators(Set<Operator> operators) {
		this.operators = operators;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
