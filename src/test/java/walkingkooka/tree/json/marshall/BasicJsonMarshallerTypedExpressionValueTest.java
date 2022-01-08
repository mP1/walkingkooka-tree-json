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
import walkingkooka.Cast;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.ValueExpression;
import walkingkooka.tree.json.JsonNode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class BasicJsonMarshallerTypedExpressionValueTest extends BasicJsonMarshallerTypedExpressionTestCase<BasicJsonMarshallerTypedExpressionValue, ValueExpression<?>> {

    @Test
    public void testRoundtripBooleanFalse() {
        this.roundTripAndCheck(Boolean.FALSE);
    }

    @Test
    public void testRoundtripBooleanTrue() {
        this.roundTripAndCheck(Boolean.TRUE);
    }

    @Test
    public void testRoundtripExpressionNumber() {
        this.roundTripAndCheck(
                ExpressionNumberKind.DEFAULT.create(123)
        );
    }

    @Test
    public void testRoundtripLocalDate() {
        this.roundTripAndCheck(
                LocalDate.of(1999, 12, 31)
        );
    }

    @Test
    public void testRoundtripLocalDateTime() {
        this.roundTripAndCheck(
                LocalDateTime.of(1999, 12, 31, 12, 58, 59)
        );
    }

    @Test
    public void testRoundtripLocalTime() {
        this.roundTripAndCheck(
                LocalTime.of(12, 58, 59)
        );
    }

    @Test
    public void testRoundtripString() {
        this.roundTripAndCheck("text123");
    }

    private void roundTripAndCheck(final Object value) {
        final ValueExpression<Object> expression = Expression.value(value);
        final JsonNode json = this.marshallContext()
                .marshall(expression);
        this.unmarshallAndCheck(json, expression);
    }

    @SuppressWarnings("unchecked")
    @Override
    BasicJsonMarshallerTypedExpressionValue marshaller() {
        return BasicJsonMarshallerTypedExpressionValue.instance();
    }

    @Override
    ValueExpression<?> value() {
        return Expression.value("1234");
    }

    @Override
    JsonNode node() {
        return JsonNode.string("1234");
    }

    @Override
    String typeName() {
        return "value-expression";
    }

    @Override
    Class<ValueExpression<?>> marshallerType() {
        return Cast.to(ValueExpression.class);
    }

    @Override
    public Class<BasicJsonMarshallerTypedExpressionValue> type() {
        return BasicJsonMarshallerTypedExpressionValue.class;
    }
}
