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
import walkingkooka.tree.json.JsonNode;

/**
 * A {@link BasicJsonMarshaller} that handles {@link java.lang.NullPointerException} ignoring any stack trace
 */
final class BasicJsonMarshallerTypedNullPointerException extends BasicJsonMarshallerTyped<java.lang.NullPointerException> {

    static BasicJsonMarshallerTypedNullPointerException instance() {
        return new BasicJsonMarshallerTypedNullPointerException();
    }

    private BasicJsonMarshallerTypedNullPointerException() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
        this.registerTypes(Lists.of(NullPointerException.class));
    }

    @Override
    Class<java.lang.NullPointerException> type() {
        return java.lang.NullPointerException.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(java.lang.NullPointerException.class);
    }

    @Override
    java.lang.NullPointerException unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    java.lang.NullPointerException unmarshallNonNull(final JsonNode node,
                                                     final JsonNodeUnmarshallContext context) {
        return NULL.equals(node) ?
                new NullPointerException() :
                new NullPointerException(node.stringOrFail());
    }

    @Override
    JsonNode marshallNonNull(final java.lang.NullPointerException value,
                             final JsonNodeMarshallContext context) {
        final String message = value.getMessage();
        return null != message ?
                JsonNode.string(message) :
                NULL;
    }

    private final static JsonNode NULL = JsonNode.number(0);
}
