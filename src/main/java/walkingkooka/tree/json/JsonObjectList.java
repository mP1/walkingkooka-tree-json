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

import walkingkooka.collect.list.ImmutableList;
import walkingkooka.collect.list.ImmutableListDefaults;
import walkingkooka.collect.map.Maps;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * An immutable {@link List} view of elements belonging to a {@link JsonObject}.
 */
final class JsonObjectList extends AbstractList<JsonNode> implements ImmutableListDefaults<ImmutableList<JsonNode>, JsonNode> {

    /**
     * Empty list constant.
     */
    static final JsonObjectList EMPTY = new JsonObjectList(Maps.empty());

    /**
     * Factory only used by {@link JsonObject}
     */
    static JsonObjectList with(final Map<JsonPropertyName, JsonNode> nameToValues) {
        return new JsonObjectList(nameToValues);
    }

    // @VisibleForTesting
    JsonObjectList(final Map<JsonPropertyName, JsonNode> nameToValues) {
        super();
        this.nameToValues = nameToValues;
    }

    @Override
    public JsonNode get(int index) {
        return this.list().get(index);
    }

    @Override
    public int size() {
        return this.nameToValues.size();
    }

    @Override
    public JsonObjectList subList(final int from,
                                  final int to) {
        if (from < 0) {
            throw new IndexOutOfBoundsException("Invalid from index " + from + " < 0");
        }

        if (to < from) {
            throw new IndexOutOfBoundsException("Invalid to index " + to + " < " + from);
        }

        final int size = this.size();
        if (to > size) {
            throw new IndexOutOfBoundsException("Invalid to index " + to + " > " + size);
        }

        final JsonObjectList jsonObjectList;

        if (0 == from && this.size() == to) {
            jsonObjectList = this;
        } else {
            if (from == to) {
                jsonObjectList = EMPTY;
            } else {
                final Map<JsonPropertyName, JsonNode> copy = Maps.sorted();

                int i = 0;
                for (final Entry<JsonPropertyName, JsonNode> nameAndValue : this.nameToValues.entrySet()) {
                    if (i >= to) {
                        break;
                    }
                    if (i >= from) {
                        copy.put(
                            nameAndValue.getKey(),
                            nameAndValue.getValue()
                        );
                    }
                    i++;
                }

                jsonObjectList = new JsonObjectList(copy);
            }
        }

        return jsonObjectList;
    }

    @Override
    public String toString() {
        final String toString = this.nameToValues.toString();
        return '[' + toString.substring(1, toString.length() - 1) + ']';
    }

    final Map<JsonPropertyName, JsonNode> nameToValues;

    /**
     * Lazily loaded list view of json object properties.
     */
    private List<JsonNode> list() {
        if (null == this.list) {
            this.list = new ArrayList<>(this.nameToValues.values());
        }
        return this.list;
    }

    private List<JsonNode> list;

    // ImmutableList....................................................................................................

    /**
     * Null elements within an array or object are not allowed.
     */
    @Override
    public void elementCheck(final JsonNode node) {
        Objects.requireNonNull(node, "node");
    }

    @Override
    public ImmutableList<JsonNode> setElements(final Collection<JsonNode> collection) {
        Objects.requireNonNull(collection, "collection");

        final Map<JsonPropertyName, JsonNode> map = Maps.sorted();

        for (final JsonNode json : collection) {
            map.put(
                json.name(),
                json
            );
        }

        return map.equals(this.nameToValues) ?
            this :
            new JsonObjectList(map);
    }
}
