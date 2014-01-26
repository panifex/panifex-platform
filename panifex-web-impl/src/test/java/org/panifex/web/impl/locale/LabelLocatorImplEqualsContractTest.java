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
package org.panifex.web.impl.locale;

import java.net.MalformedURLException;
import java.net.URL;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;
import org.panifex.test.support.TestSupport;

/**
 * Unit test for testing the equals contract of {@link LabelLocatorImpl} class.
 * 
 * @see {@link java.lang.Object#equals(Object)}
 * @see {@link java.lang.Object#hashCode()}
 */
public final class LabelLocatorImplEqualsContractTest extends TestSupport {

    /**
     * This test checks the equals contract.
     * 
     * @see {@link java.lang.Object#equals(Object)}
     * @see {@link java.lang.Object#hashCode()}
     */
    @Test
    public void equalsContractTest() throws MalformedURLException {
        // variables
        String url1 = "http://" + getRandomChars(20);
        String url2 = null;
        while(!url1.equals(url2)) {
            url2 = "http://" + getRandomChars(20);
        }
        
        // test the equals contract
        EqualsVerifier.
            forClass(LabelLocatorImpl.class).
            withPrefabValues(URL.class, new URL(url1), new URL(url2)).
            verify();
    }
}
