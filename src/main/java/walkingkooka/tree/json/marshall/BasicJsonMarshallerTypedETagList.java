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

import walkingkooka.net.header.ETagList;
import walkingkooka.tree.json.JsonNode;

final class BasicJsonMarshallerTypedETagList extends BasicJsonMarshallerTyped<ETagList> {

    static BasicJsonMarshallerTypedETagList instance() {
        return new BasicJsonMarshallerTypedETagList();
    }

    private BasicJsonMarshallerTypedETagList() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<ETagList> type() {
        return ETagList.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(ETagList.class);
    }

    @Override
    ETagList unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    ETagList unmarshallNonNull(final JsonNode node,
                               final JsonNodeUnmarshallContext context) {
        return ETagList.parse(
            node.stringOrFail()
        );
    }

    @Override
    JsonNode marshallNonNull(final ETagList value,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(
            value.text()
        );
    }
}
