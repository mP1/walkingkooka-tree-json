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

import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.tree.search.SearchNode;

/**
 * Represents a json null.
 */
public final class JsonNull extends JsonLeafNode<Void> {

    private final static JsonPropertyName NAME = JsonPropertyName.fromClass(JsonNull.class);

    /**
     * Singleton
     */
    final static JsonNull INSTANCE = new JsonNull(NAME, NO_INDEX, null);

    /**
     * Private ctor use singleton
     */
    private JsonNull(final JsonPropertyName name, final int index, final Void value) {
        super(name, index, value);
    }

    @Override
    public JsonNull setName(final JsonPropertyName name) {
        checkName(name);
        return this.setName0(name)
                .cast(JsonNull.class);
    }

    /**
     * Always returns this, only included for completeness.
     */
    public JsonNull setValue(final Void value) {
        return this;
    }

    /**
     * Returns the singleton which doesnt have a parent.
     */
    @Override
    public JsonNull removeParent() {
        return INSTANCE;
    }

    @Override
    JsonNull replace0(final JsonPropertyName name, final int index, final Void value) {
        return new JsonNull(name, index, value);
    }

    @Override
    JsonPropertyName defaultName() {
        return NAME;
    }

    // HasSearchNode...............................................................................................

    @Override
    public SearchNode toSearchNode() {
        final String text = this.text();
        return SearchNode.text(text, text);
    }

    // HasText..........................................................................................................

    @Override
    public final String text() {
        return NULL;
    }

    private final static String NULL = "null";

    // Object......................................................................................................

    @Override
    public final int hashCode() {
        return this.name.hashCode();
    }

    @Override
    final boolean equalsDescendants(final JsonNode other) {
        return true;
    }

    @Override
    final boolean equalsValue(final JsonNode other) {
        return true;
    }

    // Visitor .................................................................................................

    @Override
    public void accept(final JsonNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof JsonNull;
    }

    @Override
    void printJson0(final IndentingPrinter printer) {
        printer.print(String.valueOf(this.value));
    }
}
