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
import walkingkooka.tree.json.JsonNode;

import java.util.List;

/**
 * Holds a either true or false boolean value
 */
public final class JsonNodeBooleanParserToken extends JsonNodeValueParserToken<Boolean> {

    static JsonNodeBooleanParserToken with(final boolean value, final String text) {
        return new JsonNodeBooleanParserToken(
                value,
                CharSequences.failIfNullOrEmpty(text, "text")
        );
    }

    private JsonNodeBooleanParserToken(final boolean value, final String text) {
        super(value, text);
    }

    @Override
    JsonNode toJsonNodeOrNull() {
        return JsonNode.booleanNode(this.value);
    }

    @Override
    void addJsonNode(final List<JsonNode> children) {
        children.add(JsonNode.booleanNode(this.value));
    }

    // visitor ...............................................................................................

    @Override
    public void accept(final JsonNodeParserTokenVisitor visitor) {
        visitor.visit(this);
    }

    // Object ...............................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof JsonNodeBooleanParserToken;
    }
}
