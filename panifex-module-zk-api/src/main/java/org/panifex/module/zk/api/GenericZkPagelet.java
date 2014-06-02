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
package org.panifex.module.zk.api;

import org.osgi.service.blueprint.container.BlueprintContainer;
import org.zkoss.bind.DefaultBinder;
import org.zkoss.bind.impl.ValidationMessagesImpl;
import org.zkoss.bind.sys.ValidationMessages;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.GenericRichlet;

public abstract class GenericZkPagelet extends GenericRichlet implements ZkPagelet {

    private final String FX_BIND_ID = "FX";
    private final String VM_BIND_ID = "VM";
    private final String VMSGS_BIND_ID = "VMSGS";

    private BlueprintContainer container;

    public void setBlueprintContainer(BlueprintContainer container) {
        this.container = container;
    }

    protected BlueprintContainer getContainer() {
        return container;
    }

    protected Object getComponentInstance(String id) {
        return container.getComponentInstance(id);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getComponentInstance(Class<T> clazz) {
        Object instance = getComponentInstance(clazz.getSimpleName());
        if (clazz.isInstance(instance)) {
            return (T) instance;
        } else {
            throw new ClassCastException();
        }
    }

    protected String fx(String property) {
        return new StringBuilder(FX_BIND_ID).
                append('.').
                append(property).
                toString();
    }

    protected String vm(String property) {
        return new StringBuilder(VM_BIND_ID).
                append('.').
                append(property).
                toString();
    }

    protected String vmsgs(String vmessage) {
        return new StringBuilder(VMSGS_BIND_ID).
                append('.').
                append(vmessage).
                toString();
    }

    protected DefaultBinder createBinder(Component parent, Object viewModel) {
        DefaultBinder binder = new DefaultBinder();
        binder.init(parent, viewModel, null);
        parent.setAttribute(VM_BIND_ID, binder.getViewModel());
        return binder;
    }

    protected ValidationMessages createValidationMessages(Component parent, DefaultBinder binder) {
        ValidationMessages vmessages = new ValidationMessagesImpl();
        binder.setValidationMessages(vmessages);
        parent.setAttribute(VMSGS_BIND_ID, vmessages);
        return vmessages;
    }
}
