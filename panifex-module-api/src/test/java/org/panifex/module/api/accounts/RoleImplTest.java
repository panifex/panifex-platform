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
package org.panifex.module.api.accounts;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;
import org.panifex.module.api.accounts.RoleImpl;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for the {@link RoleImpl} class.
 */
public final class RoleImplTest extends TestSupport {

    /**
     * This test checks the equals contract.
     * 
     * @see {@link java.lang.Object#equals(Object)}
     * @see {@link java.lang.Object#hashCode()}
     */
    @Test
    public void equalsContractTest() {
        EqualsVerifier.
            forClass(RoleImpl.class).
            usingGetClass().
            allFieldsShouldBeUsed().
            suppress(Warning.NONFINAL_FIELDS).
            verify();
    }
    
    /**
     * This test checks the {@link RoleImpl#RoleImpl(Long, int, String, String)}
     * constructor.
     * <p>
     * The RoleImpl must be successfully constructed.
     */
    @Test
    public void constructRoleImplTest() {
        // variables
        Long id = new Long(getNumberUpTo(Integer.MAX_VALUE));
        int optlockVersion = getNumberUpTo(Integer.MAX_VALUE);
        String name = getRandomChars(20);
        String description = getRandomChars(20);
        
        // construct the RoleImpl instance
        RoleImpl role = new RoleImpl(id, optlockVersion, name, description);
        
        // assert has it successfully constructed
        assertNotNull(role);
        assertEquals(id, role.getId());
        assertEquals(optlockVersion, role.getOptlockVersion());
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }
}
