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

import walkingkooka.Cast;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.pointer.NodePointer;

import java.util.Optional;
import java.util.function.Function;

/**
 * Type safe {@link JsonNode} wrapper around {@link NodePointer}.
 */
public final class JsonPointer implements Function<JsonNode, Optional<JsonNode>> {

    public static JsonPointer parse(final String pointer) {
        return new JsonPointer(
            NodePointer.parse(
                pointer,
                JsonPropertyName::with,
                JsonNode.class
            )
        );
    }

    private JsonPointer(final NodePointer<JsonNode, JsonPropertyName> nodePointer) {
        super();

        this.nodePointer = nodePointer;
    }

    @Override
    public Optional<JsonNode> apply(final JsonNode node) {
        return this.nodePointer.traverse(node);
    }

    private final NodePointer<JsonNode, JsonPropertyName> nodePointer;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.nodePointer.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof JsonPointer &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final JsonPointer other) {
        return this.nodePointer.equals(other.nodePointer);
    }

    @Override
    public String toString() {
        return this.nodePointer.toString();
    }
}
