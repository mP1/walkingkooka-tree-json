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

package walkingkooka.tree.json.pointer;

import org.junit.jupiter.api.Test;
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.util.FunctionTesting;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class JsonPointerTest implements FunctionTesting<JsonPointer, JsonNode, Optional<JsonNode>>,
    ClassTesting2<JsonPointer>,
    HashCodeEqualsDefinedTesting2<JsonPointer>,
    ToStringTesting<JsonPointer> {

    // with.............................................................................................................

    @Test
    public void testParseNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> JsonPointer.parse(null)
        );
    }

    // apply.............................................................................................................

    @Test
    public void testApply() {
        this.applyAndCheck(
            this.createFunction(),
            JsonNode.parse("{\"hello\": \"World\"}"),
            Optional.of(
                JsonNode.string("World")
            )
        );
    }

    @Override
    public JsonPointer createFunction() {
        return JsonPointer.parse("/hello");
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferent() {
        this.checkNotEquals(
            JsonPointer.parse("/different")
        );
    }

    @Override
    public JsonPointer createObject() {
        return this.createFunction();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createFunction(),
            "/hello"
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonPointer> type() {
        return JsonPointer.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
