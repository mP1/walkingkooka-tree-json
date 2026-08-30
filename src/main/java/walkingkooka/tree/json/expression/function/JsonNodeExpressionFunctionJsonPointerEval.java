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

package walkingkooka.tree.json.expression.function;

import walkingkooka.Cast;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.expression.JsonNodeExpressionEvaluationContext;
import walkingkooka.tree.json.pointer.JsonPointer;

import java.util.List;

/**
 * A function that evaluates a {@link JsonPointer} with the given {@link JsonNode} returning the matching node or null
 */
final class JsonNodeExpressionFunctionJsonPointerEval<C extends JsonNodeExpressionEvaluationContext> extends JsonNodeExpressionFunction<C, JsonNode> {

    /**
     * Type safe getter.
     */
    static <C extends JsonNodeExpressionEvaluationContext> JsonNodeExpressionFunctionJsonPointerEval<C> instance() {
        return Cast.to(INSTANCE);
    }

    final static JsonNodeExpressionFunctionJsonPointerEval<?> INSTANCE = new JsonNodeExpressionFunctionJsonPointerEval<>();


    private JsonNodeExpressionFunctionJsonPointerEval() {
        super("jsonPointerEval");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        JSON,
        POINTER
    );

    @Override
    public Class<JsonNode> returnType() {
        return JsonNode.class;
    }

    @Override
    public JsonNode apply(final List<Object> parameters,
                          final C context) {
        this.checkParameterCount(parameters);

        final JsonPointer pointer = POINTER.getOrFail(parameters, 0);
        final JsonNode json = JSON.getOrFail(parameters, 1);

        return pointer.apply(json)
            .orElse(null);
    }
}
