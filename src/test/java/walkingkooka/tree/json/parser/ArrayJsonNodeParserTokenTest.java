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
import walkingkooka.tree.json.JsonNode;
import walkingkooka.visit.Visiting;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class ArrayJsonNodeParserTokenTest extends ParentJsonNodeParserTokenTestCase<ArrayJsonNodeParserToken> {

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final ArrayJsonNodeParserToken token = this.createToken();
        final ArrayBeginSymbolJsonNodeParserToken begin = token.value.get(0).cast(ArrayBeginSymbolJsonNodeParserToken.class);
        final BooleanJsonNodeParserToken booleanTrue = token.value.get(1).cast(BooleanJsonNodeParserToken.class);
        final NullJsonNodeParserToken nullParserToken = token.value.get(3).cast(NullJsonNodeParserToken.class);
        final StringJsonNodeParserToken string = token.value.get(5).cast(StringJsonNodeParserToken.class);
        final ArrayEndSymbolJsonNodeParserToken end = token.value.get(6).cast(ArrayEndSymbolJsonNodeParserToken.class);

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
            protected Visiting startVisit(final ArrayJsonNodeParserToken t) {
                assertSame(token, t);
                b.append("5");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ArrayJsonNodeParserToken t) {
                assertSame(token, t);
                b.append("6");
            }

            @Override
            protected void visit(final ArrayBeginSymbolJsonNodeParserToken t) {
                assertSame(begin, t);
                b.append("7");
            }

            @Override
            protected void visit(final ArrayEndSymbolJsonNodeParserToken t) {
                assertSame(end, t);
                b.append("8");
            }

            @Override
            protected void visit(final BooleanJsonNodeParserToken t) {
                assertSame(booleanTrue, t);
                b.append("9");
            }

            @Override
            protected void visit(final NullJsonNodeParserToken t) {
                assertSame(nullParserToken, t);
                b.append("a");
            }

            @Override
            protected void visit(final SeparatorSymbolJsonNodeParserToken t) {
                b.append("b");
            }

            @Override
            protected void visit(final StringJsonNodeParserToken t) {
                assertSame(string, t);
                b.append("c");
            }
        }.accept(token);
        this.checkEquals("135137421394213b4213a4213b4213c4213842642", b.toString());
    }

    @Test
    public void testMarshallEmpty() {
        this.checkEquals(Optional.of(JsonNode.array()), JsonNodeParserToken.array(Lists.empty(), "[]").toJsonNode());
    }

    @Test
    public void testMarshall() {
        this.checkEquals(Optional.of(JsonNode.array().appendChild(JsonNode.number(123))),
            array(number(123)).toJsonNode());
    }

    @Test
    public void testMarshallWhitespace() {
        this.checkEquals(Optional.of(JsonNode.array().appendChild(JsonNode.number(123))),
            array(number(123), whitespace(), whitespace()).toJsonNode());
    }

    @Override
    protected ArrayJsonNodeParserToken createToken(final String text, final List<ParserToken> tokens) {
        return ArrayJsonNodeParserToken.with(tokens, text);
    }

    @Override
    public String text() {
        return "[true,null,\"abc\"]";
    }

    @Override
    List<ParserToken> tokens() {
        return Lists.of(arrayBegin(), booleanTrue(), separator(), nul(), separator(), string("abc"), arrayEnd());
    }

    @Override
    public ArrayJsonNodeParserToken createDifferentToken() {
        return array(arrayBegin(), string("different"), arrayEnd())
            .cast(ArrayJsonNodeParserToken.class);
    }

    @Override
    public Class<ArrayJsonNodeParserToken> type() {
        return ArrayJsonNodeParserToken.class;
    }
}
