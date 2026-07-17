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
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public interface JsonNodeMarshallContextTesting2<C extends JsonNodeMarshallContext> extends JsonNodeMarshallContextTesting,
    JsonNodeContextTesting<C> {

    @Test
    default void testSetObjectPostProcessorNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .setObjectPostProcessor(null)
        );
    }

    @Test
    default void testSetObjectPostProcessor() {
        final JsonNodeMarshallContext context = this.createContext();
        final JsonNodeMarshallContextObjectPostProcessor processor = (value, jsonObject) -> jsonObject;

        final JsonNodeMarshallContext with = context.setObjectPostProcessor(processor);
        assertNotSame(
            context,
            with
        );
    }

    @Test
    default void testSetObjectPostProcessorSame() {
        final JsonNodeMarshallContext context = this.createContext();
        final JsonNodeMarshallContextObjectPostProcessor processor = (value, jsonObject) -> jsonObject;

        final JsonNodeMarshallContext with = context.setObjectPostProcessor(processor);
        assertSame(
            with,
            with.setObjectPostProcessor(processor)
        );
    }

    // marshall.........................................................................................................

    @Test
    default void testMarshallNull() {
        this.marshallAndCheck(
            null,
            JsonNode.nullNode()
        );
    }

    default void marshallAndCheck(final Object value,
                                  final JsonNode expected) {
        this.marshallAndCheck(
            this.createContext(),
            value,
            expected
        );
    }

    // marshallOptional.................................................................................................

    @Test
    default void testMarshallOptionalWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .marshallOptional(null)
        );
    }

    default void marshallOptionalAndCheck(final Optional<?> value,
                                          final JsonNode expected) {
        this.marshallOptionalAndCheck(
            this.createContext(),
            value,
            expected
        );
    }

    // marshallOptionalWithType.........................................................................................

    @Test
    default void testMarshallOptionalWithTypeWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .marshallOptionalWithType(null)
        );
    }

    default void marshallOptionalWithTypeAndCheck(final Optional<?> value,
                                                  final JsonNode expected) {
        this.marshallOptionalWithTypeAndCheck(
            this.createContext(),
            value,
            expected
        );
    }
    
    // marshallCollection...............................................................................................

    @Test
    default void testMarshallCollectionNull() {
        this.marshallCollectionAndCheck(
            null,
            JsonNode.nullNode()
        );
    }

    default void marshallCollectionAndCheck(final Collection<?> Collection,
                                            final JsonNode expected) {
        this.marshallCollectionAndCheck(
            this.createContext(),
            Collection,
            expected
        );
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

    // marshallMap......................................................................................................

    @Test
    default void testMarshallMapNull() {
        this.marshallMapAndCheck(
            null,
            JsonNode.nullNode()
        );
    }

    default void marshallMapAndCheck(final Map<?, ?> map,
                                     final JsonNode expected) {
        this.marshallMapAndCheck(
            this.createContext(),
            map,
            expected
        );
    }

    // marshallWithType.................................................................................................

    @Test
    default void testMarshallWithTypeNull() {
        this.marshallWithTypeAndCheck(
            null,
            JsonNode.nullNode()
        );
    }

    default void marshallWithTypeAndCheck(final Object value,
                                          final JsonNode expected) {
        this.marshallWithTypeAndCheck(
            this.createContext(),
            value,
            expected
        );
    }

    // marshallCollectionWithType.......................................................................................

    @Test
    default void testMarshallCollectionWithTypeNull() {
        this.marshallCollectionWithTypeAndCheck(
            null,
            JsonNode.nullNode()
        );
    }

    default void marshallCollectionWithTypeAndCheck(final Collection<?> Collection,
                                                    final JsonNode expected) {
        this.marshallCollectionWithTypeAndCheck(
            this.createContext(),
            Collection,
            expected
        );
    }

    // marshallMapWithType..............................................................................................

    @Test
    default void testMarshallMapWithTypeNull() {
        this.marshallMapWithTypeAndCheck(
            null,
            JsonNode.nullNode()
        );
    }

    default void marshallMapWithTypeAndCheck(final Map<?, ?> map,
                                             final JsonNode expected) {
        this.marshallMapWithTypeAndCheck(
            this.createContext(),
            map,
            expected
        );
    }

    // TypeNameTesting..................................................................................................

    @Override
    default String typeNameSuffix() {
        return JsonNodeMarshallContext.class.getSimpleName();
    }
}
