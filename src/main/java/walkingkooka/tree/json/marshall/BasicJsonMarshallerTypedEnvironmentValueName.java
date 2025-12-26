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

import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;

final class BasicJsonMarshallerTypedEnvironmentValueName extends BasicJsonMarshallerTyped<EnvironmentValueName<?>> {

    static BasicJsonMarshallerTypedEnvironmentValueName instance() {
        return new BasicJsonMarshallerTypedEnvironmentValueName();
    }

    private BasicJsonMarshallerTypedEnvironmentValueName() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<EnvironmentValueName<?>> type() {
        return EnvironmentValueName.CLASS_WILDCARD;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(EnvironmentValueName.class);
    }

    @Override
    EnvironmentValueName<?> unmarshallNonNull(final JsonNode node,
                                              final JsonNodeUnmarshallContext context) {
        String environmentValueName = null;
        Class<?> type = null;

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();

            switch (name.value()) {
                case NAME_PROPERTY_STRING:
                    environmentValueName = context.unmarshall(
                        child,
                        String.class
                    );
                    break;
                case TYPE_PROPERTY_STRING:
                    type = context.unmarshall(
                        child,
                        Class.class
                    );
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
                    break;
            }
        }

        if (null == environmentValueName) {
            JsonNodeUnmarshallContext.missingProperty(NAME_PROPERTY, node);
        }
        if (null == type) {
            JsonNodeUnmarshallContext.missingProperty(TYPE_PROPERTY, node);
        }

        return EnvironmentValueName.with(
            environmentValueName,
            type
        );
    }

    @Override
    EnvironmentValueName<?> unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    JsonNode marshallNonNull(final EnvironmentValueName<?> value,
                             final JsonNodeMarshallContext context) {
        return JsonNode.object()
            .set(
                NAME_PROPERTY,
                value.value()
            ).set(
                TYPE_PROPERTY,
                context.marshall(value.type())
            );
    }

    private final static String NAME_PROPERTY_STRING = "name";
    private final static String TYPE_PROPERTY_STRING = "type";

    final static JsonPropertyName NAME_PROPERTY = JsonPropertyName.with(NAME_PROPERTY_STRING);
    final static JsonPropertyName TYPE_PROPERTY = JsonPropertyName.with(TYPE_PROPERTY_STRING);

}
