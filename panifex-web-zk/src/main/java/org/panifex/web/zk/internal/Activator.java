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
package org.panifex.web.zk.internal;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.Servlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;

public class Activator implements BundleActivator {

    private ServiceRegistration<Servlet> dhtmlLayoutServlet;
    private ServiceRegistration<Servlet> dhtmlUpdateServlet;

    @Override
    public void start(BundleContext context) throws Exception {
        registerDHtmlLayoutServlet(context);
        registerDHtmlUpdateServlet(context);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        unregister(dhtmlLayoutServlet);
        unregister(dhtmlUpdateServlet);
    }

    private void registerDHtmlLayoutServlet(BundleContext context) {
        Dictionary<String, String> dhtmlLayoutServletProps = new Hashtable<>();
        dhtmlLayoutServletProps.put("init.update-uri", "/zkau");
        dhtmlLayoutServletProps.put("load-on-startup", "1");
        dhtmlLayoutServletProps.put("urlPatterns", "/zk/*");
        dhtmlLayoutServlet = context.
                registerService(Servlet.class, new DHtmlLayoutServlet(), dhtmlLayoutServletProps);
    }

    private void registerDHtmlUpdateServlet(BundleContext context) {
        Dictionary<String, String> dhtmlUpdateServletProps = new Hashtable<>();
        dhtmlUpdateServletProps.put("urlPatterns", "/zkau/*");
        dhtmlUpdateServlet = context.
                registerService(Servlet.class, new DHtmlUpdateServlet(), dhtmlUpdateServletProps);
    }

    private void unregister(ServiceRegistration<?> serviceRegistration) {
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }
    }
}
