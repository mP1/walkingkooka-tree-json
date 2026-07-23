package walkingkooka.tree.json.expression.function;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.Either;
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.BinaryNumberConverterFunctions;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.currency.CurrencyLocaleContextTesting;
import walkingkooka.datetime.DateTimeContextTesting;
import walkingkooka.math.DecimalNumberContextTesting;
import walkingkooka.text.BinaryTextContextTesting;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.HasExpressionNumberKindTesting;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContexts;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.convert.JsonNodeConverterContext;
import walkingkooka.tree.json.convert.JsonNodeConverterContexts;
import walkingkooka.tree.json.convert.JsonNodeConverters;
import walkingkooka.tree.json.expression.FakeJsonNodeExpressionEvaluationContext;
import walkingkooka.tree.json.expression.JsonNodeExpressionEvaluationContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallUnmarshallContextTesting;

public final class JsonNodeExpressionFunctionJsonTextTest extends JsonNodeExpressionFunctionTestCase<JsonNodeExpressionFunctionJsonText<JsonNodeExpressionEvaluationContext>, String>
    implements BinaryTextContextTesting,
    CurrencyLocaleContextTesting,
    DateTimeContextTesting,
    DecimalNumberContextTesting,
    HasExpressionNumberKindTesting,
    JsonNodeMarshallUnmarshallContextTesting {

    private final static JsonNodeConverterContext CONVERTER_CONTEXT = JsonNodeConverterContexts.basic(
        ExpressionNumberConverterContexts.basic(
            Converters.fake(),
            BinaryNumberConverterFunctions.multiply(), // multiplier
            ConverterContexts.basic(
                false, // canNumbersHaveGroupSeparator
                0, // dateOffset
                ',', // valueSeparator
                Converters.fake(),
                BinaryNumberConverterFunctions.fake(), // multiplier
                BINARY_TEXT_CONTEXT,
                CURRENCY_LOCALE_CONTEXT,
                DATE_TIME_CONTEXT,
                DECIMAL_NUMBER_CONTEXT
            ),
            EXPRESSION_NUMBER_KIND
        ),
        JSON_NODE_MARSHALL_UNMARSHALL_CONTEXT
    );

    private final static JsonNodeExpressionEvaluationContext CONTEXT = new FakeJsonNodeExpressionEvaluationContext() {

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
                JsonNodeConverters.toJsonNode()
            )
        );

        @Override
        public Indentation indentation() {
            return JsonNodeExpressionFunctionJsonTextTest.INDENTATION;
        }

        @Override
        public LineEnding lineEnding() {
            return JsonNodeExpressionFunctionJsonTextTest.LINE_ENDING;
        }
    };

    @Test
    public void testApplyWithNull() {
        this.applyAndCheck(
            Lists.of((Object)null),
            "null"
        );
    }

    @Test
    public void testApplyWithDecimalNumberSymbols() {
        this.applyAndCheck(
            Lists.of(DECIMAL_NUMBER_SYMBOLS),
            JSON_NODE_MARSHALL_CONTEXT.marshall(DECIMAL_NUMBER_SYMBOLS)
                .toJsonText(CONTEXT)
        );
    }

    @Test
    public void testApplyWithJsonNode() {
        final JsonNode json = JsonNode.parse("{\"Hello\": 1, \"World\": 2}");

        this.applyAndCheck(
            Lists.of(json),
            json.toJsonText(CONTEXT)
        );
    }

    @Test
    public void testApplyWithString() {
        final String string = "Hello";

        this.applyAndCheck(
            Lists.of(string),
            JsonNode.string(string)
                .toJsonText(CONTEXT)
        );
    }

    @Override
    public JsonNodeExpressionFunctionJsonText<JsonNodeExpressionEvaluationContext> createBiFunction() {
        return JsonNodeExpressionFunctionJsonText.instance();
    }

    @Override
    public JsonNodeExpressionEvaluationContext createContext() {
        return CONTEXT;
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
            "jsonText"
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonNodeExpressionFunctionJsonText<JsonNodeExpressionEvaluationContext>> type() {
        return Cast.to(JsonNodeExpressionFunctionJsonText.class);
    }
}
