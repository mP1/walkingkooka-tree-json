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

/**
 * A {@link BasicJsonMarshaller} that handles {@link TsvStringList}
 */
final class BasicJsonMarshallerTypedTsvStringList extends BasicJsonMarshallerTyped<TsvStringList> {

    static BasicJsonMarshallerTypedTsvStringList instance() {
        return new BasicJsonMarshallerTypedTsvStringList();
    }

    private BasicJsonMarshallerTypedTsvStringList() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<TsvStringList> type() {
        return TsvStringList.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(TsvStringList.class);
    }

    @Override
    TsvStringList unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    TsvStringList unmarshallNonNull(final JsonNode node,
                                    final JsonNodeUnmarshallContext context) {
        return TsvStringList.parse(node.stringOrFail());
    }

    @Override
    JsonNode marshallNonNull(final TsvStringList list,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(list.text());
    }
}
