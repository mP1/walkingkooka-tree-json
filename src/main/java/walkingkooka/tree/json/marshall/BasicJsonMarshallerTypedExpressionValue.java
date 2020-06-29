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
import walkingkooka.Value;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.json.JsonNode;

import java.util.function.Function;

/**
 * A {@link BasicJsonMarshaller} that handles {@link Expression} and all sub classes.
 *
 * Removed & Value<V></V> due to transpiler getting confused thinking this class extends Expression.
 * <pre>
 * J2clTranspiler
 *           7 Error(s)
 *             Error:BasicJsonMarshallerTypedExpressionValue.java:29: This class must implement the inherited abstract method Expression.toString0(StringBuilder), but cannot override it since it is not visible from BasicJsonMarshallerTypedExpressionValue. Either make the type abstract or make the inherited method visible
 *             Error:BasicJsonMarshallerTypedExpressionValue.java:29: This class must implement the inherited abstract method Expression.setChild(Expression), but cannot override it since it is not visible from BasicJsonMarshallerTypedExpressionValue. Either make the type abstract or make the inherited method visible
 *             Error:BasicJsonMarshallerTypedExpressionValue.java:29: This class must implement the inherited abstract method Expression.replace(int), but cannot override it since it is not visible from BasicJsonMarshallerTypedExpressionValue. Either make the type abstract or make the inherited method visible
 *             Error:BasicJsonMarshallerTypedExpressionValue.java:29: This class must implement the inherited abstract method Expression.equalsIgnoringParentAndChildren(Expression), but cannot override it since it is not visible from BasicJsonMarshallerTypedExpressionValue. Either make the type abstract or make the inherited method visible
 *             Error:BasicJsonMarshallerTypedExpressionValue.java:29: This class must implement the inherited abstract method Expression.equalsDescendants0(Expression), but cannot override it since it is not visible from BasicJsonMarshallerTypedExpressionValue. Either make the type abstract or make the inherited method visible
 * </pre>
 */
final class BasicJsonMarshallerTypedExpressionValue<N extends Expression /*& Value<V>*/, V> extends BasicJsonMarshallerTypedExpression<N> {

    static <N extends Expression & Value<V>, V> BasicJsonMarshallerTypedExpressionValue<N, V> with(final Function<V, N> from,
                                                                                                   final Class<N> expressionType,
                                                                                                   final Class<V> valueType) {

        return new BasicJsonMarshallerTypedExpressionValue<>(from,
                expressionType,
                valueType);
    }

    private BasicJsonMarshallerTypedExpressionValue(final Function<V, N> from,
                                                    final Class<N> type,
                                                    final Class<V> valueType) {
        super(type);
        this.from = from;
        this.valueType = valueType;
    }

    @Override
    N unmarshallNonNull(final JsonNode node,
                        final JsonNodeUnmarshallContext context) {
        return this.from.apply(context.unmarshall(node, this.valueType));
    }

    private final Function<V, N> from;
    private final Class<V> valueType;

    @Override
    JsonNode marshallNonNull(final N value,
                             final JsonNodeMarshallContext context) {
        final Value<V> valueValue = Cast.to(value);
        return context.marshall(valueValue.value());
    }
}
