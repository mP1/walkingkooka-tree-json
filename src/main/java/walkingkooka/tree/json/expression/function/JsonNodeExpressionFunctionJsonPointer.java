package walkingkooka.tree.json.expression.function;

import walkingkooka.Cast;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.json.expression.JsonNodeExpressionEvaluationContext;
import walkingkooka.tree.json.pointer.JsonPointer;

import java.util.List;

/**
 * A function that returns a {@link JsonPointer}.
 */
final class JsonNodeExpressionFunctionJsonPointer<C extends JsonNodeExpressionEvaluationContext> extends JsonNodeExpressionFunction<C, JsonPointer> {

    /**
     * Type safe getter.
     */
    static <C extends JsonNodeExpressionEvaluationContext> JsonNodeExpressionFunctionJsonPointer<C> instance() {
        return Cast.to(INSTANCE);
    }

    final static JsonNodeExpressionFunctionJsonPointer<?> INSTANCE = new JsonNodeExpressionFunctionJsonPointer<>();


    private JsonNodeExpressionFunctionJsonPointer() {
        super("jsonPointer");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(POINTER);

    @Override
    public Class<JsonPointer> returnType() {
        return JsonPointer.class;
    }

    @Override
    public JsonPointer apply(final List<Object> parameters,
                             final C context) {
        checkParameterCount(parameters);
        return POINTER.getOrFail(parameters, 0);
    }
}
