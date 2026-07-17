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

import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.tree.json.JsonNode;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface JsonNodeMarshallContextTesting extends TreePrintableTesting {

    default void marshallAndCheck(final JsonNodeMarshallContext context,
                                  final Object value,
                                  final JsonNode expected) {
        this.checkEquals(
            expected,
            context.marshall(value),
            () -> context + " marshall " + value
        );
    }

    default void marshallOptionalAndCheck(final JsonNodeMarshallContext context,
                                          final Optional<?> value,
                                          final JsonNode expected) {
        this.checkEquals(
            expected,
            context.marshallOptional(value),
            () -> context + " marshallOptional " + value
        );
    }

    default void marshallOptionalWithTypeAndCheck(final JsonNodeMarshallContext context,
                                                  final Optional<?> value,
                                                  final JsonNode expected) {
        this.checkEquals(
            expected,
            context.marshallOptionalWithType(value),
            () -> context + " marshallOptionalWithType " + value
        );
    }

    default void marshallCollectionAndCheck(final JsonNodeMarshallContext context,
                                            final Collection<?> Collection,
                                            final JsonNode expected) {
        this.checkEquals(
            expected,
            context.marshallCollection(Collection),
            () -> context + " marshallCollection " + Collection
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

    default void marshallMapAndCheck(final JsonNodeMarshallContext context,
                                     final Map<?, ?> map,
                                     final JsonNode expected) {
        this.checkEquals(
            expected,
            context.marshallMap(map),
            () -> context + " marshallMap " + map
        );
    }

    default void marshallWithTypeAndCheck(final JsonNodeMarshallContext context,
                                          final Object value,
                                          final JsonNode expected) {
        this.checkEquals(
            expected,
            context.marshallWithType(value),
            () -> context + " marshallWithType " + value
        );
    }

    default void marshallCollectionWithTypeAndCheck(final JsonNodeMarshallContext context,
                                                    final Collection<?> Collection,
                                                    final JsonNode expected) {
        this.checkEquals(
            expected,
            context.marshallCollectionWithType(Collection),
            () -> context + " marshallCollectionWithType " + Collection
        );
    }

    default void marshallMapWithTypeAndCheck(final JsonNodeMarshallContext context,
                                             final Map<?, ?> map,
                                             final JsonNode expected) {
        this.checkEquals(
            expected,
            context.marshallMapWithType(map),
            () -> context + " marshallMapWithType " + map
        );
    }
}
