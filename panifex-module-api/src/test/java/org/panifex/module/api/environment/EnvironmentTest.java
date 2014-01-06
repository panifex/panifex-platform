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
package org.panifex.module.api.environment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.Events;

/**
 * Unit tests for the {@link Environment} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    Environment.class,
    Events.class,
    Executions.class})
public final class EnvironmentTest extends TestSupport {

    /**
     * Prepares an environment for performing unit tests.
     */
    @Before
    public void setUp() {
        // mock static classes
        mockStatic(Events.class);
        mockStatic(Executions.class);
    }
    
    /**
     * This test tries to get the current bookmark.
     * <p>
     * The current bookmark must be returned.
     */
    @Test
    public void getBookmarkTest() {
        // variables
        String bookmark = getRandomWord();
        
        // mocks
        Execution executionMock = createMock(Execution.class);
        Desktop desktopMock = createMock(Desktop.class);
        
        // expect getting the current execution
        expect(Executions.getCurrent()).andReturn(executionMock);
        
        // expect getting the current desktop
        expect(executionMock.getDesktop()).andReturn(desktopMock);
        
        // expect getting the current bookmark
        expect(desktopMock.getBookmark()).andReturn(bookmark);
        
        // perform test
        replayAll();
        String currentBookmark = Environment.getBookmark();
        verifyAll();
        
        // the bookmarks must be the same
        assertEquals(bookmark, currentBookmark);
    }
    
    /**
     * This test tries to set the new bookmark using the method 
     * {@link Environment#setBookmark(String)}.
     * <p>
     * The new bookmark must been set successfully.
     */
    @Test
    public void setNewBookmarkTest() throws Exception {
        // variables
        String newBookmark = getRandomWord();
        
        // mocks
        Desktop desktopMock = createMock(Desktop.class);
        Execution executionMock = createMock(Execution.class);
        BookmarkEvent bookmarkEventMock = 
                createMockAndExpectNew(BookmarkEvent.class, Events.ON_BOOKMARK_CHANGE, newBookmark);
        
        // expect getting the current execution
        expect(Executions.getCurrent()).andReturn(executionMock);
        
        // expect getting the current desktop
        expect(executionMock.getDesktop()).andReturn(desktopMock);

        // expect setting the new bookmark
        desktopMock.setBookmark(newBookmark, false);
        
        // expect sending the notification event
        Events.postEvent(bookmarkEventMock);
        
        // perform test
        replayAll();
        Environment.setBookmark(newBookmark);
        verifyAll();
    }

    /**
     * This test tries to replace the current bookmark with the new one
     * using the method {@link Environment#setBookmark(String, boolean)}.
     * <p>
     * The new bookmark must been set successfully.
     */
    @Test
    public void replaceCurrentBookmarkTest() throws Exception {
        // variables
        String newBookmark = getRandomWord();
        
        // mocks
        Desktop desktopMock = createMock(Desktop.class);
        Execution executionMock = createMock(Execution.class);
        BookmarkEvent bookmarkEventMock = 
                createMockAndExpectNew(BookmarkEvent.class, Events.ON_BOOKMARK_CHANGE, newBookmark);
        
        // expect getting the current execution
        expect(Executions.getCurrent()).andReturn(executionMock);
        
        // expect getting the current desktop
        expect(executionMock.getDesktop()).andReturn(desktopMock);

        // expect setting the new bookmark
        desktopMock.setBookmark(newBookmark, true);
        
        // expect sending the notification event
        Events.postEvent(bookmarkEventMock);
        
        // perform test
        replayAll();
        Environment.setBookmark(newBookmark, true);
        verifyAll();
    }
}
