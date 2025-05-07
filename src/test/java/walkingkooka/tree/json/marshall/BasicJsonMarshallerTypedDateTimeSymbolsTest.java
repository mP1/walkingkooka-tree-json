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

import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.tree.json.JsonNode;

import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class BasicJsonMarshallerTypedDateTimeSymbolsTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedDateTimeSymbols, DateTimeSymbols> {

    @Override
    BasicJsonMarshallerTypedDateTimeSymbols marshaller() {
        return BasicJsonMarshallerTypedDateTimeSymbols.instance();
    }

    @Override
    DateTimeSymbols value() {
        return DateTimeSymbols.fromDateFormatSymbols(
            new DateFormatSymbols(
                Locale.forLanguageTag("EN-AU")
            )
        );
    }

    @Override
    JsonNode node() {
        final DateTimeSymbols symbols = this.value();

        return JsonNode.object()
            .set(BasicJsonMarshallerTypedDateTimeSymbols.AMPMS_PROPERTY, jsonArray(symbols.ampms()))
            .set(BasicJsonMarshallerTypedDateTimeSymbols.MONTH_NAMES_PROPERTY, jsonArray(symbols.monthNames()))
            .set(BasicJsonMarshallerTypedDateTimeSymbols.MONTH_NAME_ABBREVIATIONS_PROPERTY, jsonArray(symbols.monthNameAbbreviations()))
            .set(BasicJsonMarshallerTypedDateTimeSymbols.WEEKDAY_NAMES_PROPERTY, jsonArray(symbols.weekDayNames()))
            .set(BasicJsonMarshallerTypedDateTimeSymbols.WEEKDAY_NAME_ABBREVIATIONS_PROPERTY, jsonArray(symbols.weekDayNameAbbreviations()));
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
    DateTimeSymbols jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "date-time-symbols";
    }

    @Override
    Class<DateTimeSymbols> marshallerType() {
        return DateTimeSymbols.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedDateTimeSymbols> type() {
        return BasicJsonMarshallerTypedDateTimeSymbols.class;
    }
}
