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

public abstract class EntityImpl implements Entity {
    
    private long id;
    private int optlockVersion;
    
    @Override
    public Long getId() {
        return id;
    }
    
    public int getOptlockVersion() {
        return optlockVersion;
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
        EntityImpl other = (EntityImpl) obj;
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
