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

package walkingkooka.tree.json;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.search.SearchNode;
import walkingkooka.visit.Visiting;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class JsonStringTest extends JsonLeafNonNullNodeTestCase<JsonString, String> {

    @Override
    public void testStringOrFail() {
        this.checkEquals("abc",
                JsonString.with("abc").stringOrFail());
    }

    @Test
    public void testStringOrFail2() {
        this.checkEquals("123",
                JsonString.with("123").stringOrFail());
    }

    @Test
    public void testSameValueDifferentCase() {
        this.checkNotEquals(JsonNode.string("ABC123"));
    }

    // characterOrFail..................................................................................................

    @Test
    public void testCharacterOrFailEmptyString() {
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> JsonString.with("").characterOrFail());
        this.checkEquals("Character string must have length of 1 not 0 got \"\"", thrown.getMessage());
    }

    @Test
    public void testCharacterOrFailNonOneCharacterString() {
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> JsonString.with("abc").characterOrFail());
        this.checkEquals("Character string must have length of 1 not 3 got \"abc\"", thrown.getMessage());
    }

    @Test
    public void testCharacterOrFail() {
        this.checkEquals('A', JsonString.with("A").characterOrFail());
    }

    // toSearchNode...............................................................................................

    @Test
    public void testToSearchNode() {
        final String text = "abc123";
        this.toSearchNodeAndCheck(this.createJsonNode(text), SearchNode.text(text, text));
    }

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final JsonString node = this.createJsonNode();

        new FakeJsonNodeVisitor() {
            @Override
            protected Visiting startVisit(final JsonNode n) {
                assertSame(node, n);
                b.append("1");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final JsonNode n) {
                assertSame(node, n);
                b.append("2");
            }

            @Override
            protected void visit(final JsonString n) {
                assertSame(node, n);
                b.append("3");
            }
        }.accept(node);
        this.checkEquals("132", b.toString());
    }

    @Test
    public void testToString() {
        this.toStringAndCheck2(
                "abc123",
                "\"abc123\""
        );
    }

    @Test
    public void testToStringSpace() {
        this.toStringAndCheck2(
                "abc 123 ",
                "\"abc 123 \""
        );
    }

    @Test
    public void testToStringNul() {
        this.toStringAndCheck2(
                "abc" + ((char) 0) + "xyz",
                "\"abc\\u0000xyz\""
        );
    }

    @Test
    public void testToStringBell() {
        this.toStringAndCheck2(
                "abc\bxyz",
                "\"abc\\bxyz\""
        );
    }

    @Test
    public void testToStringCR() {
        this.toStringAndCheck2(
                "abc\rxyz",
                "\"abc\\rxyz\""
        );
    }

    @Test
    public void testToStringFormFeed() {
        this.toStringAndCheck2(
                "abc\fxyz",
                "\"abc\\fxyz\""
        );
    }

    @Test
    public void testToStringNL() {
        this.toStringAndCheck2(
                "abc\nxyz",
                "\"abc\\nxyz\""
        );
    }

    @Test
    public void testToStringStringQuote() {
        this.toStringAndCheck2(
                "abc'xyz",
                "\"abc'xyz\""
        );
    }

    @Test
    public void testToStringDoubleQuote() {
        this.toStringAndCheck2(
                "abc\"xyz",
                "\"abc\\\"xyz\""
        );
    }

    @Test
    public void testToStringTab() {
        this.toStringAndCheck2(
                "abc\txyz",
                "\"abc\\txyz\""
        );
    }

    @Test
    public void testToStringControlCharacter() {
        this.toStringAndCheck2(
                "abc" + ((char) 20) + "xyz",
                "\"abc\\u0014xyz\""
        );
    }

    private void toStringAndCheck2(final String value, final String json) {
        this.toStringAndCheck(
                this.createJsonNode(value),
                json
        );
    }

    @Test
    public void testParseToStringRountrip() {
        for (int i = Character.MIN_VALUE; i < Character.MAX_VALUE; i++) {
            final char c = (char) i;
            final String text = Character.toString(c);
            final JsonString jsonString = JsonNode.string(text);
            final String json = jsonString.toString();

            this.checkEquals(
                    text,
                    JsonNode.parse(json).stringOrFail(),
                    () -> "parsing string " + CharSequences.quoteIfChars(c)
            );
        }
    }

    @Override
    JsonString createJsonNode(final String value) {
        return JsonString.with(value);
    }

    @Override
    JsonString setValue(final JsonString node, final String value) {
        return node.setValue(value);
    }

    @Override
    String value() {
        return "A";
    }

    @Override
    String differentValue() {
        return "Different";
    }

    @Override
    Class<JsonString> jsonNodeType() {
        return JsonString.class;
    }

    @Override
    List<String> propertiesNeverReturnNullSkipProperties() {
        return Lists.of(ARRAY_OR_FAIL,
                BOOLEAN_OR_FAIL,
                UNMARSHALL_LIST,
                UNMARSHALL_SET,
                UNMARSHALL_MAP,
                UNMARSHALL,
                NUMBER_OR_FAIL,
                OBJECT_OR_FAIL,
                PARENT_OR_FAIL);
    }
}
