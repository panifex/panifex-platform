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
import java.util.Collections;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.service.api.security.SecurityService;
import org.panifex.test.support.TestSupport;
import org.panifex.web.shiro.mgt.ModularFilterChainManager;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit tests for {@link ModularWebEnvironment} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ModularWebEnvironment.class)
public class ModularWebEnvironmentTest extends TestSupport {

    private ModularWebEnvironment environment;

    // mocks
    private DefaultWebSecurityManager securityManagerMock = createMock(DefaultWebSecurityManager.class);

    @Before
    public void setUp() {
        resetAll();

        environment = new ModularWebEnvironment();
        environment.setSecurityManager(securityManagerMock);
    }

    @Test(expected = IllegalStateException.class)
    public void testInitEnvironmentWithoutFilterChainManager() {
        environment.init();
    }

    public void testInitEnvironmentWithFilterChainManager() {
        ModularFilterChainManager filterChainManagerMock = createMock(ModularFilterChainManager.class);

        environment.setFilterChainManager(filterChainManagerMock);

        replayAll();
        environment.init();
        verifyAll();
    }

    /**
     * Tests binding a new security service to the security
     * manager.
     * <p>
     * The service should be successfully binded.
     */
    @Test
    public void testBindSecurityService() {
        bindSecurityService();
    }

    /**
     * Binds and unbinds the same security service to the security
     * service manager.
     * <p>
     * The security service should be unbinded and the empty simple realm
     * should be binded at the end. It should be binded because at least
     * one realm can be binded to the security manager.
     */
    @Test
    public void testBindAndUnbindSecurityService() throws Exception {
        SecurityService bindedSecurityService =
                bindSecurityService();

        // expect creating empty simple account realm
        SimpleAccountRealm simpleRealmMock = createMockAndExpectNew(SimpleAccountRealm.class);

        // expect binding simple account realm
        securityManagerMock.setRealms(buildRealmCollection(simpleRealmMock));

        // perform test
        replayAll();
        environment.unbindSecurityService(bindedSecurityService);
        verifyAll();
    }

    /**
     * Binds two security services to the security manager. When
     * they are binded, it unbinds the first one.
     * <p>
     * The first service should be unbinded.
     */
    @Test
    public void testBindTwoAndUnbindOneSecurityService() throws Exception {
        SecurityService firstSecurityService = bindSecurityService();
        SecurityService secondSecurityService = bindSecurityService(firstSecurityService);

        // after removing first service, expects binding second one
        securityManagerMock.setRealms(buildRealmCollection(secondSecurityService));

        // perform test
        replayAll();
        environment.unbindSecurityService(firstSecurityService);
        verifyAll();
    }

    /**
     * Tests binding an empty security service list.
     * <p>
     * RealmSecurityManager can accept at least one realm, so an empty
     * simple account realm will be binded. The simple realm does not deny
     * any user to perform login.
     */
    @Test
    public void testBindEmptySecurityServiceList() throws Exception {
        // expect creating empty simple account realm
        SimpleAccountRealm simpleRealmMock = createMockAndExpectNew(SimpleAccountRealm.class);

        // expect binding simple account realm
        securityManagerMock.setRealms(buildRealmCollection(simpleRealmMock));

        // perform test
        replayAll();
        environment.bindSecurityServices();
        verifyAll();
    }

    /**
     * Binds a new mocked security service to the web environment.
     *
     * @return binded mocked security service
     */
    private SecurityService bindSecurityService(SecurityService... alreadyBinded) {
        SecurityService securityServiceMock = createMock(SecurityService.class);

        // expect binding realms
        securityManagerMock.setRealms(buildRealmCollection(alreadyBinded, securityServiceMock));

        // perform test
        replayAll();
        environment.bindSecurityService(securityServiceMock);
        verifyAll();
        resetAll();

        return securityServiceMock;
    }

    /**
     * Constructs a collection of provided realms.
     *
     * @param realms an array of realms to be
     * @return a collection of provided realms
     */
    private Collection<Realm> buildRealmCollection(Realm... realms) {
        return buildRealmCollection(new Realm[0], realms);
    }

    /**
     * Constructs a collection of provided realms.
     *
     * @param realmList an array of realms to be
     * @param realms an array of realms to be
     * @return a collection of provided realms
     */
    private Collection<Realm> buildRealmCollection(Realm[] realmList, Realm... realms) {
        Collection<Realm> realmArray = new ArrayList<Realm>();

        Collections.addAll(realmArray, realmList);
        Collections.addAll(realmArray, realms);

        return realmArray;
    }
}
