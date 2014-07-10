/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2013  Mario Krizmanic
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 ******************************************************************************/
package org.panifex.module.api.accounts;

import org.panifex.module.api.Entity;

/**
 * The user's role.
 * <p>
 * The role contains a group of {@link Permission permissions} which allows the user
 * to perform some actions.
 * 
 * @since 1.0
 */
public interface Role extends Entity {

    /**
     * The constant which can be used for binding the name property to the GUI components.
     */
    public static final String NAME_PROP = "name";
    
    /**
     * The constant which can be used for binding the description property to the GUI components.
     */
    public static final String DESCRIPTION_PROP = "description";
    
    /**
     * Returns the role's name. 
     * <p>
     * It must be unique.
     * 
     * @return the role's name
     * 
     * @since 1.0
     */
    String getName();
    
    /**
     * Returns the role's description.
     * 
     * @return the role's description
     * 
     * @since 1.0
     */
    String getDescription();
}
