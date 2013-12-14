package org.panifex.web.impl.security;

import org.apache.shiro.realm.Realm;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * Tests binding and unbinding realms to SecurityFilterListener
 *
 */
public class SecurityFilterListenerBindingRealmsTest {

    private SecurityFilterListener listener = new SecurityFilterListener();;
    
    @Test
    public void bindRealmTest() {
        // create mocks
        Realm realm = EasyMock.createMock(Realm.class);
        
        EasyMock.replay(realm);
        
        // bind realm
        listener.bind(realm);
        
        EasyMock.verify(realm);
    }
    
    @Test
    public void unbindRealmTest() {
        // create mocks
        Realm realm = EasyMock.createMock(Realm.class);
        
        EasyMock.replay(realm);
        
        // unbind realm
        listener.unbind(realm);
        
        EasyMock.verify(realm);
    }
}
