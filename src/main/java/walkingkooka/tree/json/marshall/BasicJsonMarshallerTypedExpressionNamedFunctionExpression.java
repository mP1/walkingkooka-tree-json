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

import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.expression.NamedFunctionExpression;
import walkingkooka.tree.json.JsonNode;

/**
 * A {@link BasicJsonMarshaller} that handles {@link NamedFunctionExpression}
 */
final class BasicJsonMarshallerTypedExpressionNamedFunctionExpression extends BasicJsonMarshallerTypedExpression<NamedFunctionExpression> {

    static BasicJsonMarshallerTypedExpressionNamedFunctionExpression instance() {
        return new BasicJsonMarshallerTypedExpressionNamedFunctionExpression();
    }

    private BasicJsonMarshallerTypedExpressionNamedFunctionExpression() {
        super(NamedFunctionExpression.class);
    }

    @Override
    NamedFunctionExpression unmarshallNonNull(final JsonNode node,
                                              final JsonNodeUnmarshallContext context) {
        return Expression.namedFunction(
            ExpressionFunctionName.with(node.stringOrFail())
        );
    }

    @Override
    JsonNode marshallNonNull(final NamedFunctionExpression value,
                             final JsonNodeMarshallContext context) {
        return context.marshall(
            JsonNode.string(
                value.value()
                    .value()
            )
        );
    }
}
