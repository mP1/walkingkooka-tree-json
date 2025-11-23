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

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.ListExpression;
import walkingkooka.tree.json.JsonNode;

import java.util.List;

public final class BasicJsonMarshallerTypedExpressionListExpressionTest extends BasicJsonMarshallerTypedExpressionTestCase<BasicJsonMarshallerTypedExpressionListExpression, ListExpression> {

    private final static ExpressionNumberKind KIND = ExpressionNumberKind.DEFAULT;

    @Test
    public void testUnmarshallBooleanFails() {
        this.unmarshallFailed(
            JsonNode.booleanNode(true),
            ClassCastException.class
        );
    }

    @Test
    public void testUnmarshallNumberFails() {
        this.unmarshallFailed(
            JsonNode.number(123),
            ClassCastException.class
        );
    }

    @Test
    public void testUnmarshallStringFails() {
        this.unmarshallFailed(
            JsonNode.string("abc123"),
            ClassCastException.class);
    }

    @Override
    BasicJsonMarshallerTypedExpressionListExpression marshaller() {
        return BasicJsonMarshallerTypedExpressionListExpression.instance();
    }

    private final static String FUNCTION_NAME = "function123";

    @Override
    ListExpression value() {
        return Expression.list(
            this.elements()
        );
    }

    private List<Expression> elements() {
        return Lists.of(
            Expression.value(KIND.create(11)),
            Expression.value("b2"),
            Expression.add(
                Expression.value(KIND.create(3)),
                Expression.value(KIND.create(33))
            )
        );
    }

    @Override
    JsonNode node() {
        final JsonNodeMarshallContext context = this.marshallContext();

        return JsonNode.object()
            .set(
                BasicJsonMarshallerTypedExpressionListExpression.ELEMENTS_PROPERTY,
                context.marshallCollectionWithType(this.elements())
            );
    }

    @Override
    String typeName() {
        return "list-expression";
    }

    @Override
    Class<ListExpression> marshallerType() {
        return ListExpression.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedExpressionListExpression> type() {
        return BasicJsonMarshallerTypedExpressionListExpression.class;
    }
}
