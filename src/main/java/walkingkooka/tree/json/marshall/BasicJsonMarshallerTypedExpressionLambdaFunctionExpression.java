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

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.LambdaFunctionExpression;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;

import java.util.List;

/**
 * A {@link BasicJsonMarshaller} that handles {@link LambdaFunctionExpression}
 */
final class BasicJsonMarshallerTypedExpressionLambdaFunctionExpression extends BasicJsonMarshallerTypedExpression<LambdaFunctionExpression> {

    static BasicJsonMarshallerTypedExpressionLambdaFunctionExpression instance() {
        return new BasicJsonMarshallerTypedExpressionLambdaFunctionExpression();
    }

    private BasicJsonMarshallerTypedExpressionLambdaFunctionExpression() {
        super(LambdaFunctionExpression.class);
    }

    @Override
    LambdaFunctionExpression unmarshallNonNull(final JsonNode node,
                                               final JsonNodeUnmarshallContext context) {
        List<ExpressionFunctionParameter<?>> parameters = Lists.empty();
        Expression body = null;

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();

            switch (name.value()) {
                case PARAMETERS:
                    parameters = context.unmarshallList(
                            child,
                            Cast.to(ExpressionFunctionParameter.class)
                    );
                    break;
                case BODY:
                    body = context.unmarshallWithType(child);
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
            }
        }

        if (null == body) {
            JsonNodeUnmarshallContext.missingProperty(BODY_JSON_PROPERTY, node);
        }

        return Expression.lambdaFunction(
                parameters,
                body
        );
    }

    @Override
    JsonNode marshallNonNull(final LambdaFunctionExpression lambda,
                             final JsonNodeMarshallContext context) {
        return JsonNode.object()
                .setChildren(
                        Lists.of(
                                context.marshallCollection(lambda.parameters())
                                        .setName(PARAMETERS_JSON_PROPERTY),
                                context.marshallWithType(lambda.value())
                                        .setName(BODY_JSON_PROPERTY)
                        )
                );
    }

    private final static String PARAMETERS = "parameters";
    private final static String BODY = "body";

    final static JsonPropertyName PARAMETERS_JSON_PROPERTY = JsonPropertyName.with(PARAMETERS);
    final static JsonPropertyName BODY_JSON_PROPERTY = JsonPropertyName.with(BODY);
}
