package org.models;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.*;

@Entity
@Table(name="t_product")
public class Product extends ActionResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1399540734004980452L;

	@Id
	@Column(name="f_product_id") 
	@GeneratedValue(strategy = GenerationType.TABLE,generator="product_gen")
    @TableGenerator(
    	name = "product_gen",
        table="ts_generator",
        pkColumnName="f_generator_name",
        valueColumnName="f_value",
        pkColumnValue="t_product",
        initialValue=1000,
        allocationSize=1
    )
	private Integer id;

	@Column(name="f_code") 	
	private String code;
	
	@Column(name="f_product_name")
	private String name;
		
	@ManyToOne(cascade={CascadeType.REFRESH},optional=false)  
	@JoinColumn(name="f_os_id",insertable = false, updatable = false)
	private OperatingSystem os;
	
	@ManyToOne(cascade={CascadeType.REFRESH},optional=false)  
	@JoinColumn(name="f_model_id",insertable = false, updatable = false)
	private Model model;
	
	@ManyToOne(cascade={CascadeType.REFRESH},optional=false)  
	@JoinColumn(name="f_vendor_id",insertable = false, updatable = false)
	private Vendor vendor;
	
	@ManyToOne(cascade={CascadeType.REFRESH},optional=false)  
	@JoinColumn(name="f_brand_id",insertable = false, updatable = false)
	private Brand brand;
	
	@ManyToOne(cascade={CascadeType.REFRESH},optional=false)  
	@JoinColumn(name="f_operator_id",insertable = false, updatable = false)
	private Operator operator;
	
	@Column(name="f_price")
	private Double price; 
 
	@Column(name="f_features")
	private String features;
	
	@Column(name="f_remark")
	private String remark;
	
	@Column(name="f_created_time",updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdTime;

	@Column(name="f_updated_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar updatedTime;
	
	@Column(name="f_deleted")
	private Integer deleted;
	
	@Column(name="f_os_id") 
	private Integer osId;
	
	@Column(name="f_model_id") 
	private Integer modelId;
	
	@Column(name="f_brand_id") 
	private Integer brandId;
	
	@Column(name="f_vendor_id")
	private Integer vendorId;
	
	@Column(name="f_operator_id")
	private Integer operatorId;
	
	public Product(){
		this.createdTime=Calendar.getInstance();
		this.updatedTime = Calendar.getInstance();
		this.deleted=0;
		this.name="";
		this.features="";
		this.remark="";
	}
	
	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getOsId() {
		return osId;
	}

	public void setOsId(Integer osId) {
		this.osId = osId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public OperatingSystem getOs() {
		return os;
	}

	public void setOs(OperatingSystem os) {
		this.os = os;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
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

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}	
}
