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
import walkingkooka.collect.list.CsvStringList;
import walkingkooka.currency.CurrencyCode;
import walkingkooka.currency.CurrencyContextTesting;
import walkingkooka.currency.CurrencyList;
import walkingkooka.tree.json.JsonNode;

import java.util.Currency;
import java.util.stream.Collectors;

public final class BasicJsonMarshallerTypedCurrencyListTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedCurrencyList, CurrencyList>
    implements CurrencyContextTesting {

    @Test
    public void testUnmarshallAllCurrencies() {
        final CurrencyList currencyList = CurrencyList.EMPTY.setElements(
            CURRENCY_CONTEXT.availableCurrencies()
                .stream()
                .map((CurrencyCode currencyCode) -> Currency.getInstance(currencyCode.value()))
                .collect(Collectors.toList())
        );

        this.unmarshallAndCheck(
            BasicJsonMarshallerTypedCurrencyList.instance(),
            JsonNode.string(
                currencyList.csvStringList()
                    .text()
            ),
            JSON_NODE_UNMARSHALL_CONTEXT,
            currencyList
        );
    }

    @Override
    BasicJsonMarshallerTypedCurrencyList marshaller() {
        return BasicJsonMarshallerTypedCurrencyList.instance();
    }

    @Override
    CurrencyList value() {
        return CurrencyList.fromStringLikeList(
            CsvStringList.parse("AUD,NZD")
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.string("AUD,NZD");
    }

    @Override
    CurrencyList jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "currency-list";
    }

    @Override
    Class<CurrencyList> marshallerType() {
        return CurrencyList.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedCurrencyList> type() {
        return BasicJsonMarshallerTypedCurrencyList.class;
    }
}
