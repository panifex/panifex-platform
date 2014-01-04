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
package org.panifex.test.support;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.api.support.membermodification.strategy.MethodReplaceStrategy;
import org.powermock.api.support.membermodification.strategy.MethodStubStrategy;

/**
 * Inlines public static methods from {@link org.powermock.api.support.membermodification.MemberModifier MemberModifier}
 * class.
 * <p>
 * {@link org.powermock.api.support.membermodification.MemberModifier MemberModifier} contains 
 * various utilities for modifying members of classes such as constructors, fields and methods. 
 * Modifying means e.g. changing return value of method invocations or suppressing a constructor.
 */
abstract class PowerMockMemberModifierSupport extends PowerMockMemberMatcherSupport {

    /**
     * Suppress a specific method. This works on both instance methods and
     * static methods.
     */
    protected final void suppress(Method method) {
        MemberModifier.suppress(method);
    }

    /**
     * Suppress multiple methods. This works on both instance methods and static
     * methods.
     */
    protected final void suppress(Method[] methods) {
        MemberModifier.suppress(methods);
    }

    /**
     * Suppress a constructor.
     */
    protected final void suppress(Constructor<?> constructor) {
        MemberModifier.suppress(constructor);
    }

    /**
     * Suppress multiple constructors.
     */
    protected final void suppress(Constructor<?>[] constructors) {
        MemberModifier.suppress(constructors);
    }

    /**
     * Suppress a field.
     */
    protected final void suppress(Field field) {
        MemberModifier.suppress(field);
    }

    /**
     * Suppress multiple fields.
     */
    protected final void suppress(Field[] fields) {
        MemberModifier.suppress(fields);
    }

    /**
     * Suppress an array of accessible objects.
     */
    protected final void suppress(AccessibleObject[] accessibleObjects) {
        MemberModifier.suppress(accessibleObjects);
    }

    /**
     * Add a method that should be intercepted and return another value (i.e.
     * the method is stubbed).
     */
    protected final <T> MethodStubStrategy<T> stub(Method method) {
        return MemberModifier.stub(method);
    }

    /**
     * Replace a method invocation.
     */
    protected final MethodReplaceStrategy replace(Method method) {
        return MemberModifier.replace(method);
    }
}
