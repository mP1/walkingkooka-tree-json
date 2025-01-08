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

import java.nio.charset.Charset;
import java.util.Base64;

public final class BasicJsonMarshallerTypedBinaryTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedBinary, Binary> {

    private final static byte[] BYTES = "abc123".getBytes(Charset.defaultCharset());

    @Override
    BasicJsonMarshallerTypedBinary marshaller() {
        return BasicJsonMarshallerTypedBinary.instance();
    }

    @Override
    Binary value() {
        return Binary.with(BYTES);
    }

    @Override
    JsonNode node() {
        return JsonNode.string(
            Base64.getEncoder()
                .encodeToString(BYTES)
        );
    }

    @Override
    Binary jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "binary";
    }

    @Override
    Class<Binary> marshallerType() {
        return Binary.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedBinary> type() {
        return BasicJsonMarshallerTypedBinary.class;
    }
}
