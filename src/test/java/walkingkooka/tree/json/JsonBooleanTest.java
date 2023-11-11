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
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.search.SearchNode;
import walkingkooka.visit.Visiting;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class JsonBooleanTest extends JsonLeafNonNullNodeTestCase<JsonBoolean, Boolean> {

    @Test
    public void testWithTrue() {
        this.withAndCheck(true);
    }

    @Test
    public void testWithFalse() {
        this.withAndCheck(false);
    }

    private void withAndCheck(final boolean value) {
        assertSame(JsonBoolean.with(value), JsonBoolean.with(value));
    }

    @Override
    public void testBooleanOrFail() {
        // ignore
    }

    @Test
    public void testBooleanOrFailTrue() {
        this.checkEquals(true,
                JsonBoolean.with(true).booleanOrFail());
    }

    @Test
    public void testBooleanOrFailFalse() {
        this.checkEquals(false,
                JsonBoolean.with(false).booleanOrFail());
    }

    // toSearchNode.........................................................................................

    @Test
    public void testToSearchNodeTrue() {
        this.toSearchNodeAndCheck(this.createJsonNode(true), SearchNode.text("true", "true"));
    }

    @Test
    public void testToSearchNodeFalse() {
        this.toSearchNodeAndCheck(this.createJsonNode(false), SearchNode.text("false", "false"));
    }

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final JsonBoolean node = this.createJsonNode();

        new FakeJsonNodeVisitor() {
            @Override
            protected Visiting startVisit(final JsonNode n) {
                assertSame(node, n);
                b.append("1");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final JsonNode n) {
                assertSame(node, n);
                b.append("2");
            }

            @Override
            protected void visit(final JsonBoolean n) {
                assertSame(node, n);
                b.append("3");
            }
        }.accept(node);
        this.checkEquals("132", b.toString());
    }

    // toBoolean......................................................................................................

    @Test
    public void testToBooleanTrue() {
        this.toBooleanAndCheck(
                JsonBoolean.with(true),
                true
        );
    }

    @Test
    public void testToBooleanFalse() {
        this.toBooleanAndCheck(
                JsonBoolean.with(false),
                false
        );
    }

    // removeFalseLike..................................................................................................

    @Test
    public void testRemoveFalseLikeFalse() {
        this.removeFalseLikeAndCheckNothing(
                JsonBoolean.with(false)
        );
    }

    @Test
    public void testRemoveFalseLikeTrue() {
        this.removeFalseLikeAndCheckSame(
                JsonBoolean.with(true)
        );
    }

    // toString......................................................................................................

    @Test
    public void testToStringTrue() {
        this.toStringAndCheck(this.createJsonNode(true), "true");
    }

    @Test
    public void testToStringFalse() {
        this.toStringAndCheck(this.createJsonNode(false), "false");
    }

    @Override
    JsonBoolean createJsonNode(final Boolean value) {
        return JsonBoolean.with(value);
    }

    @Override
    JsonBoolean setValue(final JsonBoolean node, final Boolean value) {
        return node.setValue(value);
    }

    @Override
    Boolean value() {
        return true;
    }

    @Override
    Boolean differentValue() {
        return false;
    }

    @Override
    Class<JsonBoolean> jsonNodeType() {
        return JsonBoolean.class;
    }

    @Override
    List<String> propertiesNeverReturnNullSkipProperties() {
        return Lists.of(ARRAY_OR_FAIL,
                CHARACTER_OR_FAIL,
                UNMARSHALL_LIST,
                UNMARSHALL_SET,
                UNMARSHALL_MAP,
                UNMARSHALL,
                NUMBER_OR_FAIL,
                OBJECT_OR_FAIL,
                PARENT_OR_FAIL,
                STRING_OR_FAIL);
    }
}
