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
 * <p>
 * The implemented pagelet may use {@link BlueprintContainer} for constructing or getting managed
 * classes or instances from the blueprint container. In such cases it should provide the
 * container instance to the generic pagelet.
 * </p>
 *
 * @param <Request> the request
 * @since 1.0
 */
public abstract class GenericPagelet<Request> implements Pagelet<Request> {

    /**
     * The Blueprint Container that represents the managed state of a Blueprint bundle.
     */
    private final BlueprintContainer container;

    /**
     * Construct instance in case the implemented pagelet does not plan to use the blueprint
     * container, or it will implement its usage on its own.
     */
    public GenericPagelet() {
        this.container = null;
    }

    /**
     * Construct instance with the associated blueprint container.
     *
     * @param container the blueprint container to be associated
     * @throws IllegalArgumentException if the provided blueprint container is null
     */
    public GenericPagelet(BlueprintContainer container) {
        if (container != null) {
            this.container = container;
        } else {
            throw new IllegalArgumentException("BlueprintContainer cannot be null");
        }
    }

    /**
     * Return pagelet's class canonical name as pagelet's name.
     */
    @Override
    public String getName() {
        return getClass().getCanonicalName();
    }

    /**
     * Return the associated {@link BlueprintContainer};
     *
     * @throws IllegalStateException if container is not assigned to pagelet
     */
    protected final BlueprintContainer getContainer() {
        if (container != null) {
            return container;
        } else {
            throw new IllegalStateException("BlueprintContainer is not assigned to pagelet");
        }
    }

    /**
     * Return the component instance for the specified component id.
     * <p>
     * The method can be used only if the blueprint container is associated to the generic
     * pagelet.
     * </p>
     *
     * @param id the component id
     */
    protected final Object getComponentInstance(String id) {
        return getContainer().getComponentInstance(id);
    }

    /**
     * Return the component instance for the specified class simple name.
     * <p>
     * The method can be used only if the blueprint container is associated to the generic
     * pagelet.
     * </p>
     */
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
