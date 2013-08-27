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
@Table(name="t_model")
public class Model extends ActionResult implements Item {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6062988245415640035L;

	@Id
	@Column(name="f_model_id") 	
	@GeneratedValue(strategy = GenerationType.TABLE,generator="model_gen")
    @TableGenerator(
    	name = "model_gen",
        table="ts_generator",
        pkColumnName="f_generator_name",
        valueColumnName="f_value",
        pkColumnValue="t_model",
        initialValue=1000,
        allocationSize=1
    )
	private Integer id;
	
	@ManyToOne(cascade={CascadeType.REFRESH},optional=false,fetch =FetchType.LAZY)  
	@JoinColumn(name="f_brand_id",insertable = false, updatable = false)
	private Brand brand;
	
	@Column(name="f_brand_id") 
	private Integer brandId;
		
	@Column(name="f_model_name") 
	private String name;

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
