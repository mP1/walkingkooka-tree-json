package walkingkooka.tree.json.expression.function;

import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.expression.ExpressionPurityContext;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.expression.JsonNodeExpressionEvaluationContext;

import java.util.Optional;

abstract class JsonNodeExpressionFunction<C extends JsonNodeExpressionEvaluationContext, T> implements ExpressionFunction<T, C> {

    JsonNodeExpressionFunction(final String name) {
        super();

        this.name = Optional.of(
            ExpressionFunctionName.with(name)
        );
    }

    @Override
    public final Optional<ExpressionFunctionName> name() {
        return this.name;
    }

    private final Optional<ExpressionFunctionName> name;

    @Override
    public final boolean isPure(final ExpressionPurityContext expressionPurityContext) {
        return true; // JsonNode functions are always pure
    }

    final static ExpressionFunctionParameter<JsonNode> JSON = ExpressionFunctionParameterName.with("json")
        .required(JsonNode.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);

    // Object...........................................................................................................

    @Override
    public final String toString() {
        return this.name.get()
            .toString();
    }
}
