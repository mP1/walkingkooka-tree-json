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
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedCurrencyCodeTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedCurrencyCode, CurrencyCode> {

    @Test
    public void testUnmarshall2() {
        final CurrencyCode currencyCode = CurrencyCode.parse("NZD");

        this.unmarshallAndCheck(
            BasicJsonMarshallerTypedCurrencyCode.instance(),
            JsonNode.string(currencyCode.value()),
            JsonNodeUnmarshallContexts.fake(),
            currencyCode
        );
    }

    @Override
    BasicJsonMarshallerTypedCurrencyCode marshaller() {
        return BasicJsonMarshallerTypedCurrencyCode.instance();
    }

    @Override
    CurrencyCode value() {
        return CurrencyCode.parse("AUD");
    }

    @Override
    JsonNode node() {
        return JsonNode.string(
            this.value()
                .toString()
        );
    }

    @Override
    CurrencyCode jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "currency-code";
    }

    @Override
    Class<CurrencyCode> marshallerType() {
        return CurrencyCode.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedCurrencyCode> type() {
        return BasicJsonMarshallerTypedCurrencyCode.class;
    }
}
