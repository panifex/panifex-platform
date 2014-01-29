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

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.menu.MenuAction;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.bind.Binder;
import org.zkoss.bind.impl.BindEvaluatorXUtil;
import org.zkoss.bind.sys.BindEvaluatorX;
import org.zkoss.xel.VariableResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Composer;

/**
 * Unit tests for the {@link MenuActionTemplate} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    BindEvaluatorXUtil.class,
    MenuActionTemplate.class,
    MenuNavitem.class})
public final class MenuActionTemplateTest extends TestSupport {

    /**
     * The {@link MenuActionTemplate} instance for unit testing.
     */
    private MenuActionTemplate template;
    
    /**
     * The {@link org.zkoss.bind.Binder Binder} mocked instance.
     */
    private Binder binderMock = createMock(Binder.class);
    
    /**
     * Prepares an environment for performing unit tests.
     */
    @Before
    public void setUp() {
        // create a MenuActionTemplate instance
        template = new MenuActionTemplate(binderMock);
        
        // mock static classes
        mockStatic(BindEvaluatorXUtil.class);
    }
    
    /**
     * This test checks the method {@link MenuActionTemplate#create(Component, Component, VariableResolver, Composer)} 
     * which creates and binds a {@link MenuNavitem} component.
     */
    @Test
    public void createMenuActionTest() throws Exception {
        // variables
        Map<String, String[]> onClickArgs =  new HashMap<>();
        onClickArgs.put(MenuAction.ID, AppMenuConstants.ITEM_BIND_ARGS);
        Map<String, Object> parsedOnClickArgs = new HashMap<>();

        // mocks
        Component parentMock = createMock(Component.class);
        Component insertBeforeMock = createMock(Component.class);
        VariableResolver resolverMock = createMock(VariableResolver.class);
        @SuppressWarnings("rawtypes")
        Composer composerMock = createMock(Composer.class);
        BindEvaluatorX bindEvaluatorMock = createMock(BindEvaluatorX.class);
        
        // expect create a new MenuNavitem
        MenuNavitem navitemMock = createMockAndExpectNew(MenuNavitem.class);
        
        // expect binding properties
        binderMock.addPropertyLoadBindings(navitemMock, MenuNavitem.BOOKMARK_PROPERTY, AppMenuConstants.BOOKMARK_PROPERTY, null, null, null, null, null);
        binderMock.addPropertyLoadBindings(navitemMock, "label", AppMenuConstants.LABEL_PROPERTY, null, null, null, null, null);
        binderMock.addPropertyLoadBindings(navitemMock, "iconSclass", AppMenuConstants.ICON_S_CLASS_PROPERTY, null, null, null, null, null);
        binderMock.addPropertyLoadBindings(navitemMock, "disabled", AppMenuConstants.DISABLED_PROPERTY, null, null, null, null, null);
        binderMock.addPropertyLoadBindings(navitemMock, "visible", AppMenuConstants.VISIBLE_PROPERTY, null, null, null, null, null);
        
        // expect getting the bind evaluator
        expect(binderMock.getEvaluatorX()).andReturn(bindEvaluatorMock);
        
        // expect parsing the variables
        expect(BindEvaluatorXUtil.parseArgs(bindEvaluatorMock, onClickArgs)).andReturn(parsedOnClickArgs);
        
        // expect binding command
        binderMock.addCommandBinding(navitemMock, Events.ON_CLICK, 
            MenuActionTemplate.ON_MENU_ACTION_CLICK, parsedOnClickArgs);
        
        // expect appending the created item to the parent
        navitemMock.setParent(parentMock);
        
        // perform test
        replayAll();
        template.create(parentMock, insertBeforeMock, resolverMock, composerMock);
        verifyAll();
    }
}
