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
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.FunctionExpressionName;
import walkingkooka.tree.expression.NamedFunctionExpression;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedExpressionNamedFunctionTest extends BasicJsonMarshallerTypedExpressionTestCase<BasicJsonMarshallerTypedExpressionNamedFunction, NamedFunctionExpression> {

    private final static ExpressionNumberKind KIND = ExpressionNumberKind.DEFAULT;

    @Test
    public void testUnmarshallBooleanFails() {
        this.unmarshallFailed(
                JsonNode.booleanNode(true),
                ClassCastException.class
        );
    }

    @Test
    public void testUnmarshallNumberFails() {
        this.unmarshallFailed(
                JsonNode.number(123),
                ClassCastException.class
        );
    }

    @Test
    public void testUnmarshallObjectFails() {
        this.unmarshallFailed(
                JsonNode.object(),
                ClassCastException.class
        );
    }

    @Override
    BasicJsonMarshallerTypedExpressionNamedFunction marshaller() {
        return BasicJsonMarshallerTypedExpressionNamedFunction.instance();
    }

    private final static String FUNCTION_NAME = "function123";

    @Override
    NamedFunctionExpression value() {
        return Expression.namedFunction(
                FunctionExpressionName.with(FUNCTION_NAME)
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.string(FUNCTION_NAME);
    }

    @Override
    String typeName() {
        return "named-function-expression";
    }

    @Override
    Class<NamedFunctionExpression> marshallerType() {
        return NamedFunctionExpression.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedExpressionNamedFunction> type() {
        return BasicJsonMarshallerTypedExpressionNamedFunction.class;
    }
}
