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
package org.panifex.platform.web.impl.security;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.shiro.web.servlet.ShiroFilter;

/**
 * Primary security filter for web application. SecurityFilter inherits ShiroFilter in order to be
 * used in blueprint environment.
 * 
 */
@Bean(id = SecurityFilter.ID)
public class SecurityFilter extends ShiroFilter {

    public final static String ID = "org.panifex.platform.web.impl.security.SecurityFilter";
}
