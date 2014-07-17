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
package org.panifex.module.api.i18n;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit tests for the {@link AbstractLocaleService} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(AbstractLocaleServiceImpl.class)
public final class AbstractLocaleServiceTest extends TestSupport {

    /**
     * This test constructs a new AbstractLocaleService and then it checks
     * if the resources are successfully resolved.
     */
    @Test
    public void getSupportedLocaleTest() {
        // mocks
        Locale localeMock = createMock(Locale.class);

        // perform test
        replayAll();
        AbstractLocaleService localeService = new AbstractLocaleServiceImpl(localeMock);
        Locale supportedLocale = localeService.supportedLocale();
        Set<URL> urls = localeService.locates();
        verifyAll();

        // the locales must be the same
        assertNotNull(supportedLocale);
        assertEquals(localeMock, supportedLocale);

        // the urls set must be empty because we hadn't associated any resource
        assertNotNull(urls);
        assertTrue(urls.isEmpty());
    }

    /**
     * This test constructs a new AbstractLocaleService and associates two resources
     * and then it checks if the resources are successfully binded.
     */
    @Ignore
    @Test
    public void bindResourcesTest() throws MalformedURLException {
        // variables
        String resource1 = getRandomChars(20);
        String resource2 = getRandomChars(20);
        Set<String> resources = new HashSet<>();
        resources.add(resource1);
        resources.add(resource2);

        // mocks
        Locale localeMock = createMock(Locale.class);
        BundleContext bundleContextMock = createMock(BundleContext.class);
        Bundle bundleMock = createMock(Bundle.class);
        URL url1Mock = new URL("http://url1");
        URL url2Mock = new URL("http://url2");

        // expect getting the bundle
        expect(bundleContextMock.getBundle()).andReturn(bundleMock).times(2);

        // expect getting the URLs of the resources
        expect(bundleMock.getResource(resource1)).andReturn(url1Mock);
        expect(bundleMock.getResource(resource2)).andReturn(url2Mock);

        // perform test
        replayAll();

        // construct the AbstractLocaleService
        AbstractLocaleService localeService =
                new AbstractLocaleServiceImpl(localeMock, resources);
        localeService.setBundleContext(bundleContextMock);

        Locale supportedLocale = localeService.supportedLocale();
        Set<URL> urls = localeService.locates();
        verifyAll();

        // the locales must be the same
        assertNotNull(supportedLocale);
        assertEquals(localeMock, supportedLocale);

        // the url collection must contain two urls
        assertNotNull(urls);
        assertEquals(2, urls.size());
        assertTrue(urls.contains(url1Mock));
        assertTrue(urls.contains(url2Mock));
    }

    /**
     * This test constructs a new AbstractLocaleService and adds a resource
     * and then it checks if the resource is successfully binded.
     * <p>
     * This test checks the functionality of {@link AbstractLocaleService#addResource(String)}
     * method.
     */
    @Test
    public void addResourceTest() throws MalformedURLException {
        // variables
        String resource = getRandomChars(20);

        // mocks
        Locale localeMock = createMock(Locale.class);
        BundleContext bundleContextMock = createMock(BundleContext.class);
        Bundle bundleMock = createMock(Bundle.class);
        URL urlMock = new URL("http://url1");

        // expect getting the bundle
        expect(bundleContextMock.getBundle()).andReturn(bundleMock);

        // expect getting the URLs of the resources
        expect(bundleMock.getResource(resource)).andReturn(urlMock);

        // perform test
        replayAll();

        // construct the AbstractLocaleService
        AbstractLocaleService localeService =
                new AbstractLocaleServiceImpl(localeMock);
        localeService.setBundleContext(bundleContextMock);

        // add resource
        localeService.addResource(resource);

        Locale supportedLocale = localeService.supportedLocale();
        Set<URL> urls = localeService.locates();
        verifyAll();

        // the locales must be the same
        assertNotNull(supportedLocale);
        assertEquals(localeMock, supportedLocale);

        // the url collection must contain a url
        assertNotNull(urls);
        assertEquals(1, urls.size());
        assertTrue(urls.contains(urlMock));
    }

    /**
     * Tries to construct an AbstractLocaleService with unknown Locale.
     * <p>
     * IllegaleArgumentException must be thrown because the Locale is mandatory.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorNullLocaleTest() {
        new AbstractLocaleServiceImpl(null);
    }

    /**
     * Tries to construct an AbstractLocaleService with the correct Locale but
     * with the unknown resources set.
     * <p>
     * IllegaleArgumentException must be thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructNullResourceSetTest() {
        // mocks
        Locale localeMock = createMock(Locale.class);

        // perform test
        new AbstractLocaleServiceImpl(localeMock, null);
    }
}
