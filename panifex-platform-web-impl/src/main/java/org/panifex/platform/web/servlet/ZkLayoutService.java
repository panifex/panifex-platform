package org.panifex.platform.web.servlet;

import java.util.Map;

import javax.servlet.Servlet;

import org.zkoss.zk.ui.Richlet;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.util.Configuration;

/**
 * The zk framework configuration service.
 * 
 * <p>
 * This service can be used to register richlets.
 * 
 */
public interface ZkLayoutService extends Servlet {

    /**
     * Adds the definition of a richlet.
     * 
     * @param richlet the richlet
     * @param path the URL pattern. It must start with '/' and may end with '/*'.
     * @return the previous richlet class or class-name with the specified name, or null if no
     *         previous richlet.
     * 
     * @exception UiException if the richlet is not defined yet.
     */
    Object addRichlet(Class<? extends Richlet> richlet, String path);

    /**
     * Adds the definition of a richlet.
     * 
     * @param richlet the richlet
     * @param path the URL pattern. It must start with '/' and may end with '/*'.
     * @param params the initial parameters, or null if no initial parameter at all. Once called,
     *        the caller cannot access <code>params</code> any more.
     * @return the previous richlet class or class-name with the specified name, or null if no
     *         previous richlet.
     * 
     * @exception UiException if the richlet is not defined yet.
     */
    Object addRichlet(Class<? extends Richlet> richlet, String path, Map<String, String> params);

    /**
     * Gets Zk framework configuration which can be used for registering richlets, etc.
     * 
     * @return Zk framework configuration
     */
    Configuration getConfiguration();
}
