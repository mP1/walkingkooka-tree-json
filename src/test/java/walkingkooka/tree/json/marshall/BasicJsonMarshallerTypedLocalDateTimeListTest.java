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
import walkingkooka.datetime.LocalDateTimeList;
import walkingkooka.tree.json.JsonNode;

import java.time.LocalDateTime;

public final class BasicJsonMarshallerTypedLocalDateTimeListTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedLocalDateTimeList, LocalDateTimeList> {

    @Override
    BasicJsonMarshallerTypedLocalDateTimeList marshaller() {
        return BasicJsonMarshallerTypedLocalDateTimeList.instance();
    }

    @Override
    LocalDateTimeList value() {
        return LocalDateTimeList.EMPTY.setElements(
            Lists.of(
                LocalDateTime.of(1999, 1, 1, 1, 0, 0),
                LocalDateTime.of(2000, 2, 2, 2, 0, 0)
            )
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.parse(
            "[\n" +
            "  \"1999-01-01T01:00\",\n" +
            "  \"2000-02-02T02:00\"\n" +
            "]"
        );
    }

    @Override
    LocalDateTimeList jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "local-date-time-list";
    }

    @Override
    Class<LocalDateTimeList> marshallerType() {
        return LocalDateTimeList.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedLocalDateTimeList> type() {
        return BasicJsonMarshallerTypedLocalDateTimeList.class;
    }
}
