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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface JsonNodeUnmarshallContextTesting extends TreePrintableTesting {

    default <T> void unmarshallAndCheck(final JsonNodeUnmarshallContext context,
                                        final JsonNode node,
                                        final Class<T> type,
                                        final T expected) {
        this.checkEquals(
            expected,
            context.unmarshall(node, type),
            () -> context + " unmarshall " + node + " type " + type.getName()
        );
    }

    @SuppressWarnings("unchecked")
    default <T extends Enum<T>> void unmarshallEnumSetAndCheck(final JsonNodeUnmarshallContext context,
                                                               final JsonNode node,
                                                               final Class<T> type,
                                                               final Set<T> expected) {
        this.checkEquals(
            expected,
            context.unmarshallEnumSet(
                node,
                type,
                (s) -> {
                    try {
                        return (T) type.getMethod("valueOf", String.class)
                            .invoke(null, s);
                    } catch (final ReflectiveOperationException cause) {
                        throw new RuntimeException(cause);
                    }
                }),
            () -> context + " unmarshallEnumSet " + node + " type " + type.getName()
        );
    }

    default <T> void unmarshallOptionalAndCheck(final JsonNodeUnmarshallContext context,
                                                final JsonNode node,
                                                final Class<T> type,
                                                final Optional<T> expected) {
        this.checkEquals(
            expected,
            context.unmarshallOptional(
                node,
                type
            ),
            () -> context + " unmarshallOptional " + node + " type " + type.getName()
        );
    }

    default <T> void unmarshallOptionalWithTypeAndCheck(final JsonNodeUnmarshallContext context,
                                                        final JsonNode node,
                                                        final Optional<T> expected) {
        this.checkEquals(
            expected,
            context.unmarshallOptionalWithType(node),
            () -> context + " unmarshallOptionalWithType " + node
        );
    }

    default <T> void unmarshallListAndCheck(final JsonNodeUnmarshallContext context,
                                            final JsonNode node,
                                            final Class<T> type,
                                            final List<T> expected) {
        this.checkEquals(
            expected,
            context.unmarshallList(node, type),
            () -> context + " unmarshallList " + node + " type " + type.getName()
        );
    }

    default <T> void unmarshallSetAndCheck(final JsonNodeUnmarshallContext context,
                                           final JsonNode node,
                                           final Class<T> type,
                                           final Set<T> expected) {
        this.checkEquals(
            expected,
            context.unmarshallSet(node, type),
            () -> context + " unmarshallSet " + node + " type " + type.getName()
        );
    }

    default <K, V> void unmarshallMapAndCheck(final JsonNodeUnmarshallContext context,
                                              final JsonNode node,
                                              final Class<K> key,
                                              final Class<V> value,
                                              final Map<K, V> expected) {
        this.checkEquals(
            expected,
            context.unmarshallMap(node, key, value),
            () -> context + " unmarshallMap " + node + " key " + key.getName() + " value " + value.getName()
        );
    }

    default void unmarshallWithTypeAndCheck(final JsonNodeUnmarshallContext context,
                                            final JsonNode node,
                                            final Object expected) {
        this.checkEquals(
            expected,
            context.unmarshallWithType(node),
            () -> context + " unmarshallWithType " + node
        );
    }

    default void unmarshallListWithTypeAndCheck(final JsonNodeUnmarshallContext context,
                                                final JsonNode node,
                                                final List<?> expected) {
        this.checkEquals(
            expected,
            context.unmarshallListWithType(node),
            () -> context + " unmarshallListWithType " + node
        );
    }

    default void unmarshallSetWithTypeAndCheck(final JsonNodeUnmarshallContext context,
                                               final JsonNode node,
                                               final Set<?> expected) {
        this.checkEquals(
            expected,
            context.unmarshallSetWithType(node),
            () -> context + " unmarshallSetWithType " + node
        );
    }

    default void unmarshallMapWithTypeAndCheck(final JsonNodeUnmarshallContext context,
                                               final JsonNode node,
                                               final Map<?, ?> expected) {
        this.checkEquals(
            expected,
            context.unmarshallMapWithType(node),
            () -> context + " unmarshallMapWithType " + node
        );
    }
}
