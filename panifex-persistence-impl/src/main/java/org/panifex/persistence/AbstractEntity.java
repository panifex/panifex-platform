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
package org.panifex.persistence;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.panifex.service.api.Entity;

/**
 * An abstract base {@link org.panifex.service.api.Entity} class for implement
 * JPA entities.
 *
 */
@MappedSuperclass
public abstract class AbstractEntity implements Entity {

    private Long id;
    private int optlockVersion;
    
    /**
     * {@inheritDoc}
     */
    @Id
    @Column(name = "id", nullable = false)
    @Override
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the entity id.
     * 
     * @param id the entity id
     */
    protected void setId(Long id) {
        this.id = id;
    }
    
    /**
     * {@inheritDoc}
     */
    @Column(name = "optlock_version")
    @Version
    public int getOptlockVersion() {
        return optlockVersion;
    }
    
    /**
     * Sets the optimistic lock version.
     * 
     * @param optlockVersion the optimistic lock version
     */
    protected void setOptlockVersion(int optlockVersion) {
        this.optlockVersion = optlockVersion;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 7).
                append(id).
                append(optlockVersion).
                toHashCode();
    }

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
        AbstractEntity other = (AbstractEntity) obj;
        return new EqualsBuilder().
                append(id, other.id).
                append(optlockVersion, other.optlockVersion).
                isEquals();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", id).
                append("optlockVersion", optlockVersion).
                toString();
    }
}
