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
package org.panifex.module.api.security;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * Accesses the currently accessible {@code Subject} for the calling code depending on runtime environment.
 *
 * @see org.apache.shiro.SecurityUtils
 *
 * @since 1.0
 */
public interface SecurityUtilService {

    /**
     * Returns the currently accessible {@code Subject} available to the calling code depending on
     * runtime environment.
     * <p/>
     * This method is provided as a way of obtaining a {@code Subject} without having to resort to
     * implementation-specific methods.  It also allows the Shiro team to change the underlying implementation of
     * this method in the future depending on requirements/updates without affecting your code that uses it.
     *
     * @return the currently accessible {@code Subject} accessible to the calling code.
     * @throws IllegalStateException if no {@link Subject Subject} instance or
     *                               {@link SecurityManager SecurityManager} instance is available with which to obtain
     *                               a {@code Subject}, which which is considered an invalid application configuration
     *                               - a Subject should <em>always</em> be available to the caller.
     */
    Subject getSubject();
}
