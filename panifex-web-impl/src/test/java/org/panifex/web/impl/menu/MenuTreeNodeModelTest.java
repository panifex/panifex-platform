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

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.menu.MenuAction;
import org.panifex.module.api.menu.MenuNode;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit tests for the {@link MenuTreeModel} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    MenuTreeModel.class,
    MenuTreeNodeComparator.class
})
public final class MenuTreeNodeModelTest extends TestSupport {

    /**
     * The {@link MenuTreeModel} instance to be unit tested.
     */
    private MenuTreeModel model = new MenuTreeModel();;
    
    /**
     * Prepares an environment for performing unit tests.
     */
    @Before
    public void setUp() throws Exception {
        resetAll();
    }
    
    /**
     * This test adds the root {@link org.panifex.module.api.menu.MenuAction MenuAction}
     * to the model in case the {@link org.panifex.module.api.menu.MenuAction#getParentId() MenuAction#getParentId()}
     * returns the empty string.
     * <p>
     * The {@link org.panifex.module.api.menu.MenuAction MenuAction} must 
     * be successfully added to the model.
     */
    @Test
    public void addRootMenuActionTest() {
        // mocks
        MenuAction menuActionMock = createMock(MenuAction.class);
        
        // expect MenuAction#getParentId() returns the empty String
        expect(menuActionMock.getParentId()).andReturn(StringUtils.EMPTY);
        
        // perform test
        replayAll();
        model.addItem(menuActionMock);
        verifyAll();
        
        // the action must be added in the model
        assertEquals(1, model.getRoot().getChildCount());
        assertEquals(menuActionMock, model.getRoot().getChildAt(0).getData());
    }
    
    /**
     * This test adds the root {@link org.panifex.module.api.menu.MenuAction MenuAction}
     * to the model in case the {@link org.panifex.module.api.menu.MenuAction#getParentId() MenuAction#getParentId()}
     * returns the empty string.
     * <p>
     * The {@link org.panifex.module.api.menu.MenuAction MenuAction} must 
     * be successfully added to the model.
     */
    @Test
    public void addRootMenuActionNullParentIdTest() {
        // mocks
        MenuAction menuActionMock = createMock(MenuAction.class);
        
        // expect MenuAction#getParentId() returns the empty String
        expect(menuActionMock.getParentId()).andReturn(null);
        
        // perform test
        replayAll();
        model.addItem(menuActionMock);
        verifyAll();
        
        // the action must be added in the model
        assertEquals(1, model.getRoot().getChildCount());
        assertEquals(menuActionMock, model.getRoot().getChildAt(0).getData());
    }
    
    /**
     * This test adds the root {@link org.panifex.module.api.menu.MenuNode MenuNode}
     * to the model in case the {@link org.panifex.module.api.menu.MenuNode#getParentId() MenuNode#getParentId()}
     * returns the empty string.
     * <p>
     * The {@link org.panifex.module.api.menu.MenuNode MenuNode} must 
     * be successfully added to the model.
     */
    @Test
    public void addRootMenuNodeTest() {
        // variables
        String id = getRandomChars(20);
        
        // mocks
        MenuNode menuNodeMock = createMock(MenuNode.class);
        
        // expect getting MenuNode#getParentId() which returns the empty String
        expect(menuNodeMock.getParentId()).andReturn(StringUtils.EMPTY);
        
        // expect getting MenuNode#getId() for searching for their not yet added children
        expect(menuNodeMock.getId()).andReturn(id);
        
        // perform test
        replayAll();
        model.addItem(menuNodeMock);
        verifyAll();
        
        // the node must be added in the model
        assertEquals(1, model.getRoot().getChildCount());
        assertEquals(menuNodeMock, model.getRoot().getChildAt(0).getData());
    }
    
    /**
     * This test adds the root {@link org.panifex.module.api.menu.MenuNode MenuNode} 
     * to the model in case the {@link org.panifex.module.api.menu.MenuNode#getParentId() MenuNode#getParentId()}
     * returns the empty string.
     * <p>
     * The {@link org.panifex.module.api.menu.MenuNode MenuNode} must 
     * be successfully added to the model.
     */
    @Test
    public void addRootMenuNodeNullParentIdTest() {
        // variables
        String id = getRandomChars(20);
        
        // mocks
        MenuNode menuNodeMock = createMock(MenuNode.class);
        
        // expect getting MenuNode#getParentId() which returns the empty String
        expect(menuNodeMock.getParentId()).andReturn(null);
        
        // expect getting MenuNode#getId() for searching for their not yet added children
        expect(menuNodeMock.getId()).andReturn(id);
        
        // perform test
        replayAll();
        model.addItem(menuNodeMock);
        verifyAll();
        
        // the node must be added in the model
        assertEquals(1, model.getRoot().getChildCount());
        assertEquals(menuNodeMock, model.getRoot().getChildAt(0).getData());
    }
    
    /**
     * This test tries to add the {@link org.panifex.module.api.menu.MenuAction MenuAction}
     * with the assigned {@link org.panifex.module.api.menu.MenuAction#getParentId() MenuAction#getParentId()}
     * of the {@link org.panifex.module.api.menu.MenuNode MenuNode}.
     * <p>
     * After adding the {@link org.panifex.module.api.menu.MenuAction MenuAction}, it must 
     * not added to the model.
     * <p>
     * But after adding the {@link org.panifex.module.api.menu.MenuNode MenuNode} it must be
     * the part of the model.
     */
    @Test
    public void addMenuActionAndThenItsParentNodeTest() {
        // variables
        String parentId = getRandomChars(20);
        
        // mocks
        MenuAction menuActionMock = createMock(MenuAction.class);
        MenuNode menuNodeMock = createMock(MenuNode.class);
        
        // expect the menuAction returns the menuNode's ID
        expect(menuActionMock.getParentId()).andReturn(parentId).anyTimes();
        
        // expect the menuNode returns the empty string - because it is in a root level
        expect(menuNodeMock.getParentId()).andReturn(StringUtils.EMPTY);
        
        // expect the menuNode returns the same ID
        expect(menuNodeMock.getId()).andReturn(parentId).anyTimes();
        
        // perform test
        replayAll();
        
        // add the menu action
        model.addItem(menuActionMock);
        
        // the action must not be added to the model
        assertEquals(0, model.getRoot().getChildCount());
        
        // add the menu node
        model.addItem(menuNodeMock);
        
        verifyAll();
        
        // the node must be added to the model
        assertEquals(1, model.getRoot().getChildCount());
        
        // the action must be child of the node
        assertEquals(1, model.getRoot().getChildAt(0).getChildCount());
        assertEquals(menuActionMock, model.getRoot().getChildAt(0).getChildAt(0).getData());
    }
    
    /**
     * This test tries to add the two {@link org.panifex.module.api.menu.MenuAction MenuAction}s
     * with the assigned {@link org.panifex.module.api.menu.MenuAction#getParentId() MenuAction#getParentId()}
     * of the {@link org.panifex.module.api.menu.MenuNode MenuNode}.
     * <p>
     * After adding the two {@link org.panifex.module.api.menu.MenuAction MenuAction}s, it must 
     * not added to the model.
     * <p>
     * But after adding the {@link org.panifex.module.api.menu.MenuNode MenuNode} it must be
     * the part of the model.
     */
    @Test
    public void addTwoMenuActionAndThenItsParentNodeTest() {
        // variables
        String parentId = getRandomChars(20);
        int actionPriority1 = 1;
        int actionPriority2 = 2;
        
        // mocks
        MenuAction menuAction1Mock = createMock(MenuAction.class);
        MenuAction menuAction2Mock = createMock(MenuAction.class);
        MenuNode menuNodeMock = createMock(MenuNode.class);
        
        // expect the menuActions returns the menuNode's ID
        expect(menuAction1Mock.getParentId()).andReturn(parentId).anyTimes();
        expect(menuAction2Mock.getParentId()).andReturn(parentId).anyTimes();
        
        // expect getting the menu action's priorities because the sorting
        // they must be sorted, because they are in the same hierarchy level
        expect(menuAction1Mock.getPriority()).andReturn(actionPriority1).anyTimes();
        expect(menuAction2Mock.getPriority()).andReturn(actionPriority2).anyTimes();
        
        // expect the menuNode returns the empty string - because it is in a root level
        expect(menuNodeMock.getParentId()).andReturn(StringUtils.EMPTY);
        
        // expect the menuNode returns the same ID
        expect(menuNodeMock.getId()).andReturn(parentId).anyTimes();
        
        // perform test
        replayAll();
        
        // add the menu actions to the model
        model.addItem(menuAction1Mock);
        model.addItem(menuAction2Mock);
        
        // the menu actions must not be added to the model
        assertEquals(0, model.getRoot().getChildCount());
        
        // add the menu node
        model.addItem(menuNodeMock);
        
        verifyAll();
        
        // the node must be added to the model
        assertEquals(1, model.getRoot().getChildCount());
        
        // the actions must be child of the node
        assertEquals(2, model.getRoot().getChildAt(0).getChildCount());
        assertEquals(menuAction1Mock, model.getRoot().getChildAt(0).getChildAt(0).getData());
        assertEquals(menuAction2Mock, model.getRoot().getChildAt(0).getChildAt(1).getData());
    }
    
    /**
     * This test adds the root {@link org.panifex.module.api.menu.MenuAction MenuAction}
     * to the model, and then it removes it.
     * <p>
     * The {@link org.panifex.module.api.menu.MenuAction MenuAction} must 
     * be successfully added to the model, and then successfully removed.
     */
    @Test
    public void addAndRemoveMenuActionTest() {
        // mocks
        MenuAction menuActionMock = createMock(MenuAction.class);
        
        // expect MenuAction#getParentId() returns the empty String
        expect(menuActionMock.getParentId()).andReturn(StringUtils.EMPTY).times(2);
        
        // perform test
        replayAll();
        
        // add item
        model.addItem(menuActionMock);
        
        // the action must be added in the model
        assertEquals(1, model.getRoot().getChildCount());
        assertEquals(menuActionMock, model.getRoot().getChildAt(0).getData());
        
        // remove item
        model.removeItem(menuActionMock);
        
        // the must be removed from the model
        assertEquals(0, model.getRoot().getChildCount());
        
        verifyAll();
    }
    
    /**
     *  This test add the two {@link org.panifex.module.api.menu.MenuNode MenuNode}
     *  to the root level, and then removes the added one.
     *  <p>
     *  The nodes must be successfully added, and then the one node must be
     *  removed from the model.
     */
    @Test
    public void addAndRemoveTwoMenuNodesTest() {
        // variables
        String menuNodeId1 = getRandomChars(20);
        String menuNodeId2 = getRandomChars(20);
        int nodePriority1 = 1;
        int nodePriority2 = 2;
        
        // ids must not be equal
        while (menuNodeId1.equals(menuNodeId2)) {
            menuNodeId2 = getRandomChars(20);
        }
        
        // mocks
        MenuNode menuNode1Mock = createMock(MenuNode.class);
        MenuNode menuNode2Mock = createMock(MenuNode.class);
        
        // expect getting MenuNode#getParentId() which returns the empty String and the null
        expect(menuNode1Mock.getParentId()).andReturn(StringUtils.EMPTY).times(2);
        expect(menuNode2Mock.getParentId()).andReturn(null);
        
        // expect getting MenuNode#getId() for searching for their not yet added children
        expect(menuNode1Mock.getId()).andReturn(menuNodeId1);
        expect(menuNode2Mock.getId()).andReturn(menuNodeId2);
        
        // expect getting the menu nodes' priorities because the sorting
        // they must be sorted, because they are in the same hierarchy level
        expect(menuNode1Mock.getPriority()).andReturn(nodePriority1).anyTimes();
        expect(menuNode2Mock.getPriority()).andReturn(nodePriority2).anyTimes();
        
        // perform test
        replayAll();
        
        // add the first node
        model.addItem(menuNode1Mock);
        
        // the node must be added in the model
        assertEquals(1, model.getRoot().getChildCount());
        assertEquals(menuNode1Mock, model.getRoot().getChildAt(0).getData());
        
        // add the second node
        model.addItem(menuNode2Mock);
        
        // the node must be added in the model
        assertEquals(2, model.getRoot().getChildCount());
        assertEquals(menuNode1Mock, model.getRoot().getChildAt(0).getData());
        assertEquals(menuNode2Mock, model.getRoot().getChildAt(1).getData());
        
        // remove the first node
        model.removeItem(menuNode1Mock);
        
        // the node must be removed from the model
        assertEquals(1, model.getRoot().getChildCount());
        assertEquals(menuNode2Mock, model.getRoot().getChildAt(0).getData());
        
        verifyAll();
    }
    
    /**
     * This test adds the {@link org.panifex.module.api.menu.MenuNode MenuNode}
     * with two child {@link org.panifex.module.api.menu.MenuAction MenuAction}.
     * <p>
     * After the adding, it removes the first {@link org.panifex.module.api.menu.MenuNode MenuNode},
     * and then adds the new {@link org.panifex.module.api.menu.MenuNode MenuNode} with the same ID.
     * <p>
     * The {@link org.panifex.module.api.menu.MenuAction MenuAction} must be assigned
     * as the children of the new {@link org.panifex.module.api.menu.MenuNode MenuNode}.
     */
    @Test
    public void addAndRemoveMenuNodeWithTwoMenuActionsTest() {
        // variables
        String parentId = getRandomChars(20);
        int actionPriority1 = 1;
        int actionPriority2 = 2;
        
        // mocks
        MenuAction menuAction1Mock = createMock(MenuAction.class);
        MenuAction menuAction2Mock = createMock(MenuAction.class);
        MenuNode menuNode1Mock = createMock(MenuNode.class);
        MenuNode menuNode2Mock = createMock(MenuNode.class);
        
        // expect getting the menu action's parent ids
        expect(menuAction1Mock.getParentId()).andReturn(parentId).anyTimes();
        expect(menuAction2Mock.getParentId()).andReturn(parentId).anyTimes();
        
        // expect getting the menu action's priorities because the sorting
        // they must be sorted, because they are in the same hierarchy level
        expect(menuAction1Mock.getPriority()).andReturn(actionPriority1).anyTimes();
        expect(menuAction2Mock.getPriority()).andReturn(actionPriority2).anyTimes();
        
        // expect getting the first menu node id and the parent id
        expect(menuNode1Mock.getParentId()).andReturn(StringUtils.EMPTY).anyTimes();
        expect(menuNode1Mock.getId()).andReturn(parentId).anyTimes();
        
        // expect getting the second menu node id and the parent id
        expect(menuNode2Mock.getParentId()).andReturn(null);
        expect(menuNode2Mock.getId()).andReturn(parentId).anyTimes();
        
        // perform test
        replayAll();
        
        // add the first menu action
        model.addItem(menuAction1Mock);
        
        // add the second menu action
        model.addItem(menuAction2Mock);
        
        // the actions must not be added to the model (because their parent has
        // not be added yet
        assertEquals(0, model.getRoot().getChildCount());
        
        // add the first menu node
        model.addItem(menuNode1Mock);
        
        // the node must be added to the model
        assertEquals(1, model.getRoot().getChildCount());
        assertEquals(menuNode1Mock, model.getRoot().getChildAt(0).getData());
        
        // the node must have the two children
        assertEquals(2, model.getRoot().getChildAt(0).getChildCount());
        
        // remove the first menu node
        model.removeItem(menuNode1Mock);
        
        // the model must be empty after removing the first menu node
        assertEquals(0, model.getRoot().getChildCount());
        
        // add the second menu node
        model.addItem(menuNode2Mock);
        
        // the node must be added to the model
        assertEquals(1, model.getRoot().getChildCount());
        assertEquals(menuNode2Mock, model.getRoot().getChildAt(0).getData());
        
        // the node must have the two children
        assertEquals(2, model.getRoot().getChildAt(0).getChildCount());
        
        verifyAll();
    }
    
    /**
     * This test adds the two {@link org.panifex.module.api.menu.MenuNode MenuNode}s
     * with two child {@link org.panifex.module.api.menu.MenuAction MenuAction}.
     * The one {@link org.panifex.module.api.menu.MenuNode MenuNode} is a parent of the
     * another one.
     * <p>
     * After the adding, it removes the second {@link org.panifex.module.api.menu.MenuNode MenuNode},
     * and then adds the new {@link org.panifex.module.api.menu.MenuNode MenuNode} with the same parent id.
     * <p>
     * The {@link org.panifex.module.api.menu.MenuAction MenuAction} must be assigned
     * as the children of the new {@link org.panifex.module.api.menu.MenuNode MenuNode}.
     */
    @Test
    public void addAndRemoveTwoMenuNodesWithTwoMenuActionsTest() {
        // variables
        String firstParentId = getRandomChars(20);
        String secondParentId = getRandomChars(20);
        int actionPriority1 = 1;
        int actionPriority2 = 2;
        
        // parent ids must not be the same
        while (firstParentId.equals(secondParentId)) {
            secondParentId = getRandomChars(20);
        }
        
        // mocks
        MenuAction menuAction1Mock = createMock(MenuAction.class);
        MenuAction menuAction2Mock = createMock(MenuAction.class);
        MenuNode firstMenuNodeMock = createMock(MenuNode.class);
        MenuNode secondMenuNodeMock = createMock(MenuNode.class);
        MenuNode thirdMenuNodeMock = createMock(MenuNode.class);
        
        // expect getting the menu action's parent ids
        expect(menuAction1Mock.getParentId()).andReturn(secondParentId).anyTimes();
        expect(menuAction2Mock.getParentId()).andReturn(secondParentId).anyTimes();

        // expect getting the menu action's priorities because the sorting
        // they must be sorted, because they are in the same hierarchy level
        expect(menuAction1Mock.getPriority()).andReturn(actionPriority1).anyTimes();
        expect(menuAction2Mock.getPriority()).andReturn(actionPriority2).anyTimes();
        
        // expect getting the first menu node id and the parent id
        expect(firstMenuNodeMock.getParentId()).andReturn(StringUtils.EMPTY).anyTimes();
        expect(firstMenuNodeMock.getId()).andReturn(firstParentId).anyTimes();
        
        // expect getting the second menu node id and the parent id
        expect(secondMenuNodeMock.getParentId()).andReturn(firstParentId).anyTimes();
        expect(secondMenuNodeMock.getId()).andReturn(secondParentId).anyTimes();
        
        // expect getting the third menu node id and the parent id
        // the ids must be the same as the second node's
        expect(thirdMenuNodeMock.getParentId()).andReturn(firstParentId).anyTimes();
        expect(thirdMenuNodeMock.getId()).andReturn(secondParentId).anyTimes();
        
        // perform test
        replayAll();
        
        // add the first menu action
        model.addItem(menuAction1Mock);
        
        // add the second menu action
        model.addItem(menuAction2Mock);
        
        // add the second menu node
        model.addItem(secondMenuNodeMock);
        
        // the actions must not be added to the model (because their parent has
        // not be added yet
        assertEquals(0, model.getRoot().getChildCount());
        
        // add the first menu node
        model.addItem(firstMenuNodeMock);
        
        // the first node must be added to the model
        assertEquals(1, model.getRoot().getChildCount());
        assertEquals(firstMenuNodeMock, model.getRoot().getChildAt(0).getData());
        
        // the first node must have the one child: the second node
        assertEquals(1, model.getRoot().getChildAt(0).getChildCount());
        assertEquals(secondMenuNodeMock, model.getRoot().getChildAt(0).getChildAt(0).getData());
        
        // the second node must have two children: actions
        assertEquals(2, model.getRoot().getChildAt(0).getChildAt(0).getChildCount());
        assertEquals(menuAction1Mock, model.getRoot().getChildAt(0).getChildAt(0).getChildAt(0).getData());
        assertEquals(menuAction2Mock, model.getRoot().getChildAt(0).getChildAt(0).getChildAt(1).getData());
        
        // remove the second menu node
        model.removeItem(secondMenuNodeMock);
        
        // the first node must stay in the model
        assertEquals(1, model.getRoot().getChildCount());
        assertEquals(firstMenuNodeMock, model.getRoot().getChildAt(0).getData());
        
        // the first node must not have any children
        assertEquals(0, model.getRoot().getChildAt(0).getChildCount());
        
        // add the third menu node
        model.addItem(thirdMenuNodeMock);
        
        // the first node must have the one child: the third node
        assertEquals(1, model.getRoot().getChildAt(0).getChildCount());
        assertEquals(thirdMenuNodeMock, model.getRoot().getChildAt(0).getChildAt(0).getData());
        
        // the third node must have two children: actions
        assertEquals(2, model.getRoot().getChildAt(0).getChildAt(0).getChildCount());
        assertEquals(menuAction1Mock, model.getRoot().getChildAt(0).getChildAt(0).getChildAt(0).getData());
        assertEquals(menuAction2Mock, model.getRoot().getChildAt(0).getChildAt(0).getChildAt(1).getData());
        
        verifyAll();
    }
}
