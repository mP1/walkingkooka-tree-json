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

import org.junit.jupiter.api.Test;
import walkingkooka.tree.json.JsonNode;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public interface JsonNodeMarshallContextTesting<C extends JsonNodeMarshallContext> extends JsonNodeContextTesting<C> {

    @Test
    default void testSetObjectPostProcessorNullFails() {
        assertThrows(java.lang.NullPointerException.class, () -> this.createContext().setObjectPostProcessor(null));
    }

    @Test
    default void testSetObjectPostProcessor() {
        final JsonNodeMarshallContext context = this.createContext();
        final JsonNodeMarshallContextObjectPostProcessor processor = (value, jsonObject) -> jsonObject;

        final JsonNodeMarshallContext with = context.setObjectPostProcessor(processor);
        assertNotSame(context, with);
    }

    @Test
    default void testSetObjectPostProcessorSame() {
        final JsonNodeMarshallContext context = this.createContext();
        final JsonNodeMarshallContextObjectPostProcessor processor = (value, jsonObject) -> jsonObject;

        final JsonNodeMarshallContext with = context.setObjectPostProcessor(processor);
        assertSame(with, with.setObjectPostProcessor(processor));
    }

    @Test
    default void testMarshallNull() {
        this.marshallAndCheck(null, JsonNode.nullNode());
    }

    @Test
    default void testMarshallCollectionNull() {
        this.marshallCollectionAndCheck(null, JsonNode.nullNode());
    }

    @Test
    default void testMarshallMapNull() {
        this.marshallMapAndCheck(null, JsonNode.nullNode());
    }

    @Test
    default void testMarshallWithTypeNull() {
        this.marshallWithTypeAndCheck(null, JsonNode.nullNode());
    }

    @Test
    default void testMarshallWithTypeCollectionNull() {
        this.marshallWithTypeCollectionAndCheck(null, JsonNode.nullNode());
    }

    @Test
    default void testMarshallWithTypeMapNull() {
        this.marshallWithTypeMapAndCheck(null, JsonNode.nullNode());
    }

    // marshall.......................................................................................................

    default void marshallAndCheck(final Object value,
                                  final JsonNode expected) {
        this.marshallAndCheck(this.createContext(),
            value,
            expected);
    }

    default void marshallAndCheck(final JsonNodeMarshallContext context,
                                  final Object value,
                                  final JsonNode expected) {
        this.checkEquals(expected,
            context.marshall(value),
            () -> context + " marshall " + value);
    }

    // marshallCollection....................................................................................................

    default void marshallCollectionAndCheck(final Collection<?> Collection,
                                            final JsonNode expected) {
        this.marshallCollectionAndCheck(this.createContext(),
            Collection,
            expected);
    }

    default void marshallCollectionAndCheck(final JsonNodeMarshallContext context,
                                            final Collection<?> Collection,
                                            final JsonNode expected) {
        this.checkEquals(expected,
            context.marshallCollection(Collection),
            () -> context + " marshallCollection " + Collection);
    }

    // marshallEnumSet..................................................................................................

    default void marshallEnumSetAndCheck(final Set<? extends Enum<?>> value,
                                         final JsonNode expected) {
        this.marshallEnumSetAndCheck(
            this.createContext(),
            value,
            expected
        );
    }

    default void marshallEnumSetAndCheck(final JsonNodeMarshallContext context,
                                         final Set<? extends Enum<?>> value,
                                         final JsonNode expected) {
        this.checkEquals(
            expected,
            context.marshallEnumSet(value),
            () -> context + " marshallEnumSet " + value
        );
    }

    // marshallMap....................................................................................................

    default void marshallMapAndCheck(final Map<?, ?> map,
                                     final JsonNode expected) {
        this.marshallMapAndCheck(this.createContext(),
            map,
            expected);
    }

    default void marshallMapAndCheck(final JsonNodeMarshallContext context,
                                     final Map<?, ?> map,
                                     final JsonNode expected) {
        this.checkEquals(expected,
            context.marshallMap(map),
            () -> context + " marshallMap " + map);
    }

    // marshallWithType...............................................................................................

    default void marshallWithTypeAndCheck(final Object value,
                                          final JsonNode expected) {
        this.marshallWithTypeAndCheck(this.createContext(),
            value,
            expected);
    }

    default void marshallWithTypeAndCheck(final JsonNodeMarshallContext context,
                                          final Object value,
                                          final JsonNode expected) {
        this.checkEquals(expected,
            context.marshallWithType(value),
            () -> context + " marshallWithType " + value);
    }

    // marshallWithTypeCollection...........................................................................................

    default void marshallWithTypeCollectionAndCheck(final Collection<?> Collection,
                                                    final JsonNode expected) {
        this.marshallWithTypeCollectionAndCheck(this.createContext(),
            Collection,
            expected);
    }

    default void marshallWithTypeCollectionAndCheck(final JsonNodeMarshallContext context,
                                                    final Collection<?> Collection,
                                                    final JsonNode expected) {
        this.checkEquals(expected,
            context.marshallWithTypeCollection(Collection),
            () -> context + " marshallWithTypeCollection " + Collection);
    }

    // marshallWithTypeMap............................................................................................

    default void marshallWithTypeMapAndCheck(final Map<?, ?> map,
                                             final JsonNode expected) {
        this.marshallWithTypeMapAndCheck(this.createContext(),
            map,
            expected);
    }

    default void marshallWithTypeMapAndCheck(final JsonNodeMarshallContext context,
                                             final Map<?, ?> map,
                                             final JsonNode expected) {
        this.checkEquals(expected,
            context.marshallWithTypeMap(map),
            () -> context + " marshallWithTypeMap " + map);
    }

    // TypeNameTesting..................................................................................................

    @Override
    default String typeNameSuffix() {
        return JsonNodeMarshallContext.class.getSimpleName();
    }
}
