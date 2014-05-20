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
package org.panifex.web.impl;

import java.util.Hashtable;

import javax.servlet.ServletException;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.ops4j.pax.web.service.WebContainer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.NamespaceException;
import org.panifex.web.impl.servlet.ZkLayoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.web.servlet.dsp.InterpreterServlet;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.HttpSessionListener;

@Bean(id = WebContainerListener.ID)
@ReferenceListener
public class WebContainerListener {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public final static String ID = "org.panifex.web.impl.PaxWebServiceListener";

    private BundleContext bundleContext;

    private ZkLayoutService zkLayoutService;
    private ServiceRegistration<ZkLayoutService> zkLayoutServiceRegistration;
    
    
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }
    
    public void setZkLayoutService(ZkLayoutService zkLayoutService) {
        this.zkLayoutService = zkLayoutService;
    }

    public void bind(WebContainer container) throws Exception {
        // configure zkLoader servlet
        log.debug("Configure ZkLoader servlet");
        Hashtable<String, String> loaderParams = new Hashtable<>();
        loaderParams.put("servlet-name", "zkLoader");
        loaderParams.put("update-uri", "/zkau"); // URI mapped to auEngine
        String loaderMapping[] = {"/zk/*"}; // mapping of UI files

        // configure auEngine servlet
        log.debug("Configure auEngine servlet");
        DHtmlUpdateServlet auEngine = new DHtmlUpdateServlet();
        Hashtable<String, String> auEngineParams = new Hashtable<>();
        auEngineParams.put("servlet-name", "auEngine");
        String engineMapping[] = {"/zkau/*"}; // same URI as the parameter
                                              // "update-uri" of zkLoader
        // configure dsp servlet
        log.debug("Configure dsp servlet");
        InterpreterServlet dspServlet = new InterpreterServlet();
        Hashtable<String, String> dspParams = new Hashtable<>();
        dspParams.put("class-resource", "true");
        String dspMapping[] = {"*.dsp"}; // mapping of DSP files

        // get the http context (zk servlets should be registered with the same
        // http context)
        HttpContext ctx = container.createDefaultHttpContext();
        // register zk session listener
        container.registerEventListener(new HttpSessionListener(), ctx);

        try {
            // register zk servlets
            container.registerServlet(zkLayoutService, loaderMapping, loaderParams, ctx);
            container.registerServlet(auEngine, engineMapping, auEngineParams, ctx);
            container.registerServlet(dspServlet, dspMapping, dspParams, ctx);
            log.debug("Zk servlets have been registered");

            // register zk layout service
            zkLayoutServiceRegistration =
                    bundleContext.registerService(ZkLayoutService.class, zkLayoutService, new Hashtable<String, String>());
            log.debug("Zk layout service has been registered");
            
            // register resources
            container.registerResources("/", "/", ctx);
            container.registerResources("/css", "/css", ctx);
            container.registerResources("/img", "/img", ctx);
            container.registerResources("/js", "/js", ctx);
        } catch (ServletException e) {
            log.error("Unable to register zk servlets", e);
        } catch (NamespaceException e) {
            log.error("Unable to register resources", e);
        }
    }

    public void unbind(WebContainer container) {
        // unregister layout service
        if (zkLayoutServiceRegistration != null) {
            zkLayoutServiceRegistration.unregister();
            log.debug("Zk layout service has been unregistered");
        }
    }
}
