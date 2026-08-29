package walkingkooka.tree.json.expression.function;

import walkingkooka.Cast;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;
import walkingkooka.tree.json.expression.JsonNodeExpressionEvaluationContext;
import walkingkooka.tree.json.select.JsonSelector;

import java.util.List;

/**
 * A function that returns a {@link JsonSelector}.
 */
final class JsonNodeExpressionFunctionJsonSelector<C extends JsonNodeExpressionEvaluationContext> extends JsonNodeExpressionFunction<C, JsonSelector> {

    /**
     * Type safe getter.
     */
    static <C extends JsonNodeExpressionEvaluationContext> JsonNodeExpressionFunctionJsonSelector<C> instance() {
        return Cast.to(INSTANCE);
    }

    final static JsonNodeExpressionFunctionJsonSelector<?> INSTANCE = new JsonNodeExpressionFunctionJsonSelector<>();


    private JsonNodeExpressionFunctionJsonSelector() {
        super("jsonSelector");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    final static ExpressionFunctionParameter<JsonSelector> SELECTOR = ExpressionFunctionParameterName.with("selector")
        .required(JsonSelector.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);

    final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(SELECTOR);

    @Override
    public Class<JsonSelector> returnType() {
        return JsonSelector.class;
    }

    @Override
    public JsonSelector apply(final List<Object> parameters,
                              final C context) {
        checkParameterCount(parameters);
        return SELECTOR.getOrFail(parameters, 0);
    }
}
