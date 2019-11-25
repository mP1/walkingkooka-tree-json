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

import walkingkooka.collect.list.Lists;
import walkingkooka.text.CharacterConstant;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.tree.search.HasSearchNode;
import walkingkooka.tree.search.SearchNode;
import walkingkooka.visit.Visiting;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents an immutable json array
 */
public final class JsonArray extends JsonParentNode<List<JsonNode>> {

    private final static JsonPropertyName NAME = JsonPropertyName.fromClass(JsonArray.class);

    final static JsonArray EMPTY = new JsonArray(NAME, NO_INDEX, Lists.empty());

    private final static CharacterConstant BEGIN = CharacterConstant.with('[');
    private final static CharacterConstant END = CharacterConstant.with(']');

    private JsonArray(final JsonPropertyName name, final int index, final List<JsonNode> children) {
        super(name, index, children);
    }

    /**
     * Makes a copy of the list and sets the parent upon the children.
     */
    @Override
    final List<JsonNode> adoptChildren(final List<JsonNode> children) {
        final Optional<JsonNode> parent = Optional.of(this);

        final List<JsonNode> copy = Lists.array();
        int i = 0;
        for (JsonNode child : children) {
            copy.add(child.setParent(parent,
                    JsonPropertyName.index(i),
                    i));
            i++;
        }

        return copy;
    }

    @Override
    public JsonArray setName(final JsonPropertyName name) {
        checkName(name);
        return this.setName0(name)
                .cast(JsonArray.class);
    }

    /**
     * Returns a {@link JsonArray} with no parent but equivalent children.
     */
    @Override
    public JsonArray removeParent() {
        return this.removeParent0()
                .cast(JsonArray.class);
    }

    /**
     * Would be setter that returns an array instance with the provided children, creating a new instance if necessary.
     */
    @Override
    public final JsonArray setChildren(final List<JsonNode> children) {
        Objects.requireNonNull(children, "children");

        return this.setChildren0(children)
                .cast(JsonArray.class);
    }

    /**
     * Compares the two lists, with a custom {@link java.util.function.BiPredicate} to compare child/elements.
     */
    @Override
    boolean childrenEquals(final List<JsonNode> children) {
        return Lists.equals(this.children, children, JsonParentNodeChildPredicate.INSTANCE);
    }

    /**
     * Retrieves the element at the provided index.
     */
    public JsonNode get(final int index) {
        return this.children().get(index);
    }

    @Override
    public JsonArray setChild(final int index, final JsonNode element) {
        return this.set(index, element);
    }

    /**
     * Sets or replaces the element at the given index, returning a new array if necessary.
     * If the given index is passed the current number of children, {@link JsonNull} will be used to fill the new slots.
     * This matches behaviour in javascript.
     */
    public JsonArray set(final int index, final JsonNode element) {
        if (index < 0) {
            throw new IllegalArgumentException("Invalid index " + index + "=" + this);
        }
        Objects.requireNonNull(element, "element");

        final List<JsonNode> children = this.copyChildren();

        if (index >= children.size()) {
            while (index > children.size()) {
                children.add(nullNode());
            }
            children.add(element);
        } else {
            children.set(index, element);
        }

        return this.setChildren0(children)
                .cast(JsonArray.class);
    }

    /**
     * Appends the given element returning a new instance.
     */
    @Override
    public JsonArray appendChild(final JsonNode element) {
        Objects.requireNonNull(element, "element");

        final List<JsonNode> children = this.copyChildren();
        children.add(element);

        return this.replace0(this.name, this.index, children)
                .cast(JsonArray.class);
    }

    /**
     * Removes the child at the given index.
     */
    public JsonArray remove(final int index) {
        final List<JsonNode> children = this.copyChildren();
        children.remove(index);

        return this.replace0(this.name, this.index, children);
    }

    /**
     * Sets a new length for this {@link JsonArray}. Shorter lengths truncate existing elements, while longer lengths
     * insert {@link JsonNull}.
     */
    public JsonArray setLength(final int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Invalid length=" + length);
        }

        final int current = this.children().size();
        return current == length ?
                this :
                length < current ?
                        this.setLengthShorter(length) :
                        this.setLengthLonger(length);
    }

    private JsonArray setLengthShorter(final int length) {
        final List<JsonNode> children = this.copyChildren();

        while (length != children.size()) {
            children.remove(length);
        }
        return this.setChildren0(children)
                .cast(JsonArray.class);
    }

    private JsonArray setLengthLonger(final int length) {
        final List<JsonNode> children = this.copyChildren();

        while (length != children.size()) {
            children.add(nullNode());
        }
        return this.setChildren0(children)
                .cast(JsonArray.class);
    }

    /**
     * Creates a new list of children and replaces the child at the given slot, returning the new child.
     */
    @Override
    final JsonNode setChild0(final JsonNode newChild, final int index) {
        final List<JsonNode> newChildren = this.copyChildren();
        newChildren.set(index, newChild);

        return this.replaceChildren(newChildren).children().get(index);
    }

    @Override
    JsonArray replace0(final JsonPropertyName name, final int index, final List<JsonNode> children) {
        return new JsonArray(name, index, children);
    }

    /**
     * Copies the current children {@link List} into a new one ready for modification.
     */
    private List<JsonNode> copyChildren() {
        final List<JsonNode> copy = Lists.array();
        copy.addAll(this.children);
        return copy;
    }

    @Override
    JsonPropertyName defaultName() {
        return NAME;
    }

    // HasSearchNode....................................................................................................

    @Override
    SearchNode toSearchNode0() {
        return SearchNode.sequence(this.children.stream()
                .map(HasSearchNode::toSearchNode)
                .collect(Collectors.toList()));
    }

    // isXXX............................................................................................................

    /**
     * Is an array return this.
     */
    @Override
    public final JsonArray arrayOrFail() {
        return this;
    }

    /**
     * Arrays are not an object.
     */
    @Override
    public final JsonObject objectOrFail() {
        return this.reportInvalidNode(Object.class);
    }

    // Visitor .................................................................................................

    @Override
    public void accept(final JsonNodeVisitor visitor) {
        if (Visiting.CONTINUE == visitor.startVisit(this)) {
            this.acceptValues(visitor);
        }
        visitor.endVisit(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof JsonArray;
    }

    /**
     * Only returns true if the descendants of this node and the given children are equal ignoring the parents.
     */
    @Override
    boolean equalsDescendants0(final JsonNode child, final List<JsonNode> otherChildren, final int i) {
        return child.equalsNameValueAndDescendants(otherChildren.get(i));
    }

    @Override
    void printJson0(final IndentingPrinter printer) {
        printer.print(BEGIN.string());

        String separator = "";
        for (JsonNode child : this.children) {
            printer.print(separator);
            separator = ", ";

            child.printJson(printer);
        }

        printer.print(END.string());
    }
}
