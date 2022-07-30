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

import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.FunctionExpressionName;
import walkingkooka.tree.expression.NamedFunctionExpression;
import walkingkooka.tree.json.JsonArray;
import walkingkooka.tree.json.JsonNode;

/**
 * A {@link BasicJsonMarshaller} that handles {@link NamedFunctionExpression}
 */
final class BasicJsonMarshallerTypedExpressionNamedFunction extends BasicJsonMarshallerTypedExpression<NamedFunctionExpression> {

    static BasicJsonMarshallerTypedExpressionNamedFunction instance() {
        return new BasicJsonMarshallerTypedExpressionNamedFunction();
    }

    private BasicJsonMarshallerTypedExpressionNamedFunction() {
        super(NamedFunctionExpression.class);
    }

    @Override
    NamedFunctionExpression unmarshallNonNull(final JsonNode node,
                                              final JsonNodeUnmarshallContext context) {
        final JsonArray array = node.arrayOrFail();
        return Expression.namedFunction(
                FunctionExpressionName.with(array.get(0).stringOrFail()),
                context.unmarshallWithTypeList(array.get(1)));
    }

    @Override
    JsonNode marshallNonNull(final NamedFunctionExpression value,
                             final JsonNodeMarshallContext context) {
        return context.marshallCollection(
                Lists.of(
                        JsonNode.string(value.name().value()),
                        context.marshallWithTypeCollection(value.children())));
    }
}
