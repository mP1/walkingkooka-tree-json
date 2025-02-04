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
import walkingkooka.tree.json.JsonNull;
import walkingkooka.visit.Visiting;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class NullJsonNodeParserTokenTest extends ValueJsonNodeParserTokenTestCase2<NullJsonNodeParserToken, Void, JsonNull> {

    @Test
    @Override
    public void testPropertiesNeverReturnNull() throws Exception {
        this.allPropertiesNeverReturnNullCheck(this.createToken(),
            (m) -> m.getName().equals("value"));
    }

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final NullJsonNodeParserToken token = this.createToken();

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
            protected void visit(final NullJsonNodeParserToken t) {
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
    Void value() {
        return null;
    }

    @Override
    JsonNull jsonNode() {
        return JsonNode.nullNode();
    }

    @Override
    NullJsonNodeParserToken createToken(final Void value, final String text) {
        return NullJsonNodeParserToken.with(value, text);
    }

    @Override
    public NullJsonNodeParserToken createDifferentToken() {
        return NullJsonNodeParserToken.with(null, "'different'");
    }

    @Override
    public Class<NullJsonNodeParserToken> type() {
        return NullJsonNodeParserToken.class;
    }
}
