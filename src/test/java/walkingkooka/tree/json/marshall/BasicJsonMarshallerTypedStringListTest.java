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

import walkingkooka.collect.list.StringList;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedStringListTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedStringList, StringList> {

    @Override
    BasicJsonMarshallerTypedStringList marshaller() {
        return BasicJsonMarshallerTypedStringList.instance();
    }

    @Override
    StringList value() {
        return StringList.EMPTY.concat("111")
            .concat("\"222\"")
            .concat(null);
    }

    @Override
    JsonNode node() {
        return JsonNode.array()
            .appendChild(JsonNode.string("111"))
            .appendChild(JsonNode.string("\"222\""))
            .appendChild(JsonNode.nullNode());
    }

    @Override
    StringList jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "string-list";
    }

    @Override
    Class<StringList> marshallerType() {
        return StringList.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedStringList> type() {
        return BasicJsonMarshallerTypedStringList.class;
    }
}
