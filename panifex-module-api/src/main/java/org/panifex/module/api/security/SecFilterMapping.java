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
package org.panifex.module.api.security;

/**
 * The {@link SecFilter} mapping that associates the filter to the specified URL pattern
 * in the security context.
 * <p>
 * The mapping matches URLs using the following rules:<br>
 * <ul>
 * <li>? matches one character</li>
 * <li>* matches zero or more characters</li>
 * <li>** matches zero or more 'directories' in a path</li>
 * </ul>
 * <p>
 * Some examples:<br/>
 * <ul>
 * <li><code>com/t?st.jsp</code> - matches <code>com/test.jsp</code> but also
 * <code>com/tast.jsp</code> or <code>com/txst.jsp</code></li>
 * <li><code>com/*.jsp</code> - matches all <code>.jsp</code> files in the
 * <code>com</code> directory</li>
 * <li><code>com/&#42;&#42;/test.jsp</code> - matches all <code>test.jsp</code>
 * files underneath the <code>com</code> path</li>
 * <li><code>org/apache/shiro/&#42;&#42;/*.jsp</code> - matches all <code>.jsp</code>
 * files underneath the <code>org/apache/shiro</code> path</li>
 * <li><code>org/&#42;&#42;/servlet/bla.jsp</code> - matches
 * <code>org/apache/shiro/servlet/bla.jsp</code> but also
 * <code>org/apache/shiro/testing/servlet/bla.jsp</code> and
 * <code>org/servlet/bla.jsp</code></li>
 * </ul>
 *
 * @since 1.0
 */
public interface SecFilterMapping {

    /**
     * Returns the URL pattern of mapped {@link SecFilter}.
     *
     * @since 1.0
     */
    String getUrl();

    /**
     * Returns the mapped {@link SecFilter}'s name.
     *
     * @since 1.0
     */
    String getFilterName();
}
