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

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterCardinality;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A {@link BasicJsonMarshaller} that handles {@link ExpressionFunctionParameter}
 */
final class BasicJsonMarshallerTypedExpressionFunctionParameter extends BasicJsonMarshallerTyped<ExpressionFunctionParameter<?>> {

    static BasicJsonMarshallerTypedExpressionFunctionParameter instance() {
        return new BasicJsonMarshallerTypedExpressionFunctionParameter();
    }

    private BasicJsonMarshallerTypedExpressionFunctionParameter() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<ExpressionFunctionParameter<?>> type() {
        return Cast.to(ExpressionFunctionParameter.class);
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(ExpressionFunctionParameter.class);
    }

    @Override
    ExpressionFunctionParameter<?> unmarshallNull(final JsonNodeUnmarshallContext context) {
        throw new java.lang.NullPointerException();
    }

    @Override
    ExpressionFunctionParameter<?> unmarshallNonNull(final JsonNode node,
                                                     final JsonNodeUnmarshallContext context) {
        ExpressionFunctionParameterName parameterName = null;
        Class<?> type = null;
        List<Class<?>> typeParameters = Lists.empty();
        ExpressionFunctionParameterCardinality cardinality = null;
        Optional<?> defaultValue = Optional.empty();
        Set<ExpressionFunctionParameterKind> kinds = Sets.empty();

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();

            switch (name.value()) {
                case NAME_PROPERTY_STRING:
                    parameterName = context.unmarshall(
                        child,
                        ExpressionFunctionParameterName.class
                    );
                    break;
                case TYPE_PROPERTY_STRING:
                    type = context.unmarshall(
                        child,
                        Class.class
                    );
                    break;
                case TYPE_PARAMETERS_PROPERTY_STRING:
                    typeParameters = context.unmarshallList(
                        child,
                        Cast.to(Class.class)
                    );
                    break;
                case CARDINALITY_PROPERTY_STRING:
                    cardinality = ExpressionFunctionParameterCardinality.valueOf(
                        child.stringOrFail()
                    );
                    break;
                case DEFAULT_VALUE_PROPERTY_STRING:
                    defaultValue = context.unmarshallOptionalWithType(child);
                    break;
                case KINDS_PROPERTY_STRING:
                    kinds = context.unmarshallEnumSet(
                        child,
                        ExpressionFunctionParameterKind.class,
                        ExpressionFunctionParameterKind::valueOf
                    );
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
                    break;
            }
        }

        if (null == parameterName) {
            JsonNodeUnmarshallContext.missingProperty(NAME_PROPERTY, node);
        }
        if (null == type) {
            JsonNodeUnmarshallContext.missingProperty(TYPE_PROPERTY, node);
        }
        if (null == cardinality) {
            JsonNodeUnmarshallContext.missingProperty(CARDINALITY_PROPERTY, node);
        }

        return ExpressionFunctionParameter.with(
            parameterName,
            Cast.to(type),
            typeParameters,
            cardinality,
            Cast.to(defaultValue),
            kinds
        );
    }

    @Override
    JsonNode marshallNonNull(final ExpressionFunctionParameter<?> parameter,
                             final JsonNodeMarshallContext context) {
        JsonObject json = JsonNode.object()
            .set(NAME_PROPERTY, context.marshall(parameter.name()))
            .set(TYPE_PROPERTY, context.marshall(parameter.type()));

        final List<Class<?>> typeParameters = parameter.typeParameters();
        if (typeParameters.size() > 0) {
            json = json.set(TYPE_PARAMETERS_PROPERTY, context.marshallCollection(parameter.typeParameters()));
        }

        json = json.set(CARDINALITY_PROPERTY, context.marshall(parameter.cardinality().name()));

        final Optional<?> defaultValue = parameter.defaultValue();
        if(defaultValue.isPresent()) {
            json = json.set(
                DEFAULT_VALUE_PROPERTY,
                context.marshallOptionalWithType(defaultValue)
            );
        }

        final Set<ExpressionFunctionParameterKind> kinds = parameter.kinds();
        if (kinds.size() > 0) {
            json = json.set(KINDS_PROPERTY, context.marshallEnumSet(parameter.kinds()));
        }

        return json;
    }

    private final static String NAME_PROPERTY_STRING = "name";
    private final static String TYPE_PROPERTY_STRING = "type";
    private final static String CARDINALITY_PROPERTY_STRING = "cardinality";
    private final static String TYPE_PARAMETERS_PROPERTY_STRING = "typeParameters";
    private final static String DEFAULT_VALUE_PROPERTY_STRING = "defaultValue";
    private final static String KINDS_PROPERTY_STRING = "kinds";

    final static JsonPropertyName NAME_PROPERTY = JsonPropertyName.with(NAME_PROPERTY_STRING);
    final static JsonPropertyName TYPE_PROPERTY = JsonPropertyName.with(TYPE_PROPERTY_STRING);
    final static JsonPropertyName CARDINALITY_PROPERTY = JsonPropertyName.with(CARDINALITY_PROPERTY_STRING);
    final static JsonPropertyName TYPE_PARAMETERS_PROPERTY = JsonPropertyName.with(TYPE_PARAMETERS_PROPERTY_STRING);
    final static JsonPropertyName DEFAULT_VALUE_PROPERTY = JsonPropertyName.with(DEFAULT_VALUE_PROPERTY_STRING);
    final static JsonPropertyName KINDS_PROPERTY = JsonPropertyName.with(KINDS_PROPERTY_STRING);
}
