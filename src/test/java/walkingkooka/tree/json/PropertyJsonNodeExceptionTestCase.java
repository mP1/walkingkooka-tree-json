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

package walkingkooka.tree.json;

import org.junit.jupiter.api.Test;
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.ThrowableTesting2;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class PropertyJsonNodeExceptionTestCase<T extends PropertyJsonNodeException> implements ThrowableTesting2<T>,
    ToStringTesting<T> {

    @Override
    public final void testIfClassIsFinalIfAllConstructorsArePrivate() {
        throw new UnsupportedOperationException();
    }

    @Test
    public final void testNewNullPropertyNameFails() {
        assertThrows(NullPointerException.class, () -> this.create(null, JsonNode.object()));
    }

    @Test
    public final void testNewNullJsonFails() {
        assertThrows(NullPointerException.class, () -> this.create(JsonPropertyName.with("abc123"), null));
    }

    @Test
    public final void testNew() {
        final JsonNode json = JsonNode.string("xyz");

        final JsonPropertyName property = JsonPropertyName.with("abc");
        final T thrown = this.create(property, json);
        this.checkMessage(thrown, this.messagePrefix() + " \"" + property + "\" in " + json);
        assertSame(json, thrown.node(), "node");
    }

    @Test
    public final void testToString() {
        this.toStringAndCheck(
            this.create(JsonPropertyName.with("abc"), JsonNode.string("xyz")),
            this.type().getName() + ": " + this.messagePrefix() + " \"abc\" in \"xyz\""
        );
    }

    abstract T create(final JsonPropertyName name, final JsonNode json);

    abstract String messagePrefix();

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
