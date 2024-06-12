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
import walkingkooka.text.CharSequences;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.JsonString;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * A factory that lazily retrieves the type for any given {@link JsonNode} from a given property on another object.
 */
final class JsonNodeUnmarshallContextUnmarshallWithTypePropertyBiFunction<T> implements BiFunction<JsonNode, JsonNodeUnmarshallContext, T> {

    /**
     * Factory called only by {@link JsonNodeUnmarshallContext#unmarshallWithType(JsonPropertyName, JsonObject, Class)}
     */
    static <T> JsonNodeUnmarshallContextUnmarshallWithTypePropertyBiFunction<T> with(final JsonPropertyName property,
                                                                                     final JsonObject source,
                                                                                     final Class<T> superType) {
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(superType, "superType");

        return new JsonNodeUnmarshallContextUnmarshallWithTypePropertyBiFunction<>(property, source);
    }

    private JsonNodeUnmarshallContextUnmarshallWithTypePropertyBiFunction(final JsonPropertyName property,
                                                                          final JsonObject source) {
        super();
        this.property = property;
        this.source = source;
    }

    @Override
    public T apply(final JsonNode node,
                   final JsonNodeUnmarshallContext context) {
        final JsonPropertyName property = this.property;
        final JsonObject source = this.source;

        try {
            final JsonNode typeName = source.getOrFail(property);
            if (!typeName.isString()) {
                throw new JsonNodeUnmarshallException("Property " + property + " contains invalid type name", source);
            }
            final JsonString stringTypeName = typeName.cast(JsonString.class);
            final Class<?> type = context.registeredType(stringTypeName)
                    .orElseThrow(() -> new JsonNodeUnmarshallException("Unknown type " + CharSequences.quoteAndEscape(stringTypeName.value()), this.source));

            return Cast.to(context.unmarshall(node, type));
        } catch (final java.lang.IllegalArgumentException cause) {
            throw new JsonNodeUnmarshallException(cause.getMessage(), node);
        }
    }

    private final JsonPropertyName property;
    private final JsonObject source;

    @Override
    public String toString() {
        return this.property + " in " + this.source;
    }
}
