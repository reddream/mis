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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="t_vendor")
public class Vendor extends ActionResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3081606996739708234L;

	@Id
	@Column(name="f_vendor_id")	
	@GeneratedValue(strategy = GenerationType.TABLE,generator="vendor_gen")
    @TableGenerator(name = "vendor_gen",
        table="ts_generator",
        pkColumnName="f_generator_name",
        valueColumnName="f_value",
        pkColumnValue="t_vendor",
        initialValue=1000,
        allocationSize=1
    )
	private Integer id;
	
	
	@ManyToOne(cascade={CascadeType.REFRESH},optional=false)  
	@JoinColumn(name="f_area_id",insertable = false, updatable = false)
	private Area area;
	
	@ManyToOne(cascade={CascadeType.REFRESH},optional=false)  
	@JoinColumn(name="f_level_id",insertable = false, updatable = false)
	private Level level;
	
	@Column(name="f_vendor_name")
	private String name;
	
	@Column(name="f_parent_key")
	private String parentKey;
	
	@ManyToOne(cascade={CascadeType.REFRESH},optional=true)  
	@JoinColumn(name="f_parent_id",insertable = false, updatable = false)
	private Vendor parentVendor;
	
	@Column(name="f_parent_id")
	private Integer parentId;
	
	@ManyToOne(cascade={CascadeType.REFRESH},optional=false)  
	@JoinColumn(name="f_pay_condition_id",insertable = false, updatable = false)
	private PayCondition payCondition;
	
	@ManyToOne(cascade={CascadeType.REFRESH},optional=false)  
	@JoinColumn(name="f_operator_id",insertable = false, updatable = false)
	private Operator operator;
	
	@Column(name="f_pay_condition_id")
	private Integer payConditionId;
	
	@Column(name="f_created_time",updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdTime;

	@Column(name="f_updated_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar updatedTime;
	
	@Column(name="f_inactive")
	private Integer inactive;

	@Column(name="f_area_id")
	private Integer areaId;
	
	@Column(name="f_level_id")
	private Integer levelId;
	
	@Column(name="f_operator_id")
	private Integer operatorId;
	
	public Vendor(){
		this.createdTime=Calendar.getInstance();
		this.updatedTime=Calendar.getInstance();
		this.inactive=0;
	}
	
	
	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
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

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
	
	public Vendor getParentVendor() {
		return parentVendor;
	}

	public void setParentVendor(Vendor parentVendor) {
		this.parentVendor = parentVendor;
	}

	public PayCondition getPayCondition() {
		return payCondition;
	}

	public void setPayCondition(PayCondition payCondition) {
		this.payCondition = payCondition;
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

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
	public Integer getPayConditionId() {
		return payConditionId;
	}

	public void setPayConditionId(Integer payConditionId) {
		this.payConditionId = payConditionId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
}
