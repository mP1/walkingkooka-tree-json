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
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.tree.json.JsonArray;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.visit.Visiting;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ObjectJsonNodeParserTokenTest extends ParentJsonNodeParserTokenTestCase<ObjectJsonNodeParserToken> {

    @Test
    public void testWithArrayKeyFails() {
        assertThrows(IllegalArgumentException.class, () -> ObjectJsonNodeParserToken.with(Lists.of(array()), "{[]}"));
    }

    @Test
    public void testWithBooleanKeyFails() {
        assertThrows(IllegalArgumentException.class, () -> ObjectJsonNodeParserToken.with(Lists.of(booleanToken(true)), "{true}"));
    }

    @Test
    public void testWithNullKeyFails() {
        assertThrows(IllegalArgumentException.class, () -> ObjectJsonNodeParserToken.with(Lists.of(nul()), "{null}"));
    }

    @Test
    public void testWithIndexKeyFails() {
        assertThrows(IllegalArgumentException.class, () -> ObjectJsonNodeParserToken.with(Lists.of(number(123)), "{123}"));
    }

    @Test
    public void testWithObjectKeyFails() {
        assertThrows(IllegalArgumentException.class, () -> ObjectJsonNodeParserToken.with(Lists.of(object()), "{{}}"));
    }

    @Test
    public void testWithMissingValueFails() {
        assertThrows(IllegalArgumentException.class, () -> ObjectJsonNodeParserToken.with(Lists.of(string("key")), "{\"key\":}"));
    }

    @Test
    public void testWithMissingValueFails2() {
        assertThrows(IllegalArgumentException.class, () -> ObjectJsonNodeParserToken.with(Lists.of(string("key1"), number(123), string("key2")), "{\"key1\":123,\"key2\"}"));
    }

    @Test
    public void testMarshallEmpty() {
        this.checkEquals(Optional.of(JsonNode.object()), ObjectJsonNodeParserToken.with(Lists.empty(), "{}").toJsonNode());
    }

    @Test
    public void testMarshall() {
        this.checkEquals(Optional.of(JsonNode.object().set(JsonPropertyName.with("key1"), JsonNode.number(123))),
            object(string("key1"), number(123))
                .toJsonNode());
    }

    @Test
    public void testArrayWithObjectMarshall() {
        final JsonNodeParserToken objectToken = object(string("key1"), number(123));
        final JsonNodeParserToken arrayToken = array(objectToken);

        final JsonObject objectNode = JsonNode.object().set(JsonPropertyName.with("key1"), JsonNode.number(123));
        final JsonArray arrayNode = JsonNode.array().appendChild(objectNode);

        this.checkEquals(Optional.of(arrayNode), arrayToken.toJsonNode());
    }

    @Test
    public void testMarshallWhitespace() {
        this.checkEquals(Optional.of(JsonNode.object().set(JsonPropertyName.with("key1"), JsonNode.number(123))),
            object(whitespace(), string("key1"), whitespace(), number(123))
                .toJsonNode());
    }

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final ObjectJsonNodeParserToken token = this.createToken();
        final ObjectBeginSymbolJsonNodeParserToken begin = token.value.get(0).cast(ObjectBeginSymbolJsonNodeParserToken.class);
        final ObjectAssignmentSymbolJsonNodeParserToken assignment = token.value.get(2).cast(ObjectAssignmentSymbolJsonNodeParserToken.class);
        final ObjectEndSymbolJsonNodeParserToken end = token.value.get(4).cast(ObjectEndSymbolJsonNodeParserToken.class);

        new FakeJsonNodeParserTokenVisitor() {
            @Override
            protected Visiting startVisit(final ParserToken t) {
                b.append("1");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ParserToken t) {
                b.append("2");
            }

            @Override
            protected Visiting startVisit(final JsonNodeParserToken t) {
                b.append("3");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final JsonNodeParserToken t) {
                b.append("4");
            }

            @Override
            protected Visiting startVisit(final ObjectJsonNodeParserToken t) {
                assertSame(token, t);
                b.append("5");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ObjectJsonNodeParserToken t) {
                assertSame(token, t);
                b.append("6");
            }

            @Override
            protected void visit(final ObjectBeginSymbolJsonNodeParserToken t) {
                assertSame(begin, t);
                b.append("7");
            }

            @Override
            protected void visit(final ObjectAssignmentSymbolJsonNodeParserToken t) {
                assertSame(assignment, t);
                b.append("8");
            }

            @Override
            protected void visit(final ObjectEndSymbolJsonNodeParserToken t) {
                assertSame(end, t);
                b.append("9");
            }

            @Override
            protected void visit(final StringJsonNodeParserToken t) {
                b.append("a");
            }
        }.accept(token);

        this.checkEquals("1351374213a421384213a4213942642", b.toString());
    }

    @Override
    protected ObjectJsonNodeParserToken createToken(final String text, final List<ParserToken> tokens) {
        return ObjectJsonNodeParserToken.with(tokens, text);
    }

    @Override
    public String text() {
        return "{\"key\":\"value\"}";
    }

    @Override
    List<ParserToken> tokens() {
        return Lists.of(objectBegin(), string("key"), objectAssignment(), string("value"), objectEnd());
    }

    @Override
    public ObjectJsonNodeParserToken createDifferentToken() {
        return object(objectBegin(), string("different-key"), objectAssignment(), string("different-value"), objectEnd())
            .cast(ObjectJsonNodeParserToken.class);
    }

    @Override
    public Class<ObjectJsonNodeParserToken> type() {
        return ObjectJsonNodeParserToken.class;
    }
}
