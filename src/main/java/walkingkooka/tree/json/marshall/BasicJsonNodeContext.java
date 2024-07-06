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

import walkingkooka.collect.set.Sets;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.JsonString;

import java.util.Optional;
import java.util.Set;

/**
 * A {@link JsonNodeContext} that contains shared methods and attributes for both {@link BasicJsonNodeUnmarshallContext} and
 * {@link BasicJsonNodeMarshallContext}.
 */
abstract class BasicJsonNodeContext implements JsonNodeContext {

    /**
     * Typed objects values are stored within a JSON object with two fields.
     * The type field holds the type-name for the value.
     * <pre>
     * {
     *     "type": "java.lang.String",
     *     "value": "String-123"
     * }
     * </pre>
     */
    static final JsonPropertyName TYPE = JsonPropertyName.with("type");

    /**
     * Typed objects values are stored within a JSON object with two fields.
     * The value field holds the marshalled value as JSON.
     * <pre>
     * {
     *     "type": "java.lang.String",
     *     "value": "String-123"
     * }
     * </pre>
     */
    static final JsonPropertyName VALUE = JsonPropertyName.with("value");

    BasicJsonNodeContext() {
        super();
    }

    /**
     * Returns one of possibly many registered {@link Class types} for the given type name.
     */
    @Override
    public final Optional<Class<?>> registeredType(final JsonString name) {
        return BasicJsonMarshaller.registeredType(name);
    }

    // typeName ........................................................................................................

    /**
     * Returns the type name identifying the given {@link Class} providing it is registered.
     */
    @Override
    public final Optional<JsonString> typeName(final Class<?> type) {
        return BasicJsonMarshaller.typeName(type);
    }

    // toString ........................................................................................................

    /**
     * Dumps all types and type names registered with this context.
     */
    @Override
    public final String toString() {
        final Set<String> sorted = Sets.sorted();
        sorted.addAll(BasicJsonMarshaller.TYPENAME_TO_MARSHALLER.keySet());
        return sorted.toString();
    }
}
