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

import java.lang.IllegalArgumentException;
import java.util.List;
import java.util.OptionalInt;

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
        return JsonNodeContext.computeTypeName(walkingkooka.InvalidCharacterException.class);
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
        OptionalInt column = OptionalInt.empty();
        OptionalInt line = OptionalInt.empty();
        String appendToMessage = InvalidCharacterException.NO_APPEND_TO_MESSAGE;

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case TEXT:
                    text = child.stringOrFail();
                    break;
                case POSITION:
                    position = child.numberOrFail()
                        .intValue();
                    break;
                case COLUMN:
                    final int columnValue = child.numberOrFail()
                        .intValue();
                    if (columnValue < 1) {
                        throw new IllegalArgumentException("Invalid column " + columnValue + " < 1");
                    }
                    column = OptionalInt.of(columnValue);
                    break;
                case LINE:
                    final int lineValue = child.numberOrFail()
                        .intValue();
                    if (lineValue < 1) {
                        throw new IllegalArgumentException("Invalid line " + lineValue + " < 1");
                    }
                    line = OptionalInt.of(lineValue);
                    break;
                case APPEND_TO_MESSAGE:
                    appendToMessage = child.stringOrFail();
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
            }
        }

        if (null == text) {
            JsonNodeUnmarshallContext.missingProperty(TEXT_JSON_PROPERTY, node);
        }
        if (-1 == position) {
            JsonNodeUnmarshallContext.missingProperty(POSITION_JSON_PROPERTY, node);
        }
        if (null == appendToMessage) {
            JsonNodeUnmarshallContext.missingProperty(APPEND_TO_MESSAGE_PROPERTY, node);
        }

        return new InvalidCharacterException(
            text,
            position,
            column,
            line,
            appendToMessage,
            null // ignore cause
        );
    }

    @Override
    JsonNode marshallNonNull(final walkingkooka.InvalidCharacterException ice,
                             final JsonNodeMarshallContext context) {
        // Added type parameter to avoid
        // Error:BasicJsonMarshallerTypedInvalidCharacterException.java:93: The method of(T...) of type Lists is not applicable as the formal varargs element type T is not accessible here
        final List<JsonNode> children = Lists.array();

        children.add(
            JsonNode.string(
                ice.text()
            ).setName(TEXT_JSON_PROPERTY)
        );
        children.add(
            JsonNode.number(
                ice.position()
            ).setName(POSITION_JSON_PROPERTY)
        );

        final OptionalInt column = ice.column();
        if (column.isPresent()) {
            children.add(
                JsonNode.number(
                    ice.column()
                        .getAsInt()
                ).setName(COLUMN_JSON_PROPERTY)
            );

            children.add(
                JsonNode.number(
                    ice.line()
                        .getAsInt()
                ).setName(LINE_JSON_PROPERTY)
            );
        }

        final String appendToMessage = ice.appendToMessage();
        if (false == appendToMessage.isEmpty()) {
            children.add(
                JsonNode.string(appendToMessage)
                    .setName(APPEND_TO_MESSAGE_PROPERTY)
            );
        }

        return JsonNode.object()
            .setChildren(children);
    }

    private final static String TEXT = "text";
    private final static String POSITION = "position";
    private final static String COLUMN = "column";
    private final static String LINE = "line";
    private final static String APPEND_TO_MESSAGE = "appendToMessage";

    private final static JsonPropertyName TEXT_JSON_PROPERTY = JsonPropertyName.with(TEXT);
    private final static JsonPropertyName POSITION_JSON_PROPERTY = JsonPropertyName.with(POSITION);
    private final static JsonPropertyName COLUMN_JSON_PROPERTY = JsonPropertyName.with(COLUMN);
    private final static JsonPropertyName LINE_JSON_PROPERTY = JsonPropertyName.with(LINE);
    private final static JsonPropertyName APPEND_TO_MESSAGE_PROPERTY = JsonPropertyName.with(APPEND_TO_MESSAGE);
}
