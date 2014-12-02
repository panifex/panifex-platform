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
package org.panifex.module.api.pagelet;

import org.osgi.service.blueprint.container.BlueprintContainer;

/**
 * Generic template for implementing various pagelets.
 *
 * @param <Request> the request
 * @since 1.0
 */
public abstract class GenericPagelet<Request> implements Pagelet<Request> {

    private BlueprintContainer container;

    /**
     * Returns pagelet's class canonical name as pagelet's name.
     */
    @Override
    public String getName() {
        return getClass().getCanonicalName();
    }

    public final void setBlueprintContainer(BlueprintContainer container) {
        this.container = container;
    }

    protected final BlueprintContainer getContainer() {
        return container;
    }

    protected final Object getComponentInstance(String id) {
        return container.getComponentInstance(id);
    }

    @SuppressWarnings("unchecked")
    protected final <T> T getComponentInstance(Class<T> clazz) {
        Object instance = getComponentInstance(clazz.getSimpleName());
        if (clazz.isInstance(instance)) {
            return (T) instance;
        } else {
            throw new ClassCastException();
        }
    }
}
