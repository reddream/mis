package org.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="t_operator")
public class Operator extends ActionResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2750497084553396313L;

	@Transient
	private Role currentRole;

	@Transient
	private String actionUrls;
	
	@Transient
	private List<Integer> roleids;

	public void setRoleids(List<Integer> roleids) {
		this.roleids = roleids;
	}

	public List<Integer> getRoleids() {
		if(roleids==null)
			roleids=new ArrayList<Integer>();
		for(Role r:roles){
			roleids.add(r.getId());
		}
		return roleids;
	}

	public Operator(){
		this.inactive = 0;
	}
	
	public Operator(Integer id){
		this.id = id;
		this.inactive = 0;
	}
		
	@Id
	@Column(name="f_operator_id") 
	@GeneratedValue(strategy = GenerationType.TABLE,generator="operator_gen")
    @TableGenerator(
    	name = "operator_gen",
        table="ts_generator",
        pkColumnName="f_generator_name",
        valueColumnName="f_value",
        pkColumnValue="t_operator",
        initialValue=1000,
        allocationSize=1
    )
	private Integer id;
	
	@Column(name="f_name") 	
	private String name;
	
	@Column(name="f_login") 	
	private String login;
	
	@Column(name="f_password") 	
	private String password;
	
	@Column(name="f_created_time",updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdTime;

	@Column(name="f_updated_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar updatedTime;
	
	@Column(name="f_inactive") 	
	private Integer inactive;
	
	@ManyToMany(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY) 
	@JoinTable(name = "tr_operator_role", 
	joinColumns = { @JoinColumn(name = "f_operator_id",insertable = false, updatable = false) },
	inverseJoinColumns = { @JoinColumn(name = "f_role_id",insertable = false, updatable = false) })
	private Set<Role> roles = new HashSet<Role>();
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public Calendar getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Calendar updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getInactive() {
		return inactive;
	}

	public void setInactive(Integer inactive) {
		this.inactive = inactive;
	}

	public Role getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(Role currentRole) {
		this.currentRole = currentRole;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getActionUrls() {
		return actionUrls;
	}

	public void setActionUrls(String actionUrls) {
		this.actionUrls = actionUrls;
	}
	
	
	public boolean hasFunction(String actionUrl){
		String arr[] = actionUrl.split("/");
		String page = arr[arr.length-1];
		if(page.equals("left") || page.equals("right"))
			actionUrl = actionUrl.replace("/"+page,"/main");
		return actionUrls.contains(String.format(";%s;",actionUrl));
	}
	
}
