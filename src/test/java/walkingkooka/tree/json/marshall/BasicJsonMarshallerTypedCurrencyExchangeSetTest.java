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

import walkingkooka.currency.CurrencyExchangeSet;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedCurrencyExchangeSetTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedCurrencyExchangeSet, CurrencyExchangeSet> {

    @Override
    BasicJsonMarshallerTypedCurrencyExchangeSet marshaller() {
        return BasicJsonMarshallerTypedCurrencyExchangeSet.instance();
    }

    @Override
    CurrencyExchangeSet value() {
        return CurrencyExchangeSet.parse("AUD-NZD,NZD-CAD");
    }

    @Override
    JsonNode node() {
        return JsonNode.string("AUD-NZD,NZD-CAD");
    }

    @Override
    CurrencyExchangeSet jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "currency-exchange-set";
    }

    @Override
    Class<CurrencyExchangeSet> marshallerType() {
        return CurrencyExchangeSet.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedCurrencyExchangeSet> type() {
        return BasicJsonMarshallerTypedCurrencyExchangeSet.class;
    }
}
