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
import walkingkooka.text.CaseKind;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonString;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * A {@link Context} that combines common functionality between a {@link JsonNodeUnmarshallContext} and a {@link JsonNodeMarshallContext}.
 */
public interface JsonNodeContext extends Context {

    /**
     * Computes the type name given a {@link Class}.
     * The current standard is to turn the {@link Class#getSimpleName()} into {@link CaseKind#KEBAB}.
     */
    static String computeTypeName(final Class<?> type) {
        Objects.requireNonNull(type, "type");

        return CaseKind.CAMEL.change(
            type.getSimpleName(),
            CaseKind.KEBAB
        );
    }

    /**
     * Registers a factory that parses a {@link JsonNode} into a value for the given {@link Class}. The {@link Runnable}
     * when executed removes the just registered mapping.
     */
    @SafeVarargs
    static <T> Runnable register(final String typeName,
                                 final BiFunction<JsonNode, JsonNodeUnmarshallContext, T> from,
                                 final BiFunction<T, JsonNodeMarshallContext, JsonNode> to,
                                 final Class<T> type,
                                 final Class<? extends T>... types) {
        return BasicJsonMarshaller.register(
            typeName,
            from,
            to,
            type,
            types
        );
    }

    // registeredType ..................................................................................................

    /**
     * Returns one of possibly many registered {@link Class types} for the given type name.
     */
    Optional<Class<?>> registeredType(final JsonString name);

    // typeName ........................................................................................................

    /**
     * Returns the type name identifying the given {@link Class} providing it is registered.
     */
    Optional<JsonString> typeName(final Class<?> type);

    // isRegisteredType.................................................................................................

    /**
     * Helper that may be used to test if the given {@link Class} can be marshalled/unmarshalled.
     */
    default boolean isSupportedJsonType(final Class<?> type) {
        return this.typeName(type).isPresent();
    }
}
