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

package walkingkooka.tree.json.convert;

import org.junit.jupiter.api.Test;
import walkingkooka.ToStringTesting;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.currency.FakeCurrencyContext;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.locale.LocaleContext;
import walkingkooka.locale.LocaleContexts;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContextDelegator;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContext;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContexts;
import walkingkooka.tree.expression.convert.ExpressionNumberConverters;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContexts;
import walkingkooka.tree.json.marshall.JsonNodeMarshallUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallUnmarshallContexts;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;

import java.math.MathContext;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicJsonNodeConverterContextTest implements JsonNodeConverterContextTesting<BasicJsonNodeConverterContext>,
    ToStringTesting<BasicJsonNodeConverterContext>,
    DecimalNumberContextDelegator {

    static {
        final Locale locale = Locale.forLanguageTag("EN-AU");
        final LocaleContext localeContext = LocaleContexts.jre(locale);

        CONVERTER_CONTEXT = ExpressionNumberConverterContexts.basic(
            ExpressionNumberConverters.toNumberOrExpressionNumber(
                Converters.stringToNumber(
                    (dnc) -> (DecimalFormat) DecimalFormat.getInstance()
                )
            ),
            ConverterContexts.basic(
                false, // canNumbersHaveGroupSeparator
                Converters.JAVA_EPOCH_OFFSET,
                Indentation.SPACES2,
                LineEnding.NL,
                ',', // valueSeparator
                Converters.fake(),
                new FakeCurrencyContext() {
                    @Override
                    public Optional<Currency> currencyForLocale(final Locale locale) {
                        return Optional.of(
                            Currency.getInstance(locale)
                        );
                    }
                }.setLocaleContext(localeContext),
                DateTimeContexts.basic(
                    localeContext.dateTimeSymbolsForLocale(locale)
                        .get(),
                    locale,
                    1950,
                    50,
                    LocalDateTime::now
                ),
                DecimalNumberContexts.american(MathContext.DECIMAL32)
            ),
            ExpressionNumberKind.DEFAULT
        );
    }

    private final static ExpressionNumberConverterContext CONVERTER_CONTEXT;

    private final static JsonNodeMarshallUnmarshallContext MARSHALL_UNMARSHALL_CONTEXT = JsonNodeMarshallUnmarshallContexts.basic(
        JsonNodeMarshallContexts.basic(),
        JsonNodeUnmarshallContexts.basic(
            (String cc) -> {
                throw new UnsupportedOperationException();
            },
            (String lt) -> {
                throw new UnsupportedOperationException();
            },
            ExpressionNumberKind.DEFAULT,
            CONVERTER_CONTEXT.mathContext()
        )
    );

    @Test
    public void testWithNullConverterContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicJsonNodeConverterContext.with(
                null,
                MARSHALL_UNMARSHALL_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullJsonNodeMarshallUnmarshallContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicJsonNodeConverterContext.with(
                CONVERTER_CONTEXT,
                null
            )
        );
    }

    @Override
    public BasicJsonNodeConverterContext createContext() {
        return BasicJsonNodeConverterContext.with(
            CONVERTER_CONTEXT,
            MARSHALL_UNMARSHALL_CONTEXT
        );
    }

    @Override
    public MathContext mathContext() {
        return CONVERTER_CONTEXT.mathContext();
    }

    @Override
    public int decimalNumberDigitCount() {
        return CONVERTER_CONTEXT.decimalNumberDigitCount();
    }

    @Override
    public DecimalNumberContext decimalNumberContext() {
        return CONVERTER_CONTEXT;
    }

    @Test
    public void testConvertStringToExpressionNumber() {
        final BasicJsonNodeConverterContext context = this.createContext();

        this.convertAndCheck(
            context,
            "123",
            ExpressionNumber.class,
            context.expressionNumberKind().create(123)
        );
    }

    @Test
    public void testMarshallThenUnmarshall() {
        final BasicJsonNodeConverterContext context = this.createContext();
        final ExpressionNumber number = context.expressionNumberKind().create(12);

        this.checkEquals(
            number,
            context.unmarshallWithType(
                context.marshallWithType(number)
            )
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createContext(),
            CONVERTER_CONTEXT + " " + MARSHALL_UNMARSHALL_CONTEXT
        );
    }

    // class............................................................................................................

    @Override
    public Class<BasicJsonNodeConverterContext> type() {
        return BasicJsonNodeConverterContext.class;
    }

    @Override
    public String typeNameSuffix() {
        return JsonNodeConverterContext.class.getSimpleName();
    }
}
