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

import walkingkooka.math.NumberList;
import walkingkooka.tree.json.JsonNode;

final class BasicJsonMarshallerTypedNumberList extends BasicJsonMarshallerTyped<NumberList> {

    static BasicJsonMarshallerTypedNumberList instance() {
        return new BasicJsonMarshallerTypedNumberList();
    }

    private BasicJsonMarshallerTypedNumberList() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<NumberList> type() {
        return NumberList.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(NumberList.class);
    }

    @Override
    NumberList unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    NumberList unmarshallNonNull(final JsonNode node,
                                 final JsonNodeUnmarshallContext context) {
        return NumberList.EMPTY.setElements(
            context.unmarshallListWithType(node)
        );
    }

    @Override
    JsonNode marshallNonNull(final NumberList value,
                             final JsonNodeMarshallContext context) {
        return context.marshallCollectionWithType(value);
    }
}
