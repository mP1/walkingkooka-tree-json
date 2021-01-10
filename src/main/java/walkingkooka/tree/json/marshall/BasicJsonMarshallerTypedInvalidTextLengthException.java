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
 * A {@link BasicJsonMarshaller} that handles {@link walkingkooka.InvalidTextLengthException} ignoring any stack trace
 */
final class BasicJsonMarshallerTypedInvalidTextLengthException extends BasicJsonMarshallerTyped<walkingkooka.InvalidTextLengthException> {

    static BasicJsonMarshallerTypedInvalidTextLengthException instance() {
        return new BasicJsonMarshallerTypedInvalidTextLengthException();
    }

    private BasicJsonMarshallerTypedInvalidTextLengthException() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
        this.registerTypes(Lists.of(InvalidTextLengthException.class));
    }

    @Override
    Class<walkingkooka.InvalidTextLengthException> type() {
        return walkingkooka.InvalidTextLengthException.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(walkingkooka.InvalidTextLengthException.class);
    }

    @Override
    walkingkooka.InvalidTextLengthException unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    walkingkooka.InvalidTextLengthException unmarshallNonNull(final JsonNode node,
                                                              final JsonNodeUnmarshallContext context) {
        String label = null;
        String text = null;
        int min = -1;
        int max = -1;

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case LABEL:
                    label = child.stringOrFail();
                    break;
                case TEXT:
                    text = child.stringOrFail();
                    break;
                case MIN:
                    min = child.numberOrFail().intValue();
                    break;
                case MAX:
                    max = child.numberOrFail().intValue();
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
            }
        }

        if (null == text) {
            JsonNodeUnmarshallContext.requiredPropertyMissing(TEXT_JSON_PROPERTY, node);
        }
        if (-1 == min) {
            JsonNodeUnmarshallContext.requiredPropertyMissing(MIN_JSON_PROPERTY, node);
        }
        if (-1 == max) {
            JsonNodeUnmarshallContext.requiredPropertyMissing(MAX_JSON_PROPERTY, node);
        }
        return new InvalidTextLengthException(label, text, min, max);
    }

    @Override
    JsonNode marshallNonNull(final walkingkooka.InvalidTextLengthException value,
                             final JsonNodeMarshallContext context) {
        return JsonNode.object()
                .setChildren(
                        Lists.<JsonNode>of(
                                JsonNode.string(value.label()).setName(LABEL_JSON_PROPERTY),
                                JsonNode.string(value.text()).setName(TEXT_JSON_PROPERTY),
                                JsonNode.number(value.min()).setName(MIN_JSON_PROPERTY),
                                JsonNode.number(value.max()).setName(MAX_JSON_PROPERTY)
                        )
                );
    }

    private final static String LABEL = "label";
    private final static String TEXT = "text";
    private final static String MIN = "min";
    private final static String MAX = "max";


    private final static JsonPropertyName LABEL_JSON_PROPERTY = JsonPropertyName.with(LABEL);
    private final static JsonPropertyName TEXT_JSON_PROPERTY = JsonPropertyName.with(TEXT);
    private final static JsonPropertyName MIN_JSON_PROPERTY = JsonPropertyName.with(MIN);
    private final static JsonPropertyName MAX_JSON_PROPERTY = JsonPropertyName.with(MAX);
}
