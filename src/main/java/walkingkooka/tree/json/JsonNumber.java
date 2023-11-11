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
 * Represents an immutable json number.
 */
public final class JsonNumber extends JsonLeafNonNullNode<Double> {

    static JsonNumber with(final double value) {
        return new JsonNumber(NAME, NO_INDEX, value);
    }

    private final static JsonPropertyName NAME = JsonPropertyName.fromClass(JsonNumber.class);


    private JsonNumber(final JsonPropertyName name, final int index, final double value) {
        super(name, index, value);
    }

    @Override
    public JsonNumber setName(final JsonPropertyName name) {
        checkName(name);
        return this.setName0(name)
                .cast(JsonNumber.class);
    }

    public JsonNumber setValue(final double value) {
        return this.setValue0(value)
                .cast(JsonNumber.class);
    }

    @Override
    JsonNumber replace0(final JsonPropertyName name, final int index, final Double value) {
        return new JsonNumber(name, index, value);
    }

    /**
     * Returns a {@link JsonNumber} with the same value.
     */
    @Override
    public JsonNumber removeParent() {
        return this.removeParent0()
                .cast(JsonNumber.class);
    }

    @Override
    JsonPropertyName defaultName() {
        return NAME;
    }

    // HasText......................................................................................................

    @Override
    public String text() {
        final long i = this.value.longValue();
        return i == this.value ?
                String.valueOf(i) :
                String.valueOf(this.value);
    }

    // HasSearchNode...............................................................................................

    @Override
    public SearchNode toSearchNode() {
        final String text = this.text();
        return SearchNode.doubleSearchNode(text, this.value());
    }

    // Visitor .........................................................................................................

    @Override
    public void accept(final JsonNodeVisitor visitor) {
        visitor.visit(this);
    }

    // javascript.......................................................................................................

    @Override
    public boolean toBoolean() {
        return this.value != 0;
    }

    // JsonNode .................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof JsonNumber;
    }

    @Override
    void printJson0(final IndentingPrinter printer) {
        printer.print(this.text());
    }
}
