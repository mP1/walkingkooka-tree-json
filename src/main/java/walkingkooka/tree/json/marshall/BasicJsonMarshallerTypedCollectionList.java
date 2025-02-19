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

import walkingkooka.Cast;
import walkingkooka.tree.json.JsonNode;

import java.util.List;

final class BasicJsonMarshallerTypedCollectionList extends BasicJsonMarshallerTypedCollection<List<?>> {

    static BasicJsonMarshallerTypedCollectionList instance() {
        return new BasicJsonMarshallerTypedCollectionList();
    }

    private BasicJsonMarshallerTypedCollectionList() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<List<?>> type() {
        return Cast.to(List.class);
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(List.class);
    }

    @Override
    List<?> unmarshallNonNull(final JsonNode node,
                              final JsonNodeUnmarshallContext context) {
        return context.unmarshallListWithType(node);
    }
}
