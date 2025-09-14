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

import walkingkooka.collect.list.Lists;
import walkingkooka.datetime.LocalDateList;
import walkingkooka.tree.json.JsonNode;

import java.time.LocalDate;

public final class BasicJsonMarshallerTypedLocalDateListTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedLocalDateList, LocalDateList> {

    @Override
    BasicJsonMarshallerTypedLocalDateList marshaller() {
        return BasicJsonMarshallerTypedLocalDateList.instance();
    }

    @Override
    LocalDateList value() {
        return LocalDateList.EMPTY.setElements(
            Lists.of(
                LocalDate.of(1999, 1, 1),
                LocalDate.of(2000, 2, 2)
            )
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.parse(
            "[\n" +
            "  \"1999-01-01\",\n" +
            "  \"2000-02-02\"\n" +
            "]"
        );
    }

    @Override
    LocalDateList jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "local-date-list";
    }

    @Override
    Class<LocalDateList> marshallerType() {
        return LocalDateList.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedLocalDateList> type() {
        return BasicJsonMarshallerTypedLocalDateList.class;
    }
}
