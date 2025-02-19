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

import walkingkooka.Cast;
import walkingkooka.collect.list.ImmutableList;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;

import java.util.Map;
import java.util.Map.Entry;

final class BasicJsonMarshallerTypedMap extends BasicJsonMarshallerTyped<Map<?, ?>> {

    static BasicJsonMarshallerTypedMap instance() {
        return new BasicJsonMarshallerTypedMap();
    }

    private BasicJsonMarshallerTypedMap() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<Map<?, ?>> type() {
        return Cast.to(Map.class);
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(Map.class);
    }

    // from.............................................................................................................

    @Override
    Map<?, ?> unmarshallNonNull(final JsonNode node,
                                final JsonNodeUnmarshallContext context) {
        return context.unmarshallMapWithType(node);
    }

    @Override
    Map<?, ?> unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    // to...............................................................................................................

    @Override
    JsonNode marshallNonNull(final Map<?, ?> map,
                             final JsonNodeMarshallContext context) {
        return JsonObject.array()
            .setChildren(
                map.entrySet()
                    .stream()
                    .map(e -> entryWithType(e, context))
                    .collect(ImmutableList.collector())
            );
    }

    private static JsonNode entryWithType(final Entry<?, ?> entry,
                                          final JsonNodeMarshallContext context) {
        return JsonNode.object()
            .set(ENTRY_KEY, context.marshallWithType(entry.getKey()))
            .set(ENTRY_VALUE, context.marshallWithType(entry.getValue()));
    }

    final static JsonPropertyName ENTRY_KEY = JsonPropertyName.with("key");
    final static JsonPropertyName ENTRY_VALUE = JsonPropertyName.with("value");
}
