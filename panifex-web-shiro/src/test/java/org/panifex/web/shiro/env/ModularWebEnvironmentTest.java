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

import java.util.Collection;
import java.util.Map;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.security.AuthenticationService;
import org.panifex.module.api.security.AuthorizationService;
import org.panifex.test.support.TestSupport;
import org.panifex.web.shiro.mgt.ModularFilterChainManager;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Unit tests for {@link ModularWebEnvironment} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ModularWebEnvironment.class)
public class ModularWebEnvironmentTest extends TestSupport {

    private ModularWebEnvironment environment;

    // mocks
    private DefaultWebSecurityManager securityManagerMock = createMock(DefaultWebSecurityManager.class);
    private ModularFilterChainManager filterChainManagerMock = createMock(ModularFilterChainManager.class);

    @Before
    public void setUp() {
        resetAll();

        environment = new ModularWebEnvironment(filterChainManagerMock);
        environment.setSecurityManager(securityManagerMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructEnvironmentWithoutFilterChainManager() {
        environment = new ModularWebEnvironment(null);
    }

    @Test
    public void testInitEnvironmentWithFilterChainManager() {
        replayAll();
        environment.init();
        verifyAll();
    }

    /**
     * Synchronizes the empty collection of realms to the security manager.
     * <p>
     * The {@link SimpleAccountRealm} should be binded to the security manager because
     * at least one realm can be binded.
     */
    @Test
    public void testSynchronizeEmptyRealmCollection() throws Exception {
        // expect binding SimpleAccountRealm to security manager
        SimpleAccountRealm simpleAccountRealmMock = createMockAndExpectNew(SimpleAccountRealm.class);
        securityManagerMock.setRealm(simpleAccountRealmMock);

        replayAll();
        environment.synchronizeRealms();
        verifyAll();

        assertTrue(environment.getRealms().isEmpty());
    }

    @Test
    public void testBindNullAuthenticationService() {
        bindAuthenticationServiceAndVerify(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBindAuthenticationServiceWithoutName() {
        AuthenticationService authcServiceMock = createMock(AuthenticationService.class);
        expect(authcServiceMock.getName()).andReturn(null);

        bindAuthenticationServiceAndVerify(authcServiceMock);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testBindValidAuthenticationService() {
        String authcServiceName = "serviceName";

        AuthenticationService authcServiceMock = createMock(AuthenticationService.class);
        expect(authcServiceMock.getName()).andReturn(authcServiceName);

        // expect synchronize collection which contains authentication service
        securityManagerMock.setRealms(isA(Collection.class));

        bindAuthenticationServiceAndVerify(authcServiceMock);

        Collection<Realm> realms = environment.getRealms();
        assertEquals(1, realms.size());

        assertTrue(realms.contains(authcServiceMock));
    }

    @Test
    public void testBindAuthenticationServiceWithTheSameNameAsBindedOne() {
        String authcServiceName = "serviceName";
        Realm existedRealmMock = createMock(Realm.class);

        // realm map already contains realm with the same name
        Map<String, Realm> realmMap = Whitebox.getInternalState(environment, "realms");
        realmMap.put(authcServiceName, existedRealmMock);

        AuthenticationService authcServiceMock = createMock(AuthenticationService.class);
        expect(authcServiceMock.getName()).andReturn(authcServiceName);

        bindAuthenticationServiceAndVerify(authcServiceMock);

        Collection<Realm> realms = environment.getRealms();
        assertEquals(1, realms.size());
        assertTrue(realms.contains(existedRealmMock));
    }

    @Test
    public void testUnbindNullAuthenticationService() {
        unbindAuthenticationServiceAndVerify(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnbindAuthenticationServiceWithoutName() {
        AuthenticationService authcServiceMock = createMock(AuthenticationService.class);
        expect(authcServiceMock.getName()).andReturn(null);

        unbindAuthenticationServiceAndVerify(authcServiceMock);
    }

    @Test
    public void testUnbindRegisteredAuthenticationService() throws Exception {
        String authcServiceName = "serviceName";

        AuthenticationService authcServiceMock = createMock(AuthenticationService.class);
        expect(authcServiceMock.getName()).andReturn(authcServiceName);

        // realm map already contains authentication service
        Map<String, Realm> realmMap = Whitebox.getInternalState(environment, "realms");
        realmMap.put(authcServiceName, authcServiceMock);

        // expect binding SimpleAccountRealm to security manager
        SimpleAccountRealm simpleAccountRealmMock = createMockAndExpectNew(SimpleAccountRealm.class);
        securityManagerMock.setRealm(simpleAccountRealmMock);

        unbindAuthenticationServiceAndVerify(authcServiceMock);
    }

    @Test
    public void testUnbindAuthenticationServiceWhichIsNotRegistered() {
        String authcServiceName = "serviceName";

        AuthenticationService authcServiceMock = createMock(AuthenticationService.class);
        expect(authcServiceMock.getName()).andReturn(authcServiceName);

        unbindAuthenticationServiceAndVerify(authcServiceMock);
    }

    @Test
    public void testUnbindAuthcServiceWhileOtherRealmWithTheSameNameIsRegistered() {
        String authcServiceName = "serviceName";

        AuthenticationService authcServiceMock = createMock(AuthenticationService.class);
        expect(authcServiceMock.getName()).andReturn(authcServiceName);

        Realm otherRealmMock = createMock(Realm.class);

        // realm map already contains authentication service
        Map<String, Realm> realmMap = Whitebox.getInternalState(environment, "realms");
        realmMap.put(authcServiceName, otherRealmMock);

        unbindAuthenticationServiceAndVerify(authcServiceMock);
    }

    @Test
    public void testBindNullAuthorizationService() {
        bindAuthorizationServiceAndVerify(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBindAuthorizationServiceWithoutName() {
        AuthorizationService authzServiceMock = createMock(AuthorizationService.class);
        expect(authzServiceMock.getName()).andReturn(null);

        bindAuthorizationServiceAndVerify(authzServiceMock);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testBindValidAuthorizationService() throws Exception {
        String authzServiceName = "serviceName";

        AuthorizationService authzServiceMock = createMock(AuthorizationService.class);

        // expect extending AuthorizationService to Realm
        AuthzAwareRealm realm = createMockAndExpectNew(AuthzAwareRealm.class, authzServiceMock);
        expect(realm.getName()).andReturn(authzServiceName);

        // expect synchronize collection which contains authentication service
        securityManagerMock.setRealms(isA(Collection.class));

        bindAuthorizationServiceAndVerify(authzServiceMock);

        Collection<Realm> realms = environment.getRealms();
        assertEquals(1, realms.size());

        assertTrue(realms.contains(realm));
    }

    @Test
    public void testBindAuthorizationServiceWithTheSameNameAsBindedOne() throws Exception {
        String authzServiceName = "serviceName";
        Realm existedRealmMock = createMock(Realm.class);

        // realm map already contains realm with the same name
        Map<String, Realm> realmMap = Whitebox.getInternalState(environment, "realms");
        realmMap.put(authzServiceName, existedRealmMock);

        AuthorizationService authzServiceMock = createMock(AuthorizationService.class);

        // expect extending AuthorizationService to Realm
        AuthzAwareRealm realm = createMockAndExpectNew(AuthzAwareRealm.class, authzServiceMock);
        expect(realm.getName()).andReturn(authzServiceName);

        bindAuthorizationServiceAndVerify(authzServiceMock);

        Collection<Realm> realms = environment.getRealms();
        assertEquals(1, realms.size());
        assertTrue(realms.contains(existedRealmMock));
    }

    @Test
    public void testUnbindNullAuthorizationService() {
        unbindAuthorizationServiceAndVerify(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnbindAuthorizationServiceWithoutName() {
        AuthorizationService authzServiceMock = createMock(AuthorizationService.class);
        expect(authzServiceMock.getName()).andReturn(null);

        unbindAuthorizationServiceAndVerify(authzServiceMock);
    }

    @Test
    public void testUnbindRegisteredAuthorizationService() throws Exception {
        String authcServiceName = "serviceName";

        AuthorizationService authzServiceMock = createMock(AuthorizationService.class);
        expect(authzServiceMock.getName()).andReturn(authcServiceName);

        AuthzAwareRealm authzAwareRealmMock = createMock(AuthzAwareRealm.class);
        expect(authzAwareRealmMock.getAuthorizationService()).andReturn(authzServiceMock);

        // realm map already contains authentication service
        Map<String, Realm> realmMap = Whitebox.getInternalState(environment, "realms");
        realmMap.put(authcServiceName, authzAwareRealmMock);

        // expect binding SimpleAccountRealm to security manager
        SimpleAccountRealm simpleAccountRealmMock = createMockAndExpectNew(SimpleAccountRealm.class);
        securityManagerMock.setRealm(simpleAccountRealmMock);

        unbindAuthorizationServiceAndVerify(authzServiceMock);
    }

    @Test
    public void testUnbindAuthorizationServiceWhichIsNotRegistered() {
        String authzServiceName = "serviceName";

        AuthorizationService authzServiceMock = createMock(AuthorizationService.class);
        expect(authzServiceMock.getName()).andReturn(authzServiceName);

        unbindAuthorizationServiceAndVerify(authzServiceMock);
    }

    @Test
    public void testUnbindAuthzServiceWhileOtherRealmWithTheSameNameIsRegistered() {
        String authcServiceName = "serviceName";

        AuthorizationService authzServiceMock = createMock(AuthorizationService.class);
        expect(authzServiceMock.getName()).andReturn(authcServiceName);

        Realm otherRealmMock = createMock(Realm.class);

        // realm map already contains authentication service
        Map<String, Realm> realmMap = Whitebox.getInternalState(environment, "realms");
        realmMap.put(authcServiceName, otherRealmMock);

        unbindAuthorizationServiceAndVerify(authzServiceMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnbindAuthcOrAuthzWithoutProvidingThem() {
        environment.unbindRealm(null, null);
    }

    private void bindAuthenticationServiceAndVerify(AuthenticationService authcService) {
        replayAll();
        try {
            environment.bindAuthenticationService(authcService);
        } finally {
            verifyAll();
        }
    }

    private void unbindAuthenticationServiceAndVerify(AuthenticationService authcService) {
        replayAll();
        try {
            environment.unbindAuthenticationService(authcService);
        } finally {
            verifyAll();
        }
    }

    private void bindAuthorizationServiceAndVerify(AuthorizationService authzService) {
        replayAll();
        try {
            environment.bindAuthorizationService(authzService);
        } finally {
            verifyAll();
        }
    }

    private void unbindAuthorizationServiceAndVerify(AuthorizationService authzService) {
        replayAll();
        try {
            environment.unbindAuthorizationService(authzService);
        } finally {
            verifyAll();
        }
    }
}
