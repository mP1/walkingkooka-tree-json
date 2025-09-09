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
import walkingkooka.datetime.LocalTimeList;
import walkingkooka.tree.json.JsonNode;

import java.time.LocalTime;

public final class BasicJsonMarshallerTypedLocalTimeListTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedLocalTimeList, LocalTimeList> {

    @Override
    BasicJsonMarshallerTypedLocalTimeList marshaller() {
        return BasicJsonMarshallerTypedLocalTimeList.instance();
    }

    @Override
    LocalTimeList value() {
        return LocalTimeList.with(
            Lists.of(
                LocalTime.of(1, 11, 58),
                LocalTime.of(2, 22, 59)
            )
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.parse(
            "[\n" +
            "  \"01:11:58\",\n" +
            "  \"02:22:59\"\n" +
            "]"
        );
    }

    @Override
    LocalTimeList jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "local-time-list";
    }

    @Override
    Class<LocalTimeList> marshallerType() {
        return LocalTimeList.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedLocalTimeList> type() {
        return BasicJsonMarshallerTypedLocalTimeList.class;
    }
}
