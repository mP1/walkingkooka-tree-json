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

import java.util.Objects;

/**
 * A {@link BasicJsonMarshaller} that handles {@link DataUrl}
 */
final class BasicJsonMarshallerTypedDataUrl extends BasicJsonMarshallerTyped<DataUrl> {

    static BasicJsonMarshallerTypedDataUrl instance() {
        return new BasicJsonMarshallerTypedDataUrl();
    }

    private BasicJsonMarshallerTypedDataUrl() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<DataUrl> type() {
        return DataUrl.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(DataUrl.class);
    }

    @Override
    DataUrl unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    DataUrl unmarshallNonNull(final JsonNode node,
                              final JsonNodeUnmarshallContext context) {
        Objects.requireNonNull(node, "node");

        return Url.parseData(
            node.stringOrFail()
        );
    }

    @Override
    JsonNode marshallNonNull(final DataUrl value,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(value.toString());
    }
}
