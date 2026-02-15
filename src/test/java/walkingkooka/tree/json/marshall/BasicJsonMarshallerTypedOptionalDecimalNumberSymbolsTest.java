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
import walkingkooka.math.DecimalNumberSymbols;
import walkingkooka.math.OptionalDecimalNumberSymbols;
import walkingkooka.tree.json.JsonNode;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;

public final class BasicJsonMarshallerTypedOptionalDecimalNumberSymbolsTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedOptionalDecimalNumberSymbols, OptionalDecimalNumberSymbols> {

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
    public void testMarshallEmptyOptionalDecimalNumberSymbols() {
        this.marshallAndCheck(
            OptionalDecimalNumberSymbols.EMPTY,
            JsonNode.nullNode()
        );
    }

    @Test
    public void testMarshall2() {
        this.marshallAndCheck(
            this.value(),
            JsonNode.parse(
            "{\n" +
                "  \"negativeSign\": \"-\",\n" +
                "  \"positiveSign\": \"+\",\n" +
                "  \"zeroDigit\": \"0\",\n" +
                "  \"currencySymbol\": \"¤\",\n" +
                "  \"decimalSeparator\": \".\",\n" +
                "  \"exponentSymbol\": \"E\",\n" +
                "  \"groupSeparator\": \",\",\n" +
                "  \"infinitySymbol\": \"∞\",\n" +
                "  \"monetaryDecimalSeparator\": \".\",\n" +
                "  \"nanSymbol\": \"NaN\",\n" +
                "  \"percentSymbol\": \"%\",\n" +
                "  \"permillSymbol\": \"‰\"\n" +
                "}"
            )
        );
    }

    @Override
    BasicJsonMarshallerTypedOptionalDecimalNumberSymbols marshaller() {
        return BasicJsonMarshallerTypedOptionalDecimalNumberSymbols.instance();
    }

    @Override
    OptionalDecimalNumberSymbols value() {
        return DECIMAL_NUMBER_SYMBOLS;
    }

    @Override
    JsonNode node() {
        return JsonNode.parse(
            "{\n" +
                "  \"negativeSign\": \"-\",\n" +
                "  \"positiveSign\": \"+\",\n" +
                "  \"zeroDigit\": \"0\",\n" +
                "  \"currencySymbol\": \"¤\",\n" +
                "  \"decimalSeparator\": \".\",\n" +
                "  \"exponentSymbol\": \"E\",\n" +
                "  \"groupSeparator\": \",\",\n" +
                "  \"infinitySymbol\": \"∞\",\n" +
                "  \"monetaryDecimalSeparator\": \".\",\n" +
                "  \"nanSymbol\": \"NaN\",\n" +
                "  \"percentSymbol\": \"%\",\n" +
                "  \"permillSymbol\": \"‰\"\n" +
                "}"
        );
    }

    private final static OptionalDecimalNumberSymbols DECIMAL_NUMBER_SYMBOLS = OptionalDecimalNumberSymbols.with(
        Optional.of(
            DecimalNumberSymbols.fromDecimalFormatSymbols(
                '+',
                new DecimalFormatSymbols(Locale.ENGLISH)
            )
        )
    );

    @SuppressWarnings("OptionalDecimalNumberSymbolsAssignedToNull")
    @Override
    OptionalDecimalNumberSymbols jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "optional-decimal-number-symbols";
    }

    @Override
    Class<OptionalDecimalNumberSymbols> marshallerType() {
        return Cast.to(OptionalDecimalNumberSymbols.class);
    }

    @Override
    public Class<BasicJsonMarshallerTypedOptionalDecimalNumberSymbols> type() {
        return BasicJsonMarshallerTypedOptionalDecimalNumberSymbols.class;
    }
}
