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
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.datetime.DateTimeSymbolsList;
import walkingkooka.datetime.HasDateTimeSymbolsTesting;
import walkingkooka.tree.json.JsonNode;

import java.text.DateFormatSymbols;
import java.util.Locale;
import java.util.stream.Collectors;

public final class BasicJsonMarshallerTypedDateTimeSymbolsListTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedDateTimeSymbolsList, DateTimeSymbolsList>
    implements HasDateTimeSymbolsTesting {

    @Test
    public void testUnmarshallLocaleContextAvailableLocales() {
        final DateTimeSymbolsList list = DateTimeSymbolsList.EMPTY.setElements(
            LOCALE_CONTEXT.availableLocales()
                .stream()
                .filter((Locale locale) -> false == locale.toString().equalsIgnoreCase("no_NO_NY"))
                .map((Locale locale) -> DateTimeSymbols.fromDateFormatSymbols(
                    new DateFormatSymbols(locale)
                )).collect(Collectors.toList())
        );

        this.unmarshallAndCheck(
            BasicJsonMarshallerTypedDateTimeSymbolsList.instance(),
            JsonNode.array()
                .setChildren(
                    list.csvStringList()
                        .stream()
                        .map(JsonNode::string)
                        .collect(Collectors.toList())
                ),
            JSON_NODE_UNMARSHALL_CONTEXT,
            list
        );
    }

    @Override
    BasicJsonMarshallerTypedDateTimeSymbolsList marshaller() {
        return BasicJsonMarshallerTypedDateTimeSymbolsList.instance();
    }

    @Override
    DateTimeSymbolsList value() {
        return DateTimeSymbolsList.EMPTY
            .concat(DATE_TIME_SYMBOLS)
            .concat(DIFFERENT_DATE_TIME_SYMBOLS);
    }

    @Override
    JsonNode node() {
        return JsonNode.parse(
        "[\n" +
            "  \"\\\"am,pm\\\",\\\"January,February,March,April,May,June,July,August,September,October,November,December\\\",\\\"Jan.,Feb.,Mar.,Apr.,May,Jun.,Jul.,Aug.,Sep.,Oct.,Nov.,Dec.\\\",\\\"Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday\\\",\\\"Sun.,Mon.,Tue.,Wed.,Thu.,Fri.,Sat.\\\"\",\n" +
            "  \"\\\"AM,PM\\\",\\\"January,February,March,April,May,June,July,August,September,October,November,December\\\",\\\"Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec\\\",\\\"Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday\\\",\\\"Sun,Mon,Tue,Wed,Thu,Fri,Sat\\\"\"\n" +
            "]"
        );
    }

    @Override
    DateTimeSymbolsList jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "date-time-symbols-list";
    }

    @Override
    Class<DateTimeSymbolsList> marshallerType() {
        return DateTimeSymbolsList.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedDateTimeSymbolsList> type() {
        return BasicJsonMarshallerTypedDateTimeSymbolsList.class;
    }
}
