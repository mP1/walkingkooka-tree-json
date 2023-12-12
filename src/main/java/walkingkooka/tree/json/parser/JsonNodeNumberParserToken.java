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

import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.tree.json.JsonNode;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Holds a single integer or decimal number.
 */
public final class JsonNodeNumberParserToken extends JsonNodeValueParserToken<Double> {

    static JsonNodeNumberParserToken with(final double value, final String text) {
        checkText(text);

        return new JsonNodeNumberParserToken(value, text);
    }

    private JsonNodeNumberParserToken(final double value, final String text) {
        super(value, text);
    }

    @Override
    JsonNode toJsonNodeOrNull() {
        return JsonNode.number(this.value());
    }

    @Override
    void addJsonNode(final List<JsonNode> children) {
        children.add(JsonNode.number(value()));
    }

    // replaceFirstIf...................................................................................................

    @Override
    public JsonNodeNumberParserToken replaceFirstIf(final Predicate<ParserToken> predicate,
                                                    final Function<ParserToken, ParserToken> mapper) {
        return ParserToken.replaceFirstIf(
                this,
                predicate,
                mapper,
                JsonNodeNumberParserToken.class
        );
    }

    // replaceIf........................................................................................................

    @Override
    public JsonNodeNumberParserToken replaceIf(final Predicate<ParserToken> predicate,
                                               final Function<ParserToken, ParserToken> mapper) {
        return ParserToken.replaceIf(
                this,
                predicate,
                mapper,
                JsonNodeNumberParserToken.class
        );
    }

    // visitor ...............................................................................................

    @Override
    public void accept(final JsonNodeParserTokenVisitor visitor) {
        visitor.visit(this);
    }

    // Object ...............................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof JsonNodeNumberParserToken;
    }
}
