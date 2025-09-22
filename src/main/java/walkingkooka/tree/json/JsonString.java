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
        Objects.requireNonNull(name, "name");
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

    // javascript.......................................................................................................

    @Override
    public boolean isFalseLike() {
        return this.value.isEmpty();
    }

    @Override
    public boolean toBoolean() {
        return this.text()
            .length() > 0;
    }

    // JsonNodeVisitor .................................................................................................

    @Override
    public void accept(final JsonNodeVisitor visitor) {
        visitor.visit(this);
    }

    // https://www.json.org/json-en.html
    // https://en.wikipedia.org/wiki/JSON

    /**
     * https://www.crockford.com/mckeeman.html
     * <pre>
     * character
     * '0020' . '10FFFF' - '"' - '\'
     * '\' escape
     * </pre>
     */
    @Override
    void printJson0(final IndentingPrinter printer) {
        final String value = this.value;
        final int length = value.length();

        final StringBuilder encoded = new StringBuilder();
        encoded.append('"'); // open

        for (int i = 0; i < length; i++) {
            final char c = value.charAt(i);

            switch (c) {
                case '"':
                    encoded.append("\\\"");
                    break;
                case '\\':
                    encoded.append("\\\\");
                    break;
                case '\b':
                    encoded.append("\\b");
                    break;
                case '\f':
                    encoded.append("\\f");
                    break;
                case '\n':
                    encoded.append("\\n");
                    break;
                case '\r':
                    encoded.append("\\r");
                    break;
                case '\t':
                    encoded.append("\\t");
                    break;
                default:
                    if (c < 0x20) {
                        encoded.append("\\u")
                            .append(
                                CharSequences.padLeft(
                                    Integer.toHexString(c),
                                    4,
                                    '0'
                                )
                            );
                        break;
                    }
                    encoded.append(c);
                    break;
            }
        }

        encoded.append('"'); // close

        printer.print(encoded);
    }
}
