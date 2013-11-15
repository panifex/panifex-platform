package org.panifex.platform.web.impl.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Service;
import org.apache.aries.blueprint.annotation.Unbind;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.panifex.platform.api.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean(id = "org.panifex.platform.service.security.PanifexRealm")
@ReferenceListener
@Service(interfaces = Realm.class)
public class PanifexRealm extends AuthorizingRealm {

    private Logger log = LoggerFactory.getLogger(PanifexRealm.class);

    @Inject
    @Reference(serviceInterface = SecurityService.class, referenceListeners = @ReferenceListener(ref = "org.panifex.platform.service.security.PanifexRealm"))
    private SecurityService securityService;

    /**
     * Password hash salt configuration.
     * <ul>
     * <li>NO_SALT - password hashes are not salted.</li>
     * <li>CRYPT - password hashes are stored in unix crypt format.</li>
     * <li>COLUMN - salt is in a separate column in the database.</li>
     * <li>EXTERNAL - salt is not stored in the database. {@link #getSaltForUser(String)} will be
     * called to get the salt</li>
     * </ul>
     */
    public enum SaltStyle {
        NO_SALT, CRYPT, COLUMN, EXTERNAL
    };

    protected SaltStyle saltStyle = SaltStyle.NO_SALT;

    /**
     * Sets the salt style. See {@link #saltStyle}.
     * 
     * @param saltStyle new SaltStyle to set.
     */
    public void setSaltStyle(SaltStyle saltStyle) {
        this.saltStyle = saltStyle;
    }

    @Bind
    public void bind(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Unbind
    public void unbind(SecurityService securityService) {
        this.securityService = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        log.debug("Get authentication info for username: {}", username);

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        String password = null;
        String salt = null;
        switch (saltStyle) {
            case NO_SALT:
                password = securityService.getPasswordForUser(username)[0];
                break;
            case CRYPT:
                // TODO: separate password and hash from getPasswordForUser[0]
                throw new ConfigurationException("Not implemented yet");
                // break;
            case COLUMN:
                String[] queryResults = securityService.getPasswordForUser(username);
                password = queryResults[0];
                salt = queryResults[1];
                break;
            case EXTERNAL:
                password = securityService.getPasswordForUser(username)[0];
        }

        if (password == null) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }

        SimpleAuthenticationInfo info =
                new SimpleAuthenticationInfo(username, password.toCharArray(), getName());

        if (salt != null) {
            info.setCredentialsSalt(ByteSource.Util.bytes(salt));
        }

        log.debug("Authentication info resolved: username={}, password={}", username, password);

        return info;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        // null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String username = (String) getAvailablePrincipal(principals);

        Set<String> roleNames = null;
        Set<String> permissions = null;

        // TODO implement
        roleNames = new HashSet<>();
        permissions = new HashSet<>();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

}
