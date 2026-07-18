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
import walkingkooka.locale.CanLocaleForLanguageTagTesting2;
import walkingkooka.tree.json.JsonNode;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public interface JsonNodeUnmarshallContextTesting2<C extends JsonNodeUnmarshallContext> extends JsonNodeUnmarshallContextTesting,
    JsonNodeContextTesting<C>,
    CanLocaleForLanguageTagTesting2<C> {

    // setPreProcessor..................................................................................................

    @Test
    default void testSetPreProcessorNullFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> this.createContext().setPreProcessor(null)
        );
    }

    @Test
    default void testSetPreProcessor() {
        final JsonNodeUnmarshallContext context = this.createContext();
        final JsonNodeUnmarshallContextPreProcessor processor = (json, type) -> json;

        final JsonNodeUnmarshallContext with = context.setPreProcessor(processor);
        assertNotSame(
            context,
            with
        );
    }

    @Test
    default void testSetPreProcessorSame() {
        final JsonNodeUnmarshallContext context = this.createContext();
        final JsonNodeUnmarshallContextPreProcessor processor = (json, type) -> json;

        final JsonNodeUnmarshallContext with = context.setPreProcessor(processor);
        assertSame(
            with,
            with.setPreProcessor(processor)
        );
    }

    // unmarshall.......................................................................................................

    @Test
    default void testUnmarshallWithNullTypeFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> this.createContext()
                .unmarshall(
                    JsonNode.nullNode(),
                    null
                )
        );
    }

    default <T> void unmarshallAndCheck(final JsonNode node,
                                        final Class<T> type,
                                        final T expected) {
        this.unmarshallAndCheck(this.createContext(),
            node,
            type,
            expected);
    }

    // unmarshallEnumSet................................................................................................

    default <T extends Enum<T>> void unmarshallEnumSetAndCheck(final JsonNode node,
                                                               final Class<T> type,
                                                               final Set<T> expected) {
        this.unmarshallEnumSetAndCheck(
            this.createContext(),
            node,
            type,
            expected
        );
    }

    // unmarshallOptional...............................................................................................

    @Test
    default void testUnmarshallOptionalWithNullJsonNodeFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> this.createContext()
                .unmarshallOptional(
                    null,
                    String.class
                )
        );
    }

    @Test
    default void testUnmarshallOptionalWithNullTypeFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> this.createContext()
                .unmarshallOptional(
                    JsonNode.nullNode(),
                    null
                )
        );
    }

    default <T> void unmarshallOptionalAndCheck(final JsonNode node,
                                                final Class<T> type,
                                                final Optional<T> expected) {
        this.unmarshallOptionalAndCheck(
            this.createContext(),
            node,
            type,
            expected
        );
    }

    // unmarshallOptionalWithType.......................................................................................

    @Test
    default void testUnmarshallOptionalWithTypeWithNullJsonNodeFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> this.createContext()
                .unmarshallOptionalWithType(null)
        );
    }

    default <T> void unmarshallOptionalWithTypeAndCheck(final JsonNode node,
                                                        final Optional<T> expected) {
        this.unmarshallOptionalWithTypeAndCheck(
            this.createContext(),
            node,
            expected
        );
    }
    
    // unmarshallList...................................................................................................

    @Test
    default void testUnmarshallListWithNullTypeFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> this.createContext()
                .unmarshallList(
                    JsonNode.nullNode(),
                    null
                )
        );
    }

    default <T> void unmarshallListAndCheck(final JsonNode node,
                                            final Class<T> type,
                                            final List<T> expected) {
        this.unmarshallListAndCheck(
            this.createContext(),
            node,
            type,
            expected
        );
    }

    // unmarshallSet....................................................................................................

    @Test
    default void testUnmarshallSetWithNullTypeFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> this.createContext()
                .unmarshallSet(
                    JsonNode.nullNode(),
                    null
                )
        );
    }

    default <T> void unmarshallSetAndCheck(final JsonNode node,
                                           final Class<T> type,
                                           final Set<T> expected) {
        this.unmarshallSetAndCheck(
            this.createContext(),
            node,
            type,
            expected
        );
    }

    // unmarshallMap....................................................................................................

    @Test
    default void testUnmarshallMapNullKeyTypeFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> this.createContext()
                .unmarshallMap(
                    JsonNode.array(),
                    null,
                    String.class
                )
        );
    }

    @Test
    default void testUnmarshallMapNullValueTypeFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> this.createContext()
                .unmarshallMap(
                    JsonNode.array(),
                    String.class,
                    null
                )
        );
    }

    // unmarshallWithType...............................................................................................

    default void unmarshallWithTypeAndCheck(final JsonNode node,
                                            final Object expected) {
        this.unmarshallWithTypeAndCheck(
            this.createContext(),
            node,
            expected
        );
    }

    // unmarshallListWithType...........................................................................................

    default void unmarshallListWithTypeAndCheck(final JsonNode node,
                                                final List<?> expected) {
        this.unmarshallListWithTypeAndCheck(
            this.createContext(),
            node,
            expected
        );
    }

    // unmarshallSetWithType............................................................................................

    default void unmarshallSetWithTypeAndCheck(final JsonNode node,
                                               final Set<?> expected) {
        this.unmarshallSetWithTypeAndCheck(
            this.createContext(),
            node,
            expected
        );
    }

    // unmarshallMapWithType............................................................................................

    default void unmarshallMapWithTypeAndCheck(final JsonNode node,
                                               final Map<?, ?> expected) {
        this.unmarshallMapWithTypeAndCheck(
            this.createContext(),
            node,
            expected
        );
    }

    @Override
    default C createCanLocaleForLanguageTag() {
        return this.createContext();
    }

    // TypeNameTesting..................................................................................................

    @Override
    default String typeNameSuffix() {
        return JsonNodeUnmarshallContext.class.getSimpleName();
    }
}
