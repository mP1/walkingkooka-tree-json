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

import walkingkooka.net.DataUrl;
import walkingkooka.net.Url;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedDataUrlTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedDataUrl, DataUrl> {

    @Override
    BasicJsonMarshallerTypedDataUrl marshaller() {
        return BasicJsonMarshallerTypedDataUrl.instance();
    }

    @Override
    DataUrl value() {
        return Url.parseData("data:,Hello%2C%20World%21");
    }

    @Override
    JsonNode node() {
        return JsonNode.string(
            this.value()
                .text()
        );
    }

    @Override
    DataUrl jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "data-url";
    }

    @Override
    Class<DataUrl> marshallerType() {
        return DataUrl.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedDataUrl> type() {
        return BasicJsonMarshallerTypedDataUrl.class;
    }
}
