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
import walkingkooka.locale.LocaleList;
import walkingkooka.tree.json.JsonNode;

/**
 * A {@link BasicJsonMarshaller} that handles {@link LocaleList}
 */
final class BasicJsonMarshallerTypedLocaleList extends BasicJsonMarshallerTyped<LocaleList> {

    static BasicJsonMarshallerTypedLocaleList instance() {
        return new BasicJsonMarshallerTypedLocaleList();
    }

    private BasicJsonMarshallerTypedLocaleList() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<LocaleList> type() {
        return LocaleList.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(LocaleList.class);
    }

    @Override
    LocaleList unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    LocaleList unmarshallNonNull(final JsonNode node,
                                 final JsonNodeUnmarshallContext context) {
        return LocaleList.fromStringLikeList(
            context.unmarshall(
                node,
                CsvStringList.class
            )
        );
    }

    @Override
    JsonNode marshallNonNull(final LocaleList list,
                             final JsonNodeMarshallContext context) {
        return context.marshall(
            list.csvStringList()
        );
    }
}
