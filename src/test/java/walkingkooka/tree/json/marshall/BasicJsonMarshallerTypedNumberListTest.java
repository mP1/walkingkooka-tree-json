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
import walkingkooka.math.NumberList;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedNumberListTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedNumberList, NumberList> {

    @Override
    BasicJsonMarshallerTypedNumberList marshaller() {
        return BasicJsonMarshallerTypedNumberList.instance();
    }

    @Override
    NumberList value() {
        return NumberList.with(
            Lists.of(
                Byte.valueOf((byte)1),
                Short.valueOf((short)2),
                3,
                4.5f,
                6.75,
                EXPRESSION_NUMBER_KIND.create(999)
            )
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.parse(
            "[\n" +
                "  {\n" +
                "    \"type\": \"byte\",\n" +
                "    \"value\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"type\": \"short\",\n" +
                "    \"value\": 2\n" +
                "  },\n" +
                "  {\n" +
                "    \"type\": \"int\",\n" +
                "    \"value\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"type\": \"float\",\n" +
                "    \"value\": 4.5\n" +
                "  },\n" +
                "  6.75,\n" +
                "  {\n" +
                "    \"type\": \"expression-number\",\n" +
                "    \"value\": \"999\"\n" +
                "  }\n" +
                "]"
        );
    }

    @Override
    NumberList jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "number-list";
    }

    @Override
    Class<NumberList> marshallerType() {
        return NumberList.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedNumberList> type() {
        return BasicJsonMarshallerTypedNumberList.class;
    }
}
