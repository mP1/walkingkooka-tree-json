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
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.TypeNameTesting;
import walkingkooka.text.cursor.TextCursors;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserContexts;
import walkingkooka.text.cursor.parser.ParserTesting2;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class JsonNodeParsersStringParserTest implements ParserTesting2<JsonNodeParsersStringParser, ParserContext>, ToStringTesting<JsonNodeParsersStringParser>,
        TypeNameTesting<JsonNodeParsersStringParser> {

    @Test
    public void testUnterminatedFails() {
        this.parseUnterminatedStringFails("\"");
    }

    @Test
    public void testUnterminatedCharacterFails() {
        this.parseUnterminatedStringFails("\"a");
    }

    @Test
    public void testUnterminatedBackslashFails() {
        this.parseUnterminatedStringFails("\"\\");
    }

    @Test
    public void testUnterminatedEscapedDoubleQuoteFails() {
        this.parseUnterminatedStringFails("\"\\\"");
    }

    @Test
    public void testUnterminatedUncodeFails() {
        this.parseUnterminatedStringFails("\"\\u");
    }

    @Test
    public void testUnterminatedUncode1Fails() {
        this.parseUnterminatedStringFails("\"\\u1");
    }

    @Test
    public void testUnterminatedUncode2Fails() {
        this.parseUnterminatedStringFails("\"\\u12");
    }

    @Test
    public void testUnterminatedUncode3Fails() {
        this.parseUnterminatedStringFails("\"\\u123");
    }

    @Test
    public void testUnterminatedUncode4Fails() {
        this.parseUnterminatedStringFails("\"\\u1234");
    }

    private void parseUnterminatedStringFails(final String text) {
        assertThrows(JsonNodeParserException.class, () ->
                this.createParser().parse(TextCursors.charSequence(text), this.createContext())
        );
    }

    @Test
    public void testLetters() {
        this.parseAndCheck2("abc");
    }

    @Test
    public void testDigits() {
        this.parseAndCheck2("1234");
    }

    @Test
    public void testFF() {
        this.parseAndCheck2("\\f", "\f");
    }

    @Test
    public void testCarriageReturn() {
        this.parseAndCheck2("\\r", "\r");
    }

    @Test
    public void testNewline() {
        this.parseAndCheck2("\\n", "\n");
    }

    @Test
    public void testUnicode0000() {
        this.parseAndCheck2("\\u0000", "\u0000");
    }

    @Test
    public void testUnicode1234() {
        this.parseAndCheck2("\\u1234", "\u1234");
    }

    @Test
    public void testUnicodeABCD() {
        this.parseAndCheck2("\\uABCD", "\uABCD");
    }

    @Test
    public void testUnicodeabcd() {
        this.parseAndCheck2("\\uabcd", "\uabcd");
    }

    @Test
    public void testUnicodeDef0() {
        this.parseAndCheck2("\\uDef0", "\uDef0");
    }

    private void parseAndCheck2(final String content) {
        this.parseAndCheck2(content, content);
    }

    private void parseAndCheck2(final String content,
                                final String decoded) {

        this.parseAndCheck3(content, decoded);

        final String before = "before ";
        final String after = " after";
        this.parseAndCheck3(
                before + content + after,
                before + decoded + after
        );
    }

    private void parseAndCheck3(final String content,
                                final String decoded) {

        final String json = JsonNodeParsersStringParser.DOUBLE_QUOTE + content + JsonNodeParsersStringParser.DOUBLE_QUOTE;

        this.parseAndCheck(
                json,
                JsonNodeParserToken.string(decoded, json),
                json
        );

        final String after = "" + 1;
        this.parseAndCheck(
                json + after,
                JsonNodeParserToken.string(decoded, json),
                json,
                after
        );

        final String after2 = "" + JsonNodeParsersStringParser.DOUBLE_QUOTE + "hello" + JsonNodeParsersStringParser.DOUBLE_QUOTE;
        this.parseAndCheck(
                json + after2,
                JsonNodeParserToken.string(decoded, json),
                json,
                after2
        );
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createParser(), "STRING");
    }

    @Override
    public JsonNodeParsersStringParser createParser() {
        return JsonNodeParsersStringParser.INSTANCE;
    }

    @Override
    public ParserContext createContext() {
        return ParserContexts.fake();
    }

    @Override
    public Class<JsonNodeParsersStringParser> type() {
        return JsonNodeParsersStringParser.class;
    }

    // TypeNaming.......................................................................................................

    @Override
    public String typeNamePrefix() {
        return JsonNodeParsers.class.getSimpleName();
    }

    @Override
    public String typeNameSuffix() {
        return "String" + Parser.class.getSimpleName();
    }
}
