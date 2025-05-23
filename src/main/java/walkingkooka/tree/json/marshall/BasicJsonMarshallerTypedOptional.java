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
import walkingkooka.tree.json.JsonArray;
import walkingkooka.tree.json.JsonNode;

import java.util.List;
import java.util.Optional;

/**
 * Handles transforming Optionals and the contained value to and from json.
 */
final class BasicJsonMarshallerTypedOptional extends BasicJsonMarshallerTyped<Optional<?>> {

    static BasicJsonMarshallerTypedOptional instance() {
        return new BasicJsonMarshallerTypedOptional();
    }

    /**
     * Private ctor use factory.
     */
    private BasicJsonMarshallerTypedOptional() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<Optional<?>> type() {
        return Cast.to(Optional.class);
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(Optional.class);
    }

    @Override
    @SuppressWarnings("OptionalAssignedToNull")
    Optional<?> unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    Optional<?> unmarshallNonNull(final JsonNode node,
                                  final JsonNodeUnmarshallContext context) {
        return unmarshallNonNullArray(
            node.arrayOrFail(),
            context
        );
    }

    private Optional<?> unmarshallNonNullArray(final JsonArray array,
                                               final JsonNodeUnmarshallContext context) {
        final List<JsonNode> children = array.children();
        return children.isEmpty() ?
            Optional.empty() :
            this.unmarshallNonNullArrayElement(children, array, context);
    }

    /**
     * Optional values will appear in an array with only ONE element.
     */
    private Optional<?> unmarshallNonNullArrayElement(final List<JsonNode> children,
                                                      final JsonNode parent,
                                                      final JsonNodeUnmarshallContext context) {
        if (children.size() > 1) {
            throw new JsonNodeUnmarshallException("Optional expected only 0/1 children but got " + children, parent);
        }

        return Optional.of(
            context.unmarshallWithType(
                children.get(0)
            )
        );
    }

    /**
     * Returns an array which will hold either a single value or nothing when the optional is empty.
     */
    @Override
    JsonNode marshallNonNull(final Optional<?> value,
                             final JsonNodeMarshallContext context) {
        return value.map(
            v -> marshallNonNullValue(v, context)
            ).orElse(JsonNode.array());
    }

    private static JsonArray marshallNonNullValue(final Object value,
                                                  final JsonNodeMarshallContext context) {
        return JsonNode.array()
            .appendChild(
                context.marshallWithType(value)
            );
    }
}
