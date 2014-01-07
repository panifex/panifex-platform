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
package org.panifex.module.api.menu;

/**
 * An application menu building block which represent menu's node 
 * which contains other menu's items.
 * 
 * @since 1.0
 */
public class DefaultMenuNode extends AbstractMenuItem implements MenuNode {

    /**
     * The badge text of the {@link org.zkoss.zkmax.zul.Nav}.
     */
    private String badgeText;
    
    /**
     * Constructs the new {@link DefaultMenuNode} instance with the 
     * specified ID.
     * 
     * @param id the {@link MenuItem}'s ID
     * 
     * @since 1.0
     */
    public DefaultMenuNode(String id) {
        super(id);
    }
    
    /**
     * Constructs the {@link DefaultMenuNode} with the specified item's ID
     * and the specified parent {@link MenuNode}'s ID.
     * 
     * @param id the {@link MenuItem}'s ID
     * @param parentId the parent {@link MenuNode}'s ID
     * 
     * @since 1.0
     */
    public DefaultMenuNode(String id, String parentId) {
        super(id, parentId);
    }
    
    /**
     * Constructs the {@link DefaultMenuNode} with the specified item's ID,
     * the specified parent {@link MenuNode}'s ID and the priority which 
     * defines the position in the same hierarchy level.
     * 
     * @param id the {@link MenuItem}'s ID
     * @param parentId the parent {@link MenuNode node}'s ID
     * @param priority the priority which defines the position in the same hierarchy level
     * 
     * @since 1.0
     */
    public DefaultMenuNode(String id, String parentId, int priority) {
        super(id, parentId, priority);
    }
    
    /**
     * Returns the badge text of the {@link org.zkoss.zkmax.zul.Nav}.
     * 
     * @return the badge text of the {@link org.zkoss.zkmax.zul.Nav}
     * 
     * @since 1.0
     */
    @Override
    public String getBadgeText() {
        return badgeText;
    }

    /**
     * Sets the badge text of the {@link org.zkoss.zkmax.zul.Nav}.
     * 
     * @param badgeText badge text of the {@link org.zkoss.zkmax.zul.Nav}
     * 
     * @since 1.0
     */
    public void setBadgeText(String badgeText) {
        this.badgeText = badgeText;
    }
}
