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
package org.panifex.web.impl.menu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.menu.MenuItem;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.bind.Binder;
import org.zkoss.xel.VariableResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zkmax.zul.Nav;

/**
 * Unit tests for the {@link MenuNodeTemplate} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    MenuActionTemplate.class,
    MenuNodeTemplate.class})
public final class MenuNodeTemplateTest extends TestSupport {

    /**
     * The {@link MenuNodeTemplate} instance for unit testing.
     */
    private MenuNodeTemplate template;
    
    /**
     * The {@link org.zkoss.bind.Binder Binder} mocked instance.
     */
    private Binder binderMock = createMock(Binder.class);
    
    /**
     * Prepares an environment for performing unit tests.
     */
    @Before
    public void setUp() {
        // create a new MenuNodeTemplate instance
        template = new MenuNodeTemplate(binderMock);
    }
    

    /**
     * This test checks the method {@link MenuNodeTemplate#create(Component, Component, VariableResolver, Composer)}
     * which creates and binds a {@link org.zkoss.zkmax.zul.Nav Nav} component.
     */
    @Test
    public void createMenuNodeTest() throws Exception {
        // mocks
        Component parentMock = createMock(Component.class);
        Component insertBeforeMock = createMock(Component.class);
        VariableResolver resolverMock = createMock(VariableResolver.class);
        @SuppressWarnings("rawtypes")
        Composer composerMock = createMock(Composer.class);
        
        // expect create a new Nav
        Nav navMock = createMockAndExpectNew(Nav.class);
        MenuActionTemplate actionTemplateMock = createMockAndExpectNew(MenuActionTemplate.class, binderMock);
        MenuNodeTemplate nodeTemplateMock = createMockAndExpectNew(MenuNodeTemplate.class, binderMock);
        
        // expect binding properties
        binderMock.addPropertyLoadBindings(navMock, "label", AppMenuConstants.LABEL_PROPERTY, null, null, null, null, null);
        binderMock.addPropertyLoadBindings(navMock, "iconSclass", AppMenuConstants.ICON_S_CLASS_PROPERTY, null, null, null, null, null);
        binderMock.addPropertyLoadBindings(navMock, "disabled", AppMenuConstants.DISABLED_PROPERTY, null, null, null, null, null);
        binderMock.addPropertyLoadBindings(navMock, "visible", AppMenuConstants.VISIBLE_PROPERTY, null, null, null, null, null);
        
        // expect adding a children binding
        binderMock.addChildrenLoadBindings(navMock, MenuNodeTemplate.CHILDREN_BINDING, null, null, null, null, null);
        binderMock.setTemplate(navMock, "$CHILDREN$", AppMenuConstants.NODE_CHILDREN_CONDITION, null);
        expect(navMock.setTemplate(MenuItem.ACTION, actionTemplateMock)).andReturn(null);
        expect(navMock.setTemplate(MenuItem.NODE, nodeTemplateMock)).andReturn(null);
        
        // expect appending the created item to the parent
        navMock.setParent(parentMock);
        
        // perform test
        replayAll();
        template.create(parentMock, insertBeforeMock, resolverMock, composerMock);
        verifyAll();
    }
}
