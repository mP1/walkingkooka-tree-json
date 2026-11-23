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

import walkingkooka.collect.list.CsvStringList;
import walkingkooka.math.DecimalNumberSymbolsList;
import walkingkooka.tree.json.JsonNode;

/**
 * A {@link BasicJsonMarshaller} that handles {@link DecimalNumberSymbolsList} that handles {@link walkingkooka.tree.json.JsonArray} with each element representing a single {@link walkingkooka.datetime.DecimalNumberSymbols}.
 */
final class BasicJsonMarshallerTypedDecimalNumberSymbolsList extends BasicJsonMarshallerTyped<DecimalNumberSymbolsList> {

    static BasicJsonMarshallerTypedDecimalNumberSymbolsList instance() {
        return new BasicJsonMarshallerTypedDecimalNumberSymbolsList();
    }

    private BasicJsonMarshallerTypedDecimalNumberSymbolsList() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<DecimalNumberSymbolsList> type() {
        return DecimalNumberSymbolsList.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(DecimalNumberSymbolsList.class);
    }

    @Override
    DecimalNumberSymbolsList unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    DecimalNumberSymbolsList unmarshallNonNull(final JsonNode node,
                                          final JsonNodeUnmarshallContext context) {
        return DecimalNumberSymbolsList.fromCsvStringList(
            CsvStringList.EMPTY.setElements(
                context.unmarshallList(
                    node,
                    String.class
                )
            )
        );
    }

    @Override
    JsonNode marshallNonNull(final DecimalNumberSymbolsList list,
                             final JsonNodeMarshallContext context) {
        return context.marshallCollection(
            list.csvStringList()
        );
    }
}
