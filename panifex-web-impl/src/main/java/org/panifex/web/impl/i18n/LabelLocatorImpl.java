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
package org.panifex.web.impl.i18n;

import java.net.URL;
import java.util.Locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.LabelLocator;

/**
 * A simple implementation of {@link org.zkoss.util.resource.LabelLocator LabelLocator}.
 * 
 * @see {@link org.zkoss.util.resource.LabelLocator LabelLocator}
 */
final class LabelLocatorImpl implements LabelLocator {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final Locale supportedLocale;
    private final URL url;
    
    LabelLocatorImpl(
            Locale supportedLocale, 
            URL url) {
        this.supportedLocale = supportedLocale;
        this.url = url;
    }
    
    @Override
    public URL locate(Locale locale) {
        log.debug("Get InputStream for : {}", locale);
        if (locale != null) {
            if (locale.equals(supportedLocale)) {
                log.debug("InputStream is returning");
                return url;
            } 
        } else {
            if (supportedLocale == null) {
                log.debug("InputStream is returning");
                return url;                
            } else if (supportedLocale.getLanguage().isEmpty()) {
                log.debug("InputStream is returning");
                return url;
            }
        }
        
        // the locale does not match the supported locale, so return null
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 7).
                append(supportedLocale).
                append(url).
                toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        LabelLocatorImpl other = (LabelLocatorImpl) obj;
        return new EqualsBuilder().
                append(supportedLocale, other.supportedLocale).
                append(url, other.url).
                isEquals();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).
            append("supportedLocale", supportedLocale).
            append("url", url).
            toString();
        
    }
}
