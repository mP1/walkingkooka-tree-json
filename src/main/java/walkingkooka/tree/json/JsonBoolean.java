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
 * Represents an immutable json boolean.
 */
public final class JsonBoolean extends JsonLeafNonNullNode<Boolean> {

    static JsonBoolean with(final boolean value) {
        return value ?
                TRUE :
                FALSE;
    }

    private final static JsonPropertyName NAME = JsonPropertyName.fromClass(JsonBoolean.class);

    /**
     * Singleton with a false value and no parent.
     */
    private final static JsonBoolean FALSE = new JsonBoolean(NAME, NO_INDEX, false);

    /**
     * Singleton with a true value and no parent.
     */
    private final static JsonBoolean TRUE = new JsonBoolean(NAME, NO_INDEX, true);

    private JsonBoolean(final JsonPropertyName name, final int index, final boolean value) {
        super(name, index, value);
    }

    @Override
    public JsonBoolean setName(final JsonPropertyName name) {
        checkName(name);
        return this.setName0(name)
                .cast(JsonBoolean.class);
    }

    public JsonBoolean setValue(final boolean value) {
        return this.setValue0(value)
                .cast(JsonBoolean.class);
    }

    @Override
    JsonBoolean replace0(final JsonPropertyName name, final int index, final Boolean value) {
        return new JsonBoolean(name, index, value);
    }

    /**
     * Returns a {@link JsonBoolean} with the same value.
     */
    @Override
    public JsonBoolean removeParent() {
        return this.removeParent0()
                .cast(JsonBoolean.class);
    }

    // HasText......................................................................................................

    @Override
    public String text() {
        return this.value.toString();
    }

    // HasSearchNode...............................................................................................

    @Override
    public SearchNode toSearchNode() {
        final String text = this.text();
        return SearchNode.text(text, text);
    }

    @Override
    JsonPropertyName defaultName() {
        return NAME;
    }

    // javascript.......................................................................................................

    @Override
    public boolean toBoolean() {
        return this.value.booleanValue();
    }

    // Visitor ........................................................................................................

    @Override
    public void accept(final JsonNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof JsonBoolean;
    }

    @Override
    void printJson0(final IndentingPrinter printer) {
        printer.print(String.valueOf(this.value));
    }
}
