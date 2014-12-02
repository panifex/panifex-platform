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
package org.panifex.module.vaadin.api;

import org.osgi.service.blueprint.container.BlueprintContainer;
import org.panifex.module.api.pagelet.GenericPagelet;

import com.vaadin.server.VaadinRequest;

public abstract class GenericVaadinPagelet
        extends GenericPagelet<VaadinRequest>
        implements VaadinPagelet {

    /**
     * Construct instance in case the implemented pagelet does not plan to use the blueprint
     * container, or it will implement its usage on its own.
     */
    public GenericVaadinPagelet() {
        super();
    }

    /**
     * Construct instance with the associated blueprint container.
     *
     * @param container the blueprint container to be associated
     */
    public GenericVaadinPagelet(BlueprintContainer container) {
        super(container);
    }
}
