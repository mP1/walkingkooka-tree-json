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

import walkingkooka.math.DecimalNumberSymbols;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;

/**
 * A {@link BasicJsonMarshaller} that handles {@link DecimalNumberSymbols}
 */
final class BasicJsonMarshallerTypedDecimalNumberSymbols extends BasicJsonMarshallerTyped<DecimalNumberSymbols> {

    static BasicJsonMarshallerTypedDecimalNumberSymbols instance() {
        return new BasicJsonMarshallerTypedDecimalNumberSymbols();
    }

    private BasicJsonMarshallerTypedDecimalNumberSymbols() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<DecimalNumberSymbols> type() {
        return DecimalNumberSymbols.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(DecimalNumberSymbols.class);
    }

    @Override
    DecimalNumberSymbols unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    DecimalNumberSymbols unmarshallNonNull(final JsonNode node,
                                           final JsonNodeUnmarshallContext context) {
        Character negativeSign = null;
        Character positiveSign = null;
        Character zeroDigit = null;
        String currencySymbol = null;
        Character decimalSeparator = null;
        String exponentSymbol = null;
        Character groupSeparator = null;
        String infinitySymbol = null;
        Character monetaryDecimalSeparator = null;
        String nanSymbol = null;
        Character percentSymbol = null;
        Character permillSymbol = null;

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case NEGATIVE_SIGN_PROPERTY_STRING:
                    negativeSign = context.unmarshall(
                        child,
                        Character.class
                    );
                    break;
                case POSITIVE_SIGN_PROPERTY_STRING:
                    positiveSign = context.unmarshall(
                        child,
                        Character.class
                    );
                    break;
                case ZERO_DIGIT_PROPERTY_STRING:
                    zeroDigit = context.unmarshall(
                        child,
                        Character.class
                    );
                    break;
                case CURRENCY_SYMBOL_PROPERTY_STRING:
                    currencySymbol = context.unmarshall(
                        child,
                        String.class
                    );
                    break;
                case DECIMAL_SEPARATOR_PROPERTY_STRING:
                    decimalSeparator = context.unmarshall(
                        child,
                        Character.class
                    );
                    break;
                case EXPONENT_SYMBOL_PROPERTY_STRING:
                    exponentSymbol = context.unmarshall(
                        child,
                        String.class
                    );
                    break;
                case GROUP_SEPARATOR_PROPERTY_STRING:
                    groupSeparator = context.unmarshall(
                        child,
                        Character.class
                    );
                    break;
                case INFINITY_SYMBOL_PROPERTY_STRING:
                    infinitySymbol = context.unmarshall(
                        child,
                        String.class
                    );
                    break;
                case MONETARY_DECIMAL_SEPARATOR_PROPERTY_STRING:
                    monetaryDecimalSeparator = context.unmarshall(
                        child,
                        Character.class
                    );
                    break;
                case NAN_SYMBOL_PROPERTY_STRING:
                    nanSymbol = context.unmarshall(
                        child,
                        String.class
                    );
                    break;
                case PERCENT_SYMBOL_PROPERTY_STRING:
                    percentSymbol = context.unmarshall(
                        child,
                        Character.class
                    );
                    break;
                case PERMILL_SYMBOL_PROPERTY_STRING:
                    permillSymbol = context.unmarshall(
                        child,
                        Character.class
                    );
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
                    break;
            }
        }

        if (null == negativeSign) {
            JsonNodeUnmarshallContext.missingProperty(
                NEGATIVE_SIGN_PROPERTY,
                node
            );
        }
        if (null == positiveSign) {
            JsonNodeUnmarshallContext.missingProperty(
                POSITIVE_SIGN_PROPERTY,
                node
            );
        }
        if (null == zeroDigit) {
            JsonNodeUnmarshallContext.missingProperty(
                ZERO_DIGIT_PROPERTY,
                node
            );
        }
        if (null == currencySymbol) {
            JsonNodeUnmarshallContext.missingProperty(
                CURRENCY_SYMBOL_PROPERTY,
                node
            );
        }
        if (null == decimalSeparator) {
            JsonNodeUnmarshallContext.missingProperty(
                DECIMAL_SEPARATOR_PROPERTY,
                node
            );
        }
        if (null == exponentSymbol) {
            JsonNodeUnmarshallContext.missingProperty(
                EXPONENT_SYMBOL_PROPERTY,
                node
            );
        }
        if (null == groupSeparator) {
            JsonNodeUnmarshallContext.missingProperty(
                GROUP_SEPARATOR_PROPERTY,
                node
            );
        }
        if (null == infinitySymbol) {
            JsonNodeUnmarshallContext.missingProperty(
                INFINITY_SYMBOL_PROPERTY,
                node
            );
        }
        if (null == monetaryDecimalSeparator) {
            JsonNodeUnmarshallContext.missingProperty(
                MONETARY_DECIMAL_SEPARATOR_PROPERTY,
                node
            );
        }
        if (null == nanSymbol) {
            JsonNodeUnmarshallContext.missingProperty(
                NAN_SYMBOL_PROPERTY,
                node
            );
        }
        if (null == percentSymbol) {
            JsonNodeUnmarshallContext.missingProperty(
                PERCENT_SYMBOL_PROPERTY,
                node
            );
        }
        if (null == permillSymbol) {
            JsonNodeUnmarshallContext.missingProperty(
                PERMILL_SYMBOL_PROPERTY,
                node
            );
        }

        return DecimalNumberSymbols.with(
            negativeSign,
            positiveSign,
            zeroDigit,
            currencySymbol,
            decimalSeparator,
            exponentSymbol,
            groupSeparator,
            infinitySymbol,
            monetaryDecimalSeparator,
            nanSymbol,
            percentSymbol,
            permillSymbol
        );
    }

    @Override
    JsonNode marshallNonNull(final DecimalNumberSymbols value,
                             final JsonNodeMarshallContext context) {
        return JsonNode.object()
            .set(NEGATIVE_SIGN_PROPERTY, context.marshall(value.negativeSign()))
            .set(POSITIVE_SIGN_PROPERTY, context.marshall(value.positiveSign()))
            .set(ZERO_DIGIT_PROPERTY, context.marshall(value.zeroDigit()))
            .set(CURRENCY_SYMBOL_PROPERTY, context.marshall(value.currencySymbol()))
            .set(DECIMAL_SEPARATOR_PROPERTY, context.marshall(value.decimalSeparator()))
            .set(EXPONENT_SYMBOL_PROPERTY, context.marshall(value.exponentSymbol()))
            .set(GROUP_SEPARATOR_PROPERTY, context.marshall(value.groupSeparator()))
            .set(INFINITY_SYMBOL_PROPERTY, context.marshall(value.infinitySymbol()))
            .set(MONETARY_DECIMAL_SEPARATOR_PROPERTY, context.marshall(value.monetaryDecimalSeparator()))
            .set(NAN_SYMBOL_PROPERTY, context.marshall(value.nanSymbol()))
            .set(PERCENT_SYMBOL_PROPERTY, context.marshall(value.percentSymbol()))
            .set(PERMILL_SYMBOL_PROPERTY, context.marshall(value.permillSymbol()));
    }

    private final static String NEGATIVE_SIGN_PROPERTY_STRING = "negativeSign";

    private final static String POSITIVE_SIGN_PROPERTY_STRING = "positiveSign";

    private final static String ZERO_DIGIT_PROPERTY_STRING = "zeroDigit";

    private final static String CURRENCY_SYMBOL_PROPERTY_STRING = "currencySymbol";

    private final static String DECIMAL_SEPARATOR_PROPERTY_STRING = "decimalSeparator";

    private final static String EXPONENT_SYMBOL_PROPERTY_STRING = "exponentSymbol";

    private final static String GROUP_SEPARATOR_PROPERTY_STRING = "groupSeparator";

    private final static String INFINITY_SYMBOL_PROPERTY_STRING = "infinitySymbol";

    private final static String MONETARY_DECIMAL_SEPARATOR_PROPERTY_STRING = "monetaryDecimalSeparator";

    private final static String NAN_SYMBOL_PROPERTY_STRING = "nanSymbol";

    private final static String PERCENT_SYMBOL_PROPERTY_STRING = "percentSymbol";

    private final static String PERMILL_SYMBOL_PROPERTY_STRING = "permillSymbol";

    final static JsonPropertyName NEGATIVE_SIGN_PROPERTY = JsonPropertyName.with(NEGATIVE_SIGN_PROPERTY_STRING);

    final static JsonPropertyName POSITIVE_SIGN_PROPERTY = JsonPropertyName.with(POSITIVE_SIGN_PROPERTY_STRING);

    final static JsonPropertyName ZERO_DIGIT_PROPERTY = JsonPropertyName.with(ZERO_DIGIT_PROPERTY_STRING);

    final static JsonPropertyName CURRENCY_SYMBOL_PROPERTY = JsonPropertyName.with(CURRENCY_SYMBOL_PROPERTY_STRING);

    final static JsonPropertyName DECIMAL_SEPARATOR_PROPERTY = JsonPropertyName.with(DECIMAL_SEPARATOR_PROPERTY_STRING);

    final static JsonPropertyName EXPONENT_SYMBOL_PROPERTY = JsonPropertyName.with(EXPONENT_SYMBOL_PROPERTY_STRING);

    final static JsonPropertyName GROUP_SEPARATOR_PROPERTY = JsonPropertyName.with(GROUP_SEPARATOR_PROPERTY_STRING);

    final static JsonPropertyName INFINITY_SYMBOL_PROPERTY = JsonPropertyName.with(INFINITY_SYMBOL_PROPERTY_STRING);

    final static JsonPropertyName MONETARY_DECIMAL_SEPARATOR_PROPERTY = JsonPropertyName.with(MONETARY_DECIMAL_SEPARATOR_PROPERTY_STRING);

    final static JsonPropertyName NAN_SYMBOL_PROPERTY = JsonPropertyName.with(NAN_SYMBOL_PROPERTY_STRING);

    final static JsonPropertyName PERCENT_SYMBOL_PROPERTY = JsonPropertyName.with(PERCENT_SYMBOL_PROPERTY_STRING);

    final static JsonPropertyName PERMILL_SYMBOL_PROPERTY = JsonPropertyName.with(PERMILL_SYMBOL_PROPERTY_STRING);
}
