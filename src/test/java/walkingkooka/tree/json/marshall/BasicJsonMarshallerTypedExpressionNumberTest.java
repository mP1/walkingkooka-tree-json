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

package walkingkooka.tree.json.marshall;

import org.junit.jupiter.api.Test;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.json.JsonNode;

import java.math.BigDecimal;

public final class BasicJsonMarshallerTypedExpressionNumberTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedExpressionNumber, ExpressionNumber> {

    @Test
    public void testToJson() {
        this.marshallAndCheck(EXPRESSION_NUMBER_KIND.create(123), JsonNode.string("123"));
    }

    @Test
    public void testFromJsonEmptyString() {
        this.unmarshallFailed(JsonNode.string(""), NumberFormatException.class);
    }

    @Test
    public void testRoundtripBigDecimalZero() {
        this.roundtripAndCheck(EXPRESSION_NUMBER_KIND.create(BigDecimal.ZERO));
    }

    @Test
    public void testRoundtripBigDecimal() {
        this.roundtripAndCheck(EXPRESSION_NUMBER_KIND.create(BigDecimal.ONE));
    }

    @Test
    public void testRoundtripDoubleZero() {
        this.roundtripAndCheck(EXPRESSION_NUMBER_KIND.create(0));
    }

    @Test
    public void testRoundtripDouble() {
        this.roundtripAndCheck(EXPRESSION_NUMBER_KIND.create(1.5));
    }

    private void roundtripAndCheck(final ExpressionNumber number) {
        final JsonNode json = this.marshallContext().marshall(number);
        this.unmarshallAndCheck(json, number);
    }

    @Override
    BasicJsonMarshallerTypedExpressionNumber marshaller() {
        return BasicJsonMarshallerTypedExpressionNumber.instance();
    }

    @Override
    ExpressionNumber value() {
        return EXPRESSION_NUMBER_KIND.create(123.45);
    }

    @Override
    JsonNode node() {
        return JsonNode.string(this.value().toString());
    }

    @Override
    ExpressionNumber jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "expression-number";
    }

    @Override
    Class<ExpressionNumber> marshallerType() {
        return ExpressionNumber.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedExpressionNumber> type() {
        return BasicJsonMarshallerTypedExpressionNumber.class;
    }
}
