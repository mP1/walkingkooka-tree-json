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

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.reflect.JavaVisibility;
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

    @Test
    public void testParseBooleanFalse() {
        final String text = "false";

        this.parseAndCheck(text, booleanFalse(), text);
    }

    @Test
    public void testParseBooleanTrue() {
        final String text = "true";

        this.parseAndCheck(text, booleanTrue(), text);
    }

    @Test
    public void testParseNull() {
        final String text = "null";

        this.parseAndCheck(text, nul(), text);
    }

    @Test
    public void testParseNumber() {
        final String text = "123";

        this.parseAndCheck(text, number(123), text);
    }

    @Test
    public void testParseNumber2() {
        final String text = "-123";

        this.parseAndCheck(text, number(-123), text);
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
    public void testParseString() {
        final String text = "\"abc-123\"";

        this.parseAndCheck(text, string("abc-123"), text);
    }

    @Test
    public void testParseStringWithTab() {
        final String text = "\"abc\t123\"";

        this.parseAndCheck(text, string("abc\t123", "\"abc\t123\""), text);
    }

    @Test
    public void testParseStringWithEscapedBackslash() {
        final String text = "\"abc\\\\123\"";

        this.parseAndCheck(text, string("abc\\123", "\"abc\\\\123\""), text);
    }

    @Test
    public void testParseArrayUnclosedFails() {
        this.parseThrows(
            "[",
            "End of text at (2,1) expected [ ARRAY_ELEMENT, [{ [ WHITESPACE ], SEPARATOR, ARRAY_ELEMENT_REQUIRED }]], [ WHITESPACE ], ARRAY_END"
        );
    }

    @Test
    public void testParseArrayEmpty() {
        final String text = "[]";

        this.parseAndCheck(text, array(arrayBegin(), arrayEnd()), text);
    }

    @Test
    public void testParseArrayEmptyWhitespace() {
        final String text = "[  ]";

        this.parseAndCheck(text, array(arrayBegin(), whitespace(), arrayEnd()), text);
    }

    @Test
    public void testParseArrayBooleanFalse() {
        final String text = "[false]";

        this.parseAndCheck(text, array(arrayBegin(), booleanFalse(), arrayEnd()), text);
    }

    @Test
    public void testParseArrayBooleanTrue() {
        final String text = "[true]";

        this.parseAndCheck(text, array(arrayBegin(), booleanTrue(), arrayEnd()), text);
    }

    @Test
    public void testParseArrayWhitespaceBooleanWhitespaceTrue() {
        final String text = "[  true  ]";

        this.parseAndCheck(text, array(arrayBegin(), whitespace(), booleanTrue(), whitespace(), arrayEnd()), text);
    }

    @Test
    public void testParseArrayNull() {
        final String text = "[null]";

        this.parseAndCheck(text, array(arrayBegin(), nul(), arrayEnd()), text);
    }

    @Test
    public void testParseArrayWhitespaceNullWhitespaceTrue() {
        final String text = "[  null  ]";

        this.parseAndCheck(text, array(arrayBegin(), whitespace(), nul(), whitespace(), arrayEnd()), text);
    }

    @Test
    public void testParseArrayNumber() {
        final String text = "[123]";

        this.parseAndCheck(text, array(arrayBegin(), number(123), arrayEnd()), text);
    }

    @Test
    public void testParseArrayWhitespaceNumberWhitespaceTrue() {
        final String text = "[  123  ]";

        this.parseAndCheck(text, array(arrayBegin(), whitespace(), number(123), whitespace(), arrayEnd()), text);
    }

    @Test
    public void testParseArrayString() {
        final String text = "[\"abc\"]";

        this.parseAndCheck(text, array(arrayBegin(), string("abc"), arrayEnd()), text);
    }

    @Test
    public void testParseArrayWhitespaceStringWhitespaceTrue() {
        final String text = "[  \"abc\"  ]";

        this.parseAndCheck(text, array(arrayBegin(), whitespace(), string("abc"), whitespace(), arrayEnd()), text);
    }

    @Test
    public void testParseArrayArrayString() {
        final String text = "[[\"abc\"]]";

        this.parseAndCheck(text, array(arrayBegin(), array(arrayBegin(), string("abc"), arrayEnd()), arrayEnd()), text);
    }

    @Test
    public void testParseArrayNumberNumber() {
        final String text = "[123,456]";

        this.parseAndCheck(text, array(arrayBegin(), number(123), separator(), number(456), arrayEnd()), text);
    }

    @Test
    public void testParseArrayNumberWhitespaceNumber() {
        final String text = "[123  ,  456]";

        this.parseAndCheck(text, array(arrayBegin(), number(123), whitespace(), separator(), whitespace(), number(456), arrayEnd()), text);
    }

    @Test
    public void testParseArrayNumberNumberBooleanTrue() {
        final String text = "[123,456,true]";

        this.parseAndCheck(text, array(arrayBegin(), number(123), separator(), number(456), separator(), booleanTrue(), arrayEnd()), text);
    }

    @Test
    public void testParseArrayNumberNumberBooleanTrueString() {
        final String text = "[123,456,true,\"abc\"]";

        this.parseAndCheck(text, array(arrayBegin(), number(123), separator(), number(456), separator(), booleanTrue(), separator(), string("abc"), arrayEnd()), text);
    }

    @Test
    public void testParseObjectEmpty() {
        final String text = "{}";

        this.parseAndCheck(text, object(objectBegin(), objectEnd()), text);
    }

    @Test
    public void testParseObjectEmptyWhitespace() {
        final String text = "{  }";

        this.parseAndCheck(text, object(objectBegin(), whitespace(), objectEnd()), text);
    }

    @Test
    public void testParseObjectMissingClosingFails() {
        this.parseThrows(
            "{",
            "End of text at (2,1) expected [ OBJECT_PROPERTY, [{[ WHITESPACE ], SEPARATOR, OBJECT_PROPERTY_REQUIRED }]], [ WHITESPACE ], OBJECT_END"
        );
    }

    @Test
    public void testParseObjectMissingValueFails() {
        this.parseThrows(
            "{ \"property123\"",
            "Invalid character ' ' at (2,1) expected [ OBJECT_PROPERTY, [{[ WHITESPACE ], SEPARATOR, OBJECT_PROPERTY_REQUIRED }]], [ WHITESPACE ], OBJECT_END"
        );
    }

    @Test
    public void testParseObjectValueMissingClosingFails() {
        this.parseThrows(
            "{ \"property123\": true",
            "Invalid character ' ' at (2,1) expected [ OBJECT_PROPERTY, [{[ WHITESPACE ], SEPARATOR, OBJECT_PROPERTY_REQUIRED }]], [ WHITESPACE ], OBJECT_END"
        );
    }

    @Test
    public void testParseObjectBooleanTrue() {
        final String text = "{\"key1\":true}";

        this.parseAndCheck(text, object(objectBegin(), key1(), objectAssignment(), booleanTrue(), objectEnd()), text);
    }

    @Test
    public void testParseObjectBooleanFalse() {
        final String text = "{\"key1\":false}";

        this.parseAndCheck(text, object(objectBegin(), key1(), objectAssignment(), booleanFalse(), objectEnd()), text);
    }

    @Test
    public void testParseObjectNull() {
        final String text = "{\"key1\":null}";

        this.parseAndCheck(text, object(objectBegin(), key1(), objectAssignment(), nul(), objectEnd()), text);
    }

    @Test
    public void testParseObjectNumber() {
        final String text = "{\"key1\":123}";

        this.parseAndCheck(text, object(objectBegin(), key1(), objectAssignment(), number(123), objectEnd()), text);
    }

    @Test
    public void testParseObjectString() {
        final String text = "{\"key1\":\"abc\"}";

        this.parseAndCheck(text, object(objectBegin(), key1(), objectAssignment(), string("abc"), objectEnd()), text);
    }

    @Test
    public void testParseObjectArrayTrue() {
        final String text = "{\"key1\":[true]}";

        final JsonNodeParserToken array = array(arrayBegin(), booleanTrue(), arrayEnd());
        this.parseAndCheck(text, object(objectBegin(), key1(), objectAssignment(), array, objectEnd()), text);
    }

    @Test
    public void testParseObjectNested() {
        final String text = "{\"key1\":{\"key2\":true}}";

        final JsonNodeParserToken nested = object(objectBegin(), key2(), objectAssignment(), booleanTrue(), objectEnd());
        this.parseAndCheck(text, object(objectBegin(), key1(), objectAssignment(), nested, objectEnd()), text);
    }

    @Test
    public void testParseObjectNestedNested() {
        final String text = "{\"key1\":{\"key2\":{\"key3\":true}}}";

        final JsonNodeParserToken nested2 = object(objectBegin(), key3(), objectAssignment(), booleanTrue(), objectEnd());
        final JsonNodeParserToken nested = object(objectBegin(), key2(), objectAssignment(), nested2, objectEnd());
        this.parseAndCheck(text, object(objectBegin(), key1(), objectAssignment(), nested, objectEnd()), text);
    }

    @Test
    public void testParseObjectBooleanTrueBooleanFalse() {
        final String text = "{\"key1\":true,\"key2\":false}";

        this.parseAndCheck(text, object(objectBegin(), key1(), objectAssignment(), booleanTrue(), separator(), key2(), objectAssignment(), booleanFalse(), objectEnd()), text);
    }

    @Test
    public void testParseObjectWhitespaceKeyWhitespaceAssignmentWhitespaceValueWhitespace() {
        final String text = "{  \"key1\"  :  null  }";

        this.parseAndCheck(text,
            object(objectBegin(),
                whitespace(), key1(), whitespace(), objectAssignment(), whitespace(), nul(), whitespace(),
                objectEnd()),
            text);
    }

    @Test
    public void testParseObjectBooleanTrueBooleanFalseNul() {
        final String text = "{\"key1\":true,\"key2\":false,\"key3\":null}";

        this.parseAndCheck(text, object(objectBegin(),
            key1(), objectAssignment(), booleanTrue(), separator(),
            key2(), objectAssignment(), booleanFalse(), separator(),
            key3(), objectAssignment(), nul(),
            objectEnd()), text);
    }

    @Test
    public void testParseInvalidJsonReported() {
        this.parseThrows("!INVALID", '!', 1, 1);
    }

    @Test
    public void testParseInvalidObjectPropertyKeyReported() {
        this.parseThrows("{!INVALID}", '!', 2, 1);
    }

    @Test
    public void testParseInvalidObjectPropertyValueReported() {
        this.parseThrows("{\"key1\":!INVALID}", '!', 9, 1);
    }

    @Test
    public void testParseInvalidObjectPropertyReportedValue2() {
        this.parseThrows("{\"key1\":true,\"key2\":false,\"key3\":!INVALID}", '!', 34, 1);
    }

    @Test
    public void testParseInvalidObjectPropertyAssignmentSymbolReported() {
        this.parseThrows("{\"key1\":true,\"key2\":false,\"key3\"!true}", '"', 27, 1);
    }

    @Test
    public void testParseInvalidArrayElementReported() {
        this.parseThrows("[!ABC]", '!', 2, 1);
    }

    @Test
    public void testParseInvalidArrayElementReported2() {
        this.parseThrows("[true, 123, !ABC]", '!', 13, 1);
    }

    @Test
    public void testParseInvalidArrayElementSeparatorReported() {
        // is complaining that the token 123 <space> <exclaimation point> abc is invalid rather than the missing separator
        this.parseThrows("[123 !ABC]", '1', 2, 1);
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

    private JsonNodeParserToken arrayBegin() {
        return JsonNodeParserToken.arrayBeginSymbol("[", "[");
    }

    private JsonNodeParserToken arrayEnd() {
        return JsonNodeParserToken.arrayEndSymbol("]", "]");
    }

    private JsonNodeParserToken array(final JsonNodeParserToken... tokens) {
        return JsonNodeParserToken.array(Lists.of(tokens), text(tokens));
    }

    private JsonNodeParserToken booleanFalse() {
        return booleanToken(false);
    }

    private JsonNodeParserToken booleanTrue() {
        return booleanToken(true);
    }

    private JsonNodeParserToken booleanToken(final boolean value) {
        return JsonNodeParserToken.booleanParserToken(value, String.valueOf(value));
    }

    private JsonNodeParserToken nul() {
        return JsonNodeParserToken.nullParserToken("null");
    }

    private JsonNodeParserToken number(final int value) {
        // accept only int, keeps the creation of the matching text simple.
        return JsonNodeParserToken.number(
            value,
            String.valueOf(value)
        );
    }

    private JsonNodeParserToken number(final double value) {
        return JsonNodeParserToken.number(
            value,
            String.valueOf(value)
        );
    }

    private JsonNodeParserToken objectAssignment() {
        return JsonNodeParserToken.objectAssignmentSymbol(":", ":");
    }

    private JsonNodeParserToken objectBegin() {
        return JsonNodeParserToken.objectBeginSymbol("{", "{");
    }

    private JsonNodeParserToken objectEnd() {
        return JsonNodeParserToken.objectEndSymbol("}", "}");
    }

    private JsonNodeParserToken object(final JsonNodeParserToken... tokens) {
        return JsonNodeParserToken.object(Lists.of(tokens), text(tokens));
    }

    private JsonNodeParserToken separator() {
        return JsonNodeParserToken.separatorSymbol(",", ",");
    }

    private JsonNodeParserToken string(final String value) {
        final String quotedAndEscaped = CharSequences.quoteAndEscape(value).toString();
        this.checkEquals(CharSequences.quote(value).toString(), quotedAndEscaped, () -> "string contains escaping");
        return string(value, quotedAndEscaped);
    }

    private JsonNodeParserToken string(final String value,
                                       final String text) {
        return JsonNodeParserToken.string(value, text);
    }

    private JsonNodeParserToken whitespace() {
        return JsonNodeParserToken.whitespace("  ", "  ");
    }

    private JsonNodeParserToken key1() {
        return string("key1");
    }

    private JsonNodeParserToken key2() {
        return string("key2");
    }

    private JsonNodeParserToken key3() {
        return string("key3");
    }

    private static String text(final JsonNodeParserToken... tokens) {
        return ParserToken.text(Lists.of(tokens));
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

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
