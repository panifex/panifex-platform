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
package org.panifex.module.api.pagelet;

/**
 * Creates all necessary components for a given page in response to user's
 * request.<p/>
 *
 * @since 1.0
 */
public interface Pagelet<Request> {

    /**
     * Returns the (application-unique) name assigned to this <code>Pagelet</code>.
     * All pageleta configured for a single application must have a unique name.
     *
     * @return the (application-unique) name assigned to this <code>Pagelet</code>.
     *
     * @since 1.0
     */
    String getName();

    void service(Request request) throws Exception;
}
