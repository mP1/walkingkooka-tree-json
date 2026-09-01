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
import walkingkooka.naming.Name;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.expression.FakeJsonNodeExpressionEvaluationContext;
import walkingkooka.tree.json.expression.JsonNodeExpressionEvaluationContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallUnmarshallContextTesting;
import walkingkooka.tree.json.select.FakeJsonSelectorContext;
import walkingkooka.tree.json.select.JsonSelector;
import walkingkooka.tree.json.select.JsonSelectorContext;
import walkingkooka.tree.select.NodeSelectorExpressionEvaluationContext;
import walkingkooka.tree.select.NodeSelectorExpressionEvaluationContexts;

import java.util.List;

public final class JsonNodeExpressionFunctionJsonSelectorEvalTest extends JsonNodeExpressionFunctionTestCase<JsonNodeExpressionFunctionJsonSelectorEval<JsonNodeExpressionEvaluationContext>, List<JsonNode>>
    implements JsonNodeMarshallUnmarshallContextTesting {

    @Test
    public void testApply() {
        this.applyAndCheck(
            Lists.of(
                JsonSelector.parse("/hello/hello2"),
                JsonNode.parse("{\"hello\": {\"hello2\": \"World\"}, \"ignored\": 222}")
            ),
            Lists.of(
                JsonNode.string("World")
            )
        );
    }

    @Override
    public JsonNodeExpressionFunctionJsonSelectorEval<JsonNodeExpressionEvaluationContext> createBiFunction() {
        return JsonNodeExpressionFunctionJsonSelectorEval.instance();
    }

    @Override
    public JsonNodeExpressionEvaluationContext createContext() {
        return new FakeJsonNodeExpressionEvaluationContext() {
            @Override
            public JsonSelectorContext jsonSelectorContext() {
                return new FakeJsonSelectorContext() {
                    @Override
                    public NodeSelectorExpressionEvaluationContext<JsonNode, JsonPropertyName, Name, Object> expressionEvaluationContext(final JsonNode node) {
                        return NodeSelectorExpressionEvaluationContexts.fake();
                    }
                };
            }
        };
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
            "jsonSelectorEval"
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonNodeExpressionFunctionJsonSelectorEval<JsonNodeExpressionEvaluationContext>> type() {
        return Cast.to(JsonNodeExpressionFunctionJsonSelectorEval.class);
    }
}
