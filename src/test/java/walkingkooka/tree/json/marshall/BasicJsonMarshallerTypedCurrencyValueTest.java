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
import walkingkooka.currency.CurrencyCode;
import walkingkooka.currency.CurrencyValue;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedCurrencyValueTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedCurrencyValue, CurrencyValue> {

    @Test
    public void testMarshall2() {
        this.marshallAndCheck(
            CurrencyValue.with(
                123.5,
                CurrencyCode.parse("NZD")
            ),
            JsonNode.parse(
                "{\n" +
                    "  \"value\": 123.5,\n" +
                    "  \"currencyCode\": \"NZD\"\n" +
                    "}"
            )
        );
    }

    @Test
    public void testUnmarshall2() {
        this.unmarshallAndCheck(
            JsonNode.parse(
                "{\n" +
                    "  \"value\": 123.5,\n" +
                    "  \"currencyCode\": \"NZD\"\n" +
                    "}"
            ),
            CurrencyValue.with(
                123.5,
                CurrencyCode.parse("NZD")
            )
        );
    }

    @Override
    BasicJsonMarshallerTypedCurrencyValue marshaller() {
        return BasicJsonMarshallerTypedCurrencyValue.instance();
    }

    @Override
    CurrencyValue value() {
        return CurrencyValue.with(
            EXPRESSION_NUMBER_KIND.create(12.5),
            CurrencyCode.parse("AUD")
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.parse(
            "{\n" +
                "  \"value\": {\n" +
                "    \"type\": \"expression-number\",\n" +
                "    \"value\": \"12.5\"\n" +
                "  },\n" +
                "  \"currencyCode\": \"AUD\"\n" +
                "}"
        );
    }

    @Override
    CurrencyValue jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "currency-value";
    }

    @Override
    Class<CurrencyValue> marshallerType() {
        return CurrencyValue.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedCurrencyValue> type() {
        return BasicJsonMarshallerTypedCurrencyValue.class;
    }
}
