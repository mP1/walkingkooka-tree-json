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

import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;
import walkingkooka.tree.json.JsonNode;

/**
 * A {@link BasicJsonMarshaller} that handles {@link ExpressionFunctionParameterName}
 */
final class BasicJsonMarshallerTypedExpressionFunctionParameterName extends BasicJsonMarshallerTyped<ExpressionFunctionParameterName> {

    static BasicJsonMarshallerTypedExpressionFunctionParameterName instance() {
        return new BasicJsonMarshallerTypedExpressionFunctionParameterName();
    }

    private BasicJsonMarshallerTypedExpressionFunctionParameterName() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<ExpressionFunctionParameterName> type() {
        return ExpressionFunctionParameterName.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(ExpressionFunctionParameterName.class);
    }

    @Override
    ExpressionFunctionParameterName unmarshallNonNull(final JsonNode node,
                                                      final JsonNodeUnmarshallContext context) {
        return ExpressionFunctionParameterName.with(
                node.stringOrFail()
        );
    }

    @Override
    ExpressionFunctionParameterName unmarshallNull(final JsonNodeUnmarshallContext context) {
        throw new java.lang.NullPointerException();
    }

    @Override
    JsonNode marshallNonNull(final ExpressionFunctionParameterName value,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(value.toString());
    }
}
