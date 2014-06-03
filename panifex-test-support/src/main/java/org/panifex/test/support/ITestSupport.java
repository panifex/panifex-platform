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
package org.panifex.test.support;

import javax.inject.Inject;

import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.web.service.spi.ServletListener;
import org.ops4j.pax.web.service.spi.WebListener;
import org.osgi.framework.BundleContext;

@ExamReactorStrategy(PerMethod.class)
public abstract class ITestSupport {

    @Inject
    protected BundleContext bundleContext;

    protected ServletListener servletListener;

    protected WebListener webListener;

    protected void initServletListener() {
        initServletListener(null);
    }

    protected void initServletListener(String servletName) {
        if (servletName == null)
            servletListener = new ServletListenerImpl();
        else
            servletListener = new ServletListenerImpl(servletName);
        bundleContext.registerService(ServletListener.class, servletListener,
                null);
    }

    protected void waitForServletListener() {
        new WaitCondition("servlet startup") {
            @Override
            protected boolean isFulfilled() {
                return ((ServletListenerImpl) servletListener).gotEvent();
            }
        }.waitForCondition();
    }

    protected void initWebListener() {
        webListener = new WebListenerImpl();
        bundleContext.registerService(WebListener.class, webListener, null);
    }

    protected void waitForWebListener() {
        new WaitCondition("webapp startup") {
            @Override
            protected boolean isFulfilled() {
                return ((WebListenerImpl) webListener).gotEvent();
            }
        }.waitForCondition();
    }
}
