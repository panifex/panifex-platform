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
package org.panifex.service.api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An abstract implementation of the {@link Entity} interface.
 * <p>
 * It contains common fields for all entities in the web application's
 * scope. 
 * 
 * @since 1.0
 */
public abstract class EntityImpl implements Entity {
    
    /**
     * The entity's ID.
     */
    private long id;
    
    /**
     * The entity's optimistic-locking version.
     */
    private int optlockVersion;
    
    /**
     * Constructs a new {@link EntityImpl} which represents
     * a new entity which has not been persisted yet.
     * 
     * @since 1.0
     */
    protected EntityImpl() {
    }
    
    /**
     * Constructs a new {@link EntityImpl} for persisted
     * entities with the already defined IDs and versions.
     * 
     * @param id the entity's ID
     * @param optlockVersion the entity's optimistic-locking version
     * 
     * @since 1.0
     */
    protected EntityImpl(
            Long id,
            int optlockVersion) {
        this.id = id;
        this.optlockVersion = optlockVersion;
    }
    
    /**
     * Returns the entity's ID.
     * 
     * @return the entity's ID
     * 
     * @since 1.0
     */
    @Override
    public final Long getId() {
        return id;
    }
    
    /**
     * Returns the version of {@link Entity}. The version is used to ensure integrity 
     * when performing the merge operation and for optimistic concurrency control.
     * 
     * @return the version of {@link Entity}
     * 
     * @since 1.0
     */
    public final int getOptlockVersion() {
        return optlockVersion;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 7).
                append(id).
                append(optlockVersion).
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
        EntityImpl other = (EntityImpl) obj;
        return new EqualsBuilder().
                append(id, other.id).
                append(optlockVersion, other.optlockVersion).
                isEquals();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", id).
                append("optlockVersion", optlockVersion).
                toString();
    }
}
