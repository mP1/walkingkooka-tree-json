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
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.expression.ExpressionNumberExpression;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedExpressionValueTest extends BasicJsonMarshallerTypedExpressionTestCase<BasicJsonMarshallerTypedExpressionValue<ExpressionNumberExpression, ExpressionNumber>, ExpressionNumberExpression> {

    @Test
    public final void testFromBooleanFails() {
        this.unmarshallFailed(JsonNode.booleanNode(true), ClassCastException.class);
    }

    @Test
    public final void testFromNumberFails() {
        this.unmarshallFailed(JsonNode.number(123), ClassCastException.class);
    }

    @Test
    public final void testFromObjectFails() {
        this.unmarshallFailed(JsonNode.object(), ClassCastException.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    BasicJsonMarshallerTypedExpressionValue marshaller() {
        return BasicJsonMarshallerTypedExpressionValue.with(Expression::expressionNumber,
                ExpressionNumberExpression.class,
                ExpressionNumber.class);
    }

    @Override
    ExpressionNumberExpression value() {
        return Expression.expressionNumber(ExpressionNumber.with(1234));
    }

    @Override
    JsonNode node() {
        return JsonNode.string(this.value().toString() + "D");
    }

    @Override
    String typeName() {
        return "expression-number-expression";
    }

    @Override
    Class<ExpressionNumberExpression> marshallerType() {
        return ExpressionNumberExpression.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedExpressionValue<ExpressionNumberExpression, ExpressionNumber>> type() {
        return Cast.to(BasicJsonMarshallerTypedExpressionValue.class);
    }
}
