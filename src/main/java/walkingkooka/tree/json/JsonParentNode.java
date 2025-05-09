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

import walkingkooka.text.HasText;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Base type for all the parent json nodes that hold other nodes, such as array and object
 */
@SuppressWarnings("lgtm[java/inconsistent-equals-and-hashcode]")
abstract class JsonParentNode<C extends List<JsonNode>> extends JsonNode {

    JsonParentNode(final JsonPropertyName name, final int index, final C children) {
        super(name, index);

        this.children = this.adoptChildren(children);
    }

    /**
     * Called during construction to adopt children.
     */
    abstract C adoptChildren(final C children);

    @Override
    public final List<JsonNode> children() {
        return this.children;
    }

    /**
     * A list holding the children.
     */
    final C children;

    /**
     * If the children are different replaces the children otherwise returns this.
     */
    final JsonNode setChildren0(final C children) {
        return this.childrenEquals(children) ?
            this :
            this.replaceChildren(children);
    }

    /**
     * Allows sub-classes to have different strategies to compare children for equality.
     */
    abstract boolean childrenEquals(final List<JsonNode> children);

    /**
     * Returns a new {@link JsonParentNode} with the given children and also updates the parent/ancestors.
     */
    @SuppressWarnings("unchecked") final JsonParentNode<C> replaceChildren(final C children) {
        return this.replace0(this.name, this.index, children)
            .replaceChild(this.parent(), this.index)
            .cast(JsonParentNode.class);
    }

    @Override final JsonNode replace(final JsonPropertyName name, final int index) {
        return this.replace0(name, index, this.children);
    }

    /**
     * Factory that creates a {@link JsonParentNode} of the same type as this with the given new properties.
     */
    abstract JsonParentNode<C> replace0(final JsonPropertyName name, final int index, final C children);

    // Value....................................................................................................

    @Override
    public final Object value() {
        throw new UnsupportedOperationException();
    }

    // Visitor ........................................................................................................

    final void acceptValues(final JsonNodeVisitor visitor) {
        for (JsonNode node : this.children()) {
            visitor.accept(node);
        }
    }

    // HasText......................................................................................................

    /**
     * Combine the text of all children(descendants). Note property names and indices will not be included.
     */
    @Override
    public final String text() {
        return this.children().stream()
            .map(HasText::text)
            .collect(Collectors.joining());
    }

    // HasTextLength...................................................................................................

    @Override
    public final int textLength() {
        return this.children().stream()
            .mapToInt(HasText::textLength)
            .sum();
    }

    // javascript.......................................................................................................

    @Override
    public final boolean isFalseLike() {
        return this.children.isEmpty();
    }

    @Override
    public final boolean toBoolean() {
        return this.children().size() > 0;
    }

    // Object...........................................................................................................

    @Override
    public final int hashCode() {
        return this.children.hashCode();
    }

    @Override final boolean equalsValue(final JsonNode other) {
        return true; // no other properties name already tested.
    }
}
