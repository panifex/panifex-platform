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
import org.apache.shiro.web.env.WebEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A ModularEnvironmentLoader extends an {@link EnvironmentLoader} which is responsible
 * for loading a web application's Shiro {@link org.apache.shiro.web.env.WebEnvironment
 * WebEnvironment}.
 * <p>
 * It loads {@link ModularWebEnvironment} which supports dynamic registering and
 * unregistering filters and their mapping.
 */
public class ModularEnvironmentLoader extends EnvironmentLoader {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * A ModularWebEnvironment to be registered to the servlet context.
     */
    private ModularWebEnvironment environment;

    /**
     * Sets a ModularWebEnvironment to be registered to the servlet context. It is
     * set by the OSGi container.
     *
     * @param environment the ModularWebEnvironment to be registered
     */
    public void setEnvironment(ModularWebEnvironment environment) {
        this.environment = environment;
    }

    /**
     * Registers the ModularWebEnvironment to the specified servlet context.
     *
     * @param sc current servlet context
     * @throws IllegalStateException
     *      if an existing WebEnvironment has already been initialized and associated with
     *      the specified {@code ServletContext} or if ModularWebEnvironment is not
     *      prepared to be associated
     */
    @Override
    protected WebEnvironment createEnvironment(ServletContext sc) {
        if (environment == null) {
            String errMsg = "ModularWebEnvironment must be initialized before setting it "
                    + "to servlet context";
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }

        environment.setServletContext(sc);

        customizeEnvironment(environment);

        LifecycleUtils.init(environment);

        return environment;
    }
}
