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

import walkingkooka.collect.set.TsvStringSet;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedTsvStringSetTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedTsvStringSet, TsvStringSet> {

    @Override
    BasicJsonMarshallerTypedTsvStringSet marshaller() {
        return BasicJsonMarshallerTypedTsvStringSet.instance();
    }

    @Override
    TsvStringSet value() {
        return TsvStringSet.EMPTY.concat("2\t")
            .concat("\"1\r\n")
            .concat("3");
    }

    @Override
    JsonNode node() {
        return JsonNode.string("\"\"\"1\r\n\"\t\"2\t\"\t3");
    }

    @Override
    TsvStringSet jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "tsv-string-set";
    }

    @Override
    Class<TsvStringSet> marshallerType() {
        return TsvStringSet.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedTsvStringSet> type() {
        return BasicJsonMarshallerTypedTsvStringSet.class;
    }
}
