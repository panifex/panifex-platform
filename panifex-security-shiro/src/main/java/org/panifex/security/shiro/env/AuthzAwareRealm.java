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
package org.panifex.security.shiro.env;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.panifex.module.api.security.AuthorizationService;

/**
 * Extends the {@link AuthorizationService} to be aware of the {@link Realm} interface.
 * <p>
 * It is introduced because the {@link org.apache.shiro.authz.ModularRealmAuthorizer
 * ModularRealmAuthorizer} uses the collection of {@link Realm}s so it is not enough
 * the {@link AuthorizationService} only implements {@link org.apache.shiro.authz.Authorizer
 * Authorizer} interface.
 *
 * @since 1.0
 */
class AuthzAwareRealm implements Authorizer, Realm {

    /**
     * The {@link AuthorizationService} to be extended to implement the {@link Realm}
     * interface.
     */
    private final AuthorizationService authzService;

    /**
     * Initializes a new {@link AuthzAwareRealm} object instance that extends the
     * authorization service to implement the {@link Realm} interface.
     *
     * @param authzService
     *      the authorization service to be extended to implement the {@link Realm}
     *      interface
     */
    AuthzAwareRealm(AuthorizationService authzService) {
        assertAuthzServiceValid(authzService);
        this.authzService = authzService;
    }

    /**
     * Gets the extended {@link AuthorizationService}.
     *
     * @return the extended {@link AuthorizationService}
     */
    public AuthorizationService getAuthorizationService() {
        return authzService;
    }

    /**
     * Returns the (application-unique) name assigned to this <code>AuthorizationService</code>.
     * All services configured for a single application must have a unique name.
     *
     * @return the (application-unique) name assigned to this <code>AuthorizationService</code>.
     */
    @Override
    public String getName() {
        return authzService.getName();
    }

    /**
     * Returns <tt>false</tt> because this realm does not authenticate the Subject represented
     * by the given {@link org.apache.shiro.authc.AuthenticationToken AuthenticationToken}
     * instance.
     *
     * @param token
     *      the AuthenticationToken submitted for the authentication attempt
     * @return
     *      <tt>false</tt> because this realm cannot authenticate Subjects represented
     *      by specified token
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return false;
    }

    /**
     * Returns <tt>null</tt> because this realm does not authenticate the Subject represented
     * by the given {@link org.apache.shiro.authc.AuthenticationToken AuthenticationToken}
     * instance.
     *
     *
     * @param token
     *      the application-specific representation of an account principal and credentials.
     * @return
     *      <tt>null</tt> because this realm cannot authenticate Subjects represented
     *      by specified token
     */
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        return null;
    }

    /**
     * Returns <tt>true</tt> if the extended {@link AuthorizationService#isPermitted(PrincipalCollection, String)}
     * call return <code>true</code>, <code>false</code> otherwise.
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        return authzService.isPermitted(principals, permission);
    }

    /**
     * Returns <tt>true</tt> if the extended {@link AuthorizationService#isPermitted(PrincipalCollection, Permission)}
     * call return <code>true</code>, <code>false</code> otherwise.
     */
    @Override
    public boolean isPermitted(PrincipalCollection subjectPrincipal,
            Permission permission) {
        return authzService.isPermitted(subjectPrincipal, permission);
    }

    /**
     * Calls the extended {@link AuthorizationService#isPermitted(PrincipalCollection, String...)}
     * and returns its response.
     */
    @Override
    public boolean[] isPermitted(PrincipalCollection subjectPrincipal,
            String... permissions) {
        return authzService.isPermitted(subjectPrincipal, permissions);
    }

    /**
     * Calls the extended {@link AuthorizationService#isPermitted(PrincipalCollection, List)}
     * and returns its response.
     */
    @Override
    public boolean[] isPermitted(PrincipalCollection subjectPrincipal,
            List<Permission> permissions) {
        return authzService.isPermitted(subjectPrincipal, permissions);
    }

    /**
     * Returns <tt>true</tt> if the extended {@link AuthorizationService#isPermittedAll(PrincipalCollection, String...)}
     * call return <code>true</code>, <code>false</code> otherwise.
     */
    @Override
    public boolean isPermittedAll(PrincipalCollection subjectPrincipal,
            String... permissions) {
        return authzService.isPermittedAll(subjectPrincipal, permissions);
    }

    /**
     * Returns <tt>true</tt> if the extended {@link AuthorizationService#isPermittedAll(PrincipalCollection, Collection)}
     * call return <code>true</code>, <code>false</code> otherwise.
     */
    @Override
    public boolean isPermittedAll(PrincipalCollection subjectPrincipal,
            Collection<Permission> permissions) {
        return authzService.isPermittedAll(subjectPrincipal, permissions);
    }

    /**
     * Calls the extended {@link AuthorizationService#checkPermission(PrincipalCollection, String)}
     * method.
     */
    @Override
    public void checkPermission(PrincipalCollection subjectPrincipal,
            String permission) throws AuthorizationException {
        authzService.checkPermission(subjectPrincipal, permission);
    }

    /**
     * Calls the extended {@link AuthorizationService#checkPermission(PrincipalCollection, Permission)}
     * method.
     */
    @Override
    public void checkPermission(PrincipalCollection subjectPrincipal,
            Permission permission) throws AuthorizationException {
        authzService.checkPermission(subjectPrincipal, permission);
    }

    /**
     * Calls the extended {@link AuthorizationService#checkPermissions(PrincipalCollection, String...)}
     * method.
     */
    @Override
    public void checkPermissions(PrincipalCollection subjectPrincipal,
            String... permissions) throws AuthorizationException {
        authzService.checkPermissions(subjectPrincipal, permissions);
    }

    /**
     * Calls the extended {@link AuthorizationService#checkPermissions(PrincipalCollection, Collection)}
     * method.
     */
    @Override
    public void checkPermissions(PrincipalCollection subjectPrincipal,
            Collection<Permission> permissions) throws AuthorizationException {
        authzService.checkPermissions(subjectPrincipal, permissions);
    }

    /**
     * Returns <tt>true</tt> if the extended {@link AuthorizationService#hasRole(PrincipalCollection, String)}
     * call return <code>true</code>, <code>false</code> otherwise.
     */
    @Override
    public boolean hasRole(PrincipalCollection subjectPrincipal,
            String roleIdentifier) {
        return authzService.hasRole(subjectPrincipal, roleIdentifier);
    }

    /**
     * Calls the extended {@link AuthorizationService#hasRoles(PrincipalCollection, List)}
     * and returns its response.
     */
    @Override
    public boolean[] hasRoles(PrincipalCollection subjectPrincipal,
            List<String> roleIdentifiers) {
        return authzService.hasRoles(subjectPrincipal, roleIdentifiers);
    }

    /**
     * Returns <tt>true</tt> if the extended {@link AuthorizationService#hasAllRoles(PrincipalCollection, Collection)}
     * call return <code>true</code>, <code>false</code> otherwise.
     */
    @Override
    public boolean hasAllRoles(PrincipalCollection subjectPrincipal,
            Collection<String> roleIdentifiers) {
        return authzService.hasAllRoles(subjectPrincipal, roleIdentifiers);
    }

    /**
     * Calls the extended {@link AuthorizationService#checkRole(PrincipalCollection, String)}
     * method.
     */
    @Override
    public void checkRole(PrincipalCollection subjectPrincipal,
            String roleIdentifier) throws AuthorizationException {
        authzService.checkRole(subjectPrincipal, roleIdentifier);
    }

    /**
     * Calls the extended {@link AuthorizationService#checkRoles(PrincipalCollection, Collection)}
     * method.
     */
    @Override
    public void checkRoles(PrincipalCollection subjectPrincipal,
            Collection<String> roleIdentifiers) throws AuthorizationException {
        authzService.checkRoles(subjectPrincipal, roleIdentifiers);
    }

    /**
     * Calls the extended {@link AuthorizationService#checkRoles(PrincipalCollection, String...)}
     * method.
     */
    @Override
    public void checkRoles(PrincipalCollection subjectPrincipal,
            String... roleIdentifiers) throws AuthorizationException {
        authzService.checkRoles(subjectPrincipal, roleIdentifiers);
    }

    /**
     * Asserts the provided {@link AuthorizationService} is not null and its name is not
     * null or an empty string.
     *
     * @param authzService
     *      the {@link AuthorizationService} to be verified
     */
    private void assertAuthzServiceValid(AuthorizationService authzService) {
        if (authzService == null) {
            throw new IllegalArgumentException("authorizationService cannot be null");
        }
        String serviceName = authzService.getName();
        if (serviceName == null || serviceName.isEmpty()) {
            throw new IllegalArgumentException("authorizationService's name must be specified");
        }
    }
}
