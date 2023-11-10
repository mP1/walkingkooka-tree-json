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
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.json.JsonArray;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * A {@link JsonNodeMarshallContext} that uses a registered type primarily its {@link java.util.function.BiFunction} to
 * turn objects or values into {@link JsonNode}.
 */
final class BasicJsonNodeMarshallContext extends BasicJsonNodeContext implements JsonNodeMarshallContext {

    /**
     * Singleton
     */
    final static BasicJsonNodeMarshallContext INSTANCE = new BasicJsonNodeMarshallContext(JsonNodeMarshallContext.OBJECT_PRE_PROCESSOR);

    /**
     * Private ctor
     */
    private BasicJsonNodeMarshallContext(final BiFunction<Object, JsonObject, JsonObject> processor) {
        super();
        this.processor = processor;
    }

    // marshall. .....................................................................................................

    @Override
    public JsonNodeMarshallContext setObjectPostProcessor(final BiFunction<Object, JsonObject, JsonObject> processor) {
        Objects.requireNonNull(processor, "processor");

        return this.processor.equals(processor) ?
                this :
                new BasicJsonNodeMarshallContext(processor);
    }

    // marshall. .....................................................................................................

    /**
     * Accepts an {@link Object} and creates a {@link JsonNode} equivalent.
     */
    @Override
    public JsonNode marshall(final Object value) {
        return null == value ?
                JsonNode.nullNode() :
                this.marshallNonNull(value);
    }

    private JsonNode marshallNonNull(final Object value) {
        final JsonNode json = BasicJsonMarshaller.marshaller(value)
                .marshall(Cast.to(value), this);
        return json.isObject() ?
                this.processor.apply(value, json.objectOrFail()) :
                json;
    }

    private final BiFunction<Object, JsonObject, JsonObject> processor;

    /**
     * Accepts a {@link Collection} of elements which are assumed to be the same type and creates a {@link JsonArray}.
     */
    @Override
    public JsonNode marshallCollection(final Collection<?> collection) {
        return null == collection ?
                JsonNode.nullNode() :
                JsonObject.array()
                        .setChildren(collection.stream()
                                .map(this::marshall)
                                .collect(Collectors.toList()));
    }

    /**
     * Creates a CSV string from the provided {@link java.util.EnumSet}.
     */
    @Override
    public JsonNode marshallEnumSet(final Set<? extends Enum<?>> enumSet) {
        return null == enumSet ?
                JsonNode.nullNode() :
                marshallEnumSetNonNull(enumSet);
    }

    private JsonNode marshallEnumSetNonNull(final Set<? extends Enum<?>> enumSet) {
        return JsonNode.string(
                enumSet.stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(","))
        );
    }

    /**
     * Accepts a {@link Map} of elements creating a {@link JsonArray} holding the entries with the raw key and values,
     * without recording the types for either.
     */
    @Override
    public JsonNode marshallMap(final Map<?, ?> map) {
        return null == map ?
                JsonNode.nullNode() :
                this.marshallMap0(map);
    }

    private JsonNode marshallMap0(final Map<?, ?> map) {
        final List<JsonNode> keyAndValues = Lists.array();
        boolean allKeysString = true;

        for (final Entry<?, ?> keyAndValue : map.entrySet()) {
            final JsonNode key = marshall(keyAndValue.getKey());
            allKeysString = allKeysString & key.isString();

            final JsonNode value = marshall(keyAndValue.getValue());

            keyAndValues.add(key);
            keyAndValues.add(value);
        }

        return allKeysString ?
                marshallMapAsJsonObject(keyAndValues) :
                marshallMapAsArrayOfEntries(keyAndValues);
    }

    private JsonNode marshallMapAsJsonObject(final List<JsonNode> keyAndValues) {
        final List<JsonNode> array = Lists.array();

        final Iterator<JsonNode> iterator = keyAndValues.iterator();
        while (iterator.hasNext()) {
            final JsonNode key = iterator.next();
            final JsonNode value = iterator.next();

            array.add(value.setName(JsonPropertyName.with(key.stringOrFail())));
        }

        return JsonNode.object()
                .setChildren(array);
    }

    private JsonNode marshallMapAsArrayOfEntries(final List<JsonNode> keyAndValues) {
        final List<JsonNode> array = Lists.array();

        final Iterator<JsonNode> iterator = keyAndValues.iterator();
        while (iterator.hasNext()) {
            array.add(
                    JsonNode.object()
                            .set(BasicJsonMarshallerTypedMap.ENTRY_KEY, iterator.next())
                            .set(BasicJsonMarshallerTypedMap.ENTRY_VALUE, iterator.next())
            );
        }

        return JsonNode.array()
                .setChildren(array);
    }

    // marshallWithType...............................................................................................

    /**
     * Wraps the {@link JsonNode} with a type name declaration.
     */
    @Override
    public JsonNode marshallWithType(final Object value) {
        return null == value ?
                JsonNode.nullNode() :
                BasicJsonMarshaller.marshaller(value)
                        .marshallWithType(Cast.to(value), this);
    }

    /**
     * Accepts a {@link Collection} of elements which are assumed to be the same type and creates a {@link JsonArray}.
     */
    @Override
    public JsonNode marshallWithTypeCollection(final Collection<?> collection) {
        return BasicJsonMarshallerTypedCollectionCollection.instance()
                .marshall(collection, this);
    }

    /**
     * Accepts a {@link Map} and returns its {@link JsonNode} equivalent.
     */
    @Override
    public JsonNode marshallWithTypeMap(final Map<?, ?> map) {
        return BasicJsonMarshallerTypedMap.instance().marshall(map, this);
    }
}
