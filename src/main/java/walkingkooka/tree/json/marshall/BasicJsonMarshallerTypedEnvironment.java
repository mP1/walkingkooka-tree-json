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

import walkingkooka.environment.Environment;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;

/**
 * {@link Environment} in json form are an object with {@link EnvironmentValueName} used as the keys,
 * and the value being the value.
 */
final class BasicJsonMarshallerTypedEnvironment extends BasicJsonMarshallerTyped<Environment> {

    static BasicJsonMarshallerTypedEnvironment instance() {
        return new BasicJsonMarshallerTypedEnvironment();
    }

    private BasicJsonMarshallerTypedEnvironment() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<Environment> type() {
        return Environment.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(Environment.class);
    }

    @Override
    Environment unmarshallNonNull(final JsonNode node,
                                  final JsonNodeUnmarshallContext context) {
        Environment environment = Environment.empty();

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();

            environment = environment.set(
                context.parseEnvironmentValueName(
                    name.value()
                ),
                context.unmarshallWithType(
                    child
                )
            );
        }

        return environment;
    }

    @Override
    Environment unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    JsonNode marshallNonNull(final Environment environment,
                             final JsonNodeMarshallContext context) {
        JsonObject object = JsonNode.object();

        for(final EnvironmentValueName<?> name : environment.names()) {
            object = object.set(
                JsonPropertyName.with(
                    name.value()
                ),
                context.marshallWithType(
                    environment.getOrFail(name)
                )
            );
        }

        return object;
    }
}
