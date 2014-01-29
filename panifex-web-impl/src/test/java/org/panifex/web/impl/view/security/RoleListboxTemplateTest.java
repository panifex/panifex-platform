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
package org.panifex.web.impl.view.security;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.bind.Binder;
import org.zkoss.xel.VariableResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

/**
 * Unit tests for the {@link RoleListboxTemplate} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RoleListboxTemplate.class)
public final class RoleListboxTemplateTest extends TestSupport {

    private RoleListboxTemplate template;

    // mocks
    private Binder binderMock = createMock(Binder.class);
    
    /**
     * Prepares an environment for unit testing.
     */
    @Before
    public void setUp() {
        // create a template instance
        template = new RoleListboxTemplate(binderMock);
        
        resetAll();
    }
    
    /**
     * This test checks the method {@link RoleListboxTemplate#create(Component, Component, VariableResolver, Composer)} 
     * which creates and binds a {@link Listcell} component.
     * <p>
     * The insertBefore is not null, so the created listitem must be appended before it.
     */
    @Test
    public void createListitemInsertBeforeTest() throws Exception {
        // mocks
        Component parentMock = createMock(Component.class);
        Component insertBeforeMock = createMock(Component.class);
        VariableResolver resolverMock = createMock(VariableResolver.class);
        @SuppressWarnings("rawtypes")
        Composer composerMock = createMock(Composer.class);
        
        // expect creating a new Listitem
        Listitem listitemMock = createMockAndExpectNew(Listitem.class);
        
        // expect creating a new Listcell which shows name
        Listcell nameCellMock = createMockAndExpectNew(Listcell.class);
        expect(listitemMock.appendChild(nameCellMock)).andReturn(true);
        binderMock.addPropertyLoadBindings(nameCellMock, "label", RoleListboxTemplate.NAME_ATTR, null, null, null, null, null);
        
        // except creating a new Listcell which shows description
        Listcell descCellMock = createMockAndExpectNew(Listcell.class);
        expect(listitemMock.appendChild(descCellMock)).andReturn(true);
        binderMock.addPropertyLoadBindings(descCellMock, "label", RoleListboxTemplate.DESCRIPTION_ATTR, null, null, null, null, null);
        
        // expect appending to the parent before insertBeforeMock
        expect(parentMock.insertBefore(listitemMock, insertBeforeMock)).andReturn(true);
        
        // perform test
        replayAll();
        template.create(parentMock, insertBeforeMock, resolverMock, composerMock);
        verifyAll();
    }
    
    /**
     * This test checks the method {@link RoleListboxTemplate#create(Component, Component, VariableResolver, Composer)} 
     * which creates and binds a {@link Listcell} component.
     * <p>
     * The insertBefore is null, so the created listitem must be only appended to the parent component.
     */
    @Test
    public void createListitemNotInsertBeforeTest() throws Exception {
        // mocks
        Component parentMock = createMock(Component.class);
        VariableResolver resolverMock = createMock(VariableResolver.class);
        @SuppressWarnings("rawtypes")
        Composer composerMock = createMock(Composer.class);
        
        // expect creating a new Listitem
        Listitem listitemMock = createMockAndExpectNew(Listitem.class);
        
        // expect creating a new Listcell which shows name
        Listcell nameCellMock = createMockAndExpectNew(Listcell.class);
        expect(listitemMock.appendChild(nameCellMock)).andReturn(true);
        binderMock.addPropertyLoadBindings(nameCellMock, "label", RoleListboxTemplate.NAME_ATTR, null, null, null, null, null);
        
        // except creating a new Listcell which shows description
        Listcell descCellMock = createMockAndExpectNew(Listcell.class);
        expect(listitemMock.appendChild(descCellMock)).andReturn(true);
        binderMock.addPropertyLoadBindings(descCellMock, "label", RoleListboxTemplate.DESCRIPTION_ATTR, null, null, null, null, null);
        
        // expect appending to the parent
        expect(parentMock.appendChild(listitemMock)).andReturn(true);
        
        // perform test
        replayAll();
        template.create(parentMock, null, resolverMock, composerMock);
        verifyAll();
    }
    
    /**
     * This test checks {@link RoleListboxTemplate#getParameters()} method.
     * <p>
     * It must return an empty hashmap.
     */
    @Test
    public void getParametersTest() {
        // perform test
        replayAll();
        Map<String, Object> params = template.getParameters();
        verifyAll();
        
        // the map must be empty
        assertNotNull(params);
        assertTrue(params.isEmpty());
    }
}
