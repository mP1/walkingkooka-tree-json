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
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNumber;
import walkingkooka.visit.Visiting;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class NumberJsonNodeParserTokenTest extends ValueJsonNodeParserTokenTestCase2<NumberJsonNodeParserToken, Double, JsonNumber> {

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final NumberJsonNodeParserToken token = this.createToken();

        new FakeJsonNodeParserTokenVisitor() {
            @Override
            protected Visiting startVisit(final ParserToken t) {
                assertSame(token, t);
                b.append("1");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ParserToken t) {
                assertSame(token, t);
                b.append("2");
            }

            @Override
            protected Visiting startVisit(final JsonNodeParserToken t) {
                assertSame(token, t);
                b.append("3");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final JsonNodeParserToken t) {
                assertSame(token, t);
                b.append("4");
            }

            @Override
            protected void visit(final NumberJsonNodeParserToken t) {
                assertSame(token, t);
                b.append("5");
            }
        }.accept(token);
        this.checkEquals("13542", b.toString());
    }

    @Override
    public String text() {
        return String.valueOf(this.value());
    }

    @Override
    Double value() {
        return 123.5;
    }

    @Override
    JsonNumber jsonNode() {
        return JsonNode.number(value());
    }

    @Override
    NumberJsonNodeParserToken createToken(final Double value, final String text) {
        return NumberJsonNodeParserToken.with(value, text);
    }

    @Override
    public NumberJsonNodeParserToken createDifferentToken() {
        return NumberJsonNodeParserToken.with(-999, "-999");
    }

    @Override
    public Class<NumberJsonNodeParserToken> type() {
        return NumberJsonNodeParserToken.class;
    }
}
