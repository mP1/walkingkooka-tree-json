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

import walkingkooka.InvalidCharacterException;
import walkingkooka.ToStringBuilder;
import walkingkooka.datetime.DateTimeContext;
import walkingkooka.datetime.DateTimeContextDelegator;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContextDelegator;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.cursor.parser.InvalidCharacterExceptionFactory;
import walkingkooka.text.cursor.parser.Parser;

import java.math.MathContext;
import java.text.DateFormatSymbols;
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

        final Locale locale = Locale.getDefault();

        this.dateTimeContext = DateTimeContexts.basic(
            DateTimeSymbols.fromDateFormatSymbols(
                new DateFormatSymbols(locale)
            ),
            locale,
            1900, // defaultYear
            50, // twoDigitYear
            () -> {
                throw new UnsupportedOperationException();
            }
        );
        this.decimalNumberContext = DecimalNumberContexts.american(MathContext.DECIMAL64);
    }

    @Override
    public boolean isGroupSeparatorWithinNumbersSupported() {
        return false;
    }

    @Override
    public InvalidCharacterException invalidCharacterException(final Parser<?> parser,
                                                               final TextCursor cursor) {
        return InvalidCharacterExceptionFactory.COLUMN_AND_LINE_EXPECTED.apply(
            parser,
            cursor
        );
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
            .label("percentSymbol").value(this.percentSymbol())
            .label("positiveSign").value(this.positiveSign())
            .build();
    }
}
