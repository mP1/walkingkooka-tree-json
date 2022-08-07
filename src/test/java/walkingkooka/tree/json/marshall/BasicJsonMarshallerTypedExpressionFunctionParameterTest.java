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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterCardinality;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;
import walkingkooka.tree.json.JsonNode;

import java.util.List;
import java.util.Set;

public final class BasicJsonMarshallerTypedExpressionFunctionParameterTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedExpressionFunctionParameter, ExpressionFunctionParameter<?>> {

    private final static ExpressionFunctionParameterName NAME = ExpressionFunctionParameterName.with("parameter123");

    private final static Class<List<String>> TYPE = Cast.to(List.class);

    private final static List<Class<?>> TYPE_PARAMETERS = Lists.of(String.class);

    private final static ExpressionFunctionParameterCardinality CARDINALITY = ExpressionFunctionParameterCardinality.REQUIRED;

    private final static Set<ExpressionFunctionParameterKind> KINDS = ExpressionFunctionParameterKind.CONVERT_EVALUATE;

    @Test
    @Override
    public void testUnmarshallJsonNullNode() {
        this.unmarshallFailed(
                JsonNode.nullNode(),
                java.lang.NullPointerException.class
        );
    }

    @Override
    BasicJsonMarshallerTypedExpressionFunctionParameter marshaller() {
        return BasicJsonMarshallerTypedExpressionFunctionParameter.instance();
    }

    @Override
    ExpressionFunctionParameter<?> value() {
        return ExpressionFunctionParameter.with(
                NAME,
                TYPE,
                TYPE_PARAMETERS,
                CARDINALITY,
                KINDS
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.object()
                .set(BasicJsonMarshallerTypedExpressionFunctionParameter.NAME_PROPERTY, JsonNode.string(NAME.value()))
                .set(BasicJsonMarshallerTypedExpressionFunctionParameter.TYPE_PROPERTY, JsonNode.string("java.util.List"))
                .set(BasicJsonMarshallerTypedExpressionFunctionParameter.TYPE_PARAMETERS_PROPERTY, JsonNode.array().appendChild(JsonNode.string("java.lang.String")))
                .set(BasicJsonMarshallerTypedExpressionFunctionParameter.CARDINALITY_PROPERTY, JsonNode.string("REQUIRED"))
                .set(BasicJsonMarshallerTypedExpressionFunctionParameter.KINDS_PROPERTY, JsonNode.string("CONVERT,EVALUATE"));
    }

    @Override
    ExpressionFunctionParameter<?> jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "expression-function-parameter";
    }

    @Override
    Class<ExpressionFunctionParameter<?>> marshallerType() {
        return Cast.to(ExpressionFunctionParameter.class);
    }

    @Override
    public Class<BasicJsonMarshallerTypedExpressionFunctionParameter> type() {
        return BasicJsonMarshallerTypedExpressionFunctionParameter.class;
    }
}
