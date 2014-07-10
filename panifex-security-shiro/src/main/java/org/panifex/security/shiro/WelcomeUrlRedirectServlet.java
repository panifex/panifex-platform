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
package org.panifex.security.shiro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.panifex.module.api.WebApplicationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet which redirects a user to welcome url.<p>
 *
 * It is used because {@link SecurityFilter} should be associated
 * at least one servlet, so it is mapped to this servlet which simply
 * redirects users to the another welcome url.
 */
public class WelcomeUrlRedirectServlet extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * The welcome url to used to redirect a user.
     */
    private String welcomeUrl = WebApplicationConstants.DEFAULT_WELCOME_URL;

    /**
     * Users is only redirected in case of Http Get method.
     */
    @Override
    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {

        if (!request.getRequestURI().equals(welcomeUrl)) {
            // redirect to welcome url
            log.debug("Redirect to welcome url {}", welcomeUrl);
            response.sendRedirect(welcomeUrl);
        } else {
            // return Http 404 - Not found because welcome servlet is not defined
            log.debug("Returns Http 404 - Not found because welcome servlet {} is not defined", welcomeUrl);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Returns the welcome URL which contains base content.
     *
     * @return the welcome URL which contains base content.
     */
    public String getWelcomeUrl() {
        return welcomeUrl;
    }

    /**
     * Sets the welcome URL which contains base content.
     *
     * @param welcomeUrl the welcome URL which contains base content
     */
    public void setWelcomeUrl(String welcomeUrl) {
        log.debug("Set new welcome url {}", welcomeUrl);
        if ((welcomeUrl == null) || welcomeUrl.isEmpty()) {
            welcomeUrl = WebApplicationConstants.DEFAULT_WELCOME_URL;
        }
        this.welcomeUrl = welcomeUrl;
    }
}
