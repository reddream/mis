package org.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="t_pay_condition")
public class PayCondition extends ActionResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7899567931270332747L;

	@Id
	@Column(name="f_pay_condition_id")	
	@GeneratedValue(strategy = GenerationType.TABLE,generator="paycondition_gen")
    @TableGenerator(
    	name = "paycondition_gen",
        table="ts_generator",
        pkColumnName="f_generator_name",
        valueColumnName="f_value",
        pkColumnValue="t_pay_condition",
        initialValue=1000,
        allocationSize=1
    )
	private Integer id;
	
	@Column(name="f_pay_condition_name")
	private String name;
	
	@Column(name="f_remark")
	private String remark;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
