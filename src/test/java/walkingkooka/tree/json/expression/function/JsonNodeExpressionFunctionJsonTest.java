package walkingkooka.tree.json.expression.function;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.expression.JsonNodeExpressionEvaluationContext;
import walkingkooka.tree.json.expression.JsonNodeExpressionEvaluationContexts;

public final class JsonNodeExpressionFunctionJsonTest extends JsonNodeExpressionFunctionTestCase<JsonNodeExpressionFunctionJson<JsonNodeExpressionEvaluationContext>, JsonNode> {

    @Test
    public void testApplyWithJsonNode() {
        final JsonNode json = JsonNode.parse("{\"Hello\": 1, \"World\": 2}");

        this.applyAndCheck(
            Lists.of(json),
            json
        );
    }

    @Override
    public JsonNodeExpressionFunctionJson<JsonNodeExpressionEvaluationContext> createBiFunction() {
        return JsonNodeExpressionFunctionJson.instance();
    }

    @Override
    public JsonNodeExpressionEvaluationContext createContext() {
        return JsonNodeExpressionEvaluationContexts.fake();
    }

    @Override
    public int minimumParameterCount() {
        return 1;
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createBiFunction(),
            "json"
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonNodeExpressionFunctionJson<JsonNodeExpressionEvaluationContext>> type() {
        return Cast.to(JsonNodeExpressionFunctionJson.class);
    }
}
