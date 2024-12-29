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

import walkingkooka.collect.map.Maps;
import walkingkooka.text.CharSequences;
import walkingkooka.text.CharacterConstant;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.visit.Visiting;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents an immutable json object
 */
public final class JsonObject extends JsonParentNode<JsonObjectList> {

    private final static JsonPropertyName NAME = JsonPropertyName.fromClass(JsonObject.class);

    /**
     * An empty json object.
     */
    final static JsonObject EMPTY = new JsonObject(NAME, NO_INDEX, JsonObjectList.EMPTY);

    private final static CharacterConstant BEGIN = CharacterConstant.with('{');
    private final static CharacterConstant END = CharacterConstant.with('}');
    private final static String KEY_VALUE_SEPARATOR = ": ";
    private final static String AFTER = ",";

    /**
     * Private ctor use {@link #EMPTY} to start.
     */
    private JsonObject(final JsonPropertyName name, final int index, final JsonObjectList children) {
        super(name, index, children);
    }

    /**
     * Makes a copy of the list and sets the parent upon the children.
     */
    @Override
    JsonObjectList adoptChildren(final JsonObjectList children) {
        final Optional<JsonNode> parent = Optional.of(this);

        final Map<JsonPropertyName, JsonNode> nameToValues = Maps.ordered();
        int i = 0;
        for (JsonNode child : children) {
            nameToValues.put(child.name(), child.setParent(parent, child.name, i));
            i++;
        }

        return JsonObjectList.with(nameToValues);
    }

    /**
     * Returns a {@link JsonObject} with no parent but equivalent children.
     */
    @Override
    public JsonObject removeParent() {
        return this.removeParent0()
                .cast(JsonObject.class);
    }

    @Override
    boolean childrenEquals(final List<JsonNode> children) {
        final Map<JsonPropertyName, JsonNode> nameToValues = this.children.nameToValues;

        boolean equals = nameToValues.size() == children.size();
        if (equals) {

            for (JsonNode child : children) {
                equals = JsonParentNodeChildPredicate.INSTANCE.test(
                        nameToValues.get(child.name),
                        child
                ); // predicate doesnt throw if 1st param is null returns false.
                if (!equals) {
                    break;
                }
            }
        }

        return equals;
    }

    /**
     * Retrieves a property using its name, returning empty if its absent.
     */
    public Optional<JsonNode> get(final JsonPropertyName name) {
        checkName(name);

        return Optional.ofNullable(this.children.nameToValues.get(name));
    }

    /**
     * Retrieves a property value or throws a {@link IllegalArgumentException}.
     */
    public JsonNode getOrFail(final JsonPropertyName name) {
        return this.get(name).orElseThrow(() -> {
            throw new IllegalArgumentException("Unknown property " + CharSequences.quoteAndEscape(name.value()) + " in " + this);
        });
    }

    @Override
    public JsonObject setChild(final JsonPropertyName name, final JsonNode value) {
        return this.set(name, value);
    }

    /**
     * Sets a new property or replaces an existing.
     */
    public JsonObject set(final JsonPropertyName name, final JsonNode value) {
        checkName(name);
        Objects.requireNonNull(value, "value");

        final JsonNode previous = this.children.nameToValues.get(name);
        final JsonNode value2 = value.setName0(name);
        return null != previous ?
                this.setChild(previous, name, value2) :
                this.addChild(name, value2);
    }

    private JsonObject setChild(final JsonNode previous,
                                final JsonPropertyName name,
                                final JsonNode value) {
        return JsonParentNodeChildPredicate.INSTANCE.test(previous, value) ?
                this :
                this.setChild0(previous.index(), name, value);
    }

    private JsonObject setChild0(final int index,
                                 final JsonPropertyName name,
                                 final JsonNode value) {
        final Map<JsonPropertyName, JsonNode> children = Maps.ordered();

        int i = 0;
        for (Entry<JsonPropertyName, JsonNode> nameAndValue : this.children.nameToValues.entrySet()) {
            if (index == i) {
                children.put(name, value);
            } else {
                children.put(nameAndValue.getKey(), nameAndValue.getValue());
            }
            i++;
        }

        return this.setChildren0(JsonObjectList.with(children))
                .cast(JsonObject.class);
    }

    private JsonObject addChild(final JsonPropertyName name, final JsonNode value) {
        final Map<JsonPropertyName, JsonNode> children = Maps.ordered();
        children.putAll(this.children.nameToValues);
        children.put(name, value.setName0(name));

        return this.replaceChildren(JsonObjectList.with(children))
                .cast(JsonObject.class);
    }

    /**
     * Creates a new list of children and replaces the child at the given slot.
     */
    //@Override
    private JsonObject replaceChild0(final JsonNode newChild, final int index) {
        final Map<JsonPropertyName, JsonNode> newChildren = Maps.ordered();
        final JsonPropertyName newChildName = newChild.name;

        for (Entry<JsonPropertyName, JsonNode> nameAndValue : this.children.nameToValues.entrySet()) {
            final JsonPropertyName name = nameAndValue.getKey();
            newChildren.put(name,
                    newChildName.equals(name) ?
                            newChild :
                            nameAndValue.getValue());
        }

        return this.replaceChildren(JsonObjectList.with(newChildren))
                .cast(JsonObject.class);
    }

    @Override
    public JsonObject setName(final JsonPropertyName name) {
        checkName(name);
        return this.setName0(name)
                .cast(JsonObject.class);
    }

    @Override
    public JsonObject setChildren(final List<JsonNode> children) {
        Objects.requireNonNull(children, "children");

        final Map<JsonPropertyName, JsonNode> copy = Maps.ordered();
        for (JsonNode child : children) {
            copy.put(child.name(), child);
        }

        return this.setChildren0(JsonObjectList.with(copy))
                .cast(JsonObject.class);
    }

    @Override
    JsonNode setChild0(final JsonNode newChild, final int index) {
        return JsonParentNodeChildPredicate.INSTANCE.test(this.children.get(index), newChild) ?
                this :
                this.replaceChild0(newChild, index).children.nameToValues.get(newChild.name);
    }

    /**
     * Returns a {@link JsonObject} without the given key. If they key does not exist the original (this) is returned.
     */
    public JsonObject remove(final JsonPropertyName name) {
        checkName(name);

        final Map<JsonPropertyName, JsonNode> copy = Maps.ordered();
        for (JsonNode child : children) {
            copy.put(child.name(), child);
        }
        copy.remove(name);

        return this.setChildren0(JsonObjectList.with(copy))
                .cast(JsonObject.class);
    }

    /**
     * Tests if the given property is present.
     */
    public boolean contains(final JsonPropertyName name) {
        return this.get(name).isPresent();
    }

    @Override
    JsonObject replace0(final JsonPropertyName name, final int index, final JsonObjectList children) {
        return new JsonObject(name, index, children);
    }

    /**
     * Merges the given {@link JsonObject} copying all its properties onto this object, any properties with the same
     * name will replace an existing property.
     */
    public JsonObject merge(final JsonObject merge) {
        Objects.requireNonNull(merge, "merge");

        JsonObject merged = this;

        for (final JsonNode child : merge.children()) {
            merged = merged.set(
                    child.name(),
                    child
            );
        }

        return merged;
    }

    /**
     * Returns a {@link Map} view of the object's properties.
     */
    public Map<JsonPropertyName, JsonNode> asMap() {
        return Maps.readOnly(this.children.nameToValues);
    }

    @Override
    JsonPropertyName defaultName() {
        return NAME;
    }

    /**
     * Objects are not an array so fail.
     */
    @Override
    public JsonArray arrayOrFail() {
        return this.reportInvalidNode("Array");
    }

    @Override
    public JsonObject objectOrFail() {
        return this;
    }

    // JsonNodeVisitor .................................................................................................

    @Override
    public void accept(final JsonNodeVisitor visitor) {
        if (Visiting.CONTINUE == visitor.startVisit(this)) {
            this.acceptValues(visitor);
        }
        visitor.endVisit(this);
    }

    // removeFalseLike..................................................................................................

    @Override
    public JsonNode removeFalseLike() {
        return this.setChildren(
                        this.children()
                                .stream()
                                .filter(JsonNode::isNotFalseLike)
                                .map(JsonNode::removeFalseLike)
                                .collect(Collectors.toList())
                );
    }

    // TreePrint........................................................................................................

    @Override
    void printJson0(final IndentingPrinter printer) {
        printer.print(BEGIN.string());

        final int size = this.children.size();
        if (size > 0) {
            printer.println();
            printer.indent();

            int i = size - 1;
            for (JsonNode child : this.children) {
                printer.print(CharSequences.quoteAndEscape(child.name().value()));
                printer.print(KEY_VALUE_SEPARATOR);
                child.printJson(printer);

                if (i > 0) {
                    printer.print(AFTER);
                }
                i--;
                printer.println();
            }

            printer.outdent();
        }

        printer.print(END.string());
    }

    // JsonNode.........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof JsonObject;
    }

    /**
     * Only returns true if the child count is the same and all children are equal, note order is not important
     */
    @Override//
    boolean equalsChildren(final JsonNode other) {
        return this.children.nameToValues.equals(
                other.objectOrFail()
                        .children
                        .nameToValues
        );
    }
}
