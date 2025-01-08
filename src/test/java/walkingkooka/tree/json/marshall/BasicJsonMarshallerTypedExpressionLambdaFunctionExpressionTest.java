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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.FakeExpressionReference;
import walkingkooka.tree.expression.LambdaFunctionExpression;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;
import walkingkooka.tree.json.JsonNode;

import java.util.List;

public final class BasicJsonMarshallerTypedExpressionLambdaFunctionExpressionTest extends BasicJsonMarshallerTypedExpressionTestCase<BasicJsonMarshallerTypedExpressionLambdaFunctionExpression, LambdaFunctionExpression> {

    @BeforeAll
    @SuppressWarnings("unchecked")
    public static void beforeAll() {
        remover = BasicJsonMarshaller.register(
            REFERENCE_TYPE_NAME,
            (n, c) -> REFERENCE,
            (r, c) -> REFERENCE_JSON,
            TestExpressionReference.class
        );
    }

    @AfterAll
    public static void afterAll() {
        remover.run();
    }

    private static Runnable remover;

    private final static String REFERENCE_TYPE_NAME = "test-reference-expression";
    private final static TestExpressionReference REFERENCE = new TestExpressionReference();
    private final static JsonNode REFERENCE_JSON = JsonNode.string("reference-123abc");

    static class TestExpressionReference extends FakeExpressionReference {
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = Lists.of(
        ExpressionFunctionParameterName.with("x")
            .required(String.class)
    );

    private final static Expression BODY = Expression.not(
        Expression.reference(REFERENCE)
    );

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

    @Override
    BasicJsonMarshallerTypedExpressionLambdaFunctionExpression marshaller() {
        return BasicJsonMarshallerTypedExpressionLambdaFunctionExpression.instance();
    }

    private final static String FUNCTION_NAME = "function123";

    @Override
    LambdaFunctionExpression value() {
        return Expression.lambdaFunction(
            PARAMETERS,
            BODY
        );
    }

    @Override
    JsonNode node() {
        final JsonNodeMarshallContext marshallContext = this.marshallContext();

        return JsonNode.object()
            .set(BasicJsonMarshallerTypedExpressionLambdaFunctionExpression.PARAMETERS_JSON_PROPERTY, marshallContext.marshallCollection(PARAMETERS))
            .set(BasicJsonMarshallerTypedExpressionLambdaFunctionExpression.BODY_JSON_PROPERTY, marshallContext.marshallWithType(BODY));
    }

    @Override
    String typeName() {
        return "lambda-function-expression";
    }

    @Override
    Class<LambdaFunctionExpression> marshallerType() {
        return LambdaFunctionExpression.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedExpressionLambdaFunctionExpression> type() {
        return BasicJsonMarshallerTypedExpressionLambdaFunctionExpression.class;
    }
}
