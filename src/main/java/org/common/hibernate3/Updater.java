package org.common.hibernate3;

import java.util.HashSet;
import java.util.Set;

/**
 * </ul>.
 *
 * @param <T> the generic type
 * @author reddream
 */
public class Updater<T> {
	
	/**
	 * Instantiates a new updater.
	 *
	 * @param bean the bean
	 */
	public Updater(T bean) {
		this.bean = bean;
	}

	/**
	 * Instantiates a new updater.
	 *
	 * @param bean the bean
	 * @param mode the mode
	 */
	public Updater(T bean, UpdateMode mode) {
		this.bean = bean;
		this.mode = mode;
	}

	/**
	 * Sets the update mode.
	 *
	 * @param mode the mode
	 * @return the updater
	 */
	public Updater<T> setUpdateMode(UpdateMode mode) {
		this.mode = mode;
		return this;
	}

	/**
	 * Include.
	 *
	 * @param property the property
	 * @return the updater
	 */
	public Updater<T> include(String property) {
		includeProperties.add(property);
		return this;
	}

	/**
	 * Exclude.
	 *
	 * @param property the property
	 * @return the updater
	 */
	public Updater<T> exclude(String property) {
		excludeProperties.add(property);
		return this;
	}

	/**
	 * Checks if is update.
	 *
	 * @param name the name
	 * @param value the value
	 * @return true, if is update
	 */
	public boolean isUpdate(String name, Object value) {
		if (this.mode == UpdateMode.MAX) {
			return !excludeProperties.contains(name);
		} else if (this.mode == UpdateMode.MIN) {
			return includeProperties.contains(name);
		} else if (this.mode == UpdateMode.MIDDLE) {
			if (value != null) {
				return !excludeProperties.contains(name);
			} else {
				return includeProperties.contains(name);
			}
		} else {
			// never reach
		}
		return true;
	}

	/** The bean. */
	private T bean;

	/** The include properties. */
	private Set<String> includeProperties = new HashSet<String>();

	/** The exclude properties. */
	private Set<String> excludeProperties = new HashSet<String>();

	/** The mode. */
	private UpdateMode mode = UpdateMode.MIDDLE;

	// private static final Logger log = LoggerFactory.getLogger(Updater.class);

	/**
	 * The Enum UpdateMode.
	 */
	public static enum UpdateMode {
		
		/** The max. */
		MAX, 
 /** The min. */
 MIN, 
 /** The middle. */
 MIDDLE
	}

	/**
	 * Gets the bean.
	 *
	 * @return the bean
	 */
	public T getBean() {
		return bean;
	}

	/**
	 * Gets the exclude properties.
	 *
	 * @return the exclude properties
	 */
	public Set<String> getExcludeProperties() {
		return excludeProperties;
	}

	/**
	 * Gets the include properties.
	 *
	 * @return the include properties
	 */
	public Set<String> getIncludeProperties() {
		return includeProperties;
	}
}
