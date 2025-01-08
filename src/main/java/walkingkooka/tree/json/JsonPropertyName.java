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

import walkingkooka.Cast;
import walkingkooka.naming.Name;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.CharSequences;

/**
 * The name of any property of object key.
 */
public final class JsonPropertyName implements Name,
    Comparable<JsonPropertyName> {

    /**
     * The size of a cache for {@link JsonPropertyName} by index
     */
    // VisibleForTesting
    final static int INDEX_CACHE_SIZE = 128;

    /**
     * Creates a {@link JsonPropertyName} from the index.
     */
    static JsonPropertyName index(final int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index " + index + " must not be negative");
        }
        return index < INDEX_CACHE_SIZE ? INDEX_CACHE[index] : new JsonPropertyName(index);
    }

    private final static JsonPropertyName[] INDEX_CACHE = fillIndexCache();

    private static JsonPropertyName[] fillIndexCache() {
        final JsonPropertyName[] cache = new JsonPropertyName[INDEX_CACHE_SIZE];
        for (int i = 0; i < INDEX_CACHE_SIZE; i++) {
            cache[i] = new JsonPropertyName(i);
        }
        return cache;
    }

    /**
     * Factory that creates a new {@link JsonPropertyName}
     */
    public static JsonPropertyName with(final String name) {
        return new JsonPropertyName(
            CharSequences.failIfNullOrEmpty(name, "name")
        );
    }

    // helper only used by various JsonXXX.NAME constants.
    static JsonPropertyName fromClass(final Class<? extends JsonNode> klass) {
        final String name = klass.getSimpleName();
        return new JsonPropertyName(name.substring("Json".length()));
    }

    private JsonPropertyName(final int index) {
        this(String.valueOf(index));
    }

    private JsonPropertyName(final String name) {
        this.name = name;
    }

    // Value.........................................................................................................

    @Override
    public String value() {
        return this.name;
    }

    private final String name;

    /**
     * Returns a {@link JsonString} with this value.
     */
    public JsonString toJsonString() {
        return JsonNode.string(this.value());
    }

    // Object..........................................................................................................

    @Override
    public int hashCode() {
        return CASE_SENSITIVITY.hash(this.name);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof JsonPropertyName &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final JsonPropertyName other) {
        return CASE_SENSITIVITY.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return this.name;
    }

    // Comparable ......................................................................................................

    @Override
    public int compareTo(final JsonPropertyName other) {
        return CASE_SENSITIVITY.comparator().compare(this.name, other.name);
    }

    // HasCaseSensitivity................................................................................................

    @Override
    public CaseSensitivity caseSensitivity() {
        return CASE_SENSITIVITY;
    }

    private final static CaseSensitivity CASE_SENSITIVITY = CaseSensitivity.SENSITIVE;
}
