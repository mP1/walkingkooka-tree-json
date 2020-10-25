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
                ExpressionNumber.class, ExpressionNumber.with(1.0).getClass(), ExpressionNumber.with(BigDecimal.ZERO).getClass());
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
        final int length = string.length();
        switch (length) {
            case 0:
                throw new JsonNodeUnmarshallException("ExpressionNumber json empty", node);
            case 1:
                throw new JsonNodeUnmarshallException("ExpressionNumber missing number/type", node);
            default:
                break;
        }

        final ExpressionNumber number;

        Exit:
        for (; ; ) {
            final char type = string.charAt(length - 1);
            final String stringWithoutType = string.substring(0, length - 1);
            switch (type) {
                case BIG_DECIMAL:
                    number = ExpressionNumber.with(new BigDecimal(stringWithoutType));
                    break Exit;
                case DOUBLE:
                    number = ExpressionNumber.with(Double.parseDouble(stringWithoutType));
                    break Exit;
                default:
                    throw new JsonNodeMarshallException("Unknown ExpressionNumber type " + CharSequences.quoteIfChars(type) + " " + CharSequences.quoteAndEscape(string));
            }
        }

        return number;
    }

    @Override
    JsonNode marshallNonNull(final ExpressionNumber value,
                             final JsonNodeMarshallContext context) {
        final char type;
        for (; ; ) {
            if (value.isBigDecimal()) {
                type = BIG_DECIMAL;
                break;
            }
            if (value.isDouble()) {
                type = DOUBLE;
                break;
            }
            throw new JsonNodeMarshallException("Unknown ExpressionNumber type " + value + " (" + value.getClass().getName() + ")");
        }

        return JsonNode.string(value.toString().concat(String.valueOf(type)));
    }

    private final static char BIG_DECIMAL = 'B';
    private final static char DOUBLE = 'D';
}
