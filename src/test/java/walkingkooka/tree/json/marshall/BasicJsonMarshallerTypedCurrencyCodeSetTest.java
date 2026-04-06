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

import walkingkooka.currency.CurrencyCodeSet;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedCurrencyCodeSetTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedCurrencyCodeSet, CurrencyCodeSet> {

    @Override
    BasicJsonMarshallerTypedCurrencyCodeSet marshaller() {
        return BasicJsonMarshallerTypedCurrencyCodeSet.instance();
    }

    @Override
    CurrencyCodeSet value() {
        return CurrencyCodeSet.parse("AUD,NZD");
    }

    @Override
    JsonNode node() {
        return JsonNode.string("AUD,NZD");
    }

    @Override
    CurrencyCodeSet jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "currency-code-set";
    }

    @Override
    Class<CurrencyCodeSet> marshallerType() {
        return CurrencyCodeSet.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedCurrencyCodeSet> type() {
        return BasicJsonMarshallerTypedCurrencyCodeSet.class;
    }
}
