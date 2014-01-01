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

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.easymock.Capture;
import org.easymock.ConstructorArgs;
import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.easymock.IExpectationSetters;
import org.easymock.IMockBuilder;
import org.easymock.IMocksControl;
import org.easymock.LogicalOperator;
import org.fluttercode.datafactory.AddressDataValues;
import org.fluttercode.datafactory.ContentDataValues;
import org.fluttercode.datafactory.NameDataValues;
import org.fluttercode.datafactory.impl.DataFactory;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.internal.ArrayComparisonFailure;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;

/**
 * The template class for unit tests.
 * 
 * <p>The class inlines methods from {@link org.junit.Assert}, {@link org.easymock.EasyMock} and
 * {@link org.powermock.api.easymock.PowerMock} classes in order to subclasses do not have the reference 
 * to that classes. 
 *
 */
public abstract class TestSupport {

    /**
     * Class that provides a number of methods for generating test data.
     */
    private DataFactory df;
    
    /**
     * Asserts that a condition is true. If it isn't it throws an
     * {@link AssertionError} with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param condition condition to be checked
     */
    protected final void assertTrue(String message, boolean condition) {
        Assert.assertTrue(message, condition);
    }

    /**
     * Asserts that a condition is true. If it isn't it throws an
     * {@link AssertionError} without a message.
     *
     * @param condition condition to be checked
     */
    protected final void assertTrue(boolean condition) {
        Assert.assertTrue(condition);
    }

    /**
     * Asserts that a condition is false. If it isn't it throws an
     * {@link AssertionError} with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param condition condition to be checked
     */
    protected final void assertFalse(String message, boolean condition) {
        Assert.assertFalse(message, condition);
    }

    /**
     * Asserts that a condition is false. If it isn't it throws an
     * {@link AssertionError} without a message.
     *
     * @param condition condition to be checked
     */
    protected final void assertFalse(boolean condition) {
        Assert.assertFalse(condition);
    }

    /**
     * Fails a test with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @see AssertionError
     */
    protected final void fail(String message) {
        Assert.fail(message);
    }

    /**
     * Fails a test with no message.
     */
    protected final void fail() {
        Assert.fail();
    }

    /**
     * Asserts that two objects are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message. If
     * <code>expected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param expected expected value
     * @param actual actual value
     */
    protected final void assertEquals(String message, Object expected,
            Object actual) {
        Assert.assertEquals(message, expected, actual);
    }

    /**
     * Asserts that two objects are equal. If they are not, an
     * {@link AssertionError} without a message is thrown. If
     * <code>expected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     */
    protected final void assertEquals(Object expected, Object actual) {
        Assert.assertEquals(expected, actual);
    }

    /**
     * Asserts that two objects are <b>not</b> equals. If they are, an
     * {@link AssertionError} is thrown with the given message. If
     * <code>first</code> and <code>second</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param first first value to check
     * @param second the value to check against <code>first</code>
     */
    protected final void assertNotEquals(String message, Object first,
            Object second) {
        Assert.assertNotEquals(message, first, second);
    }

    /**
     * Asserts that two objects are <b>not</b> equals. If they are, an
     * {@link AssertionError} without a message is thrown. If
     * <code>first</code> and <code>second</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param first first value to check
     * @param second the value to check against <code>first</code>
     */
    protected final void assertNotEquals(Object first, Object second) {
        Assert.assertNotEquals(first, second);
    }

    /**
     * Asserts that two longs are <b>not</b> equals. If they are, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param first first value to check
     * @param second the value to check against <code>first</code>
     */
    protected final void assertNotEquals(String message, long first, long second) {
        Assert.assertNotEquals(message, first, second);
    }

    /**
     * Asserts that two longs are <b>not</b> equals. If they are, an
     * {@link AssertionError} without a message is thrown.
     *
     * @param first first value to check
     * @param second the value to check against <code>first</code>
     */
    protected final void assertNotEquals(long first, long second) {
        Assert.assertNotEquals(first, second);
    }

    /**
     * Asserts that two doubles or floats are <b>not</b> equal to within a positive delta.
     * If they are, an {@link AssertionError} is thrown with the given
     * message. If the expected value is infinity then the delta value is
     * ignored. NaNs are considered equal:
     * <code>assertNotEquals(Double.NaN, Double.NaN, *)</code> fails
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param first first value to check
     * @param second the value to check against <code>first</code>
     * @param delta the maximum delta between <code>expected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     */
    protected final void assertNotEquals(String message, double first,
            double second, double delta) {
        Assert.assertNotEquals(message, first, second, delta);
    }

    /**
     * Asserts that two doubles or floats are <b>not</b> equal to within a positive delta.
     * If they are, an {@link AssertionError} is thrown. If the expected
     * value is infinity then the delta value is ignored.NaNs are considered
     * equal: <code>assertNotEquals(Double.NaN, Double.NaN, *)</code> fails
     *
     * @param first first value to check
     * @param second the value to check against <code>first</code>
     * @param delta the maximum delta between <code>expected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     */
    protected final void assertNotEquals(double first, double second, double delta) {
        Assert.assertNotEquals(first, second, delta);
    }

    /**
     * Asserts that two object arrays are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message. If
     * <code>expecteds</code> and <code>actuals</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param expecteds Object array or array of arrays (multi-dimensional array) with
     * expected values.
     * @param actuals Object array or array of arrays (multi-dimensional array) with
     * actual values
     */
    protected final void assertArrayEquals(String message, Object[] expecteds,
            Object[] actuals) throws ArrayComparisonFailure {
        Assert.assertArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two object arrays are equal. If they are not, an
     * {@link AssertionError} is thrown. If <code>expected</code> and
     * <code>actual</code> are <code>null</code>, they are considered
     * equal.
     *
     * @param expecteds Object array or array of arrays (multi-dimensional array) with
     * expected values
     * @param actuals Object array or array of arrays (multi-dimensional array) with
     * actual values
     */
    protected final void assertArrayEquals(Object[] expecteds, Object[] actuals) {
        Assert.assertArrayEquals(expecteds, actuals);
    }

    /**
     * Asserts that two byte arrays are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param expecteds byte array with expected values.
     * @param actuals byte array with actual values
     */
    protected final void assertArrayEquals(String message, byte[] expecteds,
            byte[] actuals) throws ArrayComparisonFailure {
        Assert.assertArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two byte arrays are equal. If they are not, an
     * {@link AssertionError} is thrown.
     *
     * @param expecteds byte array with expected values.
     * @param actuals byte array with actual values
     */
    protected final void assertArrayEquals(byte[] expecteds, byte[] actuals) {
        Assert.assertArrayEquals(expecteds, actuals);
    }

    /**
     * Asserts that two char arrays are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param expecteds char array with expected values.
     * @param actuals char array with actual values
     */
    protected final void assertArrayEquals(String message, char[] expecteds,
            char[] actuals) throws ArrayComparisonFailure {
        Assert.assertArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two char arrays are equal. If they are not, an
     * {@link AssertionError} is thrown.
     *
     * @param expecteds char array with expected values.
     * @param actuals char array with actual values
     */
    protected final void assertArrayEquals(char[] expecteds, char[] actuals) {
        Assert.assertArrayEquals(expecteds, actuals);
    }

    /**
     * Asserts that two short arrays are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param expecteds short array with expected values.
     * @param actuals short array with actual values
     */
    protected final void assertArrayEquals(String message, short[] expecteds,
            short[] actuals) throws ArrayComparisonFailure {
        Assert.assertArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two short arrays are equal. If they are not, an
     * {@link AssertionError} is thrown.
     *
     * @param expecteds short array with expected values.
     * @param actuals short array with actual values
     */
    protected final void assertArrayEquals(short[] expecteds, short[] actuals) {
        Assert.assertArrayEquals(expecteds, actuals);
    }

    /**
     * Asserts that two int arrays are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param expecteds int array with expected values.
     * @param actuals int array with actual values
     */
    protected final void assertArrayEquals(String message, int[] expecteds,
            int[] actuals) throws ArrayComparisonFailure {
        Assert.assertArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two int arrays are equal. If they are not, an
     * {@link AssertionError} is thrown.
     *
     * @param expecteds int array with expected values.
     * @param actuals int array with actual values
     */
    protected final void assertArrayEquals(int[] expecteds, int[] actuals) {
        Assert.assertArrayEquals(expecteds, actuals);
    }

    /**
     * Asserts that two long arrays are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param expecteds long array with expected values.
     * @param actuals long array with actual values
     */
    protected final void assertArrayEquals(String message, long[] expecteds,
            long[] actuals) throws ArrayComparisonFailure {
        Assert.assertArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two long arrays are equal. If they are not, an
     * {@link AssertionError} is thrown.
     *
     * @param expecteds long array with expected values.
     * @param actuals long array with actual values
     */
    protected final void assertArrayEquals(long[] expecteds, long[] actuals) {
        Assert.assertArrayEquals(expecteds, actuals);
    }

    /**
     * Asserts that two double arrays are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param expecteds double array with expected values.
     * @param actuals double array with actual values
     */
    protected final void assertArrayEquals(String message, double[] expecteds,
            double[] actuals, double delta) throws ArrayComparisonFailure {
        Assert.assertArrayEquals(message, expecteds, actuals, delta);
    }

    /**
     * Asserts that two double arrays are equal. If they are not, an
     * {@link AssertionError} is thrown.
     *
     * @param expecteds double array with expected values.
     * @param actuals double array with actual values
     */
    protected final void assertArrayEquals(double[] expecteds, double[] actuals, double delta) {
        Assert.assertArrayEquals(expecteds, actuals, delta);
    }

    /**
     * Asserts that two float arrays are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param expecteds float array with expected values.
     * @param actuals float array with actual values
     */
    protected final void assertArrayEquals(String message, float[] expecteds,
            float[] actuals, float delta) throws ArrayComparisonFailure {
        Assert.assertArrayEquals(message, expecteds, actuals, delta);
    }

    /**
     * Asserts that two float arrays are equal. If they are not, an
     * {@link AssertionError} is thrown.
     *
     * @param expecteds float array with expected values.
     * @param actuals float array with actual values
     */
    protected final void assertArrayEquals(float[] expecteds, float[] actuals, float delta) {
        Assert.assertArrayEquals(expecteds, actuals, delta);
    }

    /**
     * Asserts that two doubles are equal to within a positive delta.
     * If they are not, an {@link AssertionError} is thrown with the given
     * message. If the expected value is infinity then the delta value is
     * ignored. NaNs are considered equal:
     * <code>assertEquals(Double.NaN, Double.NaN, *)</code> passes
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     * @param delta the maximum delta between <code>expected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     */
    protected final void assertEquals(String message, double expected,
            double actual, double delta) {
        Assert.assertEquals(message, expected, actual, delta);
    }

    /**
     * Asserts that two floats are equal to within a positive delta.
     * If they are not, an {@link AssertionError} is thrown with the given
     * message. If the expected value is infinity then the delta value is
     * ignored. NaNs are considered equal:
     * <code>assertEquals(Float.NaN, Float.NaN, *)</code> passes
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     * @param delta the maximum delta between <code>expected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     */
    protected final void assertEquals(String message, float expected,
            float actual, float delta) {
        Assert.assertEquals(message, expected, actual, delta);
    }

    /**
     * Asserts that two longs are equal. If they are not, an
     * {@link AssertionError} is thrown.
     *
     * @param expected expected long value.
     * @param actual actual long value
     */
    protected final void assertEquals(long expected, long actual) {
        Assert.assertEquals(expected, actual);
    }

    /**
     * Asserts that two longs are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param expected long expected value.
     * @param actual long actual value
     */
    protected final void assertEquals(String message, long expected, long actual) {
        Assert.assertEquals(message, expected, actual);
    }

    /**
     * Asserts that two doubles are equal to within a positive delta.
     * If they are not, an {@link AssertionError} is thrown. If the expected
     * value is infinity then the delta value is ignored.NaNs are considered
     * equal: <code>assertEquals(Double.NaN, Double.NaN, *)</code> passes
     *
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     * @param delta the maximum delta between <code>expected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     */
    protected final void assertEquals(double expected, double actual, double delta) {
        Assert.assertEquals(expected, actual, delta);
    }

    /**
     * Asserts that two floats are equal to within a positive delta.
     * If they are not, an {@link AssertionError} is thrown. If the expected
     * value is infinity then the delta value is ignored. NaNs are considered
     * equal: <code>assertEquals(Float.NaN, Float.NaN, *)</code> passes
     *
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     * @param delta the maximum delta between <code>expected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     */
    protected final void assertEquals(float expected, float actual, float delta) {
        Assert.assertEquals(expected, actual, delta);
    }

    /**
     * Asserts that an object isn't null. If it is an {@link AssertionError} is
     * thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param object Object to check or <code>null</code>
     */
    protected final void assertNotNull(String message, Object object) {
        Assert.assertNotNull(message, object);
    }

    /**
     * Asserts that an object isn't null. If it is an {@link AssertionError} is
     * thrown.
     *
     * @param object Object to check or <code>null</code>
     */
    protected final void assertNotNull(Object object) {
        Assert.assertNotNull(object);
    }

    /**
     * Asserts that an object is null. If it is not, an {@link AssertionError}
     * is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param object Object to check or <code>null</code>
     */
    protected final void assertNull(String message, Object object) {
        Assert.assertNull(message, object);
    }

    /**
     * Asserts that an object is null. If it isn't an {@link AssertionError} is
     * thrown.
     *
     * @param object Object to check or <code>null</code>
     */
    protected final void assertNull(Object object) {
        Assert.assertNull(object);
    }

    /**
     * Asserts that two objects refer to the same object. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param expected the expected object
     * @param actual the object to compare to <code>expected</code>
     */
    protected final void assertSame(String message, Object expected, Object actual) {
        Assert.assertSame(message, expected, actual);
    }

    /**
     * Asserts that two objects refer to the same object. If they are not the
     * same, an {@link AssertionError} without a message is thrown.
     *
     * @param expected the expected object
     * @param actual the object to compare to <code>expected</code>
     */
    protected final void assertSame(Object expected, Object actual) {
        Assert.assertSame(expected, actual);
    }

    /**
     * Asserts that two objects do not refer to the same object. If they do
     * refer to the same object, an {@link AssertionError} is thrown with the
     * given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param unexpected the object you don't expect
     * @param actual the object to compare to <code>unexpected</code>
     */
    protected final void assertNotSame(String message, Object unexpected,
            Object actual) {
        Assert.assertNotSame(message, unexpected, actual);
    }

    /**
     * Asserts that two objects do not refer to the same object. If they do
     * refer to the same object, an {@link AssertionError} without a message is
     * thrown.
     *
     * @param unexpected the object you don't expect
     * @param actual the object to compare to <code>unexpected</code>
     */
    protected final void assertNotSame(Object unexpected, Object actual) {
        Assert.assertNotSame(unexpected, actual);
    }

    /**
     * Asserts that <code>actual</code> satisfies the condition specified by
     * <code>matcher</code>. If not, an {@link AssertionError} is thrown with
     * information about the matcher and failing value. Example:
     *
     * <pre>
     *   assertThat(0, is(1)); // fails:
     *     // failure message:
     *     // expected: is &lt;1&gt;
     *     // got value: &lt;0&gt;
     *   assertThat(0, is(not(1))) // passes
     * </pre>
     *
     * <code>org.hamcrest.Matcher</code> does not currently document the meaning
     * of its type parameter <code>T</code>.  This method assumes that a matcher
     * typed as <code>Matcher&lt;T&gt;</code> can be meaningfully applied only
     * to values that could be assigned to a variable of type <code>T</code>.
     *
     * @param <T> the static type accepted by the matcher (this can flag obvious
     * compile-time problems such as {@code assertThat(1, is("a"))}
     * @param actual the computed value being compared
     * @param matcher an expression, built of {@link Matcher}s, specifying allowed
     * values
     * @see org.hamcrest.CoreMatchers
     * @see org.hamcrest.MatcherAssert
     */
    protected final <T> void assertThat(T actual, Matcher<? super T> matcher) {
        Assert.assertThat(actual, matcher);
    }

    /**
     * Asserts that <code>actual</code> satisfies the condition specified by
     * <code>matcher</code>. If not, an {@link AssertionError} is thrown with
     * the reason and information about the matcher and failing value. Example:
     *
     * <pre>
     *   assertThat(&quot;Help! Integers don't work&quot;, 0, is(1)); // fails:
     *     // failure message:
     *     // Help! Integers don't work
     *     // expected: is &lt;1&gt;
     *     // got value: &lt;0&gt;
     *   assertThat(&quot;Zero is one&quot;, 0, is(not(1))) // passes
     * </pre>
     *
     * <code>org.hamcrest.Matcher</code> does not currently document the meaning
     * of its type parameter <code>T</code>.  This method assumes that a matcher
     * typed as <code>Matcher&lt;T&gt;</code> can be meaningfully applied only
     * to values that could be assigned to a variable of type <code>T</code>.
     *
     * @param reason additional information about the error
     * @param <T> the static type accepted by the matcher (this can flag obvious
     * compile-time problems such as {@code assertThat(1, is("a"))}
     * @param actual the computed value being compared
     * @param matcher an expression, built of {@link Matcher}s, specifying allowed
     * values
     * @see org.hamcrest.CoreMatchers
     * @see org.hamcrest.MatcherAssert
     */
    protected final <T> void assertThat(String reason, T actual,
            Matcher<? super T> matcher) {
        Assert.assertThat(reason, actual, matcher);
    }
    
    /**
     * Creates a mock object that implements the given interface, order checking
     * is enabled by default.
     * 
     * @param name
     *            the name of the mock object.
     * @param toMock
     *            the class of the interface that the mock object should
     *            implement.
     * @param <T>
     *            the interface that the mock object should implement.
     * @return the mock object.
     * @throws IllegalArgumentException
     *             if the name is not a valid Java identifier.
     */
    protected final <T> T createStrictMock(final String name, final Class<T> toMock) {
        return EasyMock.createStrictMock(name, toMock);
    }

    /**
     * Creates a mock object that implements the given interface, order checking
     * is disabled by default.
     * 
     * @param name
     *            the name of the mock object.
     * @param toMock
     *            the class of the interface that the mock object should
     *            implement.
     * 
     * @param <T>
     *            the interface that the mock object should implement.
     * @return the mock object.
     * @throws IllegalArgumentException
     *             if the name is not a valid Java identifier.
     */
    protected final <T> T createMock(final String name, final Class<T> toMock) {
        return EasyMock.createMock(name, toMock);
    }

    /**
     * Creates a mock object that implements the given interface, order checking
     * is disabled by default, and the mock object will return <code>0</code>,
     * <code>null</code> or <code>false</code> for unexpected invocations.
     * 
     * @param name
     *            the name of the mock object.
     * @param toMock
     *            the class of the interface that the mock object should
     *            implement.
     * 
     * @param <T>
     *            the interface that the mock object should implement.
     * @return the mock object.
     * @throws IllegalArgumentException
     *             if the name is not a valid Java identifier.
     */
    protected final <T> T createNiceMock(final String name, final Class<T> toMock) {
        return EasyMock.createNiceMock(name, toMock);
    }

    /**
     * Create a mock builder allowing to create a partial mock for the given
     * class or interface.
     * 
     * @param <T>
     *            the interface that the mock object should implement.
     * @param toMock
     *            the class of the interface that the mock object should
     *            implement.
     * @return a mock builder to create a partial mock
     */
    protected final <T> IMockBuilder<T> createMockBuilder(final Class<T> toMock) {
        return EasyMock.createMockBuilder(toMock);
    }

    /**
     * Creates a control, order checking is enabled by default.
     * 
     * @return the control.
     */
    protected final IMocksControl createStrictControl() {
        return EasyMock.createStrictControl();
    }

    /**
     * Creates a control, order checking is disabled by default.
     * 
     * @return the control.
     */
    protected final IMocksControl createControl() {
        return EasyMock.createControl();
    }

    /**
     * Creates a control, order checking is disabled by default, and the mock
     * objects created by this control will return <code>0</code>,
     * <code>null</code> or <code>false</code> for unexpected invocations.
     * 
     * @return the control.
     */
    protected final IMocksControl createNiceControl() {
        return EasyMock.createNiceControl();
    }

    /**
     * Returns the expectation setter for the last expected invocation in the
     * current thread.
     * 
     * @param <T>
     *            type returned by the expected method
     * @param value
     *            the parameter is used to transport the type to the
     *            ExpectationSetter. It allows writing the expected call as
     *            argument, i.e.
     *            <code>expect(mock.getName()).andReturn("John Doe")<code>.
     * 
     * @return the expectation setter.
     */
    protected final <T> IExpectationSetters<T> expect(final T value) {
        return EasyMock.expect(value);
    }

    /**
     * Expects any boolean argument. For details, see the EasyMock
     * documentation.
     * 
     * @return <code>false</code>.
     */
    protected final boolean anyBoolean() {
        return EasyMock.anyBoolean();
    }

    /**
     * Expects any byte argument. For details, see the EasyMock documentation.
     * 
     * @return <code>0</code>.
     */
    protected final byte anyByte() {
        return EasyMock.anyByte();
    }

    /**
     * Expects any char argument. For details, see the EasyMock documentation.
     * 
     * @return <code>0</code>.
     */
    protected final char anyChar() {
        return EasyMock.anyChar();
    }

    /**
     * Expects any int argument. For details, see the EasyMock documentation.
     * 
     * @return <code>0</code>.
     */
    protected final int anyInt() {
        return EasyMock.anyInt();
    }

    /**
     * Expects any long argument. For details, see the EasyMock documentation.
     * 
     * @return <code>0</code>.
     */
    protected final long anyLong() {
        return EasyMock.anyLong();
    }

    /**
     * Expects any float argument. For details, see the EasyMock documentation.
     * 
     * @return <code>0</code>.
     */
    protected final float anyFloat() {
        return EasyMock.anyFloat();
    }

    /**
     * Expects any double argument. For details, see the EasyMock documentation.
     * 
     * @return <code>0</code>.
     */
    protected final double anyDouble() {
        return EasyMock.anyDouble();
    }

    /**
     * Expects any short argument. For details, see the EasyMock documentation.
     * 
     * @return <code>0</code>.
     */
    protected final short anyShort() {
        return EasyMock.anyShort();
    }

    /**
     * Expects any Object argument. For details, see the EasyMock documentation.
     * This matcher (and {@link #anyObject(Class)}) can be used in these three
     * ways:
     * <ul>
     * <li><code>(T)EasyMock.anyObject() // explicit cast</code></li>
     * <li>
     * <code>EasyMock.&lt;T&gt; anyObject() // fixing the returned generic</code>
     * </li>
     * <li>
     * <code>EasyMock.anyObject(T.class) // pass the returned type in parameter</code>
     * </li>
     * </ul>
     * 
     * @param <T>
     *            type of the method argument to match
     * @return <code>null</code>.
     */
    protected final <T> T anyObject() {
        return EasyMock.anyObject();
    }

    /**
     * Expects any Object argument. For details, see the EasyMock documentation.
     * To work well with generics, this matcher can be used in three different
     * ways. See {@link #anyObject()}.
     * 
     * @param <T>
     *            type of the method argument to match
     * @param clazz
     *            the class of the argument to match
     * @return <code>null</code>.
     */
    protected final <T> T anyObject(final Class<T> clazz) {
        return EasyMock.anyObject(clazz);
    }

    /**
     * Expects a comparable argument greater than or equal the given value. For
     * details, see the EasyMock documentation.
     * 
     * @param <T>
     *            type of the method argument to match
     * @param value
     *            the given value.
     * @return <code>null</code>.
     */
    protected final <T extends Comparable<T>> T geq(final Comparable<T> value) {
        return EasyMock.geq(value);
    }

    /**
     * Expects a byte argument greater than or equal to the given value. For
     * details, see the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final byte geq(final byte value) {
        return EasyMock.geq(value);
    }

    /**
     * Expects a double argument greater than or equal to the given value. For
     * details, see the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final double geq(final double value) {
        return EasyMock.geq(value);
    }

    /**
     * Expects a float argument greater than or equal to the given value. For
     * details, see the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final float geq(final float value) {
        return EasyMock.geq(value);
    }

    /**
     * Expects an int argument greater than or equal to the given value. For
     * details, see the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final int geq(final int value) {
        return EasyMock.geq(value);
    }

    /**
     * Expects a long argument greater than or equal to the given value. For
     * details, see the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final long geq(final long value) {
        return EasyMock.geq(value);
    }

    /**
     * Expects a short argument greater than or equal to the given value. For
     * details, see the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final short geq(final short value) {
        return EasyMock.geq(value);
    }

    /**
     * Expects a comparable argument less than or equal the given value. For
     * details, see the EasyMock documentation.
     * 
     * @param <T>
     *            type of the method argument to match
     * @param value
     *            the given value.
     * @return <code>null</code>.
     */
    protected final <T extends Comparable<T>> T leq(final Comparable<T> value) {
        return EasyMock.leq(value);
    }

    /**
     * Expects a byte argument less than or equal to the given value. For
     * details, see the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final byte leq(final byte value) {
        return EasyMock.leq(value);
    }

    /**
     * Expects a double argument less than or equal to the given value. For
     * details, see the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final double leq(final double value) {
        return EasyMock.leq(value);
    }

    /**
     * Expects a float argument less than or equal to the given value. For
     * details, see the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final float leq(final float value) {
        return EasyMock.leq(value);
    }

    /**
     * Expects an int argument less than or equal to the given value. For
     * details, see the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final int leq(final int value) {
        return EasyMock.leq(value);
    }

    /**
     * Expects a long argument less than or equal to the given value. For
     * details, see the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final long leq(final long value) {
        return EasyMock.leq(value);
    }

    /**
     * Expects a short argument less than or equal to the given value. For
     * details, see the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final short leq(final short value) {
        return EasyMock.leq(value);
    }

    /**
     * Expects a comparable argument greater than the given value. For details,
     * see the EasyMock documentation.
     * 
     * @param <T>
     *            type of the method argument to match
     * @param value
     *            the given value.
     * @return <code>null</code>.
     */
    protected final <T extends Comparable<T>> T gt(final Comparable<T> value) {
        return EasyMock.gt(value);
    }

    /**
     * Expects a byte argument greater than the given value. For details, see
     * the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final byte gt(final byte value) {
        return EasyMock.gt(value);
    }

    /**
     * Expects a double argument greater than the given value. For details, see
     * the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final double gt(final double value) {
        return EasyMock.gt(value);
    }

    /**
     * Expects a float argument greater than the given value. For details, see
     * the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final float gt(final float value) {
        return EasyMock.gt(value);
    }

    /**
     * Expects an int argument greater than the given value. For details, see
     * the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final int gt(final int value) {
        return EasyMock.gt(value);
    }

    /**
     * Expects a long argument greater than the given value. For details, see
     * the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final long gt(final long value) {
        return EasyMock.gt(value);
    }

    /**
     * Expects a short argument greater than the given value. For details, see
     * the EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final short gt(final short value) {
        return EasyMock.gt(value);
    }

    /**
     * Expects a comparable argument less than the given value. For details, see
     * the EasyMock documentation.
     * 
     * @param <T>
     *            type of the method argument to match
     * @param value
     *            the given value.
     * @return <code>null</code>.
     */
    protected final <T extends Comparable<T>> T lt(final Comparable<T> value) {
        return EasyMock.lt(value);
    }

    /**
     * Expects a byte argument less than the given value. For details, see the
     * EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final byte lt(final byte value) {
        return EasyMock.lt(value);
    }

    /**
     * Expects a double argument less than the given value. For details, see the
     * EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final double lt(final double value) {
        return EasyMock.lt(value);
    }

    /**
     * Expects a float argument less than the given value. For details, see the
     * EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final float lt(final float value) {
        return EasyMock.lt(value);
    }

    /**
     * Expects an int argument less than the given value. For details, see the
     * EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final int lt(final int value) {
        return EasyMock.lt(value);
    }

    /**
     * Expects a long argument less than the given value. For details, see the
     * EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final long lt(final long value) {
        return EasyMock.lt(value);
    }

    /**
     * Expects a short argument less than the given value. For details, see the
     * EasyMock documentation.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final short lt(final short value) {
        return EasyMock.lt(value);
    }

    /**
     * Expects an object implementing the given class. For details, see the
     * EasyMock documentation.
     * 
     * @param <T>
     *            the accepted type.
     * @param clazz
     *            the class of the accepted type.
     * @return <code>null</code>.
     */
    protected final <T> T isA(final Class<T> clazz) {
        return EasyMock.isA(clazz);
    }

    /**
     * Expects a string that contains the given substring. For details, see the
     * EasyMock documentation.
     * 
     * @param substring
     *            the substring.
     * @return <code>null</code>.
     */
    protected final String contains(final String substring) {
        return EasyMock.contains(substring);
    }

    /**
     * Expects a boolean that matches both given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>false</code>.
     */
    protected final boolean and(final boolean first, final boolean second) {
        return EasyMock.and(first, second);
    }

    /**
     * Expects a byte that matches both given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>0</code>.
     */
    protected final byte and(final byte first, final byte second) {
        return EasyMock.and(first, second);
    }

    /**
     * Expects a char that matches both given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>0</code>.
     */
    protected final char and(final char first, final char second) {
        return EasyMock.and(first, second);
    }

    /**
     * Expects a double that matches both given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>0</code>.
     */
    protected final double and(final double first, final double second) {
        return EasyMock.and(first, second);
    }

    /**
     * Expects a float that matches both given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>0</code>.
     */
    protected final float and(final float first, final float second) {
        return EasyMock.and(first, second);
    }

    /**
     * Expects an int that matches both given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>0</code>.
     */
    protected final int and(final int first, final int second) {
        return EasyMock.and(first, second);
    }

    /**
     * Expects a long that matches both given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>0</code>.
     */
    protected final long and(final long first, final long second) {
        return EasyMock.and(first, second);
    }

    /**
     * Expects a short that matches both given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>0</code>.
     */
    protected final short and(final short first, final short second) {
        return EasyMock.and(first, second);
    }

    /**
     * Expects an Object that matches both given expectations.
     * 
     * @param <T>
     *            the type of the object, it is passed through to prevent casts.
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>null</code>.
     */
    protected final <T> T and(final T first, final T second) {
        return EasyMock.and(first, second);
    }

    /**
     * Expects a boolean that matches one of the given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>false</code>.
     */
    protected final boolean or(final boolean first, final boolean second) {
        return EasyMock.or(first, second);
    }

    /**
     * Expects a byte that matches one of the given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>0</code>.
     */
    protected final byte or(final byte first, final byte second) {
        return EasyMock.or(first, second);
    }

    /**
     * Expects a char that matches one of the given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>0</code>.
     */
    protected final char or(final char first, final char second) {
        return EasyMock.or(first, second);
    }

    /**
     * Expects a double that matches one of the given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>0</code>.
     */
    protected final double or(final double first, final double second) {
        return EasyMock.or(first, second);
    }

    /**
     * Expects a float that matches one of the given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>0</code>.
     */
    protected final float or(final float first, final float second) {
        return EasyMock.or(first, second);
    }

    /**
     * Expects an int that matches one of the given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>0</code>.
     */
    protected final int or(final int first, final int second) {
        return EasyMock.or(first, second);
    }

    /**
     * Expects a long that matches one of the given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>0</code>.
     */
    protected final long or(final long first, final long second) {
        return EasyMock.or(first, second);
    }

    /**
     * Expects a short that matches one of the given expectations.
     * 
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>0</code>.
     */
    protected final short or(final short first, final short second) {
        return EasyMock.or(first, second);
    }

    /**
     * Expects an Object that matches one of the given expectations.
     * 
     * @param <T>
     *            the type of the object, it is passed through to prevent casts.
     * @param first
     *            placeholder for the first expectation.
     * @param second
     *            placeholder for the second expectation.
     * @return <code>null</code>.
     */
    protected final <T> T or(final T first, final T second) {
        return EasyMock.or(first, second);
    }

    /**
     * Expects a boolean that does not match the given expectation.
     * 
     * @param first
     *            placeholder for the expectation.
     * @return <code>false</code>.
     */
    protected final boolean not(final boolean first) {
        return EasyMock.not(first);
    }

    /**
     * Expects a byte that does not match the given expectation.
     * 
     * @param first
     *            placeholder for the expectation.
     * @return <code>0</code>.
     */
    protected final byte not(final byte first) {
        return EasyMock.not(first);
    }

    /**
     * Expects a char that does not match the given expectation.
     * 
     * @param first
     *            placeholder for the expectation.
     * @return <code>0</code>.
     */
    protected final char not(final char first) {
        return EasyMock.not(first);
    }

    /**
     * Expects a double that does not match the given expectation.
     * 
     * @param first
     *            placeholder for the expectation.
     * @return <code>0</code>.
     */
    protected final double not(final double first) {
        return EasyMock.not(first);
    }

    /**
     * Expects a float that does not match the given expectation.
     * 
     * @param first
     *            placeholder for the expectation.
     * @return <code>0</code>.
     */
    protected final float not(final float first) {
        return EasyMock.not(first);
    }

    /**
     * Expects an int that does not match the given expectation.
     * 
     * @param first
     *            placeholder for the expectation.
     * @return <code>0</code>.
     */
    protected final int not(final int first) {
        return EasyMock.not(first);
    }

    /**
     * Expects a long that does not match the given expectation.
     * 
     * @param first
     *            placeholder for the expectation.
     * @return <code>0</code>.
     */
    protected final long not(final long first) {
        return EasyMock.not(first);
    }

    /**
     * Expects a short that does not match the given expectation.
     * 
     * @param first
     *            placeholder for the expectation.
     * @return <code>0</code>.
     */
    protected final short not(final short first) {
        return EasyMock.not(first);
    }

    /**
     * Expects an Object that does not match the given expectation.
     * 
     * @param <T>
     *            the type of the object, it is passed through to prevent casts.
     * @param first
     *            placeholder for the expectation.
     * @return <code>null</code>.
     */
    protected final <T> T not(final T first) {
        return EasyMock.not(first);
    }

    /**
     * Expects a boolean that is equal to the given value.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final boolean eq(final boolean value) {
        return EasyMock.eq(value);
    }

    /**
     * Expects a byte that is equal to the given value.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final byte eq(final byte value) {
        return EasyMock.eq(value);
    }

    /**
     * Expects a char that is equal to the given value.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final char eq(final char value) {
        return EasyMock.eq(value);
    }

    /**
     * Expects a double that is equal to the given value.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final double eq(final double value) {
        return EasyMock.eq(value);
    }

    /**
     * Expects a float that is equal to the given value.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final float eq(final float value) {
        return EasyMock.eq(value);
    }

    /**
     * Expects an int that is equal to the given value.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final int eq(final int value) {
        return EasyMock.eq(value);
    }

    /**
     * Expects a long that is equal to the given value.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final long eq(final long value) {
        return EasyMock.eq(value);
    }

    /**
     * Expects a short that is equal to the given value.
     * 
     * @param value
     *            the given value.
     * @return <code>0</code>.
     */
    protected final short eq(final short value) {
        return EasyMock.eq(value);
    }

    /**
     * Expects an Object that is equal to the given value.
     * 
     * @param <T>
     *            type of the method argument to match
     * @param value
     *            the given value.
     * @return <code>null</code>.
     */
    protected final <T> T eq(final T value) {
        return EasyMock.eq(value);
    }

    /**
     * Expects a boolean array that is equal to the given array, i.e. it has to
     * have the same length, and each element has to be equal.
     * 
     * @param value
     *            the given arry.
     * @return <code>null</code>.
     */
    protected final boolean[] aryEq(final boolean[] value) {
        return EasyMock.aryEq(value);
    }

    /**
     * Expects a byte array that is equal to the given array, i.e. it has to
     * have the same length, and each element has to be equal.
     * 
     * @param value
     *            the given arry.
     * @return <code>null</code>.
     */
    protected final byte[] aryEq(final byte[] value) {
        return EasyMock.aryEq(value);
    }

    /**
     * Expects a char array that is equal to the given array, i.e. it has to
     * have the same length, and each element has to be equal.
     * 
     * @param value
     *            the given arry.
     * @return <code>null</code>.
     */
    protected final char[] aryEq(final char[] value) {
        return EasyMock.aryEq(value);
    }

    /**
     * Expects a double array that is equal to the given array, i.e. it has to
     * have the same length, and each element has to be equal.
     * 
     * @param value
     *            the given arry.
     * @return <code>null</code>.
     */
    protected final double[] aryEq(final double[] value) {
        return EasyMock.aryEq(value);
    }

    /**
     * Expects a float array that is equal to the given array, i.e. it has to
     * have the same length, and each element has to be equal.
     * 
     * @param value
     *            the given arry.
     * @return <code>null</code>.
     */
    protected final float[] aryEq(final float[] value) {
        return EasyMock.aryEq(value);
    }

    /**
     * Expects an int array that is equal to the given array, i.e. it has to
     * have the same length, and each element has to be equal.
     * 
     * @param value
     *            the given arry.
     * @return <code>null</code>.
     */
    protected final int[] aryEq(final int[] value) {
        return EasyMock.aryEq(value);
    }

    /**
     * Expects a long array that is equal to the given array, i.e. it has to
     * have the same length, and each element has to be equal.
     * 
     * @param value
     *            the given arry.
     * @return <code>null</code>.
     */
    protected final long[] aryEq(final long[] value) {
        return EasyMock.aryEq(value);
    }

    /**
     * Expects a short array that is equal to the given array, i.e. it has to
     * have the same length, and each element has to be equal.
     * 
     * @param value
     *            the given arry.
     * @return <code>null</code>.
     */
    protected final short[] aryEq(final short[] value) {
        return EasyMock.aryEq(value);
    }

    /**
     * Expects an Object array that is equal to the given array, i.e. it has to
     * have the same type, length, and each element has to be equal.
     * 
     * @param <T>
     *            the type of the array, it is passed through to prevent casts.
     * @param value
     *            the given arry.
     * @return <code>null</code>.
     */
    protected final <T> T[] aryEq(final T[] value) {
        return EasyMock.aryEq(value);
    }

    /**
     * Expects null. To work well with generics, this matcher (and
     * {@link #isNull(Class)}) can be used in these three ways:
     * <ul>
     * <li><code>(T)EasyMock.isNull() // explicit cast</code></li>
     * <li>
     * <code>EasyMock.&lt;T&gt; isNull() // fixing the returned generic</code></li>
     * <li>
     * <code>EasyMock.isNull(T.class) // pass the returned type in parameter</code>
     * </li>
     * </ul>
     * 
     * @param <T>
     *            type of the method argument to match
     * @return <code>null</code>.
     */
    protected final <T> T isNull() {
        return EasyMock.isNull();
    }

    /**
     * Expects null. To work well with generics, this matcher can be used in
     * three different ways. See {@link #isNull()}.
     * 
     * @param <T>
     *            type of the method argument to match
     * @param clazz
     *            the class of the argument to match
     * @return <code>null</code>.
     * 
     * @see #isNull()
     */
    protected final <T> T isNull(final Class<T> clazz) {
        return EasyMock.isNull(clazz);
    }

    /**
     * Expects not null. To work well with generics, this matcher (and
     * {@link #notNull(Class)}) can be used in these three ways:
     * <ul>
     * <li><code>(T)EasyMock.notNull() // explicit cast</code></li>
     * <li>
     * <code>EasyMock.&lt;T&gt; notNull() // fixing the returned generic</code></li>
     * <li>
     * <code>EasyMock.notNull(T.class) // pass the returned type in parameter</code>
     * </li>
     * </ul>
     * 
     * @param <T>
     *            type of the method argument to match
     * @return <code>null</code>.
     */
    protected final <T> T notNull() {
        return EasyMock.notNull();
    }

    /**
     * Expects not null. To work well with generics, this matcher can be used in
     * three different ways. See {@link #notNull()}.
     * 
     * @param <T>
     *            type of the method argument to match
     * @param clazz
     *            the class of the argument to match
     * @return <code>null</code>.
     * 
     * @see #notNull()
     */
    protected final <T> T notNull(final Class<T> clazz) {
        return EasyMock.notNull(clazz);
    }

    /**
     * Expects a string that contains a substring that matches the given regular
     * expression. For details, see the EasyMock documentation.
     * 
     * @param regex
     *            the regular expression.
     * @return <code>null</code>.
     */
    protected final String find(final String regex) {
        return EasyMock.find(regex);
    }

    /**
     * Expects a string that matches the given regular expression. For details,
     * see the EasyMock documentation.
     * 
     * @param regex
     *            the regular expression.
     * @return <code>null</code>.
     */
    protected final String matches(final String regex) {
        return EasyMock.matches(regex);
    }

    /**
     * Expects a string that starts with the given prefix. For details, see the
     * EasyMock documentation.
     * 
     * @param prefix
     *            the prefix.
     * @return <code>null</code>.
     */
    protected final String startsWith(final String prefix) {
        return EasyMock.startsWith(prefix);
    }

    /**
     * Expects a string that ends with the given suffix. For details, see the
     * EasyMock documentation.
     * 
     * @param suffix
     *            the suffix.
     * @return <code>null</code>.
     */
    protected final String endsWith(final String suffix) {
        return EasyMock.endsWith(suffix);
    }

    /**
     * Expects a double that has an absolute difference to the given value that
     * is less than the given delta. For details, see the EasyMock
     * documentation.
     * 
     * @param value
     *            the given value.
     * @param delta
     *            the given delta.
     * @return <code>0</code>.
     */
    protected final double eq(final double value, final double delta) {
        return EasyMock.eq(value, delta);
    }

    /**
     * Expects a float that has an absolute difference to the given value that
     * is less than the given delta. For details, see the EasyMock
     * documentation.
     * 
     * @param value
     *            the given value.
     * @param delta
     *            the given delta.
     * @return <code>0</code>.
     */
    protected final float eq(final float value, final float delta) {
        return EasyMock.eq(value, delta);
    }

    /**
     * Expects an Object that is the same as the given value. For details, see
     * the EasyMock documentation.
     * 
     * @param <T>
     *            the type of the object, it is passed through to prevent casts.
     * @param value
     *            the given value.
     * @return <code>null</code>.
     */
    protected final <T> T same(final T value) {
        return EasyMock.same(value);
    }

    /**
     * Expects a comparable argument equals to the given value according to
     * their compareTo method. For details, see the EasMock documentation.
     * 
     * @param <T>
     *            type of the method argument to match
     * @param value
     *            the given value.
     * @return <code>null</code>.
     */
    protected final <T extends Comparable<T>> T cmpEq(final Comparable<T> value) {
        return EasyMock.cmpEq(value);
    }

    /**
     * Expects an argument that will be compared using the provided comparator.
     * The following comparison will take place:
     * <p>
     * <code>comparator.compare(actual, expected) operator 0</code>
     * </p>
     * For details, see the EasyMock documentation.
     * 
     * @param <T>
     *            type of the method argument to match
     * @param value
     *            the given value.
     * @param comparator
     *            Comparator used to compare the actual with expected value.
     * @param operator
     *            The comparison operator.
     * @return <code>null</code>
     */
    protected final <T> T cmp(final T value, final Comparator<? super T> comparator,
            final LogicalOperator operator) {
        return EasyMock.cmp(value, comparator, operator);
    }

    /**
     * Expect any object but captures it for later use.
     * 
     * @param <T>
     *            Type of the captured object
     * @param captured
     *            Where the parameter is captured
     * @return <code>null</code>
     */
    protected final <T> T capture(final Capture<T> captured) {
        return EasyMock.capture(captured);
    }

    /**
     * Expect any boolean but captures it for later use.
     * 
     * @param captured
     *            Where the parameter is captured
     * @return <code>false</code>
     */
    protected final boolean captureBoolean(final Capture<Boolean> captured) {
        return EasyMock.captureBoolean(captured);
    }

    /**
     * Expect any int but captures it for later use.
     * 
     * @param captured
     *            Where the parameter is captured
     * @return <code>0</code>
     */
    protected final int captureInt(final Capture<Integer> captured) {
        return EasyMock.captureInt(captured);
    }

    /**
     * Expect any long but captures it for later use.
     * 
     * @param captured
     *            Where the parameter is captured
     * @return <code>0</code>
     */
    protected final long captureLong(final Capture<Long> captured) {
        return EasyMock.captureLong(captured);
    }

    /**
     * Expect any float but captures it for later use.
     * 
     * @param captured
     *            Where the parameter is captured
     * @return <code>0</code>
     */
    protected final float captureFloat(final Capture<Float> captured) {
        return EasyMock.captureFloat(captured);
    }

    /**
     * Expect any double but captures it for later use.
     * 
     * @param captured
     *            Where the parameter is captured
     * @return <code>0</code>
     */
    protected final double captureDouble(final Capture<Double> captured) {
        return EasyMock.captureDouble(captured);
    }

    /**
     * Expect any byte but captures it for later use.
     * 
     * @param captured
     *            Where the parameter is captured
     * @return <code>0</code>
     */
    protected final byte captureByte(final Capture<Byte> captured) {
        return EasyMock.captureByte(captured);
    }

    /**
     * Expect any char but captures it for later use.
     * 
     * @param captured
     *            Where the parameter is captured
     * @return <code>0</code>
     */
    protected final char captureChar(final Capture<Character> captured) {
        return EasyMock.captureChar(captured);
    }

    /**
     * Resets the given mock objects (more exactly: the controls of the mock
     * objects) and turn them to a mock with nice behavior. For details, see the
     * EasyMock documentation.
     * 
     * @param mocks
     *            the mock objects
     */
    protected final void resetToNice(final Object... mocks) {
        EasyMock.resetToNice(mocks);
    }

    /**
     * Resets the given mock objects (more exactly: the controls of the mock
     * objects) and turn them to a mock with default behavior. For details, see
     * the EasyMock documentation.
     * 
     * @param mocks
     *            the mock objects
     */
    protected final void resetToDefault(final Object... mocks) {
        EasyMock.resetToDefault(mocks);
    }

    /**
     * Resets the given mock objects (more exactly: the controls of the mock
     * objects) and turn them to a mock with strict behavior. For details, see
     * the EasyMock documentation.
     * 
     * @param mocks
     *            the mock objects
     */
    protected final void resetToStrict(final Object... mocks) {
        EasyMock.resetToStrict(mocks);
    }

    /**
     * Switches order checking of the given mock object (more exactly: the
     * control of the mock object) the on and off. For details, see the EasyMock
     * documentation.
     * 
     * @param mock
     *            the mock object.
     * @param state
     *            <code>true</code> switches order checking on,
     *            <code>false</code> switches it off.
     */
    protected final void checkOrder(final Object mock, final boolean state) {
        EasyMock.checkOrder(mock, state);
    }

    /**
     * Reports an argument matcher. This method is needed to define own argument
     * matchers. For details, see the EasyMock documentation.
     * 
     * @param matcher
     */
    protected final void reportMatcher(final IArgumentMatcher matcher) {
        EasyMock.reportMatcher(matcher);
    }

    /**
     * Returns the arguments of the current mock method call, if inside an
     * <code>IAnswer</code> callback - be careful here, reordering parameters of
     * method changes the semantics of your tests.
     * 
     * @return the arguments of the current mock method call.
     * @throws IllegalStateException
     *             if called outside of <code>IAnswer</code> callbacks.
     */
    protected final Object[] getCurrentArguments() {
        return EasyMock.getCurrentArguments();
    }

    /**
     * By default, a mock is thread safe (unless
     * {@link #NOT_THREAD_SAFE_BY_DEFAULT} is set). This method can change this
     * behavior. Two reasons are known for someone to do that: Performance or
     * dead-locking issues.
     * 
     * @param mock
     *            the mock to make thread safe
     * @param threadSafe
     *            If the mock should be thread safe or not
     */
    protected final void makeThreadSafe(final Object mock, final boolean threadSafe) {
        EasyMock.makeThreadSafe(mock, threadSafe);
    }

    /**
     * Tell that the mock should be used in only one thread. An exception will
     * be thrown if that's not the case. This can be useful when mocking an
     * object that isn't thread safe to make sure it is used correctly in a
     * multithreaded environment. By default, no check is done unless
     * {@link #ENABLE_THREAD_SAFETY_CHECK_BY_DEFAULT} was set to true.
     * 
     * @param mock
     *            the mock
     * @param shouldBeUsedInOneThread
     *            If the mock should be used in only one thread
     */
    protected final void checkIsUsedInOneThread(final Object mock, final boolean shouldBeUsedInOneThread) {
        EasyMock.checkIsUsedInOneThread(mock, shouldBeUsedInOneThread);
    }

    /**
     * Get the current value for an EasyMock property
     * 
     * @param key
     *            key for the property
     * @return the property value
     */
    protected final String getEasyMockProperty(final String key) {
        return EasyMock.getEasyMockProperty(key);
    }

    /**
     * Set a property to modify the default EasyMock behavior. These properties
     * can also be set as System properties or in easymock.properties. This
     * method can then be called to overload them. For details and a list of
     * available properties see the EasyMock documentation.
     * <p>
     * <b>Note:</b> This method is static. Setting a property will change the
     * entire EasyMock behavior.
     * 
     * @param key
     *            property key
     * @param value
     *            property value. A null value will remove the property
     * @return the previous property value
     */
    protected final String setEasyMockProperty(final String key, final String value) {
        return EasyMock.setEasyMockProperty(key, value);
    }

    /**
     * Creates a mock object that supports mocking of final and native methods.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methods
     *            optionally what methods to mock
     * @return the mock object.
     */
    protected final synchronized <T> T createMock(Class<T> type, Method... methods) {
        return PowerMock.createMock(type, methods);
    }

    /**
     * Creates a mock object that supports mocking of final and native methods.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @return the mock object.
     */
    protected final synchronized <T> T createMock(Class<T> type) {
        return PowerMock.createMock(type);
    }

    /**
     * Creates a mock object that supports mocking of final and native methods
     * and invokes a specific constructor.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param constructorArgs
     *            The constructor arguments that will be used to invoke a
     *            special constructor.
     * @param methods
     *            optionally what methods to mock
     * @return the mock object.
     */
    protected final <T> T createMock(Class<T> type, ConstructorArgs constructorArgs, Method... methods) {
        return PowerMock.createMock(type, constructorArgs, methods);
    }

    /**
     * Creates a mock object that supports mocking of final and native methods
     * and invokes a specific constructor based on the supplied argument values.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param constructorArguments
     *            The constructor arguments that will be used to invoke a
     *            certain constructor.
     * @return the mock object.
     */
    protected final <T> T createMock(Class<T> type, Object... constructorArguments) {
        return PowerMock.createMock(type, constructorArguments);
    }

    /**
     * Creates a strict mock object that supports mocking of final and native
     * methods.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methods
     *            optionally what methods to mock
     * @return the mock object.
     */
    protected final synchronized <T> T createStrictMock(Class<T> type, Method... methods) {
        return PowerMock.createStrictMock(type, methods);
    }

    /**
     * Creates a strict mock object that supports mocking of final and native
     * methods.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @return the mock object.
     */
    protected final synchronized <T> T createStrictMock(Class<T> type) {
        return PowerMock.createStrictMock(type);
    }

    /**
     * Creates a nice mock object that supports mocking of final and native
     * methods.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methods
     *            optionally what methods to mock
     * @return the mock object.
     */
    protected final synchronized <T> T createNiceMock(Class<T> type, Method... methods) {
        return PowerMock.createNiceMock(type, methods);
    }

    /**
     * Creates a nice mock object that supports mocking of final and native
     * methods.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @return the mock object.
     */
    protected final synchronized <T> T createNiceMock(Class<T> type) {
        return PowerMock.createNiceMock(type);
    }

    /**
     * Creates a strict mock object that supports mocking of final and native
     * methods and invokes a specific constructor.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param constructorArgs
     *            The constructor arguments that will be used to invoke a
     *            special constructor.
     * @param methods
     *            optionally what methods to mock
     * @return the mock object.
     */
    protected final <T> T createStrictMock(Class<T> type, ConstructorArgs constructorArgs, Method... methods) {
        return PowerMock.createStrictMock(type, constructorArgs, methods);
    }

    /**
     * Creates a nice mock object that supports mocking of final and native
     * methods and invokes a specific constructor.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param constructorArgs
     *            The constructor arguments that will be used to invoke a
     *            special constructor.
     * @param methods
     *            optionally what methods to mock
     * @return the mock object.
     */
    protected final <T> T createNiceMock(Class<T> type, ConstructorArgs constructorArgs, Method... methods) {
        return PowerMock.createNiceMock(type, constructorArgs, methods);
    }

    /**
     * Creates a strict mock object that supports mocking of final and native
     * methods and invokes a specific constructor based on the supplied argument
     * values.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param constructorArguments
     *            The constructor arguments that will be used to invoke a
     *            certain constructor.
     * @return the mock object.
     */
    protected final <T> T createStrictMock(Class<T> type, Object... constructorArguments) {
        return PowerMock.createStrictMock(type, constructorArguments);
    }

    /**
     * Creates a nice mock object that supports mocking of final and native
     * methods and invokes a specific constructor based on the supplied argument
     * values.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param constructorArguments
     *            The constructor arguments that will be used to invoke a
     *            certain constructor.
     * @return the mock object.
     */
    protected final <T> T createNiceMock(Class<T> type, Object... constructorArguments) {
        return PowerMock.createNiceMock(type, constructorArguments);
    }

    /**
     * Enable static mocking for a class.
     *
     * @param type
     *            the class to enable static mocking
     * @param methods
     *            optionally what methods to mock
     */
    protected final synchronized void mockStatic(Class<?> type, Method... methods) {
        PowerMock.mockStatic(type, methods);
    }

    /**
     * Enable static mocking for a class.
     *
     * @param type
     *            the class to enable static mocking
     */
    protected final synchronized void mockStatic(Class<?> type) {
        PowerMock.mockStatic(type);
    }

    /**
     * Enable strict static mocking for a class.
     *
     * @param type
     *            the class to enable static mocking
     * @param methods
     *            optionally what methods to mock
     */
    protected final synchronized void mockStaticStrict(Class<?> type, Method... methods) {
        PowerMock.mockStatic(type, methods);
    }

    /**
     * Enable strict static mocking for a class.
     *
     * @param type
     *            the class to enable static mocking
     */
    protected final synchronized void mockStaticStrict(Class<?> type) {
        PowerMock.mockStaticStrict(type);
    }

    /**
     * Enable nice static mocking for a class.
     *
     * @param type
     *            the class to enable static mocking
     * @param methods
     *            optionally what methods to mock
     */
    protected final synchronized void mockStaticNice(Class<?> type, Method... methods) {
        PowerMock.mockStaticNice(type, methods);
    }

    /**
     * Enable nice static mocking for a class.
     *
     * @param type
     *            the class to enable static mocking
     */
    protected final synchronized void mockStaticNice(Class<?> type) {
        PowerMock.mockStaticNice(type);
    }

    /**
     * A utility method that may be used to specify several methods that should
     * <i>not</i> be mocked in an easy manner (by just passing in the method
     * names of the method you wish <i>not</i> to mock). Note that you cannot
     * uniquely specify a method to exclude using this method if there are
     * several methods with the same name in <code>type</code>. This method will
     * mock ALL methods that doesn't match the supplied name(s) regardless of
     * parameter types and signature. If this is not the case you should
     * fall-back on using the {@link #createMock(Class, Method...)} method
     * instead.
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createPartialMockForAllMethodsExcept(Class<T> type, String... methodNames) {
        return PowerMock.createPartialMockForAllMethodsExcept(type, methodNames);
    }

    /**
     * A utility method that may be used to specify several methods that should
     * <i>not</i> be nicely mocked in an easy manner (by just passing in the
     * method names of the method you wish <i>not</i> to mock). Note that you
     * cannot uniquely specify a method to exclude using this method if there
     * are several methods with the same name in <code>type</code>. This method
     * will mock ALL methods that doesn't match the supplied name(s) regardless
     * of parameter types and signature. If this is not the case you should
     * fall-back on using the {@link #createMock(Class, Method...)} method
     * instead.
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createNicePartialMockForAllMethodsExcept(Class<T> type, String... methodNames) {
        return PowerMock.createNicePartialMockForAllMethodsExcept(type, methodNames);
    }

    /**
     * A utility method that may be used to specify several methods that should
     * <i>not</i> be strictly mocked in an easy manner (by just passing in the
     * method names of the method you wish <i>not</i> to mock). Note that you
     * cannot uniquely specify a method to exclude using this method if there
     * are several methods with the same name in <code>type</code>. This method
     * will mock ALL methods that doesn't match the supplied name(s) regardless
     * of parameter types and signature. If this is not the case you should
     * fall-back on using the {@link #createMock(Class, Method...)} method
     * instead.
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createStrictPartialMockForAllMethodsExcept(Class<T> type, String... methodNames) {
        return PowerMock.createStrictPartialMockForAllMethodsExcept(type, methodNames);
    }

    /**
     * Mock all methods of a class except for a specific one. Use this method
     * only if you have several overloaded methods.
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param methodNameToExclude
     *            The name of the method not to mock.
     * @param firstArgumentType
     *            The type of the first parameter of the method not to mock
     * @param moreTypes
     *            Optionally more parameter types that defines the method. Note
     *            that this is only needed to separate overloaded methods.
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createPartialMockForAllMethodsExcept(Class<T> type, String methodNameToExclude,
            Class<?> firstArgumentType, Class<?>... moreTypes) {
        return PowerMock.createPartialMockForAllMethodsExcept(type, methodNameToExclude, firstArgumentType, moreTypes);
    }

    /**
     * Mock all methods of a class except for a specific one nicely. Use this
     * method only if you have several overloaded methods.
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param methodNameToExclude
     *            The name of the method not to mock.
     * @param firstArgumentType
     *            The type of the first parameter of the method not to mock
     * @param moreTypes
     *            Optionally more parameter types that defines the method. Note
     *            that this is only needed to separate overloaded methods.
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createNicePartialMockForAllMethodsExcept(Class<T> type,
            String methodNameToExclude, Class<?> firstArgumentType, Class<?>... moreTypes) {
        return PowerMock.createNicePartialMockForAllMethodsExcept(type, methodNameToExclude, firstArgumentType, moreTypes);
    }

    /**
     * Mock all methods of a class except for a specific one strictly. Use this
     * method only if you have several overloaded methods.
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param methodNameToExclude
     *            The name of the method not to mock.
     * @param firstArgumentType
     *            The type of the first parameter of the method not to mock
     * @param moreTypes
     *            Optionally more parameter types that defines the method. Note
     *            that this is only needed to separate overloaded methods.
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createStrictPartialMockForAllMethodsExcept(Class<T> type,
            String methodNameToExclude, Class<?> firstArgumentType, Class<?>... moreTypes) {
        return PowerMock.createStrictPartialMockForAllMethodsExcept(type, methodNameToExclude, firstArgumentType, moreTypes);
    }

    /**
     * Mock a single specific method. Use this to handle overloaded methods.
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param methodNameToMock
     *            The name of the method to mock
     * @param firstArgumentType
     *            The type of the first parameter of the method to mock
     * @param additionalArgumentTypes
     *            Optionally more parameter types that defines the method. Note
     *            that this is only needed to separate overloaded methods.
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createPartialMock(Class<T> type, String methodNameToMock,
            Class<?> firstArgumentType, Class<?>... additionalArgumentTypes) {
        return PowerMock.createPartialMock(type, methodNameToMock, firstArgumentType, additionalArgumentTypes);
    }

    /**
     * Strictly mock a single specific method. Use this to handle overloaded
     * methods.
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param methodNameToMock
     *            The name of the method to mock
     * @param firstArgumentType
     *            The type of the first parameter of the method to mock
     * @param additionalArgumentTypes
     *            Optionally more parameter types that defines the method. Note
     *            that this is only needed to separate overloaded methods.
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createStrictPartialMock(Class<T> type, String methodNameToMock,
            Class<?> firstArgumentType, Class<?>... additionalArgumentTypes) {
        return PowerMock.createStrictPartialMock(type, methodNameToMock, firstArgumentType, additionalArgumentTypes);
    }

    /**
     * Nicely mock a single specific method. Use this to handle overloaded
     * methods.
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param methodNameToMock
     *            The name of the method to mock
     * @param firstArgumentType
     *            The type of the first parameter of the method to mock
     * @param additionalArgumentTypes
     *            Optionally more parameter types that defines the method. Note
     *            that this is only needed to separate overloaded methods.
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createNicePartialMock(Class<T> type, String methodNameToMock,
            Class<?> firstArgumentType, Class<?>... additionalArgumentTypes) {
        return PowerMock.createNicePartialMock(type, methodNameToMock, firstArgumentType, additionalArgumentTypes);
    }

    /**
     * Mock a single static method.
     *
     * @param clazz
     *            The class where the method is specified in.
     * @param methodNameToMock
     *            The first argument
     * @param firstArgumentType
     *            The first argument type.
     * @param additionalArgumentTypes
     *            Optional additional argument types.
     */
    protected final synchronized void mockStaticPartial(Class<?> clazz, String methodNameToMock,
            Class<?> firstArgumentType, Class<?>... additionalArgumentTypes) {
        PowerMock.mockStaticPartial(clazz, methodNameToMock, firstArgumentType, additionalArgumentTypes);
    }

    /**
     * Mock a single static method (strict).
     *
     * @param clazz
     *            The class where the method is specified in.
     * @param methodNameToMock
     *            The first argument
     * @param firstArgumentType
     *            The first argument type.
     * @param additionalArgumentTypes
     *            Optional additional argument types.
     */
    protected final synchronized void mockStaticPartialStrict(Class<?> clazz, String methodNameToMock,
            Class<?> firstArgumentType, Class<?>... additionalArgumentTypes) {
        PowerMock.mockStaticPartialStrict(clazz, methodNameToMock, firstArgumentType, additionalArgumentTypes);
    }

    /**
     * Mock a single static method (nice).
     *
     * @param clazz
     *            The class where the method is specified in.
     * @param methodNameToMock
     *            The first argument
     * @param firstArgumentType
     *            The first argument type.
     * @param additionalArgumentTypes
     *            Optional additional argument types.
     */
    protected final synchronized void mockStaticPartialNice(Class<?> clazz, String methodNameToMock,
            Class<?> firstArgumentType, Class<?>... additionalArgumentTypes) {
        PowerMock.mockStaticPartialNice(clazz, methodNameToMock, firstArgumentType, additionalArgumentTypes);
    }

    /**
     * A utility method that may be used to mock several <b>static</b> methods
     * in an easy way (by just passing in the method names of the method you
     * wish to mock). Note that you cannot uniquely specify a method to mock
     * using this method if there are several methods with the same name in
     * <code>type</code>. This method will mock ALL methods that match the
     * supplied name regardless of parameter types and signature. If this is the
     * case you should fall-back on using the
     * {@link #mockStatic(Class, Method...)} method instead.
     *
     * @param clazz
     *            The class that contains the static methods that should be
     *            mocked.
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #mockStatic(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     */
    protected final synchronized void mockStaticPartial(Class<?> clazz, String... methodNames) {
        PowerMock.mockStaticPartial(clazz, methodNames);
    }

    /**
     * A utility method that may be used to mock several <b>static</b> methods
     * (strict) in an easy way (by just passing in the method names of the
     * method you wish to mock). Note that you cannot uniquely specify a method
     * to mock using this method if there are several methods with the same name
     * in <code>type</code>. This method will mock ALL methods that match the
     * supplied name regardless of parameter types and signature. If this is the
     * case you should fall-back on using the
     * {@link #mockStaticStrict(Class, Method...)} method instead.
     *
     * @param clazz
     *            The class that contains the static methods that should be
     *            mocked.
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #mockStatic(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     */
    protected final synchronized void mockStaticPartialStrict(Class<?> clazz, String... methodNames) {
        PowerMock.mockStaticPartialStrict(clazz, methodNames);
    }

    /**
     * A utility method that may be used to mock several <b>static</b> methods
     * (nice) in an easy way (by just passing in the method names of the method
     * you wish to mock). Note that you cannot uniquely specify a method to mock
     * using this method if there are several methods with the same name in
     * <code>type</code>. This method will mock ALL methods that match the
     * supplied name regardless of parameter types and signature. If this is the
     * case you should fall-back on using the
     * {@link #mockStaticStrict(Class, Method...)} method instead.
     *
     * @param clazz
     *            The class that contains the static methods that should be
     *            mocked.
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #mockStatic(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     */
    protected final synchronized void mockStaticPartialNice(Class<?> clazz, String... methodNames) {
        PowerMock.mockStaticPartialNice(clazz, methodNames);
    }

    /**
     * A utility method that may be used to mock several methods in an easy way
     * (by just passing in the method names of the method you wish to mock).
     * Note that you cannot uniquely specify a method to mock using this method
     * if there are several methods with the same name in <code>type</code>.
     * This method will mock ALL methods that match the supplied name regardless
     * of parameter types and signature. If this is the case you should
     * fall-back on using the {@link #createMock(Class, Method...)} method
     * instead.
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createPartialMock(Class<T> type, String... methodNames) {
        return PowerMock.createPartialMock(type, methodNames);
    }

    /**
     * A utility method that may be used to mock several methods in an easy way
     * (by just passing in the method names of the method you wish to mock).
     * Note that you cannot uniquely specify a method to mock using this method
     * if there are several methods with the same name in <code>type</code>.
     * This method will mock ALL methods that match the supplied name regardless
     * of parameter types and signature. If this is the case you should
     * fall-back on using the {@link #createMock(Class, Method...)} method
     * instead.
     * <p>
     * With this method you can specify where the class hierarchy the methods
     * are located. This is useful in, for example, situations where class A
     * extends B and both have a method called "mockMe" (A overrides B's mockMe
     * method) and you like to specify the only the "mockMe" method in B should
     * be mocked. "mockMe" in A should be left intact. In this case you should
     * do:
     *
     * <pre>
     * A tested = createPartialMock(A.class, B.class, &quot;mockMe&quot;);
     * </pre>
     *
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param where
     *            Where in the class hierarchy the methods resides.
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createPartialMock(Class<T> type, Class<? super T> where, String... methodNames) {
        return PowerMock.createPartialMock(type, where, methodNames);
    }

    /**
     * A utility method that may be used to strictly mock several methods in an
     * easy way (by just passing in the method names of the method you wish to
     * mock). Note that you cannot uniquely specify a method to mock using this
     * method if there are several methods with the same name in
     * <code>type</code>. This method will mock ALL methods that match the
     * supplied name regardless of parameter types and signature. If this is the
     * case you should fall-back on using the
     * {@link #createMock(Class, Method...)} method instead.
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createStrictPartialMock(Class<T> type, String... methodNames) {
        return PowerMock.createStrictPartialMock(type, methodNames);
    }

    /**
     * A utility method that may be used to strictly mock several methods in an
     * easy way (by just passing in the method names of the method you wish to
     * mock). Note that you cannot uniquely specify a method to mock using this
     * method if there are several methods with the same name in
     * <code>type</code>. This method will mock ALL methods that match the
     * supplied name regardless of parameter types and signature. If this is the
     * case you should fall-back on using the
     * {@link #createMock(Class, Method...)} method instead.
     * <p>
     * With this method you can specify where the class hierarchy the methods
     * are located. This is useful in, for example, situations where class A
     * extends B and both have a method called "mockMe" (A overrides B's mockMe
     * method) and you like to specify the only the "mockMe" method in B should
     * be mocked. "mockMe" in A should be left intact. In this case you should
     * do:
     *
     * <pre>
     * A tested = createPartialMockStrict(A.class, B.class, &quot;mockMe&quot;);
     * </pre>
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param where
     *            Where in the class hierarchy the methods resides.
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createStrictPartialMock(Class<T> type, Class<? super T> where,
            String... methodNames) {
        return PowerMock.createStrictPartialMock(type, where, methodNames);
    }

    /**
     * A utility method that may be used to nicely mock several methods in an
     * easy way (by just passing in the method names of the method you wish to
     * mock). Note that you cannot uniquely specify a method to mock using this
     * method if there are several methods with the same name in
     * <code>type</code>. This method will mock ALL methods that match the
     * supplied name regardless of parameter types and signature. If this is the
     * case you should fall-back on using the
     * {@link #createMock(Class, Method...)} method instead.
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createNicePartialMock(Class<T> type, String... methodNames) {
        return PowerMock.createNicePartialMock(type, methodNames);
    }

    /**
     * A utility method that may be used to nicely mock several methods in an
     * easy way (by just passing in the method names of the method you wish to
     * mock). Note that you cannot uniquely specify a method to mock using this
     * method if there are several methods with the same name in
     * <code>type</code>. This method will mock ALL methods that match the
     * supplied name regardless of parameter types and signature. If this is the
     * case you should fall-back on using the
     * {@link #createMock(Class, Method...)} method instead.
     * <p>
     * With this method you can specify where the class hierarchy the methods
     * are located. This is useful in, for example, situations where class A
     * extends B and both have a method called "mockMe" (A overrides B's mockMe
     * method) and you like to specify the only the "mockMe" method in B should
     * be mocked. "mockMe" in A should be left intact. In this case you should
     * do:
     *
     * <pre>
     * A tested = createPartialMockNice(A.class, B.class, &quot;mockMe&quot;);
     * </pre>
     *
     * @param <T>
     *            The type of the mock.
     * @param type
     *            The type that'll be used to create a mock instance.
     * @param where
     *            Where in the class hierarchy the methods resides.
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @return A mock object of type <T>.
     */
    protected final synchronized <T> T createNicePartialMock(Class<T> type, Class<? super T> where, String... methodNames) {
        return PowerMock.createNicePartialMock(type, where, methodNames);
    }

    /**
     * A utility method that may be used to mock several methods in an easy way
     * (by just passing in the method names of the method you wish to mock). The
     * mock object created will support mocking of final methods and invokes the
     * default constructor (even if it's private).
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @return the mock object.
     */
    protected final <T> T createPartialMockAndInvokeDefaultConstructor(Class<T> type, String... methodNames)
            throws Exception {
        return PowerMock.createPartialMockAndInvokeDefaultConstructor(type, methodNames);
    }

    /**
     * A utility method that may be used to nicely mock several methods in an
     * easy way (by just passing in the method names of the method you wish to
     * mock). The mock object created will support mocking of final methods and
     * invokes the default constructor (even if it's private).
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @return the mock object.
     */
    protected final <T> T createNicePartialMockAndInvokeDefaultConstructor(Class<T> type, String... methodNames)
            throws Exception {
        return PowerMock.createNicePartialMockAndInvokeDefaultConstructor(type, methodNames);
    }

    /**
     * A utility method that may be used to strictly mock several methods in an
     * easy way (by just passing in the method names of the method you wish to
     * mock). The mock object created will support mocking of final methods and
     * invokes the default constructor (even if it's private).
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @return the mock object.
     */
    protected final <T> T createStrictPartialMockAndInvokeDefaultConstructor(Class<T> type, String... methodNames)
            throws Exception {
        return PowerMock.createStrictPartialMockAndInvokeDefaultConstructor(type, methodNames);
    }

    /**
     * A utility method that may be used to mock several methods in an easy way
     * (by just passing in the method names of the method you wish to mock). The
     * mock object created will support mocking of final and native methods and
     * invokes a specific constructor based on the supplied argument values.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @param constructorArguments
     *            The constructor arguments that will be used to invoke a
     *            certain constructor. (optional)
     * @return the mock object.
     */
    protected final <T> T createPartialMock(Class<T> type, String[] methodNames, Object... constructorArguments) {
        return PowerMock.createPartialMock(type, methodNames, constructorArguments);
    }

    /**
     * * A utility method that may be used to strictly mock several methods in
     * an easy way (by just passing in the method names of the method you wish
     * to mock). The mock object created will support mocking of final and
     * native methods and invokes a specific constructor based on the supplied
     * argument values.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @param constructorArguments
     *            The constructor arguments that will be used to invoke a
     *            certain constructor. (optional)
     * @return the mock object.
     */
    protected final <T> T createStrictPartialMock(Class<T> type, String[] methodNames, Object... constructorArguments) {
        return PowerMock.createStrictPartialMock(type, methodNames, constructorArguments);
    }

    /**
     * * A utility method that may be used to nicely mock several methods in an
     * easy way (by just passing in the method names of the method you wish to
     * mock). The mock object created will support mocking of final and native
     * methods and invokes a specific constructor based on the supplied argument
     * values.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methodNames
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @param constructorArguments
     *            The constructor arguments that will be used to invoke a
     *            certain constructor. (optional)
     * @return the mock object.
     */
    protected final <T> T createNicePartialMock(Class<T> type, String[] methodNames, Object... constructorArguments) {
        return PowerMock.createNicePartialMock(type, methodNames, constructorArguments);
    }

    /**
     * A utility method that may be used to mock several methods in an easy way
     * (by just passing in the method names of the method you wish to mock). Use
     * this to handle overloaded methods. The mock object created will support
     * mocking of final and native methods and invokes a specific constructor
     * based on the supplied argument values.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methodName
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @param methodParameterTypes
     *            Parameter types that defines the method. Note that this is
     *            only needed to separate overloaded methods.
     * @param constructorArguments
     *            The constructor arguments that will be used to invoke a
     *            certain constructor. (optional)
     * @return the mock object.
     */
    protected final <T> T createPartialMock(Class<T> type, String methodName, Class<?>[] methodParameterTypes,
            Object... constructorArguments) {
        return PowerMock.createPartialMock(type, methodName, methodParameterTypes, constructorArguments);
    }

    /**
     * A utility method that may be used to strictly mock several methods in an
     * easy way (by just passing in the method names of the method you wish to
     * mock). Use this to handle overloaded methods. The mock object created
     * will support mocking of final and native methods and invokes a specific
     * constructor based on the supplied argument values.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methodName
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @param methodParameterTypes
     *            Parameter types that defines the method. Note that this is
     *            only needed to separate overloaded methods.
     * @param constructorArguments
     *            The constructor arguments that will be used to invoke a
     *            certain constructor. (optional)
     * @return the mock object.
     */
    protected final <T> T createStrictPartialMock(Class<T> type, String methodName, Class<?>[] methodParameterTypes,
            Object... constructorArguments) {
        return PowerMock.createStrictPartialMock(type, methodName, methodParameterTypes, constructorArguments);
    }

    /**
     * A utility method that may be used to nicely mock several methods in an
     * easy way (by just passing in the method names of the method you wish to
     * mock). Use this to handle overloaded methods. The mock object created
     * will support mocking of final and native methods and invokes a specific
     * constructor based on the supplied argument values.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methodName
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @param methodParameterTypes
     *            Parameter types that defines the method. Note that this is
     *            only needed to separate overloaded methods.
     * @param constructorArguments
     *            The constructor arguments that will be used to invoke a
     *            certain constructor. (optional)
     * @return the mock object.
     */
    protected final <T> T createNicePartialMock(Class<T> type, String methodName, Class<?>[] methodParameterTypes,
            Object... constructorArguments) {
        return PowerMock.createNicePartialMock(type, methodName, methodParameterTypes, constructorArguments);
    }

    /**
     * A utility method that may be used to mock several methods in an easy way
     * (by just passing in the method names of the method you wish to mock). Use
     * this to handle overloaded methods <i>and</i> overloaded constructors. The
     * mock object created will support mocking of final and native methods and
     * invokes a specific constructor based on the supplied argument values.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methodName
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @param methodParameterTypes
     *            Parameter types that defines the method. Note that this is
     *            only needed to separate overloaded methods.
     * @param constructorArguments
     *            The constructor arguments that will be used to invoke a
     *            certain constructor.
     * @param constructorParameterTypes
     *            Parameter types that defines the constructor that should be
     *            invoked. Note that this is only needed to separate overloaded
     *            constructors.
     * @return the mock object.
     */
    protected final <T> T createPartialMock(Class<T> type, String methodName, Class<?>[] methodParameterTypes,
            Object[] constructorArguments, Class<?>[] constructorParameterTypes) {
        return PowerMock.createPartialMock(type, methodName, methodParameterTypes, constructorArguments);
    }

    /**
     * A utility method that may be used to strictly mock several methods in an
     * easy way (by just passing in the method names of the method you wish to
     * mock). Use this to handle overloaded methods <i>and</i> overloaded
     * constructors. The mock object created will support mocking of final and
     * native methods and invokes a specific constructor based on the supplied
     * argument values.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methodName
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @param methodParameterTypes
     *            Parameter types that defines the method. Note that this is
     *            only needed to separate overloaded methods.
     * @param constructorArguments
     *            The constructor arguments that will be used to invoke a
     *            certain constructor.
     * @param constructorParameterTypes
     *            Parameter types that defines the constructor that should be
     *            invoked. Note that this is only needed to separate overloaded
     *            constructors.
     * @return the mock object.
     */
    protected final <T> T createStrictPartialMock(Class<T> type, String methodName, Class<?>[] methodParameterTypes,
            Object[] constructorArguments, Class<?>[] constructorParameterTypes) {
        return PowerMock.createStrictPartialMock(type, methodName, methodParameterTypes, constructorArguments, constructorParameterTypes);
    }

    /**
     * A utility method that may be used to nicely mock several methods in an
     * easy way (by just passing in the method names of the method you wish to
     * mock). Use this to handle overloaded methods <i>and</i> overloaded
     * constructors. The mock object created will support mocking of final and
     * native methods and invokes a specific constructor based on the supplied
     * argument values.
     *
     * @param <T>
     *            the type of the mock object
     * @param type
     *            the type of the mock object
     * @param methodName
     *            The names of the methods that should be mocked. If
     *            <code>null</code>, then this method will have the same effect
     *            as just calling {@link #createMock(Class, Method...)} with the
     *            second parameter as <code>new Method[0]</code> (i.e. all
     *            methods in that class will be mocked).
     * @param methodParameterTypes
     *            Parameter types that defines the method. Note that this is
     *            only needed to separate overloaded methods.
     * @param constructorArguments
     *            The constructor arguments that will be used to invoke a
     *            certain constructor.
     * @param constructorParameterTypes
     *            Parameter types that defines the constructor that should be
     *            invoked. Note that this is only needed to separate overloaded
     *            constructors.
     * @return the mock object.
     */
    protected final <T> T createNicePartialMock(Class<T> type, String methodName, Class<?>[] methodParameterTypes,
            Object[] constructorArguments, Class<?>[] constructorParameterTypes) {
        return PowerMock.createNicePartialMock(type, methodName, methodParameterTypes, constructorArguments, constructorParameterTypes);
    }

    /**
     * Used to specify expectations on private static methods. If possible use
     * variant with only method name.
     */
    protected final synchronized <T> IExpectationSetters<T> expectPrivate(Class<?> clazz, Method method,
            Object... arguments) throws Exception {
        return PowerMock.expectPrivate(clazz, method, arguments);
    }

    /**
     * Used to specify expectations on private methods. If possible use variant
     * with only method name.
     */
    protected final synchronized <T> IExpectationSetters<T> expectPrivate(Object instance, Method method,
            Object... arguments) throws Exception {
        return PowerMock.expectPrivate(instance, arguments);
    }

    /**
     * Used to specify expectations on private methods. Use this method to
     * handle overloaded methods.
     */
    @SuppressWarnings("all")
    protected final synchronized <T> IExpectationSetters<T> expectPrivate(Object instance, String methodName,
            Class<?>[] parameterTypes, Object... arguments) throws Exception {
        return PowerMock.expectPrivate(instance, methodName, parameterTypes, arguments);
    }

    /**
     * Used to specify expectations on methods using the method name. Works on
     * for example private or package private methods.
     */
    protected final synchronized <T> IExpectationSetters<T> expectPrivate(Object instance, String methodName,
            Object... arguments) throws Exception {
        return PowerMock.expectPrivate(instance, methodName, arguments);
    }

    /**
     * Used to specify expectations on methods without specifying a method name.
     * Works on for example private or package private methods. PowerMock tries
     * to find a unique method to expect based on the argument parameters. If
     * PowerMock is unable to locate a unique method you need to revert to using
     * {@link #expectPrivate(Object, String, Object...)}.
     */
    protected final synchronized <T> IExpectationSetters<T> expectPrivate(Object instance, Object... arguments)
            throws Exception {
        return PowerMock.expectPrivate(instance, arguments);
    }

    /**
     * Used to specify expectations on methods using the method name at a
     * specific place in the class hierarchy (specified by the
     * <code>where</code> parameter). Works on for example private or package
     * private methods.
     * <p>
     * Use this for overloaded methods.
     */
    protected final synchronized <T> IExpectationSetters<T> expectPrivate(Object instance, String methodName,
            Class<?> where, Class<?>[] parameterTypes, Object... arguments) throws Exception {
        return PowerMock.expectPrivate(instance, methodName, where, arguments);
    }

    /**
     * Used to specify expectations on methods using the method name at a
     * specific place in the class hierarchy (specified by the
     * <code>where</code> parameter). Works on for example private or package
     * private methods.
     */
    protected final synchronized <T> IExpectationSetters<T> expectPrivate(Object instance, String methodName,
            Class<?> where, Object... arguments) throws Exception {
        return PowerMock.expectPrivate(instance, methodName, where, arguments);
    }

    /**
     * This method just delegates to EasyMock class extensions
     * {@link org.easymock.EasyMock#expectLastCall()} method.
     *
     * @see org.easymock.EasyMock#expectLastCall()
     *
     * @return The expectation setter.
     */
    protected final synchronized IExpectationSetters<Object> expectLastCall() {
        return PowerMock.expectLastCall();
    }

    /**
     * Sometimes it is useful to allow replay and verify on non-mocks. For
     * example when using partial mocking in some tests and no mocking in other
     * test-methods, but using the same setUp and tearDown.
     */
    protected final synchronized void niceReplayAndVerify() {
        PowerMock.niceReplayAndVerify();
    }

    /**
     * Replay all classes and mock objects known by PowerMock. This includes all
     * classes that are prepared for test using the {@link PrepareForTest} or
     * {@link PrepareOnlyThisForTest} annotations and all classes that have had
     * their static initializers removed by using the
     * {@link SuppressStaticInitializationFor} annotation. It also includes all
     * mock instances created by PowerMock such as those created or used by
     * {@link #createMock(Class, Method...)},
     * {@link #mockStatic(Class, Method...)},
     * {@link #expectNew(Class, Object...)},
     * {@link #createPartialMock(Class, String...)} etc.
     * <p>
     * To make it easy to pass in additional mocks <i>not</i> created by the
     * PowerMock API you can optionally specify them as <tt>additionalMocks</tt>
     * . These are typically those mock objects you have created using pure
     * EasyMock or EasyMock class extensions. No additional mocks needs to be
     * specified if you're only using PowerMock API methods.
     * <p>
     * Note that the <tt>additionalMocks</tt> are also automatically verified
     * when invoking the {@link #verifyAll()} method.
     *
     * @param additionalMocks
     *            Mocks not created by the PowerMock API. These are typically
     *            those mock objects you have created using pure EasyMock or
     *            EasyMock class extensions.
     */
    protected final synchronized void replayAll(Object... additionalMocks) {
        PowerMock.replayAll(additionalMocks);
    }

    /**
     * Reset all classes and mock objects known by PowerMock. This includes all
     * classes that are prepared for test using the {@link PrepareForTest} or
     * {@link PrepareOnlyThisForTest} annotations and all classes that have had
     * their static initializers removed by using the
     * {@link SuppressStaticInitializationFor} annotation. It also includes all
     * mock instances created by PowerMock such as those created or used by
     * {@link #createMock(Class, Method...)},
     * {@link #mockStatic(Class, Method...)},
     * {@link #expectNew(Class, Object...)},
     * {@link #createPartialMock(Class, String...)} etc.
     * <p>
     * To make it easy to pass in additional mocks <i>not</i> created by the
     * PowerMock API you can optionally specify them as <tt>additionalMocks</tt>
     * . These are typically those mock objects you have created using pure
     * EasyMock or EasyMock class extensions. No additional mocks needs to be
     * specified if you're only using PowerMock API methods.
     *
     * @param additionalMocks
     *            Mocks not created by the PowerMock API. These are typically
     *            those mock objects you have created using pure EasyMock or
     *            EasyMock class extensions.
     */
    protected final synchronized void resetAll(Object... additionalMocks) {
        PowerMock.resetAll(additionalMocks);
    }

    /**
     * Reset a list of class mocks.
     */
    protected final synchronized void reset(Class<?>... classMocks) {
        PowerMock.reset(classMocks);
    }

    /**
     * Reset a list of mock objects or classes.
     */
    protected final synchronized void reset(Object... mocks) {
        PowerMock.reset(mocks);
    }

    /**
     * Verify all classes and mock objects known by PowerMock. This includes all
     * classes that are prepared for test using the {@link PrepareForTest} or
     * {@link PrepareOnlyThisForTest} annotations and all classes that have had
     * their static initializers removed by using the
     * {@link SuppressStaticInitializationFor} annotation. It also includes all
     * mock instances created by PowerMock such as those created or used by
     * {@link #createMock(Class, Method...)},
     * {@link #mockStatic(Class, Method...)},
     * {@link #expectNew(Class, Object...)},
     * {@link #createPartialMock(Class, String...)} etc.
     * <p>
     * Note that all <tt>additionalMocks</tt> passed to the
     * {@link #replayAll(Object...)} method are also verified here
     * automatically.
     *
     */
    protected final synchronized void verifyAll() {
        PowerMock.verifyAll();
    }

    /**
     * Switches the mocks or classes to replay mode. Note that you must use this
     * method when using PowerMock!
     *
     * @param mocks
     *            mock objects or classes loaded by PowerMock.
     * @throws Exception
     *             If something unexpected goes wrong.
     */
    protected final synchronized void replay(Object... mocks) {
        PowerMock.replay(mocks);
    }

    /**
     * Switches the mocks or classes to verify mode. Note that you must use this
     * method when using PowerMock!
     *
     * @param objects
     *            mock objects or classes loaded by PowerMock.
     */
    protected final synchronized void verify(Object... objects) {
        PowerMock.verify(objects);
    }

    /**
     * Convenience method for createMock followed by expectNew.
     *
     * @param type
     *            The class that should be mocked.
     * @param arguments
     *            The constructor arguments.
     * @return A mock object of the same type as the mock.
     * @throws Exception
     */
    protected final synchronized <T> T createMockAndExpectNew(Class<T> type, Object... arguments) throws Exception {
        return PowerMock.createMockAndExpectNew(type, arguments);
    }

    /**
     * Convenience method for createMock followed by expectNew when PowerMock
     * cannot determine which constructor to use automatically. This happens
     * when you have one constructor taking a primitive type and another one
     * taking the wrapper type of the primitive. For example <code>int</code>
     * and <code>Integer</code>.
     *
     * @param type
     *            The class that should be mocked.
     * @param parameterTypes
     *            The constructor parameter types.
     * @param arguments
     *            The constructor arguments.
     * @return A mock object of the same type as the mock.
     * @throws Exception
     */
    protected final synchronized <T> T createMockAndExpectNew(Class<T> type, Class<?>[] parameterTypes,
            Object... arguments) throws Exception {
        return PowerMock.createMockAndExpectNew(type, parameterTypes, arguments);
    }

    /**
     * Convenience method for createNiceMock followed by expectNew.
     *
     * @param type
     *            The class that should be mocked.
     * @param arguments
     *            The constructor arguments.
     * @return A mock object of the same type as the mock.
     * @throws Exception
     */
    protected final synchronized <T> T createNiceMockAndExpectNew(Class<T> type, Object... arguments) throws Exception {
        return PowerMock.createNiceMockAndExpectNew(type, arguments);
    }

    /**
     * Convenience method for createNiceMock followed by expectNew when
     * PowerMock cannot determine which constructor to use automatically. This
     * happens when you have one constructor taking a primitive type and another
     * one taking the wrapper type of the primitive. For example
     * <code>int</code> and <code>Integer</code>.
     *
     * @param type
     *            The class that should be mocked.
     * @param parameterTypes
     *            The constructor parameter types.
     * @param arguments
     *            The constructor arguments.
     * @return A mock object of the same type as the mock.
     * @throws Exception
     */
    protected final synchronized <T> T createNiceMockAndExpectNew(Class<T> type, Class<?>[] parameterTypes,
            Object... arguments) throws Exception {
        return PowerMock.createNiceMockAndExpectNew(type, parameterTypes, arguments);
    }

    /**
     * Convenience method for createStrictMock followed by expectNew.
     *
     * @param type
     *            The class that should be mocked.
     * @param arguments
     *            The constructor arguments.
     * @return A mock object of the same type as the mock.
     * @throws Exception
     */
    protected final synchronized <T> T createStrictMockAndExpectNew(Class<T> type, Object... arguments) throws Exception {
        return PowerMock.createStrictMockAndExpectNew(type, arguments);
    }

    /**
     * Convenience method for createStrictMock followed by expectNew when
     * PowerMock cannot determine which constructor to use automatically. This
     * happens when you have one constructor taking a primitive type and another
     * one taking the wrapper type of the primitive. For example
     * <code>int</code> and <code>Integer</code>.
     *
     * @param type
     *            The class that should be mocked.
     * @param parameterTypes
     *            The constructor parameter types.
     * @param arguments
     *            The constructor arguments.
     * @return A mock object of the same type as the mock.
     * @throws Exception
     */
    protected final synchronized <T> T createStrictMockAndExpectNew(Class<T> type, Class<?>[] parameterTypes,
            Object... arguments) throws Exception {
        return PowerMock.createStrictMockAndExpectNew(type, parameterTypes, arguments);
    }

    /**
     * Allows specifying expectations on new invocations. For example you might
     * want to throw an exception or return a mock. Note that you must replay
     * the class when using this method since this behavior is part of the class
     * mock.
     * <p>
     * Use this method when you need to specify parameter types for the
     * constructor when PowerMock cannot determine which constructor to use
     * automatically. In most cases you should use
     * {@link #expectNew(Class, Object...)} instead.
     */
    protected final synchronized <T> IExpectationSetters<T> expectNew(Class<T> type, Class<?>[] parameterTypes,
            Object... arguments) throws Exception {
        return PowerMock.expectNew(type, parameterTypes, arguments);
    }

    /**
     * Allows specifying expectations on new invocations. For example you might
     * want to throw an exception or return a mock. Note that you must replay
     * the class when using this method since this behavior is part of the class
     * mock.
     */
    protected final synchronized <T> IExpectationSetters<T> expectNew(Class<T> type, Object... arguments)
            throws Exception {
        return PowerMock.expectNew(type, arguments);
    }

    /**
     * Allows specifying expectations on new invocations for private member
     * (inner) classes, local or anonymous classes. For example you might want
     * to throw an exception or return a mock. Note that you must replay the
     * class when using this method since this behavior is part of the class
     * mock.
     *
     * @param fullyQualifiedName
     *            The fully-qualified name of the inner/local/anonymous type to
     *            expect.
     * @param arguments
     *            Optional number of arguments.
     */
    protected final synchronized <T> IExpectationSetters<T> expectNew(String fullyQualifiedName, Object... arguments)
            throws Exception {
        return PowerMock.expectNew(fullyQualifiedName, arguments);
    }

    /**
     * Allows specifying expectations on new invocations. For example you might
     * want to throw an exception or return a mock.
     * <p>
     * This method checks the order of constructor invocations.
     * <p>
     * Note that you must replay the class when using this method since this
     * behavior is part of the class mock.
     */
    protected final synchronized <T> IExpectationSetters<T> expectStrictNew(Class<T> type, Object... arguments)
            throws Exception {
        return PowerMock.expectStrictNew(type, arguments);
    }

    /**
     * Allows specifying expectations on new invocations. For example you might
     * want to throw an exception or return a mock. Note that you must replay
     * the class when using this method since this behavior is part of the class
     * mock.
     * <p>
     * This method checks the order of constructor invocations.
     * <p>
     * Use this method when you need to specify parameter types for the
     * constructor when PowerMock cannot determine which constructor to use
     * automatically. In most cases you should use
     * {@link #expectNew(Class, Object...)} instead.
     */
    protected final synchronized <T> IExpectationSetters<T> expectStrictNew(Class<T> type, Class<?>[] parameterTypes,
            Object... arguments) throws Exception {
        return PowerMock.expectStrictNew(type, parameterTypes, arguments);
    }

    /**
     * Allows specifying expectations on new invocations. For example you might
     * want to throw an exception or return a mock.
     * <p>
     * This method allows any number of calls to a new constructor without
     * throwing an exception.
     * <p>
     * Note that you must replay the class when using this method since this
     * behavior is part of the class mock.
     */
    protected final synchronized <T> IExpectationSetters<T> expectNiceNew(Class<T> type, Object... arguments)
            throws Exception {
        return PowerMock.expectNiceNew(type, arguments);
    }

    /**
     * Allows specifying expectations on new invocations. For example you might
     * want to throw an exception or return a mock. Note that you must replay
     * the class when using this method since this behavior is part of the class
     * mock.
     * <p>
     * This method allows any number of calls to a new constructor without
     * throwing an exception.
     * <p>
     * Use this method when you need to specify parameter types for the
     * constructor when PowerMock cannot determine which constructor to use
     * automatically. In most cases you should use
     * {@link #expectNew(Class, Object...)} instead.
     */
    protected final synchronized <T> IExpectationSetters<T> expectNiceNew(Class<T> type, Class<?>[] parameterTypes,
            Object... arguments) throws Exception {
        return PowerMock.expectNiceNew(type, parameterTypes, arguments);
    }
    
    /**
     * Returns the {@link org.fluttercode.datafactory.impl.DataFactory DataFactory} instance.
     * @return
     */
    private DataFactory getDataFactory() {
        if (df == null) {
            // initialize the DataFactory if it has not inialized yet
            synchronized (DataFactory.class) {
                if (df == null) {
                    df = new DataFactory();
                }
            }
        }
        return df;
    }
    
    /**
     * Returns a random item from a list of items.
     * 
     * @param <T>
     *            Item type in the list and to return
     * @param items
     *            List of items to choose from
     * @return Item from the list
     */
    protected final <T> T getItem(List<T> items) {
        return getDataFactory().getItem(items);
    }

    /**
     * Returns a random item from a list of items or the null depending on the
     * probability parameter. The probability determines the chance (in %) of
     * returning an item off the list versus null.
     * 
     * @param <T>
     *            Item type in the list and to return
     * @param items
     *            List of items to choose from
     * @param probability
     *            chance (in %, 100 being guaranteed) of returning an item from
     *            the list
     * @return Item from the list or null if the probability test fails.
     */
    protected final <T> T getItem(List<T> items, int probability) {
        return getDataFactory().getItem(items, probability);
    }

    /**
     * Returns a random item from a list of items or the defaultItem depending
     * on the probability parameter. The probability determines the chance (in
     * %) of returning an item off the list versus the default value.
     * 
     * @param <T>
     *            Item type in the list and to return
     * @param items
     *            List of items to choose from
     * @param probability
     *            chance (in %, 100 being guaranteed) of returning an item from
     *            the list
     * @param defaultItem
     *            value to return if the probability test fails
     * @return Item from the list or the default value
     */
    protected final <T> T getItem(List<T> items, int probability, T defaultItem) {
        return getDataFactory().getItem(items, probability, defaultItem);
    }

    /**
     * Returns a random item from an array of items
     * 
     * @param <T>
     *            Array item type and the type to return
     * @param items
     *            Array of items to choose from
     * @return Item from the array
     */
    protected final <T> T getItem(T[] items) {
        return getDataFactory().getItem(items);
    }

    /**
     * Returns a random item from an array of items or null depending on the
     * probability parameter. The probability determines the chance (in %) of
     * returning an item from the array versus null.
     * 
     * @param <T>
     *            Array item type and the type to return
     * @param items
     *            Array of items to choose from
     * @param probability
     *            chance (in %, 100 being guaranteed) of returning an item from
     *            the array
     * @return Item from the array or the default value
     */
    protected final <T> T getItem(T[] items, int probability) {
        return getDataFactory().getItem(items, probability);
    }

    /**
     * Returns a random item from an array of items or the defaultItem depending
     * on the probability parameter. The probability determines the chance (in
     * %) of returning an item from the array versus the default value.
     * 
     * @param <T>
     *            Array item type and the type to return
     * @param items
     *            Array of items to choose from
     * @param probability
     *            chance (in %, 100 being guaranteed) of returning an item from
     *            the array
     * @param defaultItem
     *            value to return if the probability test fails
     * @return Item from the array or the default value
     */
    protected final <T> T getItem(T[] items, int probability, T defaultItem) {
        return getDataFactory().getItem(items, probability, defaultItem);
    }

    /**
     * @return A random first name
     */
    protected final String getFirstName() {
        return getDataFactory().getFirstName();
    }

    /**
     * Returns a combination of first and last name values in one string
     * 
     * @return
     */
    protected final String getName() {
        return getDataFactory().getName();
    }

    /**
     * @return A random last name
     */
    protected final String getLastName() {
        return getDataFactory().getLastName();
    }

    /**
     * @return A random street name
     */
    protected final String getStreetName() {
        return getDataFactory().getStreetName();
    }

    /**
     * @return A random street suffix
     */
    protected final String getStreetSuffix() {
        return getDataFactory().getStreetSuffix();
    }

    /**
     * Generates a random city value
     * 
     * @return City as a string
     */
    protected final String getCity() {
        return getDataFactory().getCity();
    }

    /**
     * Generates an address value consisting of house number, street name and
     * street suffix. i.e. <code>543 Larkhill Road</code>
     * 
     * @return Address as a string
     */
    protected final String getAddress() {
        return getDataFactory().getAddress();
    }

    /**
     * Generates line 2 for a street address (usually an Apt. or Suite #).
     * Returns null if the probabilty test fails.
     * 
     * @param probability
     *            Chance as % of have a value returned instead of null.
     * @return Street address line two or null if the probability test fails
     */
    protected final String getAddressLine2(int probability) {
        return getDataFactory().getAddressLine2(probability);
    }

    /**
     * Generates line 2 for a street address (usually an Apt. or Suite #).
     * Returns default value if the probabilty test fails.
     * 
     * @param probability
     *            Chance as % of have a value returned instead of null.
     * @param defaultValue
     *            Value to return if the probability test fails.
     * @return Street address line two or null if the probability test fails
     */
    protected final String getAddressLine2(int probability, String defaultValue) {
        return getDataFactory().getAddressLine2(probability, defaultValue);
    }

    /**
     * Generates line 2 for a street address (usually an Apt. or Suite #).
     * Returns default value if the probabilty test fails.
     * 
     * @return Street address line 2
     */
    protected final String getAddressLine2() {
        return getDataFactory().getAddressLine2();
    }

    /**
     * Creates a random birthdate within the range of 1955 to 1985
     * 
     * @return Date representing a birthdate
     */
    protected final Date getBirthDate() {
        return getDataFactory().getBirthDate();
    }

    /**
     * Returns a random int value.
     * 
     * @return random number
     */
    protected final int getNumber() {
        return getDataFactory().getNumber();
    }

    /**
     * Returns a random number between 0 and max
     * 
     * @param max
     *            Maximum value of result
     * @return random number no more than max
     */
    protected final int getNumberUpTo(int max) {
        return getDataFactory().getNumberUpTo(max);
    }

    /**
     * Returns a number betwen min and max
     * 
     * @param min
     *            minimum value of result
     * @param max
     *            maximum value of result
     * @return Random number within range
     */
    protected final int getNumberBetween(int min, int max) {
        return getDataFactory().getNumberBetween(min, max);
    }

    /**
     * Builds a date from the year, month, day values passed in
     * 
     * @param year
     *            The year of the final {@link Date} result
     * @param month
     *            The month of the final {@link Date} result (from 1-12)
     * @param day
     *            The day of the final {@link Date} result
     * @return Date representing the passed in values.
     */
    protected final Date getDate(int year, int month, int day) {
        return getDataFactory().getDate(year, month, day);
    }

    /**
     * Returns a random date which is in the range <code>baseData</code> +
     * <code>minDaysFromData</code> to <code>baseData</code> +
     * <code>maxDaysFromData</code>. This method does not alter the time
     * component and the time is set to the time value of the base date.
     * 
     * @param baseDate
     *            Date to start from
     * @param minDaysFromDate
     *            minimum number of days from the baseDate the result can be
     * @param maxDaysFromDate
     *            maximum number of days from the baseDate the result can be
     * @return A random date
     */
    protected final Date getDate(Date baseDate, int minDaysFromDate, int maxDaysFromDate) {
        return getDataFactory().getDate(baseDate, minDaysFromDate, maxDaysFromDate);
    }

    /**
     * Returns a random date between two dates. This method will alter the time
     * component of the dates
     * 
     * @param minDate
     *            Minimum date that can be returned
     * @param maxDate
     *            Maximum date that can be returned
     * @return random date between these two dates.
     */
    protected final Date getDateBetween(Date minDate, Date maxDate) {
        return getDataFactory().getDateBetween(minDate, maxDate);
    }

    /**
     * Returns random text made up of english words of length
     * <code>length</code>
     * 
     * @param length
     *            length of returned string
     * 
     * @return string made up of actual words with length <code>length</code>
     */
    protected final String getRandomText(int length) {
        return getDataFactory().getRandomText(length);
    }

    /**
     * Returns random text made up of english words
     * 
     * @param minLength
     *            minimum length of returned string
     * @param maxLength
     *            maximum length of returned string
     * @return string of length between min and max length
     */
    protected final String getRandomText(int minLength, int maxLength) {
        return getDataFactory().getRandomText(minLength, maxLength);
    }
    /**
     * @return a random character
     */
    protected final char getRandomChar() {
        return getDataFactory().getRandomChar();
    }

    /**
     * Return a string containing <code>length</code> random characters
     * 
     * @param length
     *            number of characters to use in the string
     * @return A string containing <code>length</code> random characters
     */
    protected final String getRandomChars(int length) {
        return getDataFactory().getRandomChars(length);
    }

    /**
     * Return a string containing between <code>length</code> random characters
     * 
     * @param length
     *            number of characters to use in the string
     * @return A string containing <code>length</code> random characters
     */
    protected final String getRandomChars(int minLength, int maxLength) {
        return getDataFactory().getRandomChars(minLength, maxLength);
    }

    /**
     * Returns a word of a length between 1 and 10 characters.
     * 
     * @return A work of max length 10
     */
    protected final String getRandomWord() {
        return getDataFactory().getRandomWord();
    }

    /**
     * Returns a valid word with a length of <code>length</code>
     * characters.
     * 
     * @param length
     *            maximum length of the word
     * @return a word of a length up to <code>length</code> characters
     */
    protected final String getRandomWord(int length) {
        return getDataFactory().getRandomWord(length);
    }

    /**
     * Returns a valid word with a length of up to <code>length</code>
     * characters. If the <code>exactLength</code> parameter is set, then the
     * word will be exactly <code>length</code> characters in length.
     * 
     * @param length
     *            maximum length of the returned string
     * @param exactLength
     *            indicates if the word should have a length of
     *            <code>length</code>
     * @return a string with a length that matches the specified parameters.
     */
    protected final String getRandomWord(int length, boolean exactLength) {
        return getDataFactory().getRandomWord(length, exactLength);
    }

    /**
     * Returns a valid word based on the length range passed in. The length will
     * always be between the min and max length range inclusive.
     * 
     * @param minLength minimum length of the word
     * @param maxLength maximum length of the word
     * @return a word of a length between min and max length
     */
    protected final String getRandomWord(int minLength, int maxLength) {
        return getDataFactory().getRandomWord(minLength, maxLength);
    }

    /**
     * 
     * @param chance
     *            Chance of a suffix being returned
     * @return
     */
    protected final String getSuffix(int chance) {
        return getDataFactory().getSuffix(chance);
    }

    /**
     * Return a person prefix or null if the odds are too low.
     * 
     * @param chance
     *            Odds of a prefix being returned
     * @return Prefix string
     */
    protected final String getPrefix(int chance) {
        return getDataFactory().getPrefix(chance);
    }

    /**
     * Returns a string containing a set of numbers with a fixed number of
     * digits
     * 
     * @param digits
     *            number of digits in the final number
     * @return Random number as a string with a fixed length
     */
    protected final String getNumberText(int digits) {
        return getDataFactory().getNumberText(digits);
    }

    /**
     * Generates a random business name by taking a city name and additing a
     * business onto it.
     * 
     * @return A random business name
     */
    protected final String getBusinessName() {
        return getDataFactory().getBusinessName();
    }

    /**
     * Generates an email address
     * 
     * @return an email address
     */
    protected final String getEmailAddress() {
        return getDataFactory().getEmailAddress();
    }

    /**
     * Gives you a true/false based on a probability with a random number
     * generator. Can be used to optionally add elements.
     * 
     * <pre>
     * if (DataFactory.chance(70)) {
     *  // 70% chance of this code being executed
     * }
     * </pre>
     * 
     * @param chance
     *            % chance of returning true
     * @return
     */
    protected final boolean chance(int chance) {
        return getDataFactory().chance(chance);
    }

    protected final NameDataValues getNameDataValues() {
        return getDataFactory().getNameDataValues();
    }

    /**
     * Call randomize with a seed value to reset the random number generator. By
     * using the same seed over different tests, you will should get the same
     * results out for the same data generation calls.
     * 
     * @param seed
     *            Seed value to use to generate random numbers
     */
    protected final void randomize(int seed) {
        getDataFactory().randomize(seed);
    }

    /**
     * Set this to provide your own list of name data values by passing it a
     * class that implements the {@link NameDataValues} interface which just
     * returns the String arrays to use for the test data.
     * 
     * @param nameDataValues
     *            Object holding the set of data values to use
     */
    protected final void setNameDataValues(NameDataValues nameDataValues) {
        getDataFactory().setNameDataValues(nameDataValues);
    }

    /**
     * Set this to provide your own list of address data values by passing it a
     * class that implements the {@link AddressDataValues} interface which just
     * returns the String arrays to use for the test data.
     * 
     * @param addressDataValues
     *            Object holding the set of data values to use
     */
    protected final void setAddressDataValues(AddressDataValues addressDataValues) {
        getDataFactory().setAddressDataValues(addressDataValues);
    }

    /**
     * Set this to provide your own list of content data values by passing it a
     * class that implements the {@link ContentDataValues} interface which just
     * returns the String arrays to use for the test data.
     * 
     * @param contentDataValues
     *            Object holding the set of data values to use
     */
    protected final void setContentDataValues(ContentDataValues contentDataValues) {
        getDataFactory().setContentDataValues(contentDataValues);
    }

}
