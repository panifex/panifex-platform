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
package org.panifex.web.shiro.env;

import javax.servlet.ServletContext;

import org.apache.shiro.util.LifecycleUtils;
import org.apache.shiro.web.env.EnvironmentLoader;
import org.apache.shiro.web.env.MutableWebEnvironment;
import org.apache.shiro.web.env.WebEnvironment;

public class ModularEnvironmentLoader extends EnvironmentLoader {

    private MutableWebEnvironment environment;

    public void setEnvironment(MutableWebEnvironment environment) {
        this.environment = environment;
    }

    @Override
    protected WebEnvironment createEnvironment(ServletContext sc) {

        if (environment == null) {
            throw new IllegalStateException();
        }

        environment.setServletContext(sc);

        customizeEnvironment(environment);

        LifecycleUtils.init(environment);

        return environment;
    }
}
