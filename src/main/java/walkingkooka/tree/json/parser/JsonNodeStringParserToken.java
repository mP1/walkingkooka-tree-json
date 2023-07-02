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

import walkingkooka.text.CharSequences;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.tree.json.JsonNode;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Holds a json string value.
 */
public final class JsonNodeStringParserToken extends JsonNodeValueParserToken<String> {

    static JsonNodeStringParserToken with(final String value, final String text) {
        return new JsonNodeStringParserToken(
                checkValue(value),
                CharSequences.failIfNullOrEmpty(text, "text")
        );
    }

    private JsonNodeStringParserToken(final String value, final String text) {
        super(value, text);
    }

    @Override
    JsonNode toJsonNodeOrNull() {
        return JsonNode.string(this.value());
    }

    @Override
    void addJsonNode(final List<JsonNode> children) {
        children.add(JsonNode.string(value()));
    }

    // removeFirstIf....................................................................................................

    @Override
    public Optional<JsonNodeStringParserToken> removeFirstIf(final Predicate<ParserToken> predicate) {
        return ParserToken.removeFirstIfLeaf(
                this,
                predicate,
                JsonNodeStringParserToken.class
        );
    }

    // removeIf.........................................................................................................

    @Override
    public Optional<JsonNodeStringParserToken> removeIf(final Predicate<ParserToken> predicate) {
        return ParserToken.removeIfLeaf(
                this,
                predicate,
                JsonNodeStringParserToken.class
        );
    }

    // replaceFirstIf...................................................................................................

    @Override
    public JsonNodeStringParserToken replaceFirstIf(final Predicate<ParserToken> predicate,
                                                    final ParserToken token) {
        return ParserToken.replaceFirstIf(
                this,
                predicate,
                token,
                JsonNodeStringParserToken.class
        );
    }

    // replaceIf........................................................................................................

    @Override
    public JsonNodeStringParserToken replaceIf(final Predicate<ParserToken> predicate,
                                                   final ParserToken token) {
        return ParserToken.replaceIf(
                this,
                predicate,
                token,
                JsonNodeStringParserToken.class
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
        return other instanceof JsonNodeStringParserToken;
    }
}
