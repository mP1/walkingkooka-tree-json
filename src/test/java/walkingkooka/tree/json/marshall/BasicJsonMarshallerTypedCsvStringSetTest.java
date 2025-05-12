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

import walkingkooka.collect.set.CsvStringSet;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedCsvStringSetTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedCsvStringSet, CsvStringSet> {

    @Override
    BasicJsonMarshallerTypedCsvStringSet marshaller() {
        return BasicJsonMarshallerTypedCsvStringSet.instance();
    }

    @Override
    CsvStringSet value() {
        return CsvStringSet.EMPTY.concat("2,")
            .concat("\"1\r\n")
            .concat("3");
    }

    @Override
    JsonNode node() {
        return JsonNode.string("\"\"\"1\r\n\",\"2,\",3");
    }

    @Override
    CsvStringSet jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "csv-string-set";
    }

    @Override
    Class<CsvStringSet> marshallerType() {
        return CsvStringSet.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedCsvStringSet> type() {
        return BasicJsonMarshallerTypedCsvStringSet.class;
    }
}
