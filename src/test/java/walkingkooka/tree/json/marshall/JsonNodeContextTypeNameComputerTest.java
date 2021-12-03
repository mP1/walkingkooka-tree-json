/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.tree.json.marshall;

import org.junit.jupiter.api.Test;
import walkingkooka.test.Testing;
import walkingkooka.text.CharSequences;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class JsonNodeContextTypeNameComputerTest implements Testing {

    @Test
    public void testBigDecimal() {
        this.computeAndCheck(BigDecimal.class.getSimpleName(), "big-decimal");
    }

    @Test
    public void testLocalDateTime() {
        this.computeAndCheck(LocalDateTime.class.getSimpleName(), "local-date-time");
    }

    @Test
    public void testString() {
        this.computeAndCheck("String", "string");
    }

    @Test
    public void testSpreadsheetNegativeParserToken() {
        this.computeAndCheck("SpreadsheetNegativeParserToken", "spreadsheet-negative-parser-token");
    }

    @Test
    public void testSpreadsheetPercentageParserToken() {
        this.computeAndCheck("SpreadsheetPercentageParserToken", "spreadsheet-percentage-parser-token");
    }

    private void computeAndCheck(final String simpleClassName,
                                 final String expected) {
        this.checkEquals(
                expected,
                JsonNodeContextTypeNameComputer.compute(simpleClassName),
                () -> "compute " + CharSequences.quote(simpleClassName)
        );
    }
}
