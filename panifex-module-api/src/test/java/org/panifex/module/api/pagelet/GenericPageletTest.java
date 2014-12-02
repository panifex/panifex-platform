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
package org.panifex.module.api.pagelet;

import org.junit.Before;
import org.junit.Test;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for {@link GenericPagelet} class.
 */
public class GenericPageletTest extends TestSupport {

    // mocks
    private final BlueprintContainer blueprintContainer =
            createMock(BlueprintContainer.class);

    private final GenericPagelet<Object> pagelet =
            new TestGenericPagelet(blueprintContainer);

    @Before
    public void setUp() {
        resetAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructPageletWithUnknownContainer() {
        new TestGenericPagelet(null);
    }

    @Test
    public void testGetPageletName() {
        String expected = TestGenericPagelet.class.getCanonicalName();
        assertEquals(expected, pagelet.getName());
    }

    @Test
    public void testGetAssociatedBlueprintContainer() {
        assertEquals(blueprintContainer, pagelet.getContainer());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetNotAssociatedBlueprintContainer() {
        GenericPagelet<Object> pagelet = null;
        try {
            // construct pagelet without blueprint container
            pagelet = new TestGenericPagelet();
        } catch (Throwable throwable) {
            fail("Exception should not be thrown");
        }

        pagelet.getContainer();
        fail("Exception should be thrown");
    }

    class TestGenericPagelet extends GenericPagelet<Object> {
        TestGenericPagelet() {
        }

        TestGenericPagelet(BlueprintContainer container) {
            super(container);
        }

        @Override
        public void service(Object request) throws Exception {
            // do nothing
        }
    }
}
