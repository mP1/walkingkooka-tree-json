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
     * {@see JsonNodeArrayParserToken}
     */
    public static JsonNodeArrayParserToken array(final List<ParserToken> value, final String text) {
        return JsonNodeArrayParserToken.with(value, text);
    }

    /**
     * {@see JsonNodeArrayBeginSymbolParserToken}
     */
    public static JsonNodeArrayBeginSymbolParserToken arrayBeginSymbol(final String value, final String text) {
        return JsonNodeArrayBeginSymbolParserToken.with(value, text);
    }

    /**
     * {@see JsonNodeArrayEndSymbolParserToken}
     */
    public static JsonNodeArrayEndSymbolParserToken arrayEndSymbol(final String value, final String text) {
        return JsonNodeArrayEndSymbolParserToken.with(value, text);
    }

    /**
     * {@see JsonNodeBooleanParserToken}
     */
    public static JsonNodeBooleanParserToken booleanParserToken(final boolean value, final String text) {
        return JsonNodeBooleanParserToken.with(value, text);
    }

    /**
     * {@see JsonNodeNullParserToken}
     */
    public static JsonNodeNullParserToken nullParserToken(final String text) {
        return JsonNodeNullParserToken.with(null, text);
    }

    /**
     * {@see JsonNodeNumberParserToken}
     */
    public static JsonNodeNumberParserToken number(final double value, final String text) {
        return JsonNodeNumberParserToken.with(value, text);
    }

    /**
     * {@see JsonNodeObjectParserToken}
     */
    public static JsonNodeObjectParserToken object(final List<ParserToken> value, final String text) {
        return JsonNodeObjectParserToken.with(value, text);
    }

    /**
     * {@see JsonNodeObjectAssignmentSymbolParserToken}
     */
    public static JsonNodeObjectAssignmentSymbolParserToken objectAssignmentSymbol(final String value, final String text) {
        return JsonNodeObjectAssignmentSymbolParserToken.with(value, text);
    }

    /**
     * {@see JsonNodeObjectBeginSymbolParserToken}
     */
    public static JsonNodeObjectBeginSymbolParserToken objectBeginSymbol(final String value, final String text) {
        return JsonNodeObjectBeginSymbolParserToken.with(value, text);
    }

    /**
     * {@see JsonNodeObjectEndSymbolParserToken}
     */
    public static JsonNodeObjectEndSymbolParserToken objectEndSymbol(final String value, final String text) {
        return JsonNodeObjectEndSymbolParserToken.with(value, text);
    }

    /**
     * {@see JsonNodeSeparatorSymbolParserToken}
     */
    public static JsonNodeSeparatorSymbolParserToken separatorSymbol(final String value, final String text) {
        return JsonNodeSeparatorSymbolParserToken.with(value, text);
    }

    /**
     * {@see JsonNodeStringParserToken}
     */
    public static JsonNodeStringParserToken string(final String value, final String text) {
        return JsonNodeStringParserToken.with(value, text);
    }

    /**
     * {@see JsonNodeWhitespaceParserToken}
     */
    public static JsonNodeWhitespaceParserToken whitespace(final String value, final String text) {
        return JsonNodeWhitespaceParserToken.with(value, text);
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
     * Only {@link JsonNodeArrayParserToken} return true
     */
    public final boolean isArray() {
        return this instanceof JsonNodeArrayParserToken;
    }

    /**
     * Only {@link JsonNodeArrayBeginSymbolParserToken} return true
     */
    public final boolean isArrayBeginSymbol() {
        return this instanceof JsonNodeArrayBeginSymbolParserToken;
    }

    /**
     * Only {@link JsonNodeArrayEndSymbolParserToken} return true
     */
    public final boolean isArrayEndSymbol() {
        return this instanceof JsonNodeArrayEndSymbolParserToken;
    }

    /**
     * Only {@link JsonNodeBooleanParserToken} return true
     */
    public final boolean isBoolean() {
        return this instanceof JsonNodeBooleanParserToken;
    }

    /**
     * Returns true for leaf nodes.
     */
    @Override
    public final boolean isLeaf() {
        return this instanceof JsonNodeLeafParserToken;
    }

    /**
     * Only {@link JsonNodeNullParserToken} return true
     */
    public final boolean isNull() {
        return this instanceof JsonNodeNullParserToken;
    }

    /**
     * Only {@link JsonNodeNumberParserToken} return true
     */
    public final boolean isNumber() {
        return this instanceof JsonNodeNumberParserToken;
    }

    /**
     * Only {@link JsonNodeObjectParserToken} return true
     */
    public final boolean isObject() {
        return this instanceof JsonNodeObjectParserToken;
    }

    /**
     * Only {@link JsonNodeObjectAssignmentSymbolParserToken} return true
     */
    public final boolean isObjectAssignmentSymbol() {
        return this instanceof JsonNodeObjectAssignmentSymbolParserToken;
    }

    /**
     * Only {@link JsonNodeObjectBeginSymbolParserToken} return true
     */
    public final boolean isObjectBeginSymbol() {
        return this instanceof JsonNodeObjectBeginSymbolParserToken;
    }

    /**
     * Only {@link JsonNodeObjectEndSymbolParserToken} return true
     */
    public final boolean isObjectEndSymbol() {
        return this instanceof JsonNodeObjectEndSymbolParserToken;
    }

    /**
     * Returns true for array or object.
     */
    @Override
    public final boolean isParent() {
        return this instanceof JsonNodeParentParserToken;
    }

    /**
     * Only {@link JsonNodeSeparatorSymbolParserToken} return true
     */
    public final boolean isSeparatorSymbol() {
        return this instanceof JsonNodeSeparatorSymbolParserToken;
    }

    /**
     * Only {@link JsonNodeSymbolParserToken} return true
     */
    public final boolean isSymbol() {
        return this instanceof JsonNodeSymbolParserToken;
    }

    /**
     * Only {@link JsonNodeStringParserToken} return true
     */
    public final boolean isString() {
        return this instanceof JsonNodeStringParserToken;
    }

    /**
     * Only {@link JsonNodeWhitespaceParserToken} return true
     */
    public final boolean isWhitespace() {
        return this instanceof JsonNodeWhitespaceParserToken;
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
     * Converts this token to its {@link JsonNode} equivalent. Note that {@link JsonNodeWhitespaceParserToken} will
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

    // Object ...........................................................................................................

    @Override
    public final int hashCode() {
        return Objects.hash(this.text, this.value());
    }

    @Override
    public final boolean equals(final Object other) {
        return this == other ||
                this.canBeEqual(other) &&
                        this.equals0(Cast.to(other));
    }

    abstract boolean canBeEqual(final Object other);

    private boolean equals0(final JsonNodeParserToken other) {
        return this.text().equals(other.text()) &&
                Objects.equals(this.value(), other.value()); // needs to be null safe because of JsonNodeNullParserToken
    }

    @Override
    public final String toString() {
        return this.text();
    }
}
