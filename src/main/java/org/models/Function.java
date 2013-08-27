package org.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="ts_function")
public class Function extends ActionResult {

	@Id
	@Column(name="f_function_id") 
	@GeneratedValue(strategy = GenerationType.TABLE,generator="function_gen")
    @TableGenerator(
    	name = "function_gen",
        table="ts_generator",
        pkColumnName="f_generator_name",
        valueColumnName="f_value",
        pkColumnValue="ts_function",
        initialValue=1000,
        allocationSize=1
    )
	private Integer id;

	@Column(name="f_action_url") 	
	private String actionURL;

	public String getActionURL() {
		return actionURL;
	}

	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
