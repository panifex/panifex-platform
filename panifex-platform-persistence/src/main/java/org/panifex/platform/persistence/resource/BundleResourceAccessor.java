package org.panifex.platform.persistence.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import org.osgi.framework.BundleContext;

import liquibase.resource.ResourceAccessor;

public class BundleResourceAccessor implements ResourceAccessor {

    private BundleContext context;

    public BundleResourceAccessor(BundleContext context) {
        this.context = context;
    }

    @Override
    public InputStream getResourceAsStream(String file) throws IOException {
        URL resource = context.getBundle().getResource(file);
        return resource.openStream();
    }

    @Override
    public Enumeration<URL> getResources(String packageName) throws IOException {
        return context.getBundle().getResources(packageName);
    }

    @Override
    public ClassLoader toClassLoader() {
        return null;
    }

}
