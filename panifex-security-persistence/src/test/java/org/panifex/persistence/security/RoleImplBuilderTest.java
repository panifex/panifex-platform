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
package org.panifex.persistence.security;

import org.junit.Test;
import org.panifex.service.api.security.RoleImpl;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for the {@link RoleImplBuilder} class.
 */
public final class RoleImplBuilderTest extends TestSupport {

    /**
     * This test tries to build a {@link org.panifex.service.api.security.RoleImpl RoleImpl}
     * based on the mocked {@link RoleEntity} using the {@link RoleImplBuilder} class.
     * <p>
     * The {@link org.panifex.service.api.security.RoleImpl RoleImpl} must be successfully
     * builded.
     */
    @Test
    public void buildRoleImplTest() {
        // variables
        Long id = new Long(getNumberUpTo(Integer.MAX_VALUE));
        int optlockVersion = getNumberUpTo(Integer.MAX_VALUE);
        String name = getRandomChars(20);
        String description = getRandomChars(20);
        
        // mocks
        RoleEntity roleEntityMock = createMock(RoleEntity.class);
        
        // expect getting the RoleEntity's properties
        expect(roleEntityMock.getId()).andReturn(id);
        expect(roleEntityMock.getOptlockVersion()).andReturn(optlockVersion);
        expect(roleEntityMock.getName()).andReturn(name);
        expect(roleEntityMock.getDescription()).andReturn(description);
        
        // perform test
        replayAll();
        RoleImplBuilder builder = new RoleImplBuilder(roleEntityMock);
        RoleImpl buildedRoleImpl = builder.build();
        verifyAll();
        
        // assert builded properties
        assertEquals(id, buildedRoleImpl.getId());
        assertEquals(optlockVersion, buildedRoleImpl.getOptlockVersion());
        assertEquals(name, buildedRoleImpl.getName());
        assertEquals(description, buildedRoleImpl.getDescription());
    }
}
