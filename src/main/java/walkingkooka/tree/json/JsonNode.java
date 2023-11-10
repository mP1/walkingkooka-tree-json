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
import walkingkooka.collect.map.Maps;
import walkingkooka.naming.Name;
import walkingkooka.text.CharSequences;
import walkingkooka.text.HasText;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.text.cursor.TextCursors;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserException;
import walkingkooka.text.cursor.parser.ParserReporters;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.Printers;
import walkingkooka.text.printer.TreePrintable;
import walkingkooka.tree.Node;
import walkingkooka.tree.TraversableHasTextOffset;
import walkingkooka.tree.expression.FunctionExpressionName;
import walkingkooka.tree.json.parser.JsonNodeParserContext;
import walkingkooka.tree.json.parser.JsonNodeParserContexts;
import walkingkooka.tree.json.parser.JsonNodeParserToken;
import walkingkooka.tree.json.parser.JsonNodeParsers;
import walkingkooka.tree.search.HasSearchNode;
import walkingkooka.tree.select.NodeSelector;
import walkingkooka.tree.select.parser.NodeSelectorExpressionParserToken;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Base class for all json nodes, all of which are immutable. Note that performing a seemingly mutable operation
 * actually returns a new graph of nodes as would be expected including all parents and the root.
 */
public abstract class JsonNode implements Node<JsonNode, JsonPropertyName, Name, Object>,
        HasSearchNode,
        HasText,
        TraversableHasTextOffset<JsonNode>,
        TreePrintable {

    /**
     * Parsers the given json and returns its {@link JsonNode} equivalent.
     */
    public static JsonNode parse(final String text) {
        try {
            return PARSER.parse(TextCursors.charSequence(text),
                            JsonNodeParserContexts.basic())
                    .get()
                    .cast(JsonNodeParserToken.class)
                    .toJsonNode().get();
        } catch (final ParserException cause) {
            throw new IllegalArgumentException(cause.getMessage(), cause);
        }
    }

    /**
     * Parser that will consume json or report a parsing error.
     */
    private final static Parser<JsonNodeParserContext> PARSER = JsonNodeParsers.value()
            .andEmptyTextCursor()
            .orReport(ParserReporters.basic())
            .cast();

    public static JsonArray array() {
        return JsonArray.EMPTY;
    }

    public static JsonBoolean booleanNode(final boolean value) {
        return JsonBoolean.with(value);
    }

    public static JsonNull nullNode() {
        return JsonNull.INSTANCE;
    }

    public static JsonNumber number(final double value) {
        return JsonNumber.with(value);
    }

    public static JsonObject object() {
        return JsonObject.EMPTY;
    }

    public static JsonString string(final String value) {
        return JsonString.with(value);
    }

    private final static Optional<JsonNode> NO_PARENT = Optional.empty();

    /**
     * Package private ctor to limit sub classing.
     */
    JsonNode(final JsonPropertyName name, final int index) {
        super();
        this.name = name;
        this.parent = NO_PARENT;
        this.index = index;
    }

    // Name ..............................................................................................

    @Override
    public final JsonPropertyName name() {
        return this.name;
    }

    /**
     * Returns an instance with the given name, creating a new instance if necessary.
     */
    abstract public JsonNode setName(final JsonPropertyName name);

    static void checkName(final JsonPropertyName name) {
        Objects.requireNonNull(name, "name");
    }

    final JsonNode setName0(final JsonPropertyName name) {
        return this.name.equals(name) ?
                this :
                this.replaceName(name);
    }

    final JsonPropertyName name;

    /**
     * Returns a new instance with the given name.
     */
    private JsonNode replaceName(final JsonPropertyName name) {
        return this.replace(name, this.index);
    }

    @Override
    public final boolean hasUniqueNameAmongstSiblings() {
        return true;
    }

    // parent ..................................................................................................

    @Override
    public final Optional<JsonNode> parent() {
        return this.parent;
    }

    /**
     * Sub classes should call this and cast.
     */
    final JsonNode removeParent0() {
        return this.isRoot() ?
                this :
                this.replace(this.defaultName(), NO_INDEX);
    }

    /**
     * This setter is used to recreate the entire graph including parents of parents receiving new children.
     * It is only ever called by a parent node and is used to adopt new children.
     */
    final JsonNode setParent(final Optional<JsonNode> parent,
                             final JsonPropertyName name,
                             final int index) {
        final JsonNode copy = this.replace(name, index);
        copy.parent = parent;
        return copy;
    }

    private Optional<JsonNode> parent;

    /**
     * Replaces this {@link JsonNode} with the given {@link JsonNode} providing its different, keeping the parent and siblings.
     * Note the replaced {@link JsonNode} will gain the name of the previous.
     */
    @Override
    public final JsonNode replace(final JsonNode node) {
        Objects.requireNonNull(node, "node");

        return this.isRoot() ?
                node.removeParent() :
                this.replace0(node);
    }

    private JsonNode replace0(final JsonNode node) {
        return this.parent()
                .map(p -> p.setChild(this.name, node).children().get(this.index))
                .orElse(node);
    }

//    abstract JsonNode

    /**
     * Sub classes must create a new copy of the parent and replace the identified child using its index or similar,
     * and also sets its parent after creation, returning the equivalent child at the same index.
     */
    abstract JsonNode setChild0(final JsonNode newChild, final int index);

    /**
     * Only ever called after during the completion of a setChildren, basically used to recreate the parent graph
     * containing this child.
     */
    final JsonNode replaceChild(final Optional<JsonNode> previousParent, final int index) {
        return previousParent.isPresent() ?
                previousParent.get()
                        .setChild0(this, index) :
                this;
    }

    // index............................................................................................................

    @Override
    public final int index() {
        return this.index;
    }

    final int index;

    /**
     * Factory method that creates a new sub class of {@link JsonLeafNode} that is the same type as this.
     */
    abstract JsonNode replace(final JsonPropertyName name, final int index);

    // attributes............................................................................................................

    @Override
    public final Map<Name, Object> attributes() {
        return Maps.empty();
    }

    @Override
    public final JsonNode setAttributes(final Map<Name, Object> attributes) {
        Objects.requireNonNull(attributes, "attributes");
        throw new UnsupportedOperationException();
    }

    // Value<Object>................................................................................................

    public abstract Object value();

    /**
     * If a {@link JsonBoolean} returns the boolean value or fails.
     */
    public final boolean booleanOrFail() {
        if (!this.isBoolean()) {
            this.reportInvalidNode(Boolean.class);
        }

        return Cast.to(this.value());
    }

    /**
     * If a {@link JsonString} returns the character value or fails. The {@link String} must have a single character.
     */
    public final char characterOrFail() {
        final String string = this.stringOrFail();

        final int length = string.length();
        if (1 != length) {
            throw new java.lang.IllegalArgumentException("Character string must have length of 1 not " + length + " got " + CharSequences.quoteAndEscape(string));
        }
        return string.charAt(0);
    }

    /**
     * If a {@link JsonNumber} returns the number value or fails.
     */
    public final Number numberOrFail() {
        if (!this.isNumber()) {
            this.reportInvalidNode(Number.class);
        }

        return Cast.to(this.value());
    }

    /**
     * If a {@link JsonString} returns the string value or fails.
     */
    public final String stringOrFail() {
        if (!this.isString()) {
            this.reportInvalidNode(String.class);
        }

        return Cast.to(this.value());
    }

    /**
     * Type safe cast that reports a nice message about the failing array.
     */
    public abstract JsonArray arrayOrFail();

    /**
     * Type safe cast that reports a nice message about the failing object.
     */
    public abstract JsonObject objectOrFail();

    /**
     * Reports a failed attempt to extract a value or cast a node.
     */
    final <V> V reportInvalidNode(final Class<?> type) {
        return reportInvalidNode(type.getSimpleName());
    }

    /**
     * Reports a failed attempt to extract a value or cast a node. The message will include the expected and the
     * target type and its toString representation.
     */
    final <V> V reportInvalidNode(final String type) {
        throw new ClassCastException("Expected " + type + " got " + this.defaultName() + ": " + this);
    }

    // isXXX............................................................................................................

    public final boolean isArray() {
        return this instanceof JsonArray;
    }

    public final boolean isBoolean() {
        return this instanceof JsonBoolean;
    }

    public final boolean isNull() {
        return this instanceof JsonNull;
    }

    public final boolean isNumber() {
        return this instanceof JsonNumber;
    }

    public final boolean isObject() {
        return this instanceof JsonObject;
    }

    public final boolean isString() {
        return this instanceof JsonString;
    }

    /**
     * Unsafe cast to a sub class of {@link JsonNode}, if this fails a {@link ClassCastException} will be thrown.
     */
    @SuppressWarnings("unchecked")
    public final <T extends JsonNode> T cast(final Class<T> type) {
        return (T) this;
    }

    abstract void accept(final JsonNodeVisitor visitor);

    /**
     * Returns the default name for this type. This is used to assign a default name for a {@link Node} when it has no
     * parent.
     */
    abstract JsonPropertyName defaultName();

    // javascript.......................................................................................................

    /**
     * Returns true if this value is equivalent to the false following javascript conventions.
     */
    public abstract boolean isFalseLike();

    // TreePrintable.....................................................................................................

    @Override
    public final void printTree(final IndentingPrinter printer) {
        this.printJson(printer);
    }

    // Object .......................................................................................................

    @Override
    public abstract int hashCode();

    @Override
    public final boolean equals(final Object other) {
        return this == other ||
                this.canBeEqual(other) &&
                        this.equals0(Cast.to(other));
    }

    abstract boolean canBeEqual(final Object other);

    private boolean equals0(final JsonNode other) {
        return this.equalsAncestors(other) && this.equalsDescendants(other);
    }

    private boolean equalsAncestors(final JsonNode other) {
        boolean result = this.equalsNameAndValue(other);

        if (result) {
            final Optional<JsonNode> parent = this.parent();
            final Optional<JsonNode> otherParent = other.parent();
            final boolean hasParent = parent.isPresent();
            final boolean hasOtherParent = otherParent.isPresent();

            if (hasParent) {
                if (hasOtherParent) {
                    result = parent.get().equalsAncestors(otherParent.get());
                }
            } else {
                // result is only true if other is false
                result = !hasOtherParent;
            }
        }

        return result;
    }

    final boolean equalsNameValueAndDescendants(final JsonNode other) {
        return this.canBeEqual(other) &&
                this.equalsNameAndValue(other) &&
                this.equalsDescendants(other);
    }

    abstract boolean equalsDescendants(final JsonNode other);

    private boolean equalsNameAndValue(final JsonNode other) {
        return this.name.equals(other.name) &&
                this.equalsValue(other);
    }

    /**
     * Sub classes should do equals but ignore the parent and children properties.
     */
    abstract boolean equalsValue(final JsonNode other);

    final static Indentation INDENTATION = Indentation.SPACES2;

    /**
     * Pretty prints the entire json graph.
     */
    @Override
    public final String toString() {
        final StringBuilder b = new StringBuilder();
        try (final IndentingPrinter printer = Printers.stringBuilder(b, LineEnding.SYSTEM).indenting(INDENTATION)) {
            this.printJson(printer);
        }
        return b.toString();
    }

    /**
     * Prints this node to the printer.<br>
     * Other combinations of printers can be used to ignore printing all possible optional whitespace.
     */
    public final void printJson(final IndentingPrinter printer) {
        Objects.requireNonNull(printer, "printer");
        this.printJson0(printer);
        printer.flush();
    }

    abstract void printJson0(final IndentingPrinter printer);

    /**
     * {@see NodeSelector#absolute}
     */
    public static NodeSelector<JsonNode, JsonPropertyName, Name, Object> absoluteNodeSelector() {
        return NodeSelector.absolute();
    }

    /**
     * {@see NodeSelector#relative}
     */
    public static NodeSelector<JsonNode, JsonPropertyName, Name, Object> relativeNodeSelector() {
        return NodeSelector.relative();
    }

    /**
     * Creates a {@link NodeSelector} for {@link JsonNode} from a {@link NodeSelectorExpressionParserToken}.
     */
    public static NodeSelector<JsonNode, JsonPropertyName, Name, Object> nodeSelectorExpressionParserToken(final NodeSelectorExpressionParserToken token,
                                                                                                           final Predicate<FunctionExpressionName> functions) {
        return NodeSelector.parserToken(token,
                n -> JsonPropertyName.with(n.value()),
                functions,
                JsonNode.class);
    }
}
