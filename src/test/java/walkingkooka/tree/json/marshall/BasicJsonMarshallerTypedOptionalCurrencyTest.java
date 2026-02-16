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
import walkingkooka.Cast;
import walkingkooka.currency.OptionalCurrency;
import walkingkooka.tree.json.JsonNode;

import java.util.Currency;
import java.util.Optional;

public final class BasicJsonMarshallerTypedOptionalCurrencyTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedOptionalCurrency, OptionalCurrency> {

    @Test
    public void testUnmarshallBooleanFails() {
        this.unmarshallFailed(
            JsonNode.booleanNode(true),
            ClassCastException.class
        );
    }

    @Test
    public void testUnmarshallNumberFails() {
        this.unmarshallFailed(
            JsonNode.number(123),
            ClassCastException.class
        );
    }

    @Test
    public void testMarshallEmptyOptionalCurrency() {
        this.marshallAndCheck(
            OptionalCurrency.EMPTY,
            JsonNode.nullNode()
        );
    }

    @Test
    public void testMarshall2() {
        this.marshallAndCheck(
            this.value(),
            JsonNode.string("AUD")
        );
    }

    @Override
    BasicJsonMarshallerTypedOptionalCurrency marshaller() {
        return BasicJsonMarshallerTypedOptionalCurrency.instance();
    }

    @Override
    OptionalCurrency value() {
        return CURRENCY;
    }

    @Override
    JsonNode node() {
        return JsonNode.string("AUD");
    }

    private final static OptionalCurrency CURRENCY = OptionalCurrency.with(
        Optional.of(
            Currency.getInstance("AUD")
        )
    );

    @SuppressWarnings("OptionalCurrencyAssignedToNull")
    @Override
    OptionalCurrency jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "optional-currency";
    }

    @Override
    Class<OptionalCurrency> marshallerType() {
        return Cast.to(OptionalCurrency.class);
    }

    @Override
    public Class<BasicJsonMarshallerTypedOptionalCurrency> type() {
        return BasicJsonMarshallerTypedOptionalCurrency.class;
    }
}
