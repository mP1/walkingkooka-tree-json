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
import walkingkooka.tree.json.JsonPropertyName;

/**
 * A {@link BasicJsonMarshaller} that handles {@link walkingkooka.InvalidCharacterException} ignoring any stack trace
 */
final class BasicJsonMarshallerTypedInvalidCharacterException extends BasicJsonMarshallerTyped<walkingkooka.InvalidCharacterException> {

    static BasicJsonMarshallerTypedInvalidCharacterException instance() {
        return new BasicJsonMarshallerTypedInvalidCharacterException();
    }

    private BasicJsonMarshallerTypedInvalidCharacterException() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
        this.registerTypes(Lists.of(InvalidCharacterException.class));
    }

    @Override
    Class<walkingkooka.InvalidCharacterException> type() {
        return walkingkooka.InvalidCharacterException.class;
    }

    @Override
    String typeName() {
        return "invalid-character-exception";
    }

    @Override
    walkingkooka.InvalidCharacterException unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    walkingkooka.InvalidCharacterException unmarshallNonNull(final JsonNode node,
                                                             final JsonNodeUnmarshallContext context) {
        String text = null;
        int position = -1;

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case TEXT:
                    text = child.stringOrFail();
                    break;
                case POSITION:
                    position = child.numberOrFail().intValue();
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
            }
        }

        if (null == text) {
            JsonNodeUnmarshallContext.requiredPropertyMissing(TEXT_JSON_PROPERTY, node);
        }
        if (-1 == position) {
            JsonNodeUnmarshallContext.requiredPropertyMissing(POSITION_JSON_PROPERTY, node);
        }

        return new InvalidCharacterException(text, position);
    }

    @Override
    JsonNode marshallNonNull(final walkingkooka.InvalidCharacterException value,
                             final JsonNodeMarshallContext context) {
        // Added type parameter to avoid
        // Error:BasicJsonMarshallerTypedInvalidCharacterException.java:93: The method of(T...) of type Lists is not applicable as the formal varargs element type T is not accessible here
        return JsonNode.object()
                .setChildren(
                        Lists.<JsonNode>of(
                                JsonNode.string(value.text()).setName(TEXT_JSON_PROPERTY),
                                JsonNode.number(value.position()).setName(POSITION_JSON_PROPERTY)
                        )
                );
    }

    private final static String TEXT = "text";
    private final static String POSITION = "position";

    private final static JsonPropertyName TEXT_JSON_PROPERTY = JsonPropertyName.with(TEXT);
    private final static JsonPropertyName POSITION_JSON_PROPERTY = JsonPropertyName.with(POSITION);
}
