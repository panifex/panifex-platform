/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2015  Mario Krizmanic
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
package org.panifex.web.zk.runtime.html;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.trim;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.panifex.web.spi.html.HtmlComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;

public class ZkHtmlComponentUtil {

    /**
     * Adds the component to the container.
     *
     * @param htmlComp the component to be added to the container
     * @param container the container the the component to be added into
     */
    public static void addComponentToContainer(HtmlComponent htmlComp, Component container) {
        Component comp = castHtmlComponent(htmlComp);
        container.appendChild(comp);
    }

    /**
     * Removes the component from the container.
     *
     * @param htmlComp the component to be removed from the container
     * @param container the container from the component to be removed
     */
    public static void removeComponentFromContainer(
            HtmlComponent htmlComp, Component container) {
        Component comp = castHtmlComponent(htmlComp);
        container.removeChild(comp);
    }

    /**
     * Adds one or more style names to the component.
     *
     * @param styleName
     *      the style name or the style names to be added to the component
     * @param component
     *      the component to that the style name or the style names be added into
     */
    public static void addStyleName(String styleName, HtmlBasedComponent component) {
        if (isBlank(styleName)) {
            return;
        }

        Set<String> styles = splitStylesToSet(component);

        if (styleName.contains(" ")) {
            // Split space separated style names and add them one by one.
            StringTokenizer tokenizer = new StringTokenizer(styleName, " ");
            while (tokenizer.hasMoreTokens()) {
                String style = tokenizer.nextToken();
                styles.add(style);
            }
        } else {
            styles.add(styleName);
        }

        component.setSclass(join(styles, ' '));
    }

    /**
     * Removes one or more style names from the component.
     *
     * @param styleName
     *      the style name or the style names to be removed from the component
     * @param component
     *      the component to that the style name or the style names be removed from
     */
    public static void removeStyleName(String styleName, HtmlBasedComponent component) {
        if (isBlank(styleName)) {
            return;
        }

        Set<String> styles = splitStylesToSet(component);

        if (styleName.contains(" ")) {
            // Split space separated style names and add them one by one.
            StringTokenizer tokenizer = new StringTokenizer(styleName, " ");
            while (tokenizer.hasMoreTokens()) {
                String style = tokenizer.nextToken();
                styles.remove(style);
            }
        } else {
            styles.remove(styleName);
        }

        component.setSclass(join(styles, ' '));
    }

    /**
     * Casts the {@link HtmlComponent} instance as the {@link Component} instance, or throws
     * an IllegalArgumentException if it cannot be cast.
     *
     * @param htmlComp the html component to be cast
     * @return the casted html component
     */
    public static Component castHtmlComponent(HtmlComponent htmlComp) {
        if (Component.class.isInstance(htmlComp)) {
            return (Component) htmlComp;
        } else {
            String msg = new StringBuilder("Only ").
                    append(Component.class.getCanonicalName()).
                    append(" instance may be set as page content").
                    toString();
            throw new IllegalArgumentException(msg);
        }
    }

    private static Set<String> splitStylesToSet(HtmlBasedComponent component) {
        String actualStyles = trim(component.getSclass());

        Set<String> styles;
        if (isNotBlank(actualStyles)) {
            styles = new HashSet<>(Arrays.asList(actualStyles.split(" ")));
        } else {
            styles = new HashSet<>();
        }

        return styles;
    }

    /**
     * Private constructors protects from instancing - singleton.
     */
    private ZkHtmlComponentUtil() {
        // do nothing
    }
}
