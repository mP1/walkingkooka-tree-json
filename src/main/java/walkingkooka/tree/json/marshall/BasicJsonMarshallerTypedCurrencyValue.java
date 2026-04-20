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

import walkingkooka.currency.CurrencyCode;
import walkingkooka.currency.CurrencyValue;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;

final class BasicJsonMarshallerTypedCurrencyValue extends BasicJsonMarshallerTyped<CurrencyValue> {

    static BasicJsonMarshallerTypedCurrencyValue instance() {
        return new BasicJsonMarshallerTypedCurrencyValue();
    }

    private BasicJsonMarshallerTypedCurrencyValue() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<CurrencyValue> type() {
        return CurrencyValue.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(CurrencyValue.class);
    }

    @Override
    CurrencyValue unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    CurrencyValue unmarshallNonNull(final JsonNode node,
                                    final JsonNodeUnmarshallContext context) {
        Number value = null;
        CurrencyCode currencyCode = null;

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case VALUE_PROPERTY_STRING:
                    value = context.unmarshallWithType(child);
                    break;
                case CURRENCY_CODE_PROPERTY_STRING:
                    currencyCode = context.unmarshall(
                        child,
                        CurrencyCode.class
                    );
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
                    break;
            }
        }

        if (null == value) {
            JsonNodeUnmarshallContext.missingProperty(
                VALUE_PROPERTY,
                node
            );
        }
        if (null == currencyCode) {
            JsonNodeUnmarshallContext.missingProperty(
                CURRENCY_CODE_PROPERTY,
                node
            );
        }

        return CurrencyValue.with(
            value,
            currencyCode
        );
    }

    @Override
    JsonNode marshallNonNull(final CurrencyValue value,
                             final JsonNodeMarshallContext context) {
        return JsonNode.object()
            .set(
                VALUE_PROPERTY,
                context.marshallWithType(
                    value.value()
                )
            ).set(
                CURRENCY_CODE_PROPERTY,
                context.marshall(value.currencyCode())
            );
    }

    private final static String VALUE_PROPERTY_STRING = "value";

    private final static String CURRENCY_CODE_PROPERTY_STRING = "currencyCode";

    final static JsonPropertyName VALUE_PROPERTY = JsonPropertyName.with(VALUE_PROPERTY_STRING);

    final static JsonPropertyName CURRENCY_CODE_PROPERTY = JsonPropertyName.with(CURRENCY_CODE_PROPERTY_STRING);
}
