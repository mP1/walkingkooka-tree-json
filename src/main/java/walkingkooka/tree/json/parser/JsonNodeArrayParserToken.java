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

import walkingkooka.collect.list.Lists;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.visit.Visiting;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Holds a json array which may contain further json values.
 */
public final class JsonNodeArrayParserToken extends JsonNodeParentParserToken<JsonNodeArrayParserToken> {

    static JsonNodeArrayParserToken with(final List<ParserToken> value, final String text) {
        return new JsonNodeArrayParserToken(copyAndCheckTokens(value),
                checkText(text));
    }

    private JsonNodeArrayParserToken(final List<ParserToken> value, final String text) {
        super(value, text);
    }

    @Override
    JsonNode toJsonNodeOrNull() {
        final List<JsonNode> children = Lists.array();
        this.addJsonNode(children);

        return JsonNode.array().setChildren(children);
    }

    @Override
    void addJsonNode(final List<JsonNode> children) {
        for (ParserToken element : this.value()) {
            if (element instanceof JsonNodeParserToken) {
                element.cast(JsonNodeParserToken.class).addJsonNode(children);
            }
        }
    }

    // children.........................................................................................................

    @Override
    public JsonNodeArrayParserToken setChildren(final List<ParserToken> children) {
        return ParserToken.parentSetChildren(
                this,
                children,
                JsonNodeArrayParserToken::with
        );
    }

    // removeFirstIf....................................................................................................

    @Override
    public Optional<JsonNodeArrayParserToken> removeFirstIf(final Predicate<ParserToken> predicate) {
        return ParserToken.removeFirstIfParent(
                this,
                predicate,
                JsonNodeArrayParserToken.class
        );
    }

    // removeIf.........................................................................................................

    @Override
    public Optional<JsonNodeArrayParserToken> removeIf(final Predicate<ParserToken> predicate) {
        return ParserToken.removeIfParent(
                this,
                predicate,
                JsonNodeArrayParserToken.class
        );
    }

    // replaceFirstIf...................................................................................................

    @Override
    public JsonNodeArrayParserToken replaceFirstIf(final Predicate<ParserToken> predicate,
                                                   final Function<ParserToken, ParserToken> mapper) {
        return ParserToken.replaceFirstIf(
                this,
                predicate,
                mapper,
                JsonNodeArrayParserToken.class
        );
    }

    // replaceIf........................................................................................................

    @Override
    public JsonNodeArrayParserToken replaceIf(final Predicate<ParserToken> predicate,
                                               final ParserToken token) {
        return ParserToken.replaceIf(
                this,
                predicate,
                token,
                JsonNodeArrayParserToken.class
        );
    }

    // visitor .........................................................................................................

    @Override
    public void accept(final JsonNodeParserTokenVisitor visitor) {
        if (Visiting.CONTINUE == visitor.startVisit(this)) {
            this.acceptValues(visitor);
        }
        visitor.endVisit(this);
    }

    // Object ...............................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof JsonNodeArrayParserToken;
    }
}
