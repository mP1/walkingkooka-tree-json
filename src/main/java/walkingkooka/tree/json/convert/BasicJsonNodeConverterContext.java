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

import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ConverterContextDelegator;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContextObjectPostProcessor;
import walkingkooka.tree.json.marshall.JsonNodeMarshallUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallUnmarshallContextDelegator;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContextPreProcessor;

import java.math.MathContext;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link JsonNodeConverterContext} that uses given {@link ExpressionNumberConverterContext}, {@link JsonNodeMarshallContext}
 * and {@link JsonNodeUnmarshallContext}. Note the {@link ExpressionNumberKind} returned for all context should be the same.
 */
final class BasicJsonNodeConverterContext implements JsonNodeConverterContext,
    JsonNodeMarshallUnmarshallContextDelegator,
    ConverterContextDelegator {

    static BasicJsonNodeConverterContext with(final ExpressionNumberConverterContext converterContext,
                                              final JsonNodeMarshallUnmarshallContext marshallUnmarshallContext) {
        return new BasicJsonNodeConverterContext(
            Objects.requireNonNull(converterContext, "converterContext"),
            Objects.requireNonNull(marshallUnmarshallContext, "marshallUnmarshallContext")
        );
    }

    private BasicJsonNodeConverterContext(final ExpressionNumberConverterContext converterContext,
                                          final JsonNodeMarshallUnmarshallContext marshallUnmarshallContext) {
        this.converterContext = converterContext;
        this.marshallUnmarshallContext = marshallUnmarshallContext;
    }

    @Override
    public JsonNodeConverterContext setObjectPostProcessor(final JsonNodeMarshallContextObjectPostProcessor processor) {
        final JsonNodeMarshallUnmarshallContext before = this.marshallUnmarshallContext;
        final JsonNodeMarshallUnmarshallContext after = before.setObjectPostProcessor(processor);

        return before.equals(after) ?
            this :
            BasicJsonNodeConverterContext.with(
                this.converterContext,
                after
            );
    }

    @Override
    public JsonNodeConverterContext setPreProcessor(final JsonNodeUnmarshallContextPreProcessor processor) {
        final JsonNodeMarshallUnmarshallContext before = this.marshallUnmarshallContext;
        final JsonNodeMarshallUnmarshallContext after = before.setPreProcessor(processor);

        return before.equals(after) ?
            this :
            BasicJsonNodeConverterContext.with(
                this.converterContext,
                after
            );
    }

    // ConverterContextDelegator........................................................................................

    @Override
    public ConverterContext converterContext() {
        return this.converterContext;
    }

    // DecimalNumberContextDelegator....................................................................................

    @Override
    public ExpressionNumberKind expressionNumberKind() {
        // prefer to source ExpressionNumberKind from ExpressionNumberConverterContext rather than JsonNodeUnmarshallContext
        return this.converterContext.expressionNumberKind();
    }

    @Override
    public MathContext mathContext() {
        return this.converterContext.mathContext();
    }

    @Override
    public DecimalNumberContext decimalNumberContext() {
        return this.converterContext;
    }

    @Override
    public Optional<Locale> localeForLanguageTag(final String languageTag) {
        return this.converterContext.localeForLanguageTag(languageTag);
    }

    private final ExpressionNumberConverterContext converterContext;

    // JsonNodeMarshallUnmarshallContext................................................................................

    @Override
    public JsonNodeMarshallUnmarshallContext jsonNodeMarshallUnmarshallContext() {
        return this.marshallUnmarshallContext;
    }

    private final JsonNodeMarshallUnmarshallContext marshallUnmarshallContext;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.converterContext + " " + this.marshallUnmarshallContext;
    }
}
