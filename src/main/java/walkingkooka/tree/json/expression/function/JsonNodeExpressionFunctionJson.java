package walkingkooka.tree.json.expression.function;

import walkingkooka.Cast;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.expression.JsonNodeExpressionEvaluationContext;

import java.util.List;

/**
 * A function that returns a {@link JsonNode}, with the input parameter possibly being converted to {@link JsonNode}.
 */
final class JsonNodeExpressionFunctionJson<C extends JsonNodeExpressionEvaluationContext> extends JsonNodeExpressionFunction<C, JsonNode> {

    /**
     * Type safe getter.
     */
    static <C extends JsonNodeExpressionEvaluationContext> JsonNodeExpressionFunctionJson<C> instance() {
        return Cast.to(INSTANCE);
    }

    final static JsonNodeExpressionFunctionJson<?> INSTANCE = new JsonNodeExpressionFunctionJson<>();


    private JsonNodeExpressionFunctionJson() {
        super("json");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(JSON);

    @Override
    public Class<JsonNode> returnType() {
        return JsonNode.class;
    }

    @Override
    public JsonNode apply(final List<Object> parameters,
                          final C context) {
        checkParameterCount(parameters);
        return JSON.getOrFail(parameters, 0);
    }
}
