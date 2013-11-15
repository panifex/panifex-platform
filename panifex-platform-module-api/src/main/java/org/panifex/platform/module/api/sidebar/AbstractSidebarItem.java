package org.panifex.platform.module.api.sidebar;

/**
 * This is an abstract sidebar item which can be node or item. The sidebar items are used
 * to define dynamic sidebar menu.
 *
 * @since 1.0
 */
public interface AbstractSidebarItem {

	/**
	 * Returns the label
	 * 
	 * @return the label (never null)
	 * @since 1.0
	 */
	String getLabel();
	
	/**
	 * Returns the icon font
	 * 
	 * @return the icon font
	 * @since 1.0
	 */
	String getIconSclass();
	
	/**
	 * A priority of item. The items will be ordered by priority in sidebar menu.
	 *  
	 * @return the priority of item 
	 * @since 1.0
	 */
	int priority();
}
