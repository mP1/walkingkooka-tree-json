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
import walkingkooka.tree.expression.AddExpression;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.json.JsonNode;

import java.math.BigInteger;

public final class BasicJsonMarshallerTypedExpressionBinaryTest extends BasicJsonMarshallerTypedExpressionTestCase<BasicJsonMarshallerTypedExpressionBinary<AddExpression>, AddExpression> {

    @Test
    public void testFromBooleanFails() {
        this.unmarshallFailed(JsonNode.booleanNode(true), null);
    }

    @Test
    public void testFromNumberFails() {
        this.unmarshallFailed(JsonNode.number(123), null);
    }

    @Test
    public void testFromStringFails() {
        this.unmarshallFailed(JsonNode.string("abc123"), null);
    }

    @Override
    @SuppressWarnings("unchecked")
    BasicJsonMarshallerTypedExpressionBinary marshaller() {
        return BasicJsonMarshallerTypedExpressionBinary.with(Expression::add, AddExpression.class);
    }

    @Override
    AddExpression value() {
        return AddExpression.add(this.leftValue(), this.rightValue());
    }

    private Expression leftValue() {
        return Expression.expressionNumber(EXPRESSION_NUMBER_KIND.create(11));
    }

    private Expression rightValue() {
        return Expression.string("b2");
    }

    @Override
    JsonNode node() {
        final JsonNodeMarshallContext context = this.marshallContext();

        return JsonNode.array()
                .appendChild(context.marshallWithType(this.leftValue()))
                .appendChild(context.marshallWithType(this.rightValue()));
    }

    @Override
    String typeName() {
        return "add-expression";
    }

    @Override
    Class<AddExpression> marshallerType() {
        return AddExpression.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedExpressionBinary<AddExpression>> type() {
        return Cast.to(BasicJsonMarshallerTypedExpressionBinary.class);
    }
}
