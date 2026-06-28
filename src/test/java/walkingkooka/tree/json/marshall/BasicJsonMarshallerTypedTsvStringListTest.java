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

import walkingkooka.collect.list.TsvStringList;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedTsvStringListTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedTsvStringList, TsvStringList> {

    @Override
    public void testRoundtripList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testRoundtripSet() {
        throw new UnsupportedOperationException();
    }

    @Override
    BasicJsonMarshallerTypedTsvStringList marshaller() {
        return BasicJsonMarshallerTypedTsvStringList.instance();
    }

    @Override
    TsvStringList value() {
        return TsvStringList.EMPTY.concat("abc")
            .concat("123\t")
            .concat("\"Hello\r\n\"");
    }

    @Override
    JsonNode node() {
        return JsonNode.string("abc\t\"123\t\"\t\"\"\"Hello\r\n\"\"\"");
    }

    @Override
    TsvStringList jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "tsv-string-list";
    }

    @Override
    Class<TsvStringList> marshallerType() {
        return TsvStringList.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedTsvStringList> type() {
        return BasicJsonMarshallerTypedTsvStringList.class;
    }
}
