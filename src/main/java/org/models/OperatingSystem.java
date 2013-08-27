package org.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="t_os")
public class OperatingSystem extends ActionResult {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2291771861593022465L;

	@Id
	@Column(name="f_os_id")	
	@GeneratedValue(strategy = GenerationType.TABLE,generator="os_gen")
    @TableGenerator(
    	name = "os_gen",
        table="ts_generator",
        pkColumnName="f_generator_name",
        valueColumnName="f_value",
        pkColumnValue="t_os",
        initialValue=1000,
        allocationSize=1
    )
	private Integer id;
	
	@Column(name="f_os_name")
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
}
