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

import walkingkooka.NeverError;
import walkingkooka.Value;
import walkingkooka.collect.list.Lists;

import java.util.List;
import java.util.Objects;

/**
 * Base type for all the leaf json nodes that hold a simple value, including null.
 */
abstract class JsonLeafNode<V> extends JsonNode implements Value<V> {

    JsonLeafNode(final JsonPropertyName name, final int index, final V value) {
        super(name, index);
        this.value = value;
    }

    @Override
    public final V value() {
        return this.value;
    }

    final V value;

    //abstract JsonLeafNode<V> setValue(final V value);

    final JsonLeafNode<V> setValue0(final V value) {
        return Objects.equals(this.value(), value) ?
                this :
                this.replaceValue(value);
    }

    @SuppressWarnings("unchecked") final JsonLeafNode<V> replaceValue(final V value) {
        return this.replace0(this.name, this.index, value)
                .replaceChild(this.parent(), this.index)
                .cast(JsonLeafNode.class);
    }

    @Override final JsonNode replace(final JsonPropertyName name, final int index) {
        return this.replace0(name, index, this.value);
    }

    abstract JsonLeafNode<V> replace0(final JsonPropertyName name, final int index, final V value);

    /**
     * leaf nodes are not an array and always fail.
     */
    @Override
    public final JsonArray arrayOrFail() {
        return this.reportInvalidNode("Array");
    }

    /**
     * leaf objects are not an object and always fail.
     */
    @Override
    public final JsonObject objectOrFail() {
        return this.reportInvalidNode(Object.class);
    }

    @Override
    public final List<JsonNode> children() {
        return Lists.empty();
    }

    @Override
    public final JsonNode setChildren(final List<JsonNode> children) {
        Objects.requireNonNull(children, "children");
        throw new UnsupportedOperationException();
    }

    @Override final JsonNode setChild0(final JsonNode newChild, final int index) {
        throw new NeverError(this.getClass().getSimpleName() + ".setChild");
    }
}
