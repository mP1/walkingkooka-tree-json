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

import walkingkooka.Context;
import walkingkooka.tree.json.JsonNode;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * A {@link Context} that accompanies transforming an object into a {@link JsonNode}.
 */
public interface JsonNodeMarshallContext extends JsonNodeContext {

    /**
     * A {@link JsonNodeMarshallContextObjectPostProcessor processor} that simply returns the given object ignoring the value.
     */
    JsonNodeMarshallContextObjectPostProcessor OBJECT_PRE_PROCESSOR = (value, jsonObject) -> jsonObject;

    /**
     * Sets or replaces the {@link BiFunction object post processor} creating a new instance as necessary.
     */
    JsonNodeMarshallContext setObjectPostProcessor(final JsonNodeMarshallContextObjectPostProcessor processor);

    /**
     * Returns the {@link JsonNode} equivalent of this object. This is ideal for situations where the value is not dynamic.
     */
    JsonNode marshall(final Object value);

    /**
     * Marshalls the {@link Set} to a {@link JsonNode}.
     */
    JsonNode marshallEnumSet(final Set<? extends Enum<?>> enumSet);

    /**
     * Creates a {@link JsonNode} that records the type name and the json representation of the given object.
     */
    JsonNode marshallWithType(final Object value);

    /**
     * Marshalls the given {@link Optional} without recording the type.
     */
    JsonNode marshallOptional(final Optional<?> optional);

    /**
     * Marshalls the given {@link Optional} WITH the value type.
     */
    JsonNode marshallOptionalWithType(final Optional<?> optional);

    /**
     * Accepts a {@link Collection} of elements which are assumed to be the same supported type.
     */
    JsonNode marshallCollection(final Collection<?> collection);

    /**
     * Accepts a {@link Map} of elements which are assumed to be supported.
     */
    JsonNode marshallMap(final Map<?, ?> map);

    /**
     * Accepts a {@link Collection} of elements which are assumed to be supported.
     */
    JsonNode marshallCollectionWithType(final Collection<?> collection);

    /**
     * Accepts a {@link Map} and returns its {@link JsonNode} equivalent.
     */
    JsonNode marshallMapWithType(final Map<?, ?> map);
}
