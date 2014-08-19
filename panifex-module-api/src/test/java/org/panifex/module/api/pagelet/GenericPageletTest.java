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

    private GenericPagelet<Object> pagelet = new TestGenericPagelet();

    // mocks
    private BlueprintContainer blueprintContainer = createMock(BlueprintContainer.class);

    @Before
    public void setUp() {
        resetAll();

        pagelet.setBlueprintContainer(blueprintContainer);
    }

    @Test
    public void testGetPageletName() {
        String expected = TestGenericPagelet.class.getCanonicalName();
        assertEquals(expected, pagelet.getName());
    }

    @Test
    public void testSetAndGetBlueprintContainer() {
        BlueprintContainer container = createMock(BlueprintContainer.class);
        pagelet.setBlueprintContainer(container);
        assertEquals(container, pagelet.getContainer());
    }

    class TestGenericPagelet extends GenericPagelet<Object> {
        @Override
        public void service(Object request) throws Exception {
            // do nothing
        }
    }
}
