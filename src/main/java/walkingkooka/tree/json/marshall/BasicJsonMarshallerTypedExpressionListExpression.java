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
import walkingkooka.tree.expression.ListExpression;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;

import java.util.List;

/**
 * A {@link BasicJsonMarshaller} that handles {@link ListExpression}
 */
final class BasicJsonMarshallerTypedExpressionListExpression extends BasicJsonMarshallerTypedExpression<ListExpression> {

    static BasicJsonMarshallerTypedExpressionListExpression instance() {
        return new BasicJsonMarshallerTypedExpressionListExpression();
    }

    private BasicJsonMarshallerTypedExpressionListExpression() {
        super(ListExpression.class);
    }

    @Override
    ListExpression unmarshallNonNull(final JsonNode node,
                                     final JsonNodeUnmarshallContext context) {
        List<Expression> elements = ListExpression.NO_CHILDREN;

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case ELEMENTS_PROPERTY_STRING:
                    elements = context.unmarshallListWithType(child);
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
                    break;
            }
        }

        return Expression.list(elements);
    }

    @Override
    JsonNode marshallNonNull(final ListExpression list,
                             final JsonNodeMarshallContext context) {
        return JsonNode.object()
            .set(
                ELEMENTS_PROPERTY,
                context.marshallCollectionWithType(
                    list.value()
                )
            );
    }

    private final static String ELEMENTS_PROPERTY_STRING = "elements";
    final static JsonPropertyName ELEMENTS_PROPERTY = JsonPropertyName.with(ELEMENTS_PROPERTY_STRING);
}
