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

import org.junit.jupiter.api.Test;
import walkingkooka.naming.NameTesting;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.CaseSensitivity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class JsonPropertyNameTest implements ClassTesting2<JsonPropertyName>,
        NameTesting<JsonPropertyName, JsonPropertyName> {

    @Test
    public void testWithNegativeIndexFails() {
        assertThrows(IllegalArgumentException.class, () -> JsonPropertyName.index(-1));
    }

    @Test
    public void testFromClassArray() {
        this.fromClassAndCheck(JsonArray.class, "Array");
    }

    @Test
    public void testFromClassBoolean() {
        this.fromClassAndCheck(JsonBoolean.class, "Boolean");
    }

    @Test
    public void testFromClassNull() {
        this.fromClassAndCheck(JsonNull.class, "Null");
    }

    @Test
    public void testFromClassNumber() {
        this.fromClassAndCheck(JsonNumber.class, "Number");
    }

    @Test
    public void testFromClassObject() {
        this.fromClassAndCheck(JsonObject.class, "Object");
    }

    @Test
    public void testFromClassString() {
        this.fromClassAndCheck(JsonString.class, "String");
    }

    private void fromClassAndCheck(final Class<? extends JsonNode> type,
                                   final String expected) {
        assertEquals(expected, JsonPropertyName.fromClass(type).value());
    }

    @Test
    public void testIndex() {
        assertEquals("123", JsonPropertyName.index(123).value());
    }

    @Test
    public void testCompareToArraySort() {
        final JsonPropertyName a1 = JsonPropertyName.with("A1");
        final JsonPropertyName b2 = JsonPropertyName.with("B2");
        final JsonPropertyName c3 = JsonPropertyName.with("c3");
        final JsonPropertyName d4 = JsonPropertyName.with("d4");

        this.compareToArraySortAndCheck(d4, a1, c3, b2,
                a1, b2, c3, d4);
    }

    @Override
    public JsonPropertyName createName(final String name) {
        return JsonPropertyName.with(name);
    }

    @Override
    public CaseSensitivity caseSensitivity() {
        return CaseSensitivity.SENSITIVE;
    }

    @Override
    public String nameText() {
        return "property-2";
    }

    @Override
    public String differentNameText() {
        return "different";
    }

    @Override
    public String nameTextLess() {
        return "property-1";
    }

    @Override
    public Class<JsonPropertyName> type() {
        return JsonPropertyName.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
