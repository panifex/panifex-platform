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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.panifex.module.api.persistence.EntityImpl;

/**
 * The user's role.
 * <p>
 * The role contains a group of {@link Permission permissions} which allows the user
 * to perform some actions.
 * 
 * @since 1.0
 */
public final class RoleImpl extends EntityImpl implements Role {

    private String name;
    private String description;
    
    /**
     * Constructs a new {@link Role} instance for persisted
     * roles.
     * 
     * @param id the {@link org.panifex.module.api.persistence.Entity Entity}'s ID
     * @param optlockVersion the {@link org.panifex.module.api.persistence.Entity Entity}'s version
     * @param name the role name
     * @param description the role description
     * 
     * @since 1.0
     */
    public RoleImpl(
            Long id,
            int optlockVersion,
            String name,
            String description) {
        super(id, optlockVersion);
        this.name = name;
        this.description = description;
    }
    
    /**
     * Returns the role's name. 
     * <p>
     * It must be unique.
     * 
     * @return the role's name
     * 
     * @since 1.0
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the role's description.
     * 
     * @return the role's description
     * 
     * @since 1.0
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 7).
                appendSuper(super.hashCode()).
                append(name).
                append(description).
                toHashCode();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        RoleImpl other = (RoleImpl) obj;
        return new EqualsBuilder().
                appendSuper(super.equals(obj)).
                append(name, other.name).
                append(description, other.description).
                isEquals();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).
                appendSuper(super.toString()).
                append("name", name).
                append("description", description).
                toString();
    }
    
}
