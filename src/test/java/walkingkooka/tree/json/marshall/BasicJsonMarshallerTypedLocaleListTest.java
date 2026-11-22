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
import walkingkooka.locale.LocaleContextTesting;
import walkingkooka.locale.LocaleList;
import walkingkooka.tree.json.JsonNode;

import java.util.Locale;
import java.util.stream.Collectors;

public final class BasicJsonMarshallerTypedLocaleListTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedLocaleList, LocaleList>
    implements LocaleContextTesting {

    @Test
    public void testUnmarshallLocaleContextAvailableLocales() {
        final LocaleList localeList = LocaleList.EMPTY.setElements(
            LOCALE_CONTEXT.availableLocales()
                .stream()
                .filter((Locale locale) -> false == locale.toString().equalsIgnoreCase("no_NO_NY"))
                .collect(Collectors.toList())
        );

        this.unmarshallAndCheck(
            BasicJsonMarshallerTypedLocaleList.instance(),
            JsonNode.string(
                localeList.csvStringList()
                    .text()
            ),
            JSON_NODE_UNMARSHALL_CONTEXT,
            localeList
        );
    }

    @Override
    BasicJsonMarshallerTypedLocaleList marshaller() {
        return BasicJsonMarshallerTypedLocaleList.instance();
    }

    @Override
    LocaleList value() {
        return LocaleList.fromStringLikeList(
            CsvStringList.parse("en-AU,en-NZ")
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.string("en-AU,en-NZ");
    }

    @Override
    LocaleList jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "locale-list";
    }

    @Override
    Class<LocaleList> marshallerType() {
        return LocaleList.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedLocaleList> type() {
        return BasicJsonMarshallerTypedLocaleList.class;
    }
}
