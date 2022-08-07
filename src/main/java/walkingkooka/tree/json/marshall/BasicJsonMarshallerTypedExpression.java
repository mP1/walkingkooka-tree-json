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

import walkingkooka.text.CharSequences;
import walkingkooka.tree.expression.CallExpression;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.LambdaFunctionExpression;
import walkingkooka.tree.expression.NamedFunctionExpression;
import walkingkooka.tree.expression.ReferenceExpression;
import walkingkooka.tree.expression.ValueExpression;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A {@link BasicJsonMarshaller} that handles {@link Expression} and all sub classes.
 */
abstract class BasicJsonMarshallerTypedExpression<N extends Expression> extends BasicJsonMarshallerTyped<N> {

    /**
     * {@see BasicJsonMarshallerTypedExpressionUnary}
     */
    static <N extends Expression> BasicJsonMarshallerTypedExpression<N> unary(final Function<Expression, N> from,
                                                                              final Class<N> type) {
        return BasicJsonMarshallerTypedExpressionUnary.with(from, type);
    }

    /**
     * {@see BasicJsonMarshallerTypedExpressionBinary}
     */
    static <N extends Expression> BasicJsonMarshallerTypedExpression<N> binary(final BiFunction<Expression, Expression, N> from,
                                                                               final Class<N> type) {

        return BasicJsonMarshallerTypedExpressionBinary.with(from, type);
    }

    /**
     * {@see BasicJsonMarshallerTypedExpressionCall}
     */
    static BasicJsonMarshallerTypedExpression<CallExpression> call() {
        return BasicJsonMarshallerTypedExpressionCall.instance();
    }
    
    /**
     * {@see BasicJsonMarshallerTypedExpressionLambdaFunction}
     */
    static BasicJsonMarshallerTypedExpression<LambdaFunctionExpression> lambdaFunction() {
        return BasicJsonMarshallerTypedExpressionLambdaFunction.instance();
    }
    
    /**
     * {@see BasicJsonMarshallerTypedExpressionNamedFunction}
     */
    static BasicJsonMarshallerTypedExpression<NamedFunctionExpression> namedFunction() {
        return BasicJsonMarshallerTypedExpressionNamedFunction.instance();
    }

    /**
     * {@see BasicJsonMarshallerTypedExpressionValue}
     */
    static BasicJsonMarshallerTypedExpression<ValueExpression<?>> value() {
        return BasicJsonMarshallerTypedExpressionValue.instance();
    }

    /**
     * {@see BasicJsonMarshallerTypedExpressionReference}
     */
    static BasicJsonMarshallerTypedExpression<ReferenceExpression> reference() {
        return BasicJsonMarshallerTypedExpressionReference.instance();
    }

    /**
     * Package private to limit sub classing.
     */
    BasicJsonMarshallerTypedExpression(final Class<N> type) {
        super();

        // remove trailing "expression-" and prepend "-expression"
        final String expression = Expression.class.getSimpleName();
        this.name =
                CharSequences.subSequence(
                        JsonNodeContext.computeTypeName(type),
                        0,
                        -expression.length()
                ) +
                        expression.toLowerCase();
        this.type = type;
    }

    @Override
    final void register() {
        this.registerTypeNameAndType();
    }

    @Override
    final Class<N> type() {
        return this.type;
    }

    private final Class<N> type;

    @Override
    final String typeName() {
        return name;
    }

    private final String name;

    @Override
    final N unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }
}
