package org.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="t_area")
public class Area extends ActionResult{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4736753063025128698L;

	@Id
	@Column(name="f_area_id")	
	@GeneratedValue(strategy = GenerationType.TABLE,generator="area_gen")
    @TableGenerator(
    	name = "area_gen",
        table="ts_generator",
        pkColumnName="f_generator_name",
        valueColumnName="f_value",
        pkColumnValue="t_area",
        initialValue=1000,
        allocationSize=1
    )
	private Integer id;

	@Column(name="f_code")
	private String code;
	
	@Column(name="f_name")
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
}
