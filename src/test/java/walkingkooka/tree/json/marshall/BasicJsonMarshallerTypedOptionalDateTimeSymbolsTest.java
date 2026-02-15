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
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.datetime.OptionalDateTimeSymbols;
import walkingkooka.tree.json.JsonNode;

import java.text.DateFormatSymbols;
import java.util.Locale;
import java.util.Optional;

public final class BasicJsonMarshallerTypedOptionalDateTimeSymbolsTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedOptionalDateTimeSymbols, OptionalDateTimeSymbols> {

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
    public void testMarshallEmptyOptionalDateTimeSymbols() {
        this.marshallAndCheck(
            OptionalDateTimeSymbols.EMPTY,
            JsonNode.nullNode()
        );
    }

    @Test
    public void testMarshall2() {
        this.marshallAndCheck(
            this.value(),
            JsonNode.parse(
                "{\n" +
                    "  \"ampms\": [\n" +
                    "    \"AM\",\n" +
                    "    \"PM\"\n" +
                    "  ],\n" +
                    "  \"monthNames\": [\n" +
                    "    \"January\",\n" +
                    "    \"February\",\n" +
                    "    \"March\",\n" +
                    "    \"April\",\n" +
                    "    \"May\",\n" +
                    "    \"June\",\n" +
                    "    \"July\",\n" +
                    "    \"August\",\n" +
                    "    \"September\",\n" +
                    "    \"October\",\n" +
                    "    \"November\",\n" +
                    "    \"December\"\n" +
                    "  ],\n" +
                    "  \"monthNameAbbreviations\": [\n" +
                    "    \"Jan\",\n" +
                    "    \"Feb\",\n" +
                    "    \"Mar\",\n" +
                    "    \"Apr\",\n" +
                    "    \"May\",\n" +
                    "    \"Jun\",\n" +
                    "    \"Jul\",\n" +
                    "    \"Aug\",\n" +
                    "    \"Sep\",\n" +
                    "    \"Oct\",\n" +
                    "    \"Nov\",\n" +
                    "    \"Dec\"\n" +
                    "  ],\n" +
                    "  \"weekDayNames\": [\n" +
                    "    \"Sunday\",\n" +
                    "    \"Monday\",\n" +
                    "    \"Tuesday\",\n" +
                    "    \"Wednesday\",\n" +
                    "    \"Thursday\",\n" +
                    "    \"Friday\",\n" +
                    "    \"Saturday\"\n" +
                    "  ],\n" +
                    "  \"weekDayNameAbbreviations\": [\n" +
                    "    \"Sun\",\n" +
                    "    \"Mon\",\n" +
                    "    \"Tue\",\n" +
                    "    \"Wed\",\n" +
                    "    \"Thu\",\n" +
                    "    \"Fri\",\n" +
                    "    \"Sat\"\n" +
                    "  ]\n" +
                    "}"
            )
        );
    }

    @Override
    BasicJsonMarshallerTypedOptionalDateTimeSymbols marshaller() {
        return BasicJsonMarshallerTypedOptionalDateTimeSymbols.instance();
    }

    @Override
    OptionalDateTimeSymbols value() {
        return DATE_TIME_SYMBOLS;
    }

    @Override
    JsonNode node() {
        return JsonNode.parse(
            "{\n" +
                "  \"ampms\": [\n" +
                "    \"AM\",\n" +
                "    \"PM\"\n" +
                "  ],\n" +
                "  \"monthNames\": [\n" +
                "    \"January\",\n" +
                "    \"February\",\n" +
                "    \"March\",\n" +
                "    \"April\",\n" +
                "    \"May\",\n" +
                "    \"June\",\n" +
                "    \"July\",\n" +
                "    \"August\",\n" +
                "    \"September\",\n" +
                "    \"October\",\n" +
                "    \"November\",\n" +
                "    \"December\"\n" +
                "  ],\n" +
                "  \"monthNameAbbreviations\": [\n" +
                "    \"Jan\",\n" +
                "    \"Feb\",\n" +
                "    \"Mar\",\n" +
                "    \"Apr\",\n" +
                "    \"May\",\n" +
                "    \"Jun\",\n" +
                "    \"Jul\",\n" +
                "    \"Aug\",\n" +
                "    \"Sep\",\n" +
                "    \"Oct\",\n" +
                "    \"Nov\",\n" +
                "    \"Dec\"\n" +
                "  ],\n" +
                "  \"weekDayNames\": [\n" +
                "    \"Sunday\",\n" +
                "    \"Monday\",\n" +
                "    \"Tuesday\",\n" +
                "    \"Wednesday\",\n" +
                "    \"Thursday\",\n" +
                "    \"Friday\",\n" +
                "    \"Saturday\"\n" +
                "  ],\n" +
                "  \"weekDayNameAbbreviations\": [\n" +
                "    \"Sun\",\n" +
                "    \"Mon\",\n" +
                "    \"Tue\",\n" +
                "    \"Wed\",\n" +
                "    \"Thu\",\n" +
                "    \"Fri\",\n" +
                "    \"Sat\"\n" +
                "  ]\n" +
                "}"
        );
    }

    private final static OptionalDateTimeSymbols DATE_TIME_SYMBOLS = OptionalDateTimeSymbols.with(
        Optional.of(
            DateTimeSymbols.fromDateFormatSymbols(
                new DateFormatSymbols(Locale.ENGLISH)
            )
        )
    );

    @SuppressWarnings("OptionalDateTimeSymbolsAssignedToNull")
    @Override
    OptionalDateTimeSymbols jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "optional-date-time-symbols";
    }

    @Override
    Class<OptionalDateTimeSymbols> marshallerType() {
        return Cast.to(OptionalDateTimeSymbols.class);
    }

    @Override
    public Class<BasicJsonMarshallerTypedOptionalDateTimeSymbols> type() {
        return BasicJsonMarshallerTypedOptionalDateTimeSymbols.class;
    }
}
