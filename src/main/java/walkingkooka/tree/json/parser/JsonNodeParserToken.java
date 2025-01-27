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
package walkingkooka.tree.json.parser;

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.text.Whitespace;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.cursor.parser.ParserTokenVisitor;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.visit.Visiting;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a token within the grammar.
 */
public abstract class JsonNodeParserToken implements ParserToken {

    /**
     * {@see ArrayJsonNodeParserToken}
     */
    public static ArrayJsonNodeParserToken array(final List<ParserToken> value, final String text) {
        return ArrayJsonNodeParserToken.with(value, text);
    }

    /**
     * {@see ArrayBeginSymbolJsonNodeParserToken}
     */
    public static ArrayBeginSymbolJsonNodeParserToken arrayBeginSymbol(final String value, final String text) {
        return ArrayBeginSymbolJsonNodeParserToken.with(value, text);
    }

    /**
     * {@see ArrayEndSymbolJsonNodeParserToken}
     */
    public static ArrayEndSymbolJsonNodeParserToken arrayEndSymbol(final String value, final String text) {
        return ArrayEndSymbolJsonNodeParserToken.with(value, text);
    }

    /**
     * {@see BooleanJsonNodeParserToken}
     */
    public static BooleanJsonNodeParserToken booleanJsonNodeParserToken(final boolean value, final String text) {
        return BooleanJsonNodeParserToken.with(value, text);
    }

    /**
     * {@see NullJsonNodeParserToken}
     */
    public static NullJsonNodeParserToken nullJsonNodeParserToken(final String text) {
        return NullJsonNodeParserToken.with(null, text);
    }

    /**
     * {@see NumberJsonNodeParserToken}
     */
    public static NumberJsonNodeParserToken number(final double value, final String text) {
        return NumberJsonNodeParserToken.with(value, text);
    }

    /**
     * {@see ObjectJsonNodeParserToken}
     */
    public static ObjectJsonNodeParserToken object(final List<ParserToken> value, final String text) {
        return ObjectJsonNodeParserToken.with(value, text);
    }

    /**
     * {@see ObjectAssignmentSymbolJsonNodeParserToken}
     */
    public static ObjectAssignmentSymbolJsonNodeParserToken objectAssignmentSymbol(final String value, final String text) {
        return ObjectAssignmentSymbolJsonNodeParserToken.with(value, text);
    }

    /**
     * {@see ObjectBeginSymbolJsonNodeParserToken}
     */
    public static ObjectBeginSymbolJsonNodeParserToken objectBeginSymbol(final String value, final String text) {
        return ObjectBeginSymbolJsonNodeParserToken.with(value, text);
    }

    /**
     * {@see ObjectEndSymbolJsonNodeParserToken}
     */
    public static ObjectEndSymbolJsonNodeParserToken objectEndSymbol(final String value, final String text) {
        return ObjectEndSymbolJsonNodeParserToken.with(value, text);
    }

    /**
     * {@see SeparatorSymbolJsonNodeParserToken}
     */
    public static SeparatorSymbolJsonNodeParserToken separatorSymbol(final String value, final String text) {
        return SeparatorSymbolJsonNodeParserToken.with(value, text);
    }

    /**
     * {@see StringJsonNodeParserToken}
     */
    public static StringJsonNodeParserToken string(final String value, final String text) {
        return StringJsonNodeParserToken.with(value, text);
    }

    /**
     * {@see WhitespaceJsonNodeParserToken}
     */
    public static WhitespaceJsonNodeParserToken whitespace(final String value, final String text) {
        return WhitespaceJsonNodeParserToken.with(value, text);
    }

    static List<ParserToken> copyAndCheckTokens(final List<ParserToken> tokens) {
        Objects.requireNonNull(tokens, "tokens");
        return Lists.immutable(tokens);
    }

    static String checkText(final String text) {
        return Whitespace.failIfNullOrEmptyOrWhitespace(text, "text");
    }

    /**
     * Package private ctor to limit sub classing.
     */
    JsonNodeParserToken(final String text) {
        this.text = text;
    }

    @Override
    public final String text() {
        return this.text;
    }

    private final String text;

    /**
     * Fetches the value which may be a scalar or child tokens.
     */
    abstract Object value();

    // isXXX.............................................................................................................

    /**
     * Only {@link ArrayJsonNodeParserToken} return true
     */
    public final boolean isArray() {
        return this instanceof ArrayJsonNodeParserToken;
    }

    /**
     * Only {@link ArrayBeginSymbolJsonNodeParserToken} return true
     */
    public final boolean isArrayBeginSymbol() {
        return this instanceof ArrayBeginSymbolJsonNodeParserToken;
    }

    /**
     * Only {@link ArrayEndSymbolJsonNodeParserToken} return true
     */
    public final boolean isArrayEndSymbol() {
        return this instanceof ArrayEndSymbolJsonNodeParserToken;
    }

    /**
     * Only {@link BooleanJsonNodeParserToken} return true
     */
    public final boolean isBoolean() {
        return this instanceof BooleanJsonNodeParserToken;
    }

    /**
     * Returns true for leaf nodes.
     */
    @Override
    public final boolean isLeaf() {
        return this instanceof LeafJsonNodeParserToken;
    }

    /**
     * Only {@link NullJsonNodeParserToken} return true
     */
    public final boolean isNull() {
        return this instanceof NullJsonNodeParserToken;
    }

    /**
     * Only {@link NumberJsonNodeParserToken} return true
     */
    public final boolean isNumber() {
        return this instanceof NumberJsonNodeParserToken;
    }

    /**
     * Only {@link ObjectJsonNodeParserToken} return true
     */
    public final boolean isObject() {
        return this instanceof ObjectJsonNodeParserToken;
    }

    /**
     * Only {@link ObjectAssignmentSymbolJsonNodeParserToken} return true
     */
    public final boolean isObjectAssignmentSymbol() {
        return this instanceof ObjectAssignmentSymbolJsonNodeParserToken;
    }

    /**
     * Only {@link ObjectBeginSymbolJsonNodeParserToken} return true
     */
    public final boolean isObjectBeginSymbol() {
        return this instanceof ObjectBeginSymbolJsonNodeParserToken;
    }

    /**
     * Only {@link ObjectEndSymbolJsonNodeParserToken} return true
     */
    public final boolean isObjectEndSymbol() {
        return this instanceof ObjectEndSymbolJsonNodeParserToken;
    }

    /**
     * Returns true for array or object.
     */
    @Override
    public final boolean isParent() {
        return this instanceof ParentJsonNodeParserToken;
    }

    /**
     * Only {@link SeparatorSymbolJsonNodeParserToken} return true
     */
    public final boolean isSeparatorSymbol() {
        return this instanceof SeparatorSymbolJsonNodeParserToken;
    }

    /**
     * Only {@link SymbolJsonNodeParserToken} return true
     */
    @Override
    public final boolean isSymbol() {
        return this instanceof SymbolJsonNodeParserToken;
    }

    /**
     * Only {@link StringJsonNodeParserToken} return true
     */
    public final boolean isString() {
        return this instanceof StringJsonNodeParserToken;
    }

    /**
     * Only {@link WhitespaceJsonNodeParserToken} return true
     */
    @Override
    public final boolean isWhitespace() {
        return this instanceof WhitespaceJsonNodeParserToken;
    }

    // Visitor .........................................................................................................

    /**
     * Begins the visiting process.
     */
    @Override
    public final void accept(final ParserTokenVisitor visitor) {
        if (visitor instanceof JsonNodeParserTokenVisitor) {
            final JsonNodeParserTokenVisitor visitor2 = Cast.to(visitor);

            if (Visiting.CONTINUE == visitor2.startVisit(this)) {
                this.accept(visitor2);
            }
            visitor2.endVisit(this);
        }
    }

    abstract void accept(final JsonNodeParserTokenVisitor visitor);

    /**
     * Converts this token to its {@link JsonNode} equivalent. Note that {@link WhitespaceJsonNodeParserToken} will
     * be removed
     */
    final public Optional<JsonNode> toJsonNode() {
        return Optional.ofNullable(this.toJsonNodeOrNull());
    }

    /**
     * Returns the {@link JsonNode} form or null.
     */
    abstract JsonNode toJsonNodeOrNull();

    /**
     * Sub-classes should add themselves to the list of children.
     */
    abstract void addJsonNode(final List<JsonNode> children);

    // Object ..........................................................................................................

    @Override
    public final int hashCode() {
        return Objects.hash(this.text, this.value());
    }

    @Override
    public final boolean equals(final Object other) {
        return this == other ||
            null != other && this.getClass() == other.getClass() && this.equals0(Cast.to(other));
    }

    private boolean equals0(final JsonNodeParserToken other) {
        return this.text().equals(other.text()) &&
            Objects.equals(this.value(), other.value()); // needs to be null safe because of NullJsonNodeParserToken
    }

    @Override
    public final String toString() {
        return this.text();
    }
}
