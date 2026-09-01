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
import walkingkooka.convert.BinaryNumberConverterFunctions;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.environment.EnvironmentContextTesting;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContextDelegator;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContext;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContexts;
import walkingkooka.tree.expression.convert.ExpressionNumberConverters;

import java.math.MathContext;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class JsonNodeConverterContextBasicTest implements JsonNodeConverterContextTesting2<JsonNodeConverterContextBasic>,
    ToStringTesting<JsonNodeConverterContextBasic>,
    DecimalNumberContextDelegator,
    EnvironmentContextTesting {

    private final static ExpressionNumberConverterContext CONVERTER_CONTEXT = ExpressionNumberConverterContexts.basic(
        ExpressionNumberConverters.toNumberOrExpressionNumber(
            Converters.textToNumber(
                (dnc) -> (DecimalFormat) DecimalFormat.getInstance()
            )
        ),
        BinaryNumberConverterFunctions.multiply(), // multiplier
        ConverterContexts.basic(
            false, // canNumbersHaveGroupSeparator
            Converters.JAVA_EPOCH_OFFSET,
            ',', // valueSeparator
            Converters.fake(),
            BinaryNumberConverterFunctions.fake(), // multiplier
            BINARY_TEXT_CONTEXT,
            CURRENCY_LOCALE_CONTEXT,
            DATE_TIME_CONTEXT,
            DECIMAL_NUMBER_CONTEXT
        ),
        EXPRESSION_NUMBER_KIND
    );

    @Test
    public void testWithNullConverterContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> JsonNodeConverterContextBasic.with(
                null,
                JSON_NODE_MARSHALL_UNMARSHALL_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullJsonNodeMarshallUnmarshallContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> JsonNodeConverterContextBasic.with(
                CONVERTER_CONTEXT,
                null
            )
        );
    }

    @Override
    public JsonNodeConverterContextBasic createContext() {
        return JsonNodeConverterContextBasic.with(
            CONVERTER_CONTEXT,
            JSON_NODE_MARSHALL_UNMARSHALL_CONTEXT
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
        final JsonNodeConverterContextBasic context = this.createContext();

        this.convertAndCheck(
            context,
            "123",
            ExpressionNumber.class,
            context.expressionNumberKind().create(123)
        );
    }

    @Test
    public void testMarshallThenUnmarshall() {
        final JsonNodeConverterContextBasic context = this.createContext();
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
            CONVERTER_CONTEXT + " " + JSON_NODE_MARSHALL_UNMARSHALL_CONTEXT
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonNodeConverterContextBasic> type() {
        return JsonNodeConverterContextBasic.class;
    }

    @Override
    public String typeNameSuffix() {
        return JsonNodeConverterContext.class.getSimpleName();
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
