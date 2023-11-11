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
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ValueExpression;
import walkingkooka.tree.json.JsonNode;

/**
 * A {@link BasicJsonMarshaller} that handles {@link Expression} and all sub-classes.
 */
final class BasicJsonMarshallerTypedExpressionValueExpression extends BasicJsonMarshallerTypedExpression<ValueExpression<?>> {

    static BasicJsonMarshallerTypedExpressionValueExpression instance() {
        return new BasicJsonMarshallerTypedExpressionValueExpression();
    }

    private BasicJsonMarshallerTypedExpressionValueExpression() {
        super(Cast.to(ValueExpression.class));
    }

    @Override
    ValueExpression<?> unmarshallNonNull(final JsonNode node,
                                         final JsonNodeUnmarshallContext context) {
        return Expression.value(
                context.unmarshallWithType(node)
        );
    }

    @Override
    JsonNode marshallNonNull(final ValueExpression<?> value,
                             final JsonNodeMarshallContext context) {
        return context.marshallWithType(value.value());
    }
}
