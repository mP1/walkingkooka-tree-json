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
import walkingkooka.tree.json.JsonObject;

import java.util.Arrays;
import java.util.List;

/**
 * A {@link BasicJsonMarshaller} that includes a json object with the type name and the actual jsonized value.
 * This is mostly used for in built JDK types that cannot be altered.
 */
abstract class BasicJsonMarshallerTyped<T> extends BasicJsonMarshaller<T> {

    BasicJsonMarshallerTyped() {
        super();
    }

    /**
     * For some {@link Enum} which have a separate sub class for each value it is necessary to also register the
     * class for each value otherwise future lookups of those values will not have a a marshaller mapped.
     */
    final <E extends Enum<E>> void registerEnum(final E[] values) {
        this.registerTypeNameAndType();

        Arrays.stream(values)
            .map(v -> v.getClass().getName())
            .filter(t -> false == TYPENAME_TO_MARSHALLER.containsKey(t))
            .forEach(this::registerWithTypeName);
    }

    final void registerTypes(final List<Class<?>> types) {
        types.stream()
            .filter(t -> t != this.type())
            .map(Class::getName)
            .forEach(this::registerWithTypeName);
    }

    @Override final JsonNode marshallWithTypeNonNull(final T value,
                                                     final JsonNodeMarshallContext context) {
        return this.objectWithType()
            .set(BasicJsonNodeContext.VALUE, context.marshall(value));
    }

    /**
     * The {@link JsonObject} holding type=$typename must be created lazily after all registration. Attempts to create
     * during registration will result in exceptions when the {@link JsonObject} is created and the TYPE property set.
     */
    final JsonObject objectWithType() {
        if (null == this.objectWithType) {
            this.objectWithType = JsonNode.object()
                .set(BasicJsonNodeContext.TYPE, JsonNode.string(this.toString()));
        }
        return this.objectWithType;
    }

    private JsonObject objectWithType;
}
