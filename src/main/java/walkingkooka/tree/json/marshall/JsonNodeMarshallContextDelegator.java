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

import walkingkooka.tree.json.JsonNode;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface JsonNodeMarshallContextDelegator extends JsonNodeMarshallContext, JsonNodeContextDelegator {
    @Override
    default JsonNodeMarshallContext setObjectPostProcessor(final JsonNodeMarshallContextObjectPostProcessor processor) {
        return this.jsonNodeMarshallContext()
            .setObjectPostProcessor(processor);
    }

    @Override
    default JsonNode marshall(final Object value) {
        return this.jsonNodeMarshallContext()
            .marshall(value);
    }

    @Override
    default JsonNode marshallEnumSet(final Set<? extends Enum<?>> enumSet) {
        return this.jsonNodeMarshallContext()
            .marshallEnumSet(enumSet);
    }

    @Override
    default JsonNode marshallOptional(final Optional<?> optional) {
        return this.jsonNodeMarshallContext()
            .marshallOptional(optional);
    }

    @Override
    default JsonNode marshallOptionalWithType(final Optional<?> optional) {
        return this.jsonNodeMarshallContext()
            .marshallOptionalWithType(optional);
    }

    @Override
    default JsonNode marshallWithType(final Object value) {
        return this.jsonNodeMarshallContext()
            .marshallWithType(value);
    }

    @Override
    default JsonNode marshallCollection(final Collection<?> collection) {
        return this.jsonNodeMarshallContext()
            .marshallCollection(collection);
    }

    @Override
    default JsonNode marshallMap(final Map<?, ?> map) {
        return this.jsonNodeMarshallContext()
            .marshallMap(map);
    }

    @Override
    default JsonNode marshallCollectionWithType(final Collection<?> collection) {
        return this.jsonNodeMarshallContext()
            .marshallCollectionWithType(collection);
    }

    @Override
    default JsonNode marshallMapWithType(final Map<?, ?> map) {
        return this.jsonNodeMarshallContext()
            .marshallMapWithType(map);
    }

    @Override
    default JsonNodeContext jsonNodeContext() {
        return this.jsonNodeMarshallContext();
    }

    JsonNodeMarshallContext jsonNodeMarshallContext();
}
