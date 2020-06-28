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
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.FunctionExpression;
import walkingkooka.tree.expression.FunctionExpressionName;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public final class BasicJsonMarshallerTypedExpressionFunctionTest extends BasicJsonMarshallerTypedExpressionTestCase<BasicJsonMarshallerTypedExpressionFunction, FunctionExpression> {

    @Test
    public final void testFromBooleanFails() {
        this.unmarshallFailed(JsonNode.booleanNode(true), ClassCastException.class);
    }

    @Test
    public final void testFromNumberFails() {
        this.unmarshallFailed(JsonNode.number(123), ClassCastException.class);
    }

    @Test
    public final void testFromStringFails() {
        this.unmarshallFailed(JsonNode.string("abc123"), ClassCastException.class);
    }

    @Override
    BasicJsonMarshallerTypedExpressionFunction marshaller() {
        return BasicJsonMarshallerTypedExpressionFunction.instance();
    }

    private final static String FUNCTION_NAME = "function123";

    @Override
    FunctionExpression value() {
        return Expression.function(FunctionExpressionName.with(FUNCTION_NAME), this.parameters());
    }

    private List<Expression> parameters() {
        return Lists.of(
                Expression.bigInteger(BigInteger.valueOf(11)),
                Expression.string("b2"),
                Expression.add(Expression.bigDecimal(BigDecimal.valueOf(3)), Expression.bigInteger(BigInteger.valueOf(33))));
    }

    @Override
    JsonNode node() {
        final JsonNodeMarshallContext context = this.marshallContext();

        return JsonNode.array()
                .appendChild(JsonNode.string(FUNCTION_NAME))
                .appendChild(context.marshallWithTypeList(this.parameters()));
    }

    @Override
    String typeName() {
        return "function-expression";
    }

    @Override
    Class<FunctionExpression> marshallerType() {
        return FunctionExpression.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedExpressionFunction> type() {
        return BasicJsonMarshallerTypedExpressionFunction.class;
    }
}
