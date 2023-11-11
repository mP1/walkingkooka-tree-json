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
import walkingkooka.collect.list.Lists;
import walkingkooka.text.HasText;
import walkingkooka.tree.search.SearchNode;

import java.util.List;
import java.util.Optional;
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
    @SuppressWarnings("unchecked")
    final JsonParentNode<C> replaceChildren(final C children) {
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

    // HasSearchNode...............................................................................................

    @Override
    public final SearchNode toSearchNode() {
        return this.children.isEmpty() ?
                SearchNode.text("", "") :
                this.toSearchNode0();
    }

    abstract SearchNode toSearchNode0();

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
    public String text() {
        return this.children().stream()
                .map(HasText::text)
                .collect(Collectors.joining());
    }

    // HasTextLength...................................................................................................

    @Override
    public int textLength() {
        return this.children().stream()
                .mapToInt(HasText::textLength)
                .sum();
    }

    // javascript.......................................................................................................

    @Override
    public final boolean toBoolean() {
        return this.children().size() > 0;
    }

    @Override
    public final Optional<JsonNode> removeFalseLike() {
        final List<JsonNode> filtered = Lists.array();

        for (final JsonNode child : this.children()) {
            final Optional<JsonNode> removed = child.removeFalseLike();
            if (removed.isPresent()) {
                filtered.add(
                        removed.get()
                );
            }
        }

        return filtered.isEmpty() ?
                Optional.empty() :
                Optional.of(
                        this.setChildren(filtered)
                );
    }

    // Object.....................................................................................................

    @Override
    public final int hashCode() {
        return this.children.hashCode();
    }

    /**
     * Only returns true if the descendants of this node and the given children are equal ignoring the parents.
     * A compatibility test between both objects is also done as this is called directly when parents compare their children.
     */
    @Override
    final boolean equalsDescendants(final JsonNode other) {
        boolean equals = this.canBeEqual(other);

        if (equals) {
            final C children = this.children;
            final int count = children.size();

            final C otherChildren = Cast.to(other.children());
            equals = count == other.children().size();

            if (equals) {
                for (int i = 0; equals && i < count; i++) {
                    equals = this.equalsDescendants0(children.get(i), otherChildren, i);
                }
            }
        }

        return equals;
    }

    /**
     * Tests a child for equality. It should ignore the parent.
     */
    abstract boolean equalsDescendants0(final JsonNode child, final C otherChildren, final int i);

    /**
     * Tests if the immediate value belonging to this node for equality.
     */
    @Override
    final boolean equalsValue(final JsonNode other) {
        return true; // no other properties name already tested.
    }
}
