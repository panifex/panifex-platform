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
package org.panifex.web.shiro.env;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.PrincipalCollection;
import org.junit.Before;
import org.junit.Test;
import org.panifex.module.api.security.AuthorizationService;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for the {@link AuthzAwareRealm} class.
 */
public class AuthzAwareRealmTest extends TestSupport {

    private AuthzAwareRealm realm;

    // mocks
    private AuthorizationService authzService = createMock(AuthorizationService.class);
    PrincipalCollection principalCollectionMock = createMock(PrincipalCollection.class);

    final String AUTH_SERVICE_NAME = "authzService";

    @Before
    public void setUp() {
        resetAll();

        expect(authzService.getName()).andReturn(AUTH_SERVICE_NAME);

        constructAuthzAwareRealm();
        resetAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructDummyRealmForNullAuthorizationService() {
        realm = new AuthzAwareRealm(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructDummyRealmWithoutAuthorizationServiceName() {
        expect(authzService.getName()).andReturn(null);

        constructAuthzAwareRealm();
    }

    @Test
    public void testGetName() {
        expect(authzService.getName()).andReturn(AUTH_SERVICE_NAME);

        replayAll();
        String serviceName = realm.getName();
        verifyAll();

        assertEquals(AUTH_SERVICE_NAME, serviceName);
    }

    @Test
    public void testSupportsToken() {
        AuthenticationToken token = createMock(AuthenticationToken.class);

        replayAll();
        boolean supports = realm.supports(token);
        verifyAll();

        assertFalse(supports);
    }

    @Test
    public void testGetAuthenticationInfo() {
        AuthenticationToken token = createMock(AuthenticationToken.class);

        replayAll();
        AuthenticationInfo info = realm.getAuthenticationInfo(token);
        verifyAll();

        assertNull(info);
    }

    @Test
    public void testIsPermittedForStringPermission() {
        boolean isPermittedResp = false;
        String permission = "permission";

        expect(authzService.isPermitted(principalCollectionMock, permission))
            .andReturn(isPermittedResp);

        replayAll();
        boolean isPermitted = realm.isPermitted(principalCollectionMock, permission);
        verifyAll();

        assertEquals(isPermittedResp, isPermitted);
    }

    @Test
    public void testIsPermittedForPermission() {
        boolean isPermittedResp = false;
        Permission permissionMock = createMock(Permission.class);

        expect(authzService.isPermitted(principalCollectionMock, permissionMock))
            .andReturn(isPermittedResp);

        replayAll();
        boolean isPermitted = realm.isPermitted(principalCollectionMock, permissionMock);
        verifyAll();

        assertEquals(isPermittedResp, isPermitted);
    }

    @Test
    public void testIsPermittedForArrayOfStringPermissions() {
        boolean[] isPermittedResp = new boolean[] { false, true };
        String permission1 = "permission1";
        String permission2 = "permission2";

        expect(authzService.isPermitted(principalCollectionMock, permission1, permission2))
            .andReturn(isPermittedResp);

        replayAll();
        boolean[] isPermitted = realm.isPermitted(principalCollectionMock, permission1, permission2);
        verifyAll();

        assertEquals(isPermittedResp, isPermitted);
    }

    @Test
    public void testIsPermittedForListOfPermissions() {
        boolean[] isPermittedResp = new boolean[] { true };
        List<Permission> permissions = new ArrayList<>();

        expect(authzService.isPermitted(principalCollectionMock, permissions))
            .andReturn(isPermittedResp);

        replayAll();
        boolean[] isPermitted = realm.isPermitted(principalCollectionMock, permissions);
        verifyAll();

        assertEquals(isPermittedResp, isPermitted);
    }

    @Test
    public void testIsPermittedAllForArrayOfStringPermissions() {
        boolean isPermittedResp = true;
        String permission1 = "permission1";
        String permission2 = "permission2";

        expect(authzService.isPermittedAll(principalCollectionMock, permission1, permission2))
            .andReturn(isPermittedResp);

        replayAll();
        boolean isPermitted = realm.isPermittedAll(principalCollectionMock, permission1, permission2);
        verifyAll();

        assertEquals(isPermittedResp, isPermitted);
    }

    @Test
    public void testIsPermittedAllForCollectionOfPermissions() {
        boolean isPermittedResp = false;
        Collection<Permission> permissions = new ArrayList<>();

        expect(authzService.isPermittedAll(principalCollectionMock, permissions))
            .andReturn(isPermittedResp);

        replayAll();
        boolean isPermitted = realm.isPermittedAll(principalCollectionMock, permissions);
        verifyAll();

        assertEquals(isPermittedResp, isPermitted);
    }

    @Test
    public void testCheckPermissionForStringPermission() {
        String permission = "permission";

        authzService.checkPermission(principalCollectionMock, permission);

        replayAll();
        realm.checkPermission(principalCollectionMock, permission);
        verifyAll();
    }

    @Test
    public void testCheckPermissionForPermissionInstance() {
        Permission permission = createMock(Permission.class);

        authzService.checkPermission(principalCollectionMock, permission);

        replayAll();
        realm.checkPermission(principalCollectionMock, permission);
        verifyAll();
    }

    @Test
    public void testCheckPermissionsForArrayOfStringPermissions() {
        String permission1 = "permission1";
        String permission2 = "permission2";

        authzService.checkPermissions(principalCollectionMock, permission1, permission2);

        replayAll();
        realm.checkPermissions(principalCollectionMock, permission1, permission2);
        verifyAll();
    }

    @Test
    public void testCheckPermissionForCollectionOfPermissions() {
        Collection<Permission> permissions = new ArrayList<>();

        authzService.checkPermissions(principalCollectionMock, permissions);

        replayAll();
        realm.checkPermissions(principalCollectionMock, permissions);
        verifyAll();
    }

    @Test
    public void testHasRoleOfStringRoleIdentifier() {
        boolean hasRoleResp = true;
        String roleIdentifier = "role";

        expect(authzService.hasRole(principalCollectionMock, roleIdentifier))
            .andReturn(hasRoleResp);

        replayAll();
        boolean hasRole = realm.hasRole(principalCollectionMock, roleIdentifier);
        verifyAll();

        assertEquals(hasRoleResp, hasRole);
    }

    @Test
    public void testHasRolesOfListOfRoleIdentifiers() {
        boolean[] hasRolesResp = new boolean[] { false };
        List<String> roleIdentifiers = new ArrayList<>();

        expect(authzService.hasRoles(principalCollectionMock, roleIdentifiers))
            .andReturn(hasRolesResp);

        replayAll();
        boolean[] hasRoles = realm.hasRoles(principalCollectionMock, roleIdentifiers);
        verifyAll();

        assertEquals(hasRolesResp, hasRoles);
    }

    @Test
    public void testHasAllRolesOfCollectionOfRoleIdentifiers() {
        boolean hasAllRolesResp = true;
        Collection<String> roleIdentifiers = new ArrayList<>();

        expect(authzService.hasAllRoles(principalCollectionMock, roleIdentifiers))
            .andReturn(hasAllRolesResp);

        replayAll();
        boolean hasAllRoles = realm.hasAllRoles(principalCollectionMock, roleIdentifiers);
        verifyAll();

        assertEquals(hasAllRolesResp, hasAllRoles);
    }

    @Test
    public void testCheckRoleForRoleIdentifier() {
        String roleIdentifier = "role";

        authzService.checkRole(principalCollectionMock, roleIdentifier);

        replayAll();
        realm.checkRole(principalCollectionMock, roleIdentifier);
        verifyAll();
    }

    @Test
    public void testCheckRolesOfCollectionOfRoleIdentifiers() {
        Collection<String> roleIdentifiers = new ArrayList<>(1);

        authzService.checkRoles(principalCollectionMock, roleIdentifiers);

        replayAll();
        realm.checkRoles(principalCollectionMock, roleIdentifiers);
        verifyAll();
    }

    @Test
    public void testCheckRolesOfArrayOfRoleIdentifiers() {
        String roleIdentifier1 = "role1";
        String roleIdentifier2 = "role2";

        authzService.checkRoles(principalCollectionMock, roleIdentifier1, roleIdentifier2);

        replayAll();
        realm.checkRoles(principalCollectionMock, roleIdentifier1, roleIdentifier2);
        verifyAll();
    }

    private void constructAuthzAwareRealm() {
        replayAll();
        try {
            realm = new AuthzAwareRealm(authzService);
        } finally {
            verifyAll();
        }
    }
}
