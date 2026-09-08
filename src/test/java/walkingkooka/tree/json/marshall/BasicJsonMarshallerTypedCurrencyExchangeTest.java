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
import walkingkooka.currency.CurrencyExchange;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedCurrencyExchangeTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedCurrencyExchange, CurrencyExchange> {

    private final static String CURRENCY_EXCHANGE = "AUD-NZD";

    @Test
    public void testUnmarshall2() {
        this.unmarshallAndCheck(
            BasicJsonMarshallerTypedCurrencyExchange.instance(),
            JsonNode.string(CURRENCY_EXCHANGE),
            JsonNodeUnmarshallContexts.fake(),
            this.value()
        );
    }

    @Override
    BasicJsonMarshallerTypedCurrencyExchange marshaller() {
        return BasicJsonMarshallerTypedCurrencyExchange.instance();
    }

    @Override
    CurrencyExchange value() {
        return CurrencyExchange.parse(CURRENCY_EXCHANGE);
    }

    @Override
    JsonNode node() {
        return JsonNode.string(CURRENCY_EXCHANGE);
    }

    @Override
    CurrencyExchange jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "currency-exchange";
    }

    @Override
    Class<CurrencyExchange> marshallerType() {
        return CurrencyExchange.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedCurrencyExchange> type() {
        return BasicJsonMarshallerTypedCurrencyExchange.class;
    }
}
