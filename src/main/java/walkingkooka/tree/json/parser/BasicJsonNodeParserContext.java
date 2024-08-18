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

package walkingkooka.tree.json.parser;

import walkingkooka.ToStringBuilder;
import walkingkooka.datetime.DateTimeContext;
import walkingkooka.datetime.DateTimeContextDelegator;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContextDelegator;
import walkingkooka.math.DecimalNumberContexts;

import java.math.MathContext;
import java.util.Locale;

/**
 * A {@link JsonNodeParserContext} without any functionality.
 */
final class BasicJsonNodeParserContext implements JsonNodeParserContext,
        DateTimeContextDelegator,
        DecimalNumberContextDelegator {

    /**
     * Type safe getter
     */
    static BasicJsonNodeParserContext instance() {
        return INSTANCE;
    }

    /**
     * Singleton
     */
    private final static BasicJsonNodeParserContext INSTANCE = new BasicJsonNodeParserContext();

    private BasicJsonNodeParserContext() {
        super();
        this.dateTimeContext = DateTimeContexts.locale(
                Locale.getDefault(),
                1900, // defaultYear
                50, // twoDigitYear
                () -> {
                    throw new UnsupportedOperationException();
                }
        );
        this.decimalNumberContext = DecimalNumberContexts.american(MathContext.DECIMAL64);
    }

    @Override
    public Locale locale() {
        return this.dateTimeContext.locale();
    }

    // DateTimeContextDelegator.........................................................................................

    @Override
    public DateTimeContext dateTimeContext() {
        return this.dateTimeContext;
    }

    private final DateTimeContext dateTimeContext;

    // DecimalNumberContextDelegator....................................................................................

    @Override
    public DecimalNumberContext decimalNumberContext() {
        return this.decimalNumberContext;
    }

    private final DecimalNumberContext decimalNumberContext;

    @Override
    public String toString() {
        return ToStringBuilder.empty()
                .label("decimalSeparator").value(this.decimalSeparator())
                .label("exponentSymbol").value(this.exponentSymbol())
                .label("negativeSign").value(this.negativeSign())
                .label("percentageSymbol").value(this.percentageSymbol())
                .label("positiveSign").value(this.positiveSign())
                .build();
    }
}
