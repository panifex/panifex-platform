/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2015  Mario Krizmanic
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
package org.panifex.module.api.persistence;

/**
 * An entity which can be persisted or shown in the Web application.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Entity_class">Entity class</a>
 *
 * @since 1.0
 */
public interface Entity {

    /**
     * Return the entity's ID.
     *
     * @return the entity's ID
     */
    Long getId();

    /**
     * Returns the version of {@link Entity}. The version is used to ensure integrity
     * when performing the merge operation and for optimistic concurrency control.
     *
     * @return the version of {@link Entity}
     *
     * @since 1.0
     */
    int getOptlockVersion();
}
