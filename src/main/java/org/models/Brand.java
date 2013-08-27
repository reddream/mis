package org.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="t_brand")
public class Brand extends ActionResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5911853280551625175L;


	@Id
	@Column(name="f_brand_id")	
	@GeneratedValue(strategy = GenerationType.TABLE,generator="brand_gen")
    @TableGenerator(
    	name = "brand_gen",
        table="ts_generator",
        pkColumnName="f_generator_name",
        valueColumnName="f_value",
        pkColumnValue="t_brand",
        initialValue=1000,
        allocationSize=1
    )
	private Integer id;
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name="f_brand_id",insertable = false, updatable = false)
	private Set<Model> models;

	@Column(name="f_brand_name")
	private String name;

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
	
	public Set<Model> getModels() {
		return models;
	}

	public void setModels(Set<Model> models) {
		this.models = models;
	}
	
}
