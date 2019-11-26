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
import walkingkooka.tree.expression.FunctionExpression;
import walkingkooka.tree.expression.FunctionExpressionName;
import walkingkooka.tree.json.JsonArray;
import walkingkooka.tree.json.JsonNode;

/**
 * A {@link BasicJsonMarshaller} that handles {@link FunctionExpression}
 */
final class BasicJsonMarshallerTypedExpressionFunction extends BasicJsonMarshallerTypedExpression<FunctionExpression> {

    static BasicJsonMarshallerTypedExpressionFunction instance() {
        return new BasicJsonMarshallerTypedExpressionFunction();
    }

    private BasicJsonMarshallerTypedExpressionFunction() {
        super(FunctionExpression.class);
    }

    @Override
    FunctionExpression unmarshallNonNull(final JsonNode node,
                                         final JsonNodeUnmarshallContext context) {
        final JsonArray array = node.arrayOrFail();
        return Expression.function(
                FunctionExpressionName.with(array.get(0).stringValueOrFail()),
                context.unmarshallWithTypeList(array.get(1)));
    }

    @Override
    JsonNode marshallNonNull(final FunctionExpression value,
                             final JsonNodeMarshallContext context) {
        return context.marshallList(
                Lists.of(
                        JsonNode.string(value.name().value()),
                        context.marshallWithTypeList(value.children())));
    }
}
