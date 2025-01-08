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
import walkingkooka.visit.Visiting;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class JsonNumberTest extends JsonLeafNonNullNodeTestCase<JsonNumber, Double> {

    @Override
    public void testNumberOrFail() {
        this.checkEquals(1.0,
            JsonNumber.with(1).numberOrFail());
    }

    @Test
    public void testNumberOrFail2() {
        this.checkEquals(2.0,
            JsonNumber.with(2).numberOrFail());
    }

    @Test
    public void testTextWholeNumber() {
        this.checkEquals("123", JsonNumber.with(123).text());
    }

    @Test
    public void testTextDecimalNumber() {
        this.checkEquals("123.5", JsonNumber.with(123.5).text());
    }

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final JsonNumber node = this.createJsonNode();

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
            protected void visit(final JsonNumber n) {
                assertSame(node, n);
                b.append("3");
            }
        }.accept(node);
        this.checkEquals("132", b.toString());
    }

    // isFalseLike......................................................................................................

    @Test
    public void testIsFalseLikeWhenZero() {
        this.isFalseLike(
            JsonNode.number(0),
            true
        );
    }

    @Test
    public void testIsFalseLikeWhenMinusZero() {
        this.isFalseLike(
            JsonNode.number(-0.0),
            true
        );
    }

    @Test
    public void testIsFalseLikeWhenNonZero() {
        this.isFalseLike(
            JsonNode.number(1),
            false
        );
    }

    // toBoolean......................................................................................................

    @Test
    public void testToBooleanZero() {
        this.toBooleanAndCheck(
            JsonNumber.number(0),
            false
        );
    }

    @Test
    public void testToBooleanNonZero() {
        this.toBooleanAndCheck(
            JsonNumber.with(1),
            true
        );
    }

    // toString......................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createJsonNode(1.0), "1");
    }

    @Test
    public void testToString2() {
        this.toStringAndCheck(this.createJsonNode(234.5), "234.5");
    }

    @Override
    JsonNumber createJsonNode(final Double value) {
        return JsonNumber.with(value);
    }

    @Override
    JsonNumber setValue(final JsonNumber node, final Double value) {
        return node.setValue(value);
    }

    @Override
    Double value() {
        return 1.5;
    }

    @Override
    Double differentValue() {
        return 999.0;
    }

    @Override
    Class<JsonNumber> jsonNodeType() {
        return JsonNumber.class;
    }

    @Override
    List<String> propertiesNeverReturnNullSkipProperties() {
        return Lists.of(ARRAY_OR_FAIL,
            BOOLEAN_OR_FAIL,
            CHARACTER_OR_FAIL,
            UNMARSHALL_LIST,
            UNMARSHALL_SET,
            UNMARSHALL_MAP,
            UNMARSHALL,
            OBJECT_OR_FAIL,
            PARENT_OR_FAIL,
            STRING_OR_FAIL);
    }
}
