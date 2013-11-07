package org.panifex.platform.web.impl.security;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.shiro.web.servlet.ShiroFilter;

/**
 * Primary security filter for web application. SecurityFilter inherits
 * ShiroFilter in order to be used in blueprint environment. 
 *
 */
@Bean(id = "org.panifex.platform.web.impl.security.SecurityFilter")
public class SecurityFilter extends ShiroFilter {

}
