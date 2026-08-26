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

import walkingkooka.tree.json.JsonNode;

import java.nio.charset.Charset;
import java.util.stream.Collectors;

final class BasicJsonMarshallerTypedCharset extends BasicJsonMarshallerTyped<Charset> {

    static BasicJsonMarshallerTypedCharset instance() {
        return new BasicJsonMarshallerTypedCharset();
    }

    private BasicJsonMarshallerTypedCharset() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
        this.registerTypes(
            Charset.availableCharsets()
                .values()
                .stream()
                .map(
                    charset -> charset.getClass()
                ).collect(Collectors.toList())
        );
    }

    @Override
    Class<Charset> type() {
        return Charset.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(Charset.class);
    }

    @Override
    Charset unmarshallNonNull(final JsonNode node,
                              final JsonNodeUnmarshallContext context) {
        return Charset.forName(
            node.stringOrFail()
        );
    }

    @Override
    Charset unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    JsonNode marshallNonNull(final Charset charset,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(
            charset.name()
        );
    }
}
