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

package walkingkooka.tree.json.marshall.util;

import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * A {@link Function} that unmarshalls an object from JSON, invokes a mapper and then marshalls that result back to JSON.
 */
final class JsonNodeMarshallUnmarshallMapperFunction<I, O> implements UnaryOperator<JsonNode> {

    static <I, O> JsonNodeMarshallUnmarshallMapperFunction<I, O> with(final Class<I> inputType,
                                                                      final JsonNodeUnmarshallContext unmarshallContext,
                                                                      final JsonNodeMarshallContext marshallContext,
                                                                      final Function<I, O> mapper) {
        Objects.requireNonNull(inputType, "inputType");
        Objects.requireNonNull(unmarshallContext, "unmarshallContext");
        Objects.requireNonNull(marshallContext, "marshallContext");
        Objects.requireNonNull(mapper, "mapper");

        return new JsonNodeMarshallUnmarshallMapperFunction<>(inputType, unmarshallContext, marshallContext, mapper);
    }

    private JsonNodeMarshallUnmarshallMapperFunction(final Class<I> inputType,
                                                     final JsonNodeUnmarshallContext unmarshallContext,
                                                     final JsonNodeMarshallContext marshallContext,
                                                     final Function<I, O> mapper) {
        this.inputType = inputType;
        this.unmarshallContext = unmarshallContext;
        this.marshallContext = marshallContext;
        this.mapper = mapper;
    }

    @Override
    public JsonNode apply(final JsonNode jsonNode) {
        return this.marshallContext.marshall(
            this.mapper.apply(
                this.unmarshallContext.unmarshall(jsonNode, this.inputType)
            )
        );
    }

    /**
     * The java type that will be unmarshalled.
     */
    private final Class<I> inputType;

    /**
     * A context used to unmarshall the given {@link JsonNode}
     */
    private final JsonNodeUnmarshallContext unmarshallContext;

    /**
     * A context used to marshall the result returned by the mapper back to {@link JsonNode}
     */
    private final JsonNodeMarshallContext marshallContext;

    /**
     * The mapper {@link Function}.
     */
    private final Function<I, O> mapper;

    @Override
    public String toString() {
        return this.mapper.toString();
    }
}
