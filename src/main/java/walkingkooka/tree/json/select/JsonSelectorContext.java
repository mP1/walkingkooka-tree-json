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

package walkingkooka.tree.json.select;

import walkingkooka.Context;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.json.JsonNode;

/**
 * The {@link Context} that accompanies {@link JsonSelector#apply(JsonNode, JsonSelectorContext)}, providing
 * support for executing any functions within a selector.
 */
public interface JsonSelectorContext extends Context {

    /**
     * Creates a {@link ExpressionEvaluationContext} with the given {@link JsonNode} which will be used during
     * function evaluation.
     */
    ExpressionEvaluationContext expressionEvaluationContext(final JsonNode node);
}
