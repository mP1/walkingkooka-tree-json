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

import walkingkooka.collect.list.Lists;
import walkingkooka.net.header.ETag;
import walkingkooka.net.header.ETagList;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedETagListTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedETagList, ETagList> {

    @Override
    BasicJsonMarshallerTypedETagList marshaller() {
        return BasicJsonMarshallerTypedETagList.instance();
    }

    @Override
    ETagList value() {
        return ETagList.EMPTY.setElements(
            Lists.of(
                ETag.strong("Strong222"),
                ETag.weak("Weak333"),
                ETag.wildcard()
            )
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.string(
            this.value()
                .text()
        );
    }

    @Override
    ETagList jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "e-tag-list";
    }

    @Override
    Class<ETagList> marshallerType() {
        return ETagList.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedETagList> type() {
        return BasicJsonMarshallerTypedETagList.class;
    }
}
