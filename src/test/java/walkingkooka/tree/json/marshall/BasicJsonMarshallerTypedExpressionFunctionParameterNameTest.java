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
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedExpressionFunctionParameterNameTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedExpressionFunctionParameterName, ExpressionFunctionParameterName> {

    @Test
    @Override
    public void testUnmarshallJsonNullNode() {
        this.unmarshallFailed(
            JsonNode.nullNode(),
            java.lang.NullPointerException.class
        );
    }

    @Override
    BasicJsonMarshallerTypedExpressionFunctionParameterName marshaller() {
        return BasicJsonMarshallerTypedExpressionFunctionParameterName.instance();
    }

    @Override
    ExpressionFunctionParameterName value() {
        return ExpressionFunctionParameterName.with("parameter123");
    }

    @Override
    JsonNode node() {
        return JsonNode.string(this.value().toString());
    }

    @Override
    ExpressionFunctionParameterName jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "expression-function-parameter-name";
    }

    @Override
    Class<ExpressionFunctionParameterName> marshallerType() {
        return ExpressionFunctionParameterName.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedExpressionFunctionParameterName> type() {
        return BasicJsonMarshallerTypedExpressionFunctionParameterName.class;
    }
}
