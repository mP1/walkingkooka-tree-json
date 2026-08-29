package walkingkooka.tree.json.expression.function;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.Either;
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContexts;
import walkingkooka.tree.json.convert.JsonNodeConverterContext;
import walkingkooka.tree.json.convert.JsonNodeConverterContexts;
import walkingkooka.tree.json.convert.JsonNodeConverters;
import walkingkooka.tree.json.expression.FakeJsonNodeExpressionEvaluationContext;
import walkingkooka.tree.json.expression.JsonNodeExpressionEvaluationContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallUnmarshallContextTesting;
import walkingkooka.tree.json.pointer.JsonPointer;

public final class JsonNodeExpressionFunctionJsonPointerTest extends JsonNodeExpressionFunctionTestCase<JsonNodeExpressionFunctionJsonPointer<JsonNodeExpressionEvaluationContext>, JsonPointer>
    implements JsonNodeMarshallUnmarshallContextTesting {

    private final static JsonNodeConverterContext CONVERTER_CONTEXT = JsonNodeConverterContexts.basic(
        ExpressionNumberConverterContexts.fake(),
        JSON_NODE_MARSHALL_UNMARSHALL_CONTEXT
    );

    private final static JsonNodeExpressionEvaluationContext JSON_NODE_EXPRESSION_EVALUATION_CONTEXT = new FakeJsonNodeExpressionEvaluationContext() {

        @Override
        public boolean canConvert(final Object value,
                                  final Class<?> type) {
            return this.converter.canConvert(
                value,
                type,
                CONVERTER_CONTEXT
            );
        }

        @Override
        public <T> Either<T, String> convert(final Object value,
                                             final Class<T> type) {
            return this.converter.convert(
                value,
                type,
                CONVERTER_CONTEXT
            );
        }

        private final Converter<JsonNodeConverterContext> converter = Converters.collection(
            Lists.of(
                Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString(),
                JsonNodeConverters.textToJsonPointer()
            )
        );
    };

    @Test
    public void testApplyWithJsonPointer() {
        final JsonPointer pointer = JsonPointer.parse("/Hello");

        this.applyAndCheck(
            Lists.of(pointer),
            pointer
        );
    }

    @Override
    public JsonNodeExpressionFunctionJsonPointer<JsonNodeExpressionEvaluationContext> createBiFunction() {
        return JsonNodeExpressionFunctionJsonPointer.instance();
    }

    @Override
    public JsonNodeExpressionEvaluationContext createContext() {
        return JSON_NODE_EXPRESSION_EVALUATION_CONTEXT;
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
            "jsonPointer"
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonNodeExpressionFunctionJsonPointer<JsonNodeExpressionEvaluationContext>> type() {
        return Cast.to(JsonNodeExpressionFunctionJsonPointer.class);
    }
}
