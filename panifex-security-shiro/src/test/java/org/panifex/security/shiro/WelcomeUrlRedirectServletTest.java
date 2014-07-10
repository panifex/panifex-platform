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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.panifex.module.api.WebApplicationConstants;
import org.panifex.security.shiro.WelcomeUrlRedirectServlet;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for {@link WelcomeUrlRedirectServlet} class.
 */
public final class WelcomeUrlRedirectServletTest extends TestSupport {

    private WelcomeUrlRedirectServlet servlet;

    @Before
    public void setUp() {
        servlet = new WelcomeUrlRedirectServlet();
    }

    @Test
    public void testSetNullWelcomeUrl() {
        // set null welcome url
        servlet.setWelcomeUrl(null);

        assertDefault(servlet);
    }

    @Test
    public void testSetEmptyWelcomeUrl() {
        // set empty welcome url
        servlet.setWelcomeUrl(StringUtils.EMPTY);

        assertDefault(servlet);
    }

    @Test
    public void testSetValidWelcomeUrl() {
        String validUrl = "/valid";

        // set valid welcome url
        servlet.setWelcomeUrl(validUrl);

        assertEquals(validUrl, servlet.getWelcomeUrl());
    }

    @Test
    public void testRedirectOnHttpGet() throws Exception {
        HttpServletRequest requestMock = createMock(HttpServletRequest.class);
        HttpServletResponse responseMock = createMock(HttpServletResponse.class);

        // expect getting request url
        expect(requestMock.getRequestURI()).andReturn("/request");

        // expect sending redirect
        responseMock.sendRedirect(WebApplicationConstants.DEFAULT_WELCOME_URL);

        // perform test
        replayAll();
        servlet.doGet(requestMock, responseMock);
        verifyAll();
    }

    @Test
    public void testGetTheSameUrl() throws Exception {
        HttpServletRequest requestMock = createMock(HttpServletRequest.class);
        HttpServletResponse responseMock = createMock(HttpServletResponse.class);

        // expect getting request url
        expect(requestMock.getRequestURI()).andReturn(WebApplicationConstants.DEFAULT_WELCOME_URL);

        // expect returning HTTP 404 - Not found because welcome servlet is not defined
        responseMock.sendError(HttpServletResponse.SC_NOT_FOUND);

        // perform test
        replayAll();
        servlet.doGet(requestMock, responseMock);
        verifyAll();
    }

    private void assertDefault(WelcomeUrlRedirectServlet servlet) {
        assertEquals(WebApplicationConstants.DEFAULT_WELCOME_URL, servlet.getWelcomeUrl());
    }
}
