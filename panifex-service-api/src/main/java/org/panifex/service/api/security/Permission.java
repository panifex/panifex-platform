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
package org.panifex.service.api.security;

import org.panifex.service.api.Entity;

/**
 * A permission represents the ability to perform an action or access a resource.
 * 
 * @see <a href="https://shiro.apache.org/static/current/apidocs/org/apache/shiro/authz/Permission.html">Permission</a>
 *
 * @since 1.0
 */
public interface Permission extends Entity {

    /**
     * Returns the permission's name, the easily readable free text.
     * <p>
     * It must be unique.
     * 
     * @return the permission's name
     * 
     * @since 1.0
     */
    String getName();

    /**
     * Returns the permission's wildcard expression.
     * 
     * @return wildcard the <a href="http://shiro.apache.org/permissions.html">wildcard permission expression</a>
     * 
     * @since 1.0
     */
    String getWildcardExpression();
    
    /**
     * Returns the permission's description.
     * 
     * @return the permission's description
     * 
     * @since 1.0
     */
    String getDescription(); 

}
