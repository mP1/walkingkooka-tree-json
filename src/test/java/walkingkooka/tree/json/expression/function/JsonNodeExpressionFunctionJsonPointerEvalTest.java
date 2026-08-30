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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.expression.FakeJsonNodeExpressionEvaluationContext;
import walkingkooka.tree.json.expression.JsonNodeExpressionEvaluationContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallUnmarshallContextTesting;
import walkingkooka.tree.json.pointer.JsonPointer;

public final class JsonNodeExpressionFunctionJsonPointerEvalTest extends JsonNodeExpressionFunctionTestCase<JsonNodeExpressionFunctionJsonPointerEval<JsonNodeExpressionEvaluationContext>, JsonNode>
    implements JsonNodeMarshallUnmarshallContextTesting {

    @Test
    public void testApplyWithJsonPointerMatch() {
        final JsonPointer pointer = JsonPointer.parse("/hello");

        final String value = "World123";

        final JsonObject json = JsonNode.object()
            .set(
                JsonPropertyName.with("hello"),
                value
            );

        this.applyAndCheck(
            Lists.of(
                pointer,
                json
            ),
            JsonNode.string(value)
        );
    }

    @Test
    public void testApplyWithJsonPointerNonMatch() {
        final JsonPointer pointer = JsonPointer.parse("/missing");

        final String value = "World123";

        final JsonObject json = JsonNode.object()
            .set(
                JsonPropertyName.with("hello"),
                value
            );

        this.applyAndCheck(
            Lists.of(
                pointer,
                json
            ),
            null
        );
    }

    @Override
    public JsonNodeExpressionFunctionJsonPointerEval<JsonNodeExpressionEvaluationContext> createBiFunction() {
        return JsonNodeExpressionFunctionJsonPointerEval.instance();
    }

    @Override
    public JsonNodeExpressionEvaluationContext createContext() {
        return new FakeJsonNodeExpressionEvaluationContext();
    }

    @Override
    public int minimumParameterCount() {
        return 2;
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createBiFunction(),
            "jsonPointerEval"
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonNodeExpressionFunctionJsonPointerEval<JsonNodeExpressionEvaluationContext>> type() {
        return Cast.to(JsonNodeExpressionFunctionJsonPointerEval.class);
    }
}
