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

package walkingkooka.tree.json.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.reflect.PublicStaticHelperTesting;
import walkingkooka.text.CharSequences;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserReporters;
import walkingkooka.text.cursor.parser.ParserTesting2;
import walkingkooka.text.cursor.parser.ParserToken;

import java.lang.reflect.Method;
import java.math.MathContext;

public final class JsonNodeParsersTest implements PublicStaticHelperTesting<JsonNodeParsers>,
    ParserTesting2<Parser<JsonNodeParserContext>, JsonNodeParserContext> {

    private final static JsonNodeParserToken ARRAY_BEGIN = JsonNodeParserToken.arrayBeginSymbol(
        "[",
        "["
    );

    private final static JsonNodeParserToken ARRAY_END = JsonNodeParserToken.arrayEndSymbol(
        "]",
        "]"
    );

    private final static JsonNodeParserToken FALSE = booleanToken(false);

    private final static JsonNodeParserToken TRUE = booleanToken(true);

    private final static JsonNodeParserToken booleanToken(final boolean value) {
        return JsonNodeParserToken.booleanJsonNodeParserToken(
            value,
            String.valueOf(value)
        );
    }

    private final static JsonNodeParserToken NULL = JsonNodeParserToken.nullJsonNodeParserToken("null");

    private final static JsonNodeParserToken number(final int value) {
        // accept only int, keeps the creation of the matching text simple.
        return JsonNodeParserToken.number(
            value,
            String.valueOf(value)
        );
    }
    
    private final static JsonNodeParserToken OBJECT_ASSIGNMENT = JsonNodeParserToken.objectAssignmentSymbol(
        ":",
        ":"
    );

    private final static JsonNodeParserToken OBJECT_BEGIN = JsonNodeParserToken.objectBeginSymbol(
        "{",
        "{"
    );

    private final static JsonNodeParserToken OBJECT_END = JsonNodeParserToken.objectEndSymbol(
        "}",
        "}"
    );

    private final static JsonNodeParserToken SEPARATOR = JsonNodeParserToken.separatorSymbol(
        ",",
        ","
    );

    private final static JsonNodeParserToken WHITESPACE = JsonNodeParserToken.whitespace(
        "  ",
        "  "
    );

    private final static JsonNodeParserToken KEY1 = string("key1");

    private final static JsonNodeParserToken KEY2 =  string("key2");

    private final static JsonNodeParserToken KEY3 = string("key3");
    
    @Test
    public void testParseBooleanInvalidFails() {
        this.parseThrows(
            JsonNodeParsers.booleanParser()
                .orFailIfCursorNotEmpty(ParserReporters.basic())
                .cast(),
            "true 123",
            "Invalid character ' ' at (5,1) expected \"false\" | \"true\""
        );
    }

    @Test
    public void testParseBooleanFalse() {
        final String text = "false";

        this.parseAndCheck(
            text,
            FALSE,
            text
        );
    }

    @Test
    public void testParseBooleanTrue() {
        final String text = "true";

        this.parseAndCheck(text, TRUE, text);
    }

    @Test
    public void testParseNullInvalidFails() {
        this.parseThrows(
            JsonNodeParsers.nullParser()
                .orFailIfCursorNotEmpty(ParserReporters.basic())
                .cast(),
            "null 123",
            "Invalid character ' ' at (5,1) expected \"null\""
        );
    }

    @Test
    public void testParseNull() {
        final String text = "null";

        this.parseAndCheck(text, NULL, text);
    }

    @Test
    public void testParseNumberInvalidFails() {
        this.parseThrows(
            JsonNodeParsers.number()
                .orFailIfCursorNotEmpty(ParserReporters.basic())
                .cast(),
            "123 abc",
            "Invalid character ' ' at (4,1) expected NUMBER"
        );
    }

    @Test
    public void testParseNumber() {
        final String text = "123";

        this.parseAndCheck(
            text,
            number(123),
            text
        );
    }

    @Test
    public void testParseNumber2() {
        final String text = "-123";

        this.parseAndCheck(
            text,
            number(-123),
            text
        );
    }

    @Test
    public void testParseNumberNan() {
        final String text = "NaN";

        this.parseAndCheck(
            text,
            number(Double.NaN),
            text
        );
    }

    @Test
    public void testParseNumberPositiveInfinity() {
        final String text = "Infinity";

        this.parseAndCheck(
            text,
            number(Double.POSITIVE_INFINITY),
            text
        );
    }

    @Test
    public void testParseNumberNegativeInfinity() {
        final String text = "-Infinity";

        this.parseAndCheck(
            text,
            number(Double.NEGATIVE_INFINITY),
            text
        );
    }

    @Test
    public void testParseStringUnterminatedFails() {
        this.parseThrows(
            "\"abc",
            "Missing closing \"\'\""
        );
    }

    @Test
    public void testParseStringMissingSeparatorFails() {
        this.parseThrows(
            JsonNodeParsers.string()
                .orFailIfCursorNotEmpty(ParserReporters.basic())
                .cast(),
            "\"abc\" hello",
            "Invalid character ' ' at (6,1) expected STRING"
        );
    }

    @Test
    public void testParseString() {
        final String text = "\"abc-123\"";

        this.parseAndCheck(
            text,
            string("abc-123"),
            text
        );
    }

    @Test
    public void testParseStringWithTab() {
        final String text = "\"abc\t123\"";

        this.parseAndCheck(
            text,
            string("abc\t123", "\"abc\t123\""),
            text
        );
    }

    @Test
    public void testParseStringWithEscapedBackslash() {
        final String text = "\"abc\\\\123\"";

        this.parseAndCheck(
            text,
            string("abc\\123", "\"abc\\\\123\""),
            text
        );
    }

    @Test
    public void testParseArrayUnclosedFails() {
        this.parseThrows(
            "[",
            "End of text, expected [ARRAY_ELEMENT, {[WHITESPACE], \",\", ARRAY_ELEMENT_REQUIRED}], [WHITESPACE], \"]\""
        );
    }

    @Test
    public void testParseArrayEmpty() {
        final String text = "[]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, ARRAY_END),
            text
        );
    }

    @Test
    public void testParseArrayEmptyWhitespace() {
        final String text = "[  ]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, WHITESPACE, ARRAY_END),
            text
        );
    }

    @Test
    public void testParseArrayBooleanFalse() {
        final String text = "[false]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, FALSE, ARRAY_END),
            text
        );
    }

    @Test
    public void testParseArrayBooleanTrue() {
        final String text = "[true]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, TRUE, ARRAY_END),
            text
        );
    }

    @Test
    public void testParseArrayWhitespaceBooleanWhitespaceTrue() {
        final String text = "[  true  ]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, WHITESPACE, TRUE, WHITESPACE, ARRAY_END),
            text
        );
    }

    @Test
    public void testParseArrayNull() {
        final String text = "[null]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, NULL, ARRAY_END),
            text
        );
    }

    @Test
    public void testParseArrayWhitespaceNullWhitespaceTrue() {
        final String text = "[  null  ]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, WHITESPACE, NULL, WHITESPACE, ARRAY_END),
            text
        );
    }

    @Test
    public void testParseArrayNumber() {
        final String text = "[123]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, number(123), ARRAY_END),
            text
        );
    }

    @Test
    public void testParseArrayWhitespaceNumberWhitespaceTrue() {
        final String text = "[  123  ]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, WHITESPACE, number(123), WHITESPACE, ARRAY_END),
            text
        );
    }

    @Test
    public void testParseArrayString() {
        final String text = "[\"abc\"]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, string("abc"), ARRAY_END),
            text
        );
    }

    @Test
    public void testParseArrayWhitespaceStringWhitespaceTrue() {
        final String text = "[  \"abc\"  ]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, WHITESPACE, string("abc"), WHITESPACE, ARRAY_END),
            text
        );
    }

    @Test
    public void testParseArrayArrayString() {
        final String text = "[[\"abc\"]]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN,
                array(ARRAY_BEGIN, string("abc"), ARRAY_END),
                ARRAY_END
            ),
            text
        );
    }

    @Test
    public void testParseArrayNumberNumber() {
        final String text = "[123,456]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, number(123), SEPARATOR, number(456), ARRAY_END),
            text
        );
    }

    @Test
    public void testParseArrayNumberWhitespaceNumber() {
        final String text = "[123  ,  456]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, number(123), WHITESPACE, SEPARATOR, WHITESPACE, number(456), ARRAY_END),
            text
        );
    }

    @Test
    public void testParseArrayNumberNumberBooleanTrue() {
        final String text = "[123,456,true]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, number(123), SEPARATOR, number(456), SEPARATOR, TRUE, ARRAY_END),
            text
        );
    }

    @Test
    public void testParseArrayNumberNumberBooleanTrueString() {
        final String text = "[123,456,true,\"abc\"]";

        this.parseAndCheck(
            text,
            array(ARRAY_BEGIN, number(123), SEPARATOR, number(456), SEPARATOR, TRUE, SEPARATOR, string("abc"), ARRAY_END),
            text
        );
    }

    @Test
    public void testParseObjectEmpty() {
        final String text = "{}";

        this.parseAndCheck(
            text,
            object(OBJECT_BEGIN, OBJECT_END),
            text
        );
    }

    @Test
    public void testParseObjectEmptyWhitespace() {
        final String text = "{  }";

        this.parseAndCheck(
            text,
            object(OBJECT_BEGIN, WHITESPACE, OBJECT_END),
            text
        );
    }

    @Test
    public void testParseObjectMissingClosingFails() {
        this.parseThrows(
            "{",
            "End of text, expected [OBJECT_PROPERTY, {[WHITESPACE], \",\", OBJECT_PROPERTY_REQUIRED}], [WHITESPACE], \"}\""
        );
    }

    @Test
    public void testParseObjectMissingValueFails() {
        this.parseThrows(
            "{ \"property123\"",
            "Invalid character ' ' at (2,1) expected [OBJECT_PROPERTY, {[WHITESPACE], \",\", OBJECT_PROPERTY_REQUIRED}], [WHITESPACE], \"}\""
        );
    }

    @Test
    public void testParseObjectValueMissingClosingFails() {
        this.parseThrows(
            "{ \"property123\": true",
            "Invalid character ' ' at (2,1) expected [OBJECT_PROPERTY, {[WHITESPACE], \",\", OBJECT_PROPERTY_REQUIRED}], [WHITESPACE], \"}\""
        );
    }

    @Test
    public void testParseObjectBooleanTrue() {
        final String text = "{\"key1\":true}";

        this.parseAndCheck(
            text,
            object(OBJECT_BEGIN, KEY1, OBJECT_ASSIGNMENT, TRUE, OBJECT_END),
            text
        );
    }

    @Test
    public void testParseObjectBooleanFalse() {
        final String text = "{\"key1\":false}";

        this.parseAndCheck(
            text,
            object(OBJECT_BEGIN, KEY1, OBJECT_ASSIGNMENT, FALSE, OBJECT_END),
            text
        );
    }

    @Test
    public void testParseObjectNull() {
        final String text = "{\"key1\":null}";

        this.parseAndCheck(
            text,
            object(OBJECT_BEGIN, KEY1, OBJECT_ASSIGNMENT, NULL, OBJECT_END),
            text
        );
    }

    @Test
    public void testParseObjectNumber() {
        final String text = "{\"key1\":123}";

        this.parseAndCheck(
            text,
            object(OBJECT_BEGIN, KEY1, OBJECT_ASSIGNMENT, number(123), OBJECT_END),
            text
        );
    }

    @Test
    public void testParseObjectString() {
        final String text = "{\"key1\":\"abc\"}";

        this.parseAndCheck(
            text,
            object(OBJECT_BEGIN, KEY1, OBJECT_ASSIGNMENT, string("abc"), OBJECT_END),
            text
        );
    }

    @Test
    public void testParseObjectArrayTrue() {
        final String text = "{\"key1\":[true]}";

        this.parseAndCheck(
            text,
            object(
                OBJECT_BEGIN, KEY1, OBJECT_ASSIGNMENT,
                array(ARRAY_BEGIN, TRUE, ARRAY_END),
                OBJECT_END
            ),
            text
        );
    }

    @Test
    public void testParseObjectNested() {
        final String text = "{\"key1\":{\"key2\":true}}";

        this.parseAndCheck(
            text,
            object(
                OBJECT_BEGIN, KEY1, OBJECT_ASSIGNMENT,
                object(OBJECT_BEGIN, KEY2, OBJECT_ASSIGNMENT, TRUE, OBJECT_END),
                OBJECT_END
            ),
            text
        );
    }

    @Test
    public void testParseObjectNestedNested() {
        final String text = "{\"key1\":{\"key2\":{\"key3\":true}}}";

        final JsonNodeParserToken nested2 = object(OBJECT_BEGIN, KEY3, OBJECT_ASSIGNMENT, TRUE, OBJECT_END);
        final JsonNodeParserToken nested = object(OBJECT_BEGIN, KEY2, OBJECT_ASSIGNMENT, nested2, OBJECT_END);
        this.parseAndCheck(
            text,
            object(OBJECT_BEGIN, KEY1, OBJECT_ASSIGNMENT, nested, OBJECT_END),
            text
        );
    }

    @Test
    public void testParseObjectBooleanTrueBooleanFalse() {
        final String text = "{\"key1\":true,\"key2\":false}";

        this.parseAndCheck(
            text,
            object(OBJECT_BEGIN, KEY1, OBJECT_ASSIGNMENT, TRUE, SEPARATOR, KEY2, OBJECT_ASSIGNMENT, FALSE, OBJECT_END),
            text
        );
    }

    @Test
    public void testParseObjectWhitespaceKeyWhitespaceAssignmentWhitespaceValueWhitespace() {
        final String text = "{  \"key1\"  :  null  }";

        this.parseAndCheck(
            text,
            object(
                OBJECT_BEGIN,
                WHITESPACE, KEY1, WHITESPACE, OBJECT_ASSIGNMENT, WHITESPACE, NULL, WHITESPACE,
                OBJECT_END
            ),
            text
        );
    }

    @Test
    public void testParseObjectBooleanTrueBooleanFalseNul() {
        final String text = "{\"key1\":true,\"key2\":false,\"key3\":null}";

        this.parseAndCheck(
            text,
            object(
                OBJECT_BEGIN,
                KEY1, OBJECT_ASSIGNMENT, TRUE, SEPARATOR,
                KEY2, OBJECT_ASSIGNMENT, FALSE, SEPARATOR,
                KEY3, OBJECT_ASSIGNMENT, NULL,
                OBJECT_END
            ),
            text
        );
    }

    @Test
    public void testParseInvalidJsonReported() {
        this.parseThrowsInvalidCharacterException(
            "!INVALID",
            '!',
            1,
            1
        );
    }

    @Test
    public void testParseInvalidObjectPropertyKeyReported() {
        this.parseThrowsInvalidCharacterException(
            "{!INVALID}",
            '!',
            2,
            1
        );
    }

    @Test
    public void testParseInvalidObjectPropertyValueReported() {
        this.parseThrowsInvalidCharacterException(
            "{\"key1\":!INVALID}",
            '!',
            9,
            1
        );
    }

    @Test
    public void testParseInvalidObjectPropertyReportedValue2() {
        this.parseThrowsInvalidCharacterException(
            "{\"key1\":true,\"key2\":false,\"key3\":!INVALID}",
            '!',
            34,
            1
        );
    }

    @Test
    public void testParseInvalidObjectPropertyAssignmentSymbolReported() {
        this.parseThrowsInvalidCharacterException(
            "{\"key1\":true,\"key2\":false,\"key3\"!true}",
            '"',
            27,
            1
        );
    }

    @Test
    public void testParseInvalidArrayElementReported() {
        this.parseThrowsInvalidCharacterException(
            "[!ABC]",
            '!',
            2,
            1
        );
    }

    @Test
    public void testParseInvalidArrayElementReported2() {
        this.parseThrowsInvalidCharacterException(
            "[true, 123, !ABC]",
            '!',
            13,
            1
        );
    }

    @Test
    public void testParseInvalidArrayElementSeparatorReported() {
        // is complaining that the token 123 <space> <exclaimation point> abc is invalid rather than the missing separator
        this.parseThrowsInvalidCharacterException(
            "[123 !ABC]",
            '1',
            2,
            1
        );
    }

    @Test
    public void testPublicStaticMethodsWithoutMathContextParameter() {
        this.publicStaticMethodParametersTypeCheck(MathContext.class);
    }

    @Override
    public Parser<JsonNodeParserContext> createParser() {
        return JsonNodeParsers.value()
            .orReport(ParserReporters.basic())
            .cast();
    }

    @Override
    public JsonNodeParserContext createContext() {
        return JsonNodeParserContexts.basic();
    }

    private static JsonNodeParserToken array(final JsonNodeParserToken... tokens) {
        return JsonNodeParserToken.array(
            Lists.of(tokens),
            text(tokens)
        );
    }

    private static JsonNodeParserToken number(final double value) {
        return JsonNodeParserToken.number(
            value,
            String.valueOf(value)
        );
    }

    private JsonNodeParserToken object(final JsonNodeParserToken... tokens) {
        return JsonNodeParserToken.object(
            Lists.of(tokens),
            text(tokens)
        );
    }

    private static JsonNodeParserToken string(final String value) {
        final String quotedAndEscaped = CharSequences.quoteAndEscape(value).toString();
        Assertions.assertEquals(
            CharSequences.quote(value)
                .toString(),
            quotedAndEscaped.toString()
        );
        return string(
            value,
            quotedAndEscaped
        );
    }

    private static JsonNodeParserToken string(final String value,
                                              final String text) {
        return JsonNodeParserToken.string(
            value,
            text
        );
    }

    private static String text(final JsonNodeParserToken... tokens) {
        return ParserToken.text(
            Lists.of(tokens)
        );
    }

    // PublicStaticHelper...............................................................................................

    @Override
    public Class<JsonNodeParsers> type() {
        return JsonNodeParsers.class;
    }

    @Override
    public boolean canHavePublicTypes(final Method method) {
        return false;
    }
}
