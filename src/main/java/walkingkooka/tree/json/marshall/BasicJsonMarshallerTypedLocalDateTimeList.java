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

import walkingkooka.datetime.LocalDateTimeList;
import walkingkooka.tree.json.JsonNode;

import java.time.LocalDateTime;

final class BasicJsonMarshallerTypedLocalDateTimeList extends BasicJsonMarshallerTyped<LocalDateTimeList> {

    static BasicJsonMarshallerTypedLocalDateTimeList instance() {
        return new BasicJsonMarshallerTypedLocalDateTimeList();
    }

    private BasicJsonMarshallerTypedLocalDateTimeList() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<LocalDateTimeList> type() {
        return LocalDateTimeList.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(LocalDateTimeList.class);
    }

    @Override
    LocalDateTimeList unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    LocalDateTimeList unmarshallNonNull(final JsonNode node,
                                        final JsonNodeUnmarshallContext context) {
        return LocalDateTimeList.EMPTY.setElements(
            context.unmarshallList(
                node,
                LocalDateTime.class
            )
        );
    }

    @Override
    JsonNode marshallNonNull(final LocalDateTimeList value,
                             final JsonNodeMarshallContext context) {
        return context.marshallCollection(value);
    }
}
