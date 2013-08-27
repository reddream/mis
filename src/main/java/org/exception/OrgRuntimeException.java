package org.exception;

public class OrgRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	public OrgRuntimeException(String message, Exception e) {
		super(message, e);
	}

	public OrgRuntimeException(String message) {
		super(message, null);
	}
	
	public OrgRuntimeException(Integer id,String message) {
		super(message, null);
		this.id=id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
}
