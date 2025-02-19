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
import walkingkooka.tree.json.JsonNode;

import java.util.List;
import java.util.function.BiFunction;

/**
 * A {@link BasicJsonMarshaller} that handles {@link Expression} that have two children.
 */
final class BasicJsonMarshallerTypedExpressionBinaryExpression<N extends Expression> extends BasicJsonMarshallerTypedExpression<N> {

    static <N extends Expression> BasicJsonMarshallerTypedExpressionBinaryExpression<N> with(final BiFunction<Expression, Expression, N> from,
                                                                                             final Class<N> expressionType) {

        return new BasicJsonMarshallerTypedExpressionBinaryExpression<>(from, expressionType);
    }

    private BasicJsonMarshallerTypedExpressionBinaryExpression(final BiFunction<Expression, Expression, N> from,
                                                               final Class<N> type) {
        super(type);
        this.from = from;
    }

    @Override
    N unmarshallNonNull(final JsonNode node,
                        final JsonNodeUnmarshallContext context) {
        final List<Expression> children = context.unmarshallListWithType(node);
        final int count = children.size();
        if (count != 2) {
            throw new JsonNodeUnmarshallException("Expected 2 children but got " + count, node);
        }

        return this.from.apply(children.get(0), children.get(1));
    }

    private final BiFunction<Expression, Expression, N> from;

    @Override
    JsonNode marshallNonNull(final N value,
                             final JsonNodeMarshallContext context) {
        return context.marshallCollectionWithType(value.children());
    }
}
