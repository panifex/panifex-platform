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
package org.panifex.web.vaadin;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.util.PatternMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.pagelet.PageletMapping;
import org.panifex.module.vaadin.api.VaadinPagelet;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.ui.UI;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ModularUIProvider.class, VaadinPageletTracker.class})
public class ModularUIProviderTest extends TestSupport {

    // mocks
    private PatternMatcher patternMatcherMock = createMock(PatternMatcher.class);
    private VaadinPageletTracker pageletTrackerMock = createMock(VaadinPageletTracker.class);

    private ModularUIProvider provider = new ModularUIProvider(
            patternMatcherMock, pageletTrackerMock);

    @Before
    public void setUp() {
        resetAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithNullPatternMatcher() {
        provider = new ModularUIProvider(null, pageletTrackerMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithNullPageletTracker() {
        provider = new ModularUIProvider(patternMatcherMock, null);
    }

    /**
     * Asserts the {@link ModularUIProvider#getUIClass(com.vaadin.server.UIClassSelectionEvent)}
     * always returns the {@link PageletAwareUI} class.
     */
    @Test
    public void testGetUIClass() {
        UIClassSelectionEvent event = createMock(UIClassSelectionEvent.class);

        Class<?> uiClass = provider.getUIClass(event);

        assertEquals(PageletAwareUI.class, uiClass);
    }

    @Test
    public void testCreateUIInstance() throws Exception {
        // expect getting active pagelets
        List<VaadinPagelet> pagelets = new ArrayList<>();
        expect(pageletTrackerMock.getPagelets()).andReturn(pagelets);

        // expect getting active pagelet mappings
        List<PageletMapping> mappings = new ArrayList<>();
        expect(pageletTrackerMock.getPageletMappings()).andReturn(mappings);

        // expect constructing new UI instance
        PageletAwareUI ui = createMockAndExpectNew(PageletAwareUI.class,
                mappings, pagelets, patternMatcherMock);

        UICreateEvent event = createMock(UICreateEvent.class);

        UI uiInstance = null;

        replayAll();
        try {
            uiInstance = provider.createInstance(event);
        } finally {
            verifyAll();

            assertEquals(ui, uiInstance);
        }
    }
}
