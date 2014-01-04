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

import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.reflect.exceptions.ConstructorNotFoundException;
import org.powermock.reflect.exceptions.FieldNotFoundException;
import org.powermock.reflect.exceptions.MethodNotFoundException;
import org.powermock.reflect.exceptions.TooManyConstructorsFoundException;
import org.powermock.reflect.exceptions.TooManyMethodsFoundException;

/**
 * Inlines public static method from {@link org.powermock.api.support.membermodification.MemberMatcher MemberMatcher}
 * class.
 *
 */
abstract class PowerMockMemberMatcherSupport {

    /**
     * Get all methods in a class hierarchy of the supplied classes. Both
     * declared an non-declared (no duplicates).
     * 
     * @param cls
     *            The class whose methods to get.
     * @param additionalClasses
     *            Additional classes whose methods to get.
     * @return All methods declared in this class hierarchy.
     */
    protected final Method[] methodsDeclaredIn(final Class<?> cls, final Class<?>... additionalClasses) {
        return MemberMatcher.methodsDeclaredIn(cls, additionalClasses);
    }

    /**
     * Get a method when it cannot be determined by methodName or parameter
     * types only.
     * <p>
     * The method will first try to look for a declared method in the same
     * class. If the method is not declared in this class it will look for the
     * method in the super class. This will continue throughout the whole class
     * hierarchy. If the method is not found an {@link IllegalArgumentException}
     * is thrown.
     * 
     * @param declaringClass
     *            The declaringClass of the class where the method is located.
     * @param methodName
     *            The method names.
     * @param parameterTypes
     *            All parameter types of the method (may be <code>null</code>).
     * @return A <code>java.lang.reflect.Method</code>.
     * @throws MethodNotFoundException
     *             If a method cannot be found in the hierarchy.
     */
    protected final Method method(Class<?> declaringClass, String methodName, Class<?>... parameterTypes) {
        return MemberMatcher.method(declaringClass, methodName, parameterTypes);
    }

    /**
     * Get a method without having to specify the method name.
     * <p>
     * The method will first try to look for a declared method in the same
     * class. If the method is not declared in this class it will look for the
     * method in the super class. This will continue throughout the whole class
     * hierarchy. If the method is not found an {@link IllegalArgumentException}
     * is thrown. Since the method name is not specified an
     * {@link IllegalArgumentException} is thrown if two or more methods matches
     * the same parameter types in the same class.
     * 
     * @param declaringClass
     *            The declaringClass of the class where the method is located.
     * @param parameterTypes
     *            All parameter types of the method (may be <code>null</code>).
     * @return A <code>java.lang.reflect.Method</code>.
     * @throws MethodNotFoundException
     *             If a method cannot be found in the hierarchy.
     * @throws TooManyMethodsFoundException
     *             If several methods were found.
     */
    protected final Method method(Class<?> declaringClass, Class<?>... parameterTypes) {
        return MemberMatcher.method(declaringClass, parameterTypes);
    }

    /**
     * Get an array of {@link Method}'s that matches the supplied list of method
     * names. Both instance and static methods are taken into account.
     * 
     * @param clazz
     *            The class that should contain the methods.
     * @param methodName
     *            The name of the first method.
     * @param additionalMethodNames
     *            Additional names of the methods that will be returned.
     * @return An array of Method's. May be of length 0 but not
     *         <code>null</code>.
     * @throws MethodNotFoundException
     *             If no method was found.
     */
    protected final Method[] methods(Class<?> clazz, String methodName, String... additionalMethodNames) {
        return MemberMatcher.methods(clazz, methodName, additionalMethodNames);
    }

    /**
     * Get an array of {@link Field}'s.
     * 
     * @param method
     *            The first field.
     * @param additionalMethods
     *            Additional fields
     * @return An array of {@link Field}.
     */
    protected final Method[] methods(Method method, Method... additionalMethods) {
        return MemberMatcher.methods(method, additionalMethods);
    }

    /**
     * Get an array of {@link Method}'s that matches the supplied list of method
     * names. Both instance and static methods are taken into account.
     * 
     * @param clazz
     *            The class that should contain the methods.
     * @param methodNames
     *            The names of the methods.
     * @return An array of Method's. May be of length 0 but not
     *         <code>null</code>.
     * @throws MethodNotFoundException
     *             If no method was found.
     */
    protected final Method[] methods(Class<?> clazz, String[] methodNames) {
        return MemberMatcher.methods(clazz, methodNames);
    }

    /**
     * Get a field from a class.
     * <p>
     * The method will first try to look for a declared field in the same class.
     * If the method is not declared in this class it will look for the field in
     * the super class. This will continue throughout the whole class hierarchy.
     * If the field is not found an {@link IllegalArgumentException} is thrown.
     * 
     * @param declaringClass
     *            The declaringClass of the class where the method is located.
     * @param fieldName
     *            The method names.
     * @return A <code>java.lang.reflect.Field</code>.
     * @throws FieldNotFoundException
     *             If a field cannot be found in the hierarchy.
     */
    protected final Field field(Class<?> declaringClass, String fieldName) {
        return MemberMatcher.field(declaringClass, fieldName);
    }

    /**
     * Get an array of {@link Field}'s that matches the supplied list of field
     * names.
     * 
     * @param clazz
     *            The class that should contain the fields.
     * @param firstFieldName
     *            The name of the first field.
     * @param additionalfieldNames
     *            The additional names of the fields that will be returned.
     * @return An array of Field's. May be of length 0 but not <code>null</code>
     * 
     */
    protected final Field[] fields(Class<?> clazz, String firstFieldName, String... additionalfieldNames) {
        return MemberMatcher.fields(clazz, firstFieldName, additionalfieldNames);
    }

    /**
     * Get all fields in a class hierarchy.
     * 
     * @param clazz
     *            The class that should contain the fields.
     * @param firstFieldName
     *            The name of the first field.
     * @param additionalfieldNames
     *            The additional names of the fields that will be returned.
     * @return An array of Field's. May be of length 0 but not <code>null</code>
     * 
     */
    protected final Field[] fields(Class<?> clazz) {
        return MemberMatcher.fields(clazz);
    }

    /**
     * Get an array of {@link Field}'s.
     * 
     * @param field
     *            The first field.
     * @param additionalFields
     *            Additional fields
     * @return An array of {@link Field}.
     */
    protected final Field[] fields(Field field, Field... additionalFields) {
        return MemberMatcher.fields(field, additionalFields);
    }

    /**
     * Get an array of {@link Field}'s that matches the supplied list of field
     * names.
     * 
     * @param clazz
     *            The class that should contain the fields.
     * @param fieldNames
     *            The names of the fields that will be returned.
     * @return An array of Field's. May be of length 0 but not <code>null</code>
     * 
     */
    protected final Field[] fields(Class<?> clazz, String[] fieldNames) {
        return MemberMatcher.fields(clazz, fieldNames);
    }

    /**
     * Returns a constructor specified in declaringClass.
     * 
     * @param declaringClass
     *            The declaringClass of the class where the constructor is
     *            located.
     * @param parameterTypes
     *            All parameter types of the constructor (may be
     *            <code>null</code>).
     * @return A <code>java.lang.reflect.Constructor</code>.
     * @throws ConstructorNotFoundException
     *             if the constructor cannot be found.
     */
    protected final <T> Constructor<T> constructor(Class<T> declaringClass, Class<?>... parameterTypes) {
        return MemberMatcher.constructor(declaringClass, parameterTypes);
    }

    /**
     * Returns any one constructor specified in declaringClass. Is is useful when you only have ONE constructor
     * declared in <code>declaringClass</code> but you don't care which parameters it take.
     * 
     * @param declaringClass
     *            The declaringClass of the class where the constructor is
     *            located.
     * @return A <code>java.lang.reflect.Constructor</code>.
     * @throws TooManyConstructorsFoundException
     *             If more than one constructor was present in
     *             <code>declaringClass</code>
     */
    protected final <T> Constructor<T> constructor(Class<T> declaringClass) {
        return MemberMatcher.constructor(declaringClass);
    }

    /**
     * Returns the default constructor in <code>declaringClass</code>
     *
     * @param declaringClass
     *            The declaringClass of the class where the constructor is
     *            located.
     * @return A <code>java.lang.reflect.Constructor</code>.
     * @throws ConstructorNotFoundException
     *             If no default constructor was found in  <code>declaringClass</code>
     */
    protected final <T> Constructor<T> defaultConstructorIn(Class<T> declaringClass) {
        return MemberMatcher.defaultConstructorIn(declaringClass);
    }

    /**
     * Get all constructors in the supplied class(es).
     * 
     * @param cls
     *            The class whose constructors to get.
     * @param additionalClasses
     *            Additional classes whose constructors to get.
     * @return All constructors declared in this class.
     */
    protected final Constructor<?>[] constructorsDeclaredIn(final Class<?> cls, final Class<?>... additionalClasses) {
        return MemberMatcher.constructorsDeclaredIn(cls, additionalClasses);
    }

    /**
     * Convenience method to get a constructor from a class.
     * 
     * @param constructor
     *            The first constructor.
     * @param additionalConstructors
     *            Additional constructors
     * @return An array of <code>java.lang.reflect.Constructor</code>.
     */
    protected final Constructor<?>[] constructors(Constructor<?> constructor, Constructor<?>... additionalConstructors) {
        return MemberMatcher.constructors(constructor, additionalConstructors);
    }

    /**
     * Get all constructors and methods in the supplied class(es).
     * 
     * @param cls
     *            The class whose constructors and methods to get.
     * @param additionalClasses
     *            Additional classes whose constructors and methods to get.
     * @return All constructors and methods declared in this class.
     */
    protected final AccessibleObject[] everythingDeclaredIn(final Class<?> cls, final Class<?>... additionalClasses) {
        return MemberMatcher.everythingDeclaredIn(cls, additionalClasses);
    }
}
