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

import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;

import java.util.List;

/**
 * A {@link BasicJsonMarshaller} that handles {@link DateTimeSymbols}
 */
final class BasicJsonMarshallerTypedDateTimeSymbols extends BasicJsonMarshallerTyped<DateTimeSymbols> {

    static BasicJsonMarshallerTypedDateTimeSymbols instance() {
        return new BasicJsonMarshallerTypedDateTimeSymbols();
    }

    private BasicJsonMarshallerTypedDateTimeSymbols() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<DateTimeSymbols> type() {
        return DateTimeSymbols.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(DateTimeSymbols.class);
    }

    @Override
    DateTimeSymbols unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    DateTimeSymbols unmarshallNonNull(final JsonNode node,
                                      final JsonNodeUnmarshallContext context) {
        List<String> ampms = null;
        List<String> monthNames = null;
        List<String> monthNameAbbreviations = null;
        List<String> weekDayNames = null;
        List<String> weekDayNameAbbreviations = null;

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case AMPMS_PROPERTY_STRING:
                    ampms = context.unmarshallList(
                        child,
                        String.class
                    );
                    break;
                case MONTH_NAMES_PROPERTY_STRING:
                    monthNames = context.unmarshallList(
                        child,
                        String.class
                    );
                    break;
                case MONTH_NAME_ABBREVIATIONS_PROPERTY_STRING:
                    monthNameAbbreviations = context.unmarshallList(
                        child,
                        String.class
                    );
                    break;
                case WEEKDAY_NAMES_PROPERTY_STRING:
                    weekDayNames = context.unmarshallList(
                        child,
                        String.class
                    );
                    break;
                case WEEKDAY_NAME_ABBREVIATIONS_PROPERTY_STRING:
                    weekDayNameAbbreviations = context.unmarshallList(
                        child,
                        String.class
                    );
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
                    break;
            }
        }

        if (null == ampms) {
            JsonNodeUnmarshallContext.missingProperty(
                AMPMS_PROPERTY,
                node
            );
        }
        if (null == monthNames) {
            JsonNodeUnmarshallContext.missingProperty(
                MONTH_NAMES_PROPERTY,
                node
            );
        }
        if (null == monthNameAbbreviations) {
            JsonNodeUnmarshallContext.missingProperty(
                MONTH_NAME_ABBREVIATIONS_PROPERTY,
                node
            );
        }
        if (null == weekDayNames) {
            JsonNodeUnmarshallContext.missingProperty(
                WEEKDAY_NAMES_PROPERTY,
                node
            );
        }
        if (null == weekDayNameAbbreviations) {
            JsonNodeUnmarshallContext.missingProperty(
                WEEKDAY_NAME_ABBREVIATIONS_PROPERTY,
                node
            );
        }

        return DateTimeSymbols.with(
            ampms,
            monthNames,
            monthNameAbbreviations,
            weekDayNames,
            weekDayNameAbbreviations
        );
    }

    @Override
    JsonNode marshallNonNull(final DateTimeSymbols value,
                             final JsonNodeMarshallContext context) {
        return JsonNode.object()
            .set(AMPMS_PROPERTY, context.marshallCollection(value.ampms()))
            .set(MONTH_NAMES_PROPERTY, context.marshallCollection(value.monthNames()))
            .set(MONTH_NAME_ABBREVIATIONS_PROPERTY, context.marshallCollection(value.monthNameAbbreviations()))
            .set(WEEKDAY_NAMES_PROPERTY, context.marshallCollection(value.weekDayNames()))
            .set(WEEKDAY_NAME_ABBREVIATIONS_PROPERTY, context.marshallCollection(value.weekDayNameAbbreviations()));
    }

    private final static String AMPMS_PROPERTY_STRING = "ampms";

    private final static String MONTH_NAMES_PROPERTY_STRING = "monthNames";

    private final static String MONTH_NAME_ABBREVIATIONS_PROPERTY_STRING = "monthNameAbbreviations";

    private final static String WEEKDAY_NAMES_PROPERTY_STRING = "weekDayNames";

    private final static String WEEKDAY_NAME_ABBREVIATIONS_PROPERTY_STRING = "weekDayNameAbbreviations";

    final static JsonPropertyName AMPMS_PROPERTY = JsonPropertyName.with(AMPMS_PROPERTY_STRING);

    final static JsonPropertyName MONTH_NAMES_PROPERTY = JsonPropertyName.with(MONTH_NAMES_PROPERTY_STRING);

    final static JsonPropertyName MONTH_NAME_ABBREVIATIONS_PROPERTY = JsonPropertyName.with(MONTH_NAME_ABBREVIATIONS_PROPERTY_STRING);

    final static JsonPropertyName WEEKDAY_NAMES_PROPERTY = JsonPropertyName.with(WEEKDAY_NAMES_PROPERTY_STRING);

    final static JsonPropertyName WEEKDAY_NAME_ABBREVIATIONS_PROPERTY = JsonPropertyName.with(WEEKDAY_NAME_ABBREVIATIONS_PROPERTY_STRING);
}
