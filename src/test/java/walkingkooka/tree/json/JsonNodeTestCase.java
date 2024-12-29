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
import walkingkooka.Cast;
import walkingkooka.collect.map.Maps;
import walkingkooka.naming.Name;
import walkingkooka.reflect.BeanPropertiesTesting;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.IsMethodTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.HasTextTesting;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.Printers;
import walkingkooka.tree.HasTextOffsetTesting;
import walkingkooka.tree.NodeTesting;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class JsonNodeTestCase<N extends JsonNode> implements BeanPropertiesTesting,
        ClassTesting2<JsonNode>,
        HasTextOffsetTesting,
        HasTextTesting,
        IsMethodTesting<N>,
        NodeTesting<JsonNode, JsonPropertyName, Name, Object> {

    JsonNodeTestCase() {
        super();
    }

    @Test
    public final void testSetNameNullFails() {
        assertThrows(NullPointerException.class, () -> this.createJsonNode().setName(null));
    }

    @Test
    public final void testSetNameSame() {
        final N node = this.createJsonNode();
        assertSame(node, node.setName(node.name()));
    }

    @Test
    public abstract void testSetNameDifferent();

    @Test
    public final void testSetAttributesFails() {
        assertThrows(UnsupportedOperationException.class, () -> this.createJsonNode().setAttributes(Maps.empty()));
    }

    // ToXXXValueOrFail.................................................................................................
    @Test
    public void testBooleanOrFail() {
        final JsonNode node = this.createNode();
        final ClassCastException thrown = assertThrows(ClassCastException.class, node::booleanOrFail);
        this.checkEquals("Expected Boolean got " + node.defaultName() + ": " + node, thrown.getMessage());
    }

    @Test
    public void testNumberOrFail() {
        final JsonNode node = this.createNode();
        final ClassCastException thrown = assertThrows(ClassCastException.class, node::numberOrFail);
        this.checkEquals("Expected Number got " + node.defaultName() + ": " + node, thrown.getMessage());
    }

    @Test
    public void testStringOrFail() {
        final JsonNode node = this.createNode();
        final ClassCastException thrown = assertThrows(ClassCastException.class, node::stringOrFail);
        this.checkEquals("Expected String got " + node.defaultName() + ": " + node, thrown.getMessage());
    }

    @Test
    public void testObjectOrFail() {
        final N node = this.createJsonNode();
        if (node instanceof JsonObject) {
            assertSame(node, node.objectOrFail());
        } else {
            final ClassCastException thrown = assertThrows(ClassCastException.class, node::objectOrFail);
            this.checkEquals("Expected Object got " + node.defaultName() + ": " + node, thrown.getMessage());
        }
    }

    @Test
    @Override
    public final void testPropertiesNeverReturnNull() throws Exception {
        this.allPropertiesNeverReturnNullCheck(this.createJsonNode(),
                (m) -> this.propertiesNeverReturnNullSkipProperties().contains(m.getName()));
    }

    abstract List<String> propertiesNeverReturnNullSkipProperties();

    final static String ARRAY_OR_FAIL = "arrayOrFail";
    final static String BOOLEAN_OR_FAIL = "booleanOrFail";
    final static String CHARACTER_OR_FAIL = "characterOrFail";
    final static String UNMARSHALL_LIST = "unmarshallWithTypeList";
    final static String UNMARSHALL_SET = "unmarshallWithTypeSet";
    final static String UNMARSHALL_MAP = "unmarshallWithTypeMap";
    final static String UNMARSHALL = "unmarshallWithType";
    final static String NUMBER_OR_FAIL = "numberOrFail";
    final static String OBJECT_OR_FAIL = "objectOrFail";
    final static String PARENT_OR_FAIL = "parentOrFail";
    final static String STRING_OR_FAIL = "stringOrFail";
    final static String VALUE = "value";

    // JsonNodeVisitor..................................................................................................

    @Test
    public final void testAccept2() {
        new JsonNodeVisitor() {
        }.accept(this.createNode());
    }

    // Object...........................................................................................................

    @Test
    public final void testEqualsDifferentParent() {
        this.checkNotEquals(JsonNode.array().appendChild(this.createObject()));
    }

    // HasTextOffset....................................................................................................

    @Test
    public final void testTextOffset() {
        this.textOffsetAndCheck(this.createNode(), 0);
    }

    // isFalseLike......................................................................................................

    final void isFalseLike(final JsonNode node,
                           final boolean expected) {
        this.checkEquals(
                expected,
                node.isFalseLike(),
                node::toString
        );
    }

    // toBoolean......................................................................................................

    final void toBooleanAndCheck(final JsonNode node,
                                   final boolean expected) {
        this.checkEquals(
                expected,
                node.toBoolean(),
                () -> node + " toBoolean"
        );
    }

    @Test
    public final void testToJsonBoolean() {
        final N node = this.createJsonNode();

        this.checkEquals(
                node.toBoolean(),
                node.toJsonBoolean().toBoolean()
        );
    }

    // removeFalseLike..................................................................................................

    final void removeFalseLikeAndCheck(final JsonNode node) {
        final JsonNode removed = node.removeFalseLike();

        assertSame(
                node,
                removed,
                () -> "removeFalseLike " + node
        );
    }

    final void removeFalseLikeAndCheck(final JsonNode node,
                                       final JsonNode expected) {
        this.checkEquals(
                expected,
                node.removeFalseLike(),
                () -> "removeFalseLike " + node
        );
    }

    // printJson........................................................................................................

    @Test
    public void testPrintJsonNullPrinterFails() {
        assertThrows(NullPointerException.class, () -> this.createJsonNode().printJson(null));
    }

    @Test
    public void testPrintJson() {
        final N node = this.createJsonNode();
        final StringBuilder b = new StringBuilder();
        try (final IndentingPrinter printer = Printers.stringBuilder(b, LineEnding.SYSTEM).indenting(Indentation.SPACES2)) {
            node.printJson(printer);
        }

        this.checkEquals(node.toString(), b.toString());
    }

    @Override
    public JsonNode createNode() {
        return this.createJsonNode();
    }

    abstract N createJsonNode();

    @Override
    public Class<JsonNode> type() {
        return Cast.to(this.jsonNodeType());
    }

    abstract Class<N> jsonNodeType();

    @Override
    public final String typeNamePrefix() {
        return "Json";
    }

    @Override
    public final String typeNameSuffix() {
        return "";
    }

    // IsMethodTesting.................................................................................................

    @Override
    public final N createIsMethodObject() {
        return Cast.to(this.createNode());
    }

    @Override
    public final String isMethodTypeNamePrefix() {
        return "Json";
    }

    @Override
    public final String isMethodTypeNameSuffix() {
        return "";
    }

    @Override
    public final Predicate<String> isMethodIgnoreMethodFilter() {
        return (m) -> m.equals("isRoot") ||
                m.equals("toBoolean") ||
                m.equals("isEmpty") ||
                m.equals("isFalseLike");
    }

    // ClassTesting....................................................................................................

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
