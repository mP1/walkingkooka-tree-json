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

import walkingkooka.text.CharSequences;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.tree.search.SearchNode;

import java.util.Objects;

/**
 * Represents an immutable json string.
 */
public final class JsonString extends JsonLeafNonNullNode<String> {

    static JsonString with(final String value) {
        Objects.requireNonNull(value, "value");
        return new JsonString(NAME, NO_INDEX, value);
    }

    private final static JsonPropertyName NAME = JsonPropertyName.fromClass(JsonString.class);

    private JsonString(final JsonPropertyName name, final int index, final String value) {
        super(name, index, value);
    }

    @Override
    public JsonString setName(final JsonPropertyName name) {
        checkName(name);
        return this.setName0(name)
                .cast(JsonString.class);
    }

    public JsonString setValue(final String value) {
        return this.setValue0(value)
                .cast(JsonString.class);
    }

    @Override
    JsonString replace0(final JsonPropertyName name, final int index, final String value) {
        return new JsonString(name, index, value);
    }

    /**
     * Returns a {@link JsonNumber} with the same value.
     */
    @Override
    public JsonString removeParent() {
        return this.removeParent0()
                .cast(JsonString.class);
    }

    @Override
    JsonPropertyName defaultName() {
        return NAME;
    }

    // HasText......................................................................................................

    @Override
    public String text() {
        return this.value;
    }

    // HasSearchNode...............................................................................................

    @Override
    public SearchNode toSearchNode() {
        final String text = this.text();
        return SearchNode.text(text, this.value());
    }

    // JsonNodeVisitor .................................................................................................

    @Override
    public void accept(final JsonNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof JsonString;
    }

    @Override
    void printJson0(final IndentingPrinter printer) {
        printer.print(CharSequences.quoteAndEscape(this.value));
    }
}
