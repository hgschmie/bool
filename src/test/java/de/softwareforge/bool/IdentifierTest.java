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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class IdentifierTest {

    @Test
    public void testValues() {
        BoolEval test;

        test = new BoolEval("trueValue");
        assertTrue(test.evaluate(constantProvider()));

        test = new BoolEval("falseValue");
        assertFalse(test.evaluate(constantProvider()));
    }

    @Test
    public void testBadValue() {

        assertThrows(IllegalStateException.class, () -> {
            BoolEval test = new BoolEval("nullValue");
            assertFalse(test.evaluate(constantProvider()));
        });
    }

    @Test
    public void testNotLiterals() {
        BoolEval test;

        test = new BoolEval("NOT falseValue");
        assertTrue(test.evaluate(constantProvider()));

        test = new BoolEval("NOT trueValue");
        assertFalse(test.evaluate(constantProvider()));
    }

    @Test
    public void testParens() {
        BoolEval test;

        test = new BoolEval("( trueValue )");
        assertTrue(test.evaluate(constantProvider()));

        test = new BoolEval(" (falseValue) ");
        assertFalse(test.evaluate(constantProvider()));

        test = new BoolEval("NOT (falseValue)");
        assertTrue(test.evaluate(constantProvider()));

        test = new BoolEval("(NOT trueValue)");
        assertFalse(test.evaluate(constantProvider()));
    }

    @Test
    public void testAnd() {
        BoolEval test;

        test = new BoolEval("falseValue AND falseValue");
        assertFalse(test.evaluate(constantProvider()));

        test = new BoolEval("falseValue AND trueValue");
        assertFalse(test.evaluate(constantProvider()));

        test = new BoolEval("trueValue AND falseValue");
        assertFalse(test.evaluate(constantProvider()));

        test = new BoolEval("trueValue AND trueValue");
        assertTrue(test.evaluate(constantProvider()));
    }

    @Test
    public void testOr() {
        BoolEval test;

        test = new BoolEval("falseValue OR falseValue");
        assertFalse(test.evaluate(constantProvider()));

        test = new BoolEval("falseValue OR trueValue");
        assertTrue(test.evaluate(constantProvider()));

        test = new BoolEval("trueValue OR falseValue");
        assertTrue(test.evaluate(constantProvider()));

        test = new BoolEval("trueValue OR trueValue");
        assertTrue(test.evaluate(constantProvider()));
    }




    private Function<String, Boolean> constantProvider() {
        return (name) -> {
            switch (name) {
                case "trueValue":
                    return Boolean.TRUE;

                case "falseValue":
                    return Boolean.FALSE;

                case "nullValue":
                    return null;

                default:
                    fail(format("requested identifier %s unknown", name));
                    return null;
            }
        };
    }
}
