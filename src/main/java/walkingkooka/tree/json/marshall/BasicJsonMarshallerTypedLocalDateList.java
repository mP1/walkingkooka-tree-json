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

import walkingkooka.datetime.LocalDateList;
import walkingkooka.tree.json.JsonNode;

import java.time.LocalDate;

final class BasicJsonMarshallerTypedLocalDateList extends BasicJsonMarshallerTyped<LocalDateList> {

    static BasicJsonMarshallerTypedLocalDateList instance() {
        return new BasicJsonMarshallerTypedLocalDateList();
    }

    private BasicJsonMarshallerTypedLocalDateList() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<LocalDateList> type() {
        return LocalDateList.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(LocalDateList.class);
    }

    @Override
    LocalDateList unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    LocalDateList unmarshallNonNull(final JsonNode node,
                                    final JsonNodeUnmarshallContext context) {
        return LocalDateList.EMPTY.setElements(
            context.unmarshallList(
                node,
                LocalDate.class
            )
        );
    }

    @Override
    JsonNode marshallNonNull(final LocalDateList value,
                             final JsonNodeMarshallContext context) {
        return context.marshallCollection(value);
    }
}
