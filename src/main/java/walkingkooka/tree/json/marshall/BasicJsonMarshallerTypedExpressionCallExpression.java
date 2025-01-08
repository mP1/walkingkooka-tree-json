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

import walkingkooka.tree.expression.CallExpression;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;

import java.util.List;

/**
 * A {@link BasicJsonMarshaller} that handles {@link CallExpression}
 */
final class BasicJsonMarshallerTypedExpressionCallExpression extends BasicJsonMarshallerTypedExpression<CallExpression> {

    static BasicJsonMarshallerTypedExpressionCallExpression instance() {
        return new BasicJsonMarshallerTypedExpressionCallExpression();
    }

    private BasicJsonMarshallerTypedExpressionCallExpression() {
        super(CallExpression.class);
    }

    @Override
    CallExpression unmarshallNonNull(final JsonNode node,
                                     final JsonNodeUnmarshallContext context) {
        Expression callable = null;
        List<Expression> parameters = CallExpression.NO_CHILDREN;

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case CALLABLE_PROPERTY_STRING:
                    callable = context.unmarshallWithType(child);
                    break;
                case PARAMETERS_PROPERTY_STRING:
                    parameters = context.unmarshallWithTypeList(child);
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
                    break;
            }
        }

        if (null == callable) {
            JsonNodeUnmarshallContext.missingProperty(CALLABLE_PROPERTY, node);
        }

        return Expression.call(
            callable,
            parameters
        );
    }

    @Override
    JsonNode marshallNonNull(final CallExpression call,
                             final JsonNodeMarshallContext context) {
        JsonObject json = JsonNode.object()
            .set(CALLABLE_PROPERTY, context.marshallWithType(call.callable()));

        final List<Expression> parameters = call.value();
        if (parameters.size() > 0) {
            json = json.set(PARAMETERS_PROPERTY, context.marshallWithTypeCollection(call.value()));
        }

        return json;
    }

    private final static String CALLABLE_PROPERTY_STRING = "callable";
    private final static String PARAMETERS_PROPERTY_STRING = "parameters";

    final static JsonPropertyName CALLABLE_PROPERTY = JsonPropertyName.with(CALLABLE_PROPERTY_STRING);
    final static JsonPropertyName PARAMETERS_PROPERTY = JsonPropertyName.with(PARAMETERS_PROPERTY_STRING);
}
