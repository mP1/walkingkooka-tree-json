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

import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class BasicJsonMarshallerTypedDecimalNumberSymbolsTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedDecimalNumberSymbols, DecimalNumberSymbols> {

    @Override
    BasicJsonMarshallerTypedDecimalNumberSymbols marshaller() {
        return BasicJsonMarshallerTypedDecimalNumberSymbols.instance();
    }

    @Override
    DecimalNumberSymbols value() {
        return DecimalNumberSymbols.fromDecimalFormatSymbols(
            '+',
            new DecimalFormatSymbols(
                Locale.forLanguageTag("EN-AU")
            )
        );
    }

    @Override
    JsonNode node() {
        final DecimalNumberSymbols symbols = this.value();

        return JsonNode.object()
            .set(BasicJsonMarshallerTypedDecimalNumberSymbols.NEGATIVE_SIGN_PROPERTY, JsonNode.string("-"))
            .set(BasicJsonMarshallerTypedDecimalNumberSymbols.POSITIVE_SIGN_PROPERTY, JsonNode.string("+"))
            .set(BasicJsonMarshallerTypedDecimalNumberSymbols.ZERO_DIGIT_PROPERTY, JsonNode.string("0"))
            .set(BasicJsonMarshallerTypedDecimalNumberSymbols.CURRENCY_SYMBOL_PROPERTY, JsonNode.string("$"))
            .set(BasicJsonMarshallerTypedDecimalNumberSymbols.DECIMAL_SEPARATOR_PROPERTY, JsonNode.string("."))
            .set(BasicJsonMarshallerTypedDecimalNumberSymbols.EXPONENT_SYMBOL_PROPERTY, JsonNode.string("e"))
            .set(BasicJsonMarshallerTypedDecimalNumberSymbols.GROUP_SEPARATOR_PROPERTY, JsonNode.string(","))
            .set(BasicJsonMarshallerTypedDecimalNumberSymbols.PERCENTAGE_SYMBOL_PROPERTY, JsonNode.string("%"));
    }

    private static JsonNode jsonArray(final List<String> strings) {
        return JsonNode.array()
            .setChildren(
                strings.stream()
                    .map(JsonNode::string)
                    .collect(Collectors.toList())
            );
    }

    @Override
    DecimalNumberSymbols jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "decimal-number-symbols";
    }

    @Override
    Class<DecimalNumberSymbols> marshallerType() {
        return DecimalNumberSymbols.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedDecimalNumberSymbols> type() {
        return BasicJsonMarshallerTypedDecimalNumberSymbols.class;
    }
}
