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
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.JsonNode;

import java.math.BigDecimal;

final class BasicJsonMarshallerTypedExpressionNumber extends BasicJsonMarshallerTyped<ExpressionNumber> {

    static BasicJsonMarshallerTypedExpressionNumber instance() {
        return new BasicJsonMarshallerTypedExpressionNumber();
    }

    private BasicJsonMarshallerTypedExpressionNumber() {
        super();
    }

    @Override
    void register() {
        //noinspection unchecked
        JsonNodeContext.register(this.typeName(),
                this::unmarshall,
                this::marshall,
                ExpressionNumber.class, ExpressionNumberKind.BIG_DECIMAL.create(0).getClass(), ExpressionNumberKind.DOUBLE.create(0).getClass());
    }

    @Override
    Class<ExpressionNumber> type() {
        return ExpressionNumber.class;
    }

    @Override
    String typeName() {
        return "expression-number";
    }

    @Override
    ExpressionNumber unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    ExpressionNumber unmarshallNonNull(final JsonNode node,
                                       final JsonNodeUnmarshallContext context) {
        final String string = node.stringValueOrFail();
        final ExpressionNumberKind kind = context.expressionNumberKind();
        final ExpressionNumber number;

        switch (kind) {
            case BIG_DECIMAL:
                number = ExpressionNumberKind.BIG_DECIMAL.create(new BigDecimal(string));
                break;
            case DOUBLE:
                number = ExpressionNumberKind.DOUBLE.create(Double.parseDouble(string));
                break;
            default:
                throw new JsonNodeMarshallException("Unknown ExpressionNumber kind " + kind + " " + CharSequences.quoteAndEscape(string));
        }

        return number.setKind(context.expressionNumberKind());
    }

    @Override
    JsonNode marshallNonNull(final ExpressionNumber value,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(value.toString());
    }
}
