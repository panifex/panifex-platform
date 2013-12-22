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

import java.util.Comparator;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.easymock.IExpectationSetters;
import org.easymock.IMockBuilder;
import org.easymock.IMocksControl;
import org.easymock.LogicalOperator;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.internal.ArrayComparisonFailure;

/**
 * Template class for test cases.
 * 
 * <p>The class inlines methods from {@link org.junit.Assert} and {@link org.easymock.EasyMock} classes
 * in order to subclasses do not have the reference to that classes. 
 *
 */
public abstract class TestSupport {

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
     * @param <T>
     *            the interface that the mock object should implement.
     * @param toMock
     *            the class of the interface that the mock object should
     *            implement.
     * @return the mock object.
     */
    protected final <T> T createStrictMock(final Class<T> toMock) {
        return EasyMock.createStrictMock(toMock);
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
     * @param <T>
     *            the interface that the mock object should implement.
     * @param toMock
     *            the class of the interface that the mock object should
     *            implement.
     * @return the mock object.
     */
    protected final <T> T createMock(final Class<T> toMock) {
        return EasyMock.createMock(toMock);
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
     * @param <T>
     *            the interface that the mock object should implement.
     * @param toMock
     *            the class of the interface that the mock object should
     *            implement.
     * @return the mock object.
     */
    protected final <T> T createNiceMock(final Class<T> toMock) {
        return EasyMock.createNiceMock(toMock);
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
     * Returns the expectation setter for the last expected invocation in the
     * current thread. This method is used for expected invocations on void
     * methods.
     * 
     * @param <T>
     *            type returned by the expected method
     * @return the expectation setter.
     */
    protected final <T> IExpectationSetters<T> expectLastCall() {
        return EasyMock.expectLastCall();
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
     * Switches the given mock objects (more exactly: the controls of the mock
     * objects) to replay mode. For details, see the EasyMock documentation.
     * 
     * @param mocks
     *            the mock objects.
     */
    protected final void replay(final Object... mocks) {
        EasyMock.replay(mocks);
    }

    /**
     * Resets the given mock objects (more exactly: the controls of the mock
     * objects). For details, see the EasyMock documentation.
     * 
     * @param mocks
     *            the mock objects.
     */
    protected final void reset(final Object... mocks) {
        EasyMock.reset(mocks);
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
     * Verifies the given mock objects (more exactly: the controls of the mock
     * objects).
     * 
     * @param mocks
     *            the mock objects.
     */
    protected final void verify(final Object... mocks) {
        EasyMock.verify(mocks);
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

    
}
