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

/**
 * A {@link BasicJsonMarshaller} that handles {@link CsvStringSet}
 */
final class BasicJsonMarshallerTypedCsvStringSet extends BasicJsonMarshallerTyped<CsvStringSet> {

    static BasicJsonMarshallerTypedCsvStringSet instance() {
        return new BasicJsonMarshallerTypedCsvStringSet();
    }

    private BasicJsonMarshallerTypedCsvStringSet() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<CsvStringSet> type() {
        return CsvStringSet.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(CsvStringSet.class);
    }

    @Override
    CsvStringSet unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    CsvStringSet unmarshallNonNull(final JsonNode node,
                                    final JsonNodeUnmarshallContext context) {
        return CsvStringSet.parse(node.stringOrFail());
    }

    @Override
    JsonNode marshallNonNull(final CsvStringSet list,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(list.text());
    }
}
