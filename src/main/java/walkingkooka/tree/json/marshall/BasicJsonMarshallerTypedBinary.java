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

import walkingkooka.Binary;
import walkingkooka.tree.json.JsonNode;

import java.util.Base64;

/**
 * A {@link BasicJsonMarshaller} that handles {@link Binary}, with the bytes encoded as BASE64.
 */
final class BasicJsonMarshallerTypedBinary extends BasicJsonMarshallerTyped<Binary> {

    static BasicJsonMarshallerTypedBinary instance() {
        return new BasicJsonMarshallerTypedBinary();
    }

    private BasicJsonMarshallerTypedBinary() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<Binary> type() {
        return Binary.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(Binary.class);
    }

    @Override
    Binary unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    Binary unmarshallNonNull(final JsonNode node,
                             final JsonNodeUnmarshallContext context) {
        return Binary.with(
            Base64.getDecoder()
                .decode(
                    node.stringOrFail()
                )
        );
    }

    @Override
    JsonNode marshallNonNull(final Binary value,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(
            Base64.getEncoder()
                .encodeToString(
                    value.value()
                )
        );
    }
}
