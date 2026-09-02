package walkingkooka.tree.json.expression.function;

import walkingkooka.text.HasMultiLineText;
import walkingkooka.text.MultiLineText;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.expression.JsonNodeExpressionEvaluationContext;

import java.util.List;

/**
 * A function that returns a {@link JsonNode}, with the input parameter possibly being converted to {@link JsonNode}.
 */
final class JsonNodeExpressionFunctionJsonText<C extends JsonNodeExpressionEvaluationContext> extends JsonNodeExpressionFunction<C, MultiLineText> {

    /**
     * Type safe getter.
     */
    static <C extends JsonNodeExpressionEvaluationContext> JsonNodeExpressionFunctionJsonText<C> instance() {
        return INSTANCE;
    }

    /**
     * Singleton
     */
    final static JsonNodeExpressionFunctionJsonText INSTANCE = new JsonNodeExpressionFunctionJsonText<>();


    private JsonNodeExpressionFunctionJsonText() {
        super("jsonText");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    final static ExpressionFunctionParameter<Object> VALUE = ExpressionFunctionParameterName.with("value")
        .required(Object.class)
        .setKinds(ExpressionFunctionParameterKind.EVALUATE_RESOLVE_REFERENCES);

    final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(VALUE);

    @Override
    public Class<MultiLineText> returnType() {
        return MultiLineText.class;
    }

    @Override
    public MultiLineText apply(final List<Object> parameters,
                               final C context) {
        this.checkParameterCount(parameters);

        Object value = VALUE.getOrFail(parameters, 0);

        if (false == value instanceof HasMultiLineText) {
            value = context.convertOrFail(
                value,
                JsonNode.class
            );
        }

        return value instanceof HasMultiLineText ?
            ((HasMultiLineText) value).multiLineText(context) :
            null;
    }
}
