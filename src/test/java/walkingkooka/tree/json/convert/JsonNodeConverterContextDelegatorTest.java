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

import walkingkooka.convert.BinaryNumberConverterFunctions;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.environment.EnvironmentContextTesting;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContextDelegator;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContexts;
import walkingkooka.tree.json.convert.JsonNodeConverterContextDelegatorTest.TestJsonNodeConverterContextDelegator;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContextObjectPostProcessor;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContextPreProcessor;

import java.math.MathContext;
import java.util.Objects;

public final class JsonNodeConverterContextDelegatorTest implements JsonNodeConverterContextTesting2<TestJsonNodeConverterContextDelegator>,
    DecimalNumberContextDelegator,
    EnvironmentContextTesting {

    @Override
    public void testSetObjectPostProcessor() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetObjectPostProcessorSame() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetPreProcessorNullFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetPreProcessor() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetPreProcessorSame() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TestJsonNodeConverterContextDelegator createContext() {
        return new TestJsonNodeConverterContextDelegator();
    }

    @Override
    public int decimalNumberDigitCount() {
        return DECIMAL_NUMBER_CONTEXT.decimalNumberDigitCount();
    }

    @Override
    public DecimalNumberContext decimalNumberContext() {
        return DECIMAL_NUMBER_CONTEXT;
    }

    @Override
    public MathContext mathContext() {
        return MATH_CONTEXT;
    }

    // class............................................................................................................

    @Override
    public Class<TestJsonNodeConverterContextDelegator> type() {
        return TestJsonNodeConverterContextDelegator.class;
    }

    static class TestJsonNodeConverterContextDelegator implements JsonNodeConverterContextDelegator {

        @Override
        public TestJsonNodeConverterContextDelegator setObjectPostProcessor(final JsonNodeMarshallContextObjectPostProcessor processor) {
            Objects.requireNonNull(processor, "processor");
            throw new UnsupportedOperationException();
        }

        @Override
        public TestJsonNodeConverterContextDelegator setPreProcessor(final JsonNodeUnmarshallContextPreProcessor processor) {
            throw new UnsupportedOperationException();
        }

        @Override
        public JsonNodeConverterContext jsonNodeConverterContext() {
            return JsonNodeConverterContexts.basic(
                ENVIRONMENT_CONTEXT, // CanParseEnvironmentValueName
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
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
