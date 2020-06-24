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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class ParseTest {

    @Test
    public void testBadValue() {

        ParsingException e = assertThrows(ParsingException.class, () -> {
            BoolEval test = new BoolEval("FOO BAR BAZ");
            assertFalse(test.evaluate(failingProvider()));
        });

        assertEquals(1, e.getLineNumber());
        assertEquals(5, e.getColumnNumber());
        assertEquals("mismatched input 'BAR' expecting {<EOF>, 'AND', 'OR'}", e.getErrorMessage());
    }


    private Function<String, Boolean> failingProvider() {
        return (name) -> fail(format("requested identifier %s unknown", name));
    }
}
