/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.softwareforge.bool;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class ExpressionTest {

    @Test
    public void testLiterals() {
        BoolEval test;

        test = new BoolEval("TRUE");
        assertTrue(test.evaluate(failingProvider()));

        test = new BoolEval("FALSE");
        assertFalse(test.evaluate(failingProvider()));
    }

    @Test
    public void testNotLiterals() {
        BoolEval test;

        test = new BoolEval("NOT FALSE");
        assertTrue(test.evaluate(failingProvider()));

        test = new BoolEval("NOT TRUE");
        assertFalse(test.evaluate(failingProvider()));
    }

    @Test
    public void testParens() {
        BoolEval test;

        test = new BoolEval("( TRUE )");
        assertTrue(test.evaluate(failingProvider()));

        test = new BoolEval(" (FALSE) ");
        assertFalse(test.evaluate(failingProvider()));

        test = new BoolEval("NOT (FALSE)");
        assertTrue(test.evaluate(failingProvider()));

        test = new BoolEval("(NOT TRUE)");
        assertFalse(test.evaluate(failingProvider()));
    }

    @Test
    public void testAnd() {
        BoolEval test;

        test = new BoolEval("FALSE AND FALSE");
        assertFalse(test.evaluate(failingProvider()));

        test = new BoolEval("FALSE AND TRUE");
        assertFalse(test.evaluate(failingProvider()));

        test = new BoolEval("TRUE AND FALSE");
        assertFalse(test.evaluate(failingProvider()));

        test = new BoolEval("TRUE AND TRUE");
        assertTrue(test.evaluate(failingProvider()));
    }

    @Test
    public void testOr() {
        BoolEval test;

        test = new BoolEval("FALSE OR FALSE");
        assertFalse(test.evaluate(failingProvider()));

        test = new BoolEval("FALSE OR TRUE");
        assertTrue(test.evaluate(failingProvider()));

        test = new BoolEval("TRUE OR FALSE");
        assertTrue(test.evaluate(failingProvider()));

        test = new BoolEval("TRUE OR TRUE");
        assertTrue(test.evaluate(failingProvider()));
    }

    @Test
    public void testLeftAssoc() {
        BoolEval test;

        test = new BoolEval("FALSE AND TRUE OR FALSE");
        assertFalse(test.evaluate(failingProvider()));

        test = new BoolEval("TRUE OR FALSE AND TRUE");
        assertTrue(test.evaluate(failingProvider()));
    }

    @Test
    public void testNotAssoc() {
        BoolEval test;

        test = new BoolEval("NOT FALSE AND TRUE");
        assertTrue(test.evaluate(failingProvider()));

        test = new BoolEval("NOT FALSE AND NOT FALSE");
        assertTrue(test.evaluate(failingProvider()));

        test = new BoolEval("NOT (FALSE AND TRUE)");
        assertTrue(test.evaluate(failingProvider()));

        test = new BoolEval("TRUE AND NOT FALSE");
        assertTrue(test.evaluate(failingProvider()));

        test = new BoolEval("NOT TRUE OR FALSE");
        assertFalse(test.evaluate(failingProvider()));

        test = new BoolEval("NOT TRUE OR NOT TRUE");
        assertFalse(test.evaluate(failingProvider()));

        test = new BoolEval("NOT (TRUE OR FALSE)");
        assertFalse(test.evaluate(failingProvider()));

        test = new BoolEval("FALSE OR NOT TRUE");
        assertFalse(test.evaluate(failingProvider()));
    }

    private Function<String, Boolean> failingProvider() {
        return (name) -> fail(format("requested identifier %s unknown", name));
    }
}
