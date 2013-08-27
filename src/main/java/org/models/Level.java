package org.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="t_level")
public class Level extends ActionResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = -139532254700600889L;

	@Id
	@Column(name="f_level_id")	
	@GeneratedValue(strategy = GenerationType.TABLE,generator="level_gen")
    @TableGenerator(
    	name = "level_gen",
        table="ts_generator",
        pkColumnName="f_generator_name",
        valueColumnName="f_value",
        pkColumnValue="t_level",
        initialValue=1000,
        allocationSize=1
    )
	private Integer id;
	
	@Column(name="f_level_name")
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
