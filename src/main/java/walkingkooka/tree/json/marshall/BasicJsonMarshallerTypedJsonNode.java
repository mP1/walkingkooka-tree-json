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

package walkingkooka.tree.json.marshall;

import walkingkooka.collect.list.Lists;
import walkingkooka.tree.json.JsonArray;
import walkingkooka.tree.json.JsonBoolean;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNull;
import walkingkooka.tree.json.JsonNumber;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonString;

/**
 * A {@link BasicJsonMarshallerTyped} that handles {@link JsonNode}.
 */
final class BasicJsonMarshallerTypedJsonNode extends BasicJsonMarshallerTyped<JsonNode> {

    static BasicJsonMarshallerTypedJsonNode instance() {
        return new BasicJsonMarshallerTypedJsonNode();
    }

    private BasicJsonMarshallerTypedJsonNode() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
        this.registerTypes(Lists.of(JsonArray.class,
            JsonBoolean.class,
            JsonNull.class,
            JsonNumber.class,
            JsonObject.class,
            JsonString.class));

    }

    @Override
    Class<JsonNode> type() {
        return JsonNode.class;
    }

    @Override
    String typeName() {
        return "json";
    }

    @Override
    JsonNode unmarshallNull(final JsonNodeUnmarshallContext context) {
        return JsonNode.nullNode();
    }

    @Override
    JsonNode unmarshallNonNull(final JsonNode node,
                               final JsonNodeUnmarshallContext context) {
        return node.removeParent();
    }

    @Override
    JsonNode marshallNonNull(final JsonNode value,
                             final JsonNodeMarshallContext context) {
        return value.removeParent();
    }
}
