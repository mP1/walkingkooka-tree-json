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
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.visit.Visiting;

import java.util.List;

/**
 * A wrapper around a json object.
 */
public final class JsonNodeObjectParserToken extends JsonNodeParentParserToken<JsonNodeObjectParserToken> {

    static JsonNodeObjectParserToken with(final List<ParserToken> value, final String text) {
        return new JsonNodeObjectParserToken(copyAndCheckTokens(value),
            checkText(text));
    }

    private JsonNodeObjectParserToken(final List<ParserToken> value, final String text) {
        super(value, text);
        this.checkKeys(text);
    }

    private void checkKeys(final String text) {
        final List<ParserToken> without = ParserToken.filterWithoutNoise(this.value);
        if (null != without) {
            int i = 0;
            JsonNodeParserToken j = null;

            for (ParserToken t : without) {
                j = t.cast(JsonNodeParserToken.class);
                if ((i & 1) == 0) {
                    if (!j.isString()) {
                        throw new IllegalArgumentException("Expected string key token, but got " + j + "=" + text);
                    }
                }
                i++;
            }
            if (1 == (i & 1)) {
                throw new IllegalArgumentException("Key " + j + " missing value=" + text);
            }
        }
    }

    @Override
    JsonNode toJsonNodeOrNull() {
        JsonNodeStringParserToken key = null;

        final JsonObject object = JsonNode.object();
        final List<JsonNode> objectChildren = Lists.array();

        for (ParserToken element : ParserToken.filterWithoutNoise(this.value)) {
            if (element instanceof JsonNodeParserToken) {
                final JsonNodeParserToken j = element.cast(JsonNodeParserToken.class);
                if (j.isNoise()) {
                    continue;
                }
                if (null == key) {
                    key = j.cast(JsonNodeStringParserToken.class);
                } else {
                    final JsonNode node = j.toJsonNode().get();
                    objectChildren.add(node.setName(JsonPropertyName.with(key.value())));
                    key = null;
                }
            }
        }

        return object.setChildren(objectChildren);
    }

    @Override
    void addJsonNode(final List<JsonNode> children) {
        children.add(this.toJsonNodeOrNull());
    }

    // children.........................................................................................................

    @Override
    public JsonNodeObjectParserToken setChildren(final List<ParserToken> children) {
        return ParserToken.parentSetChildren(
            this,
            children,
            JsonNodeObjectParserToken::with
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
}
