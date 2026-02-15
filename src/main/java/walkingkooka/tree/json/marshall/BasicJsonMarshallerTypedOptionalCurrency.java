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

package walkingkooka.tree.json.marshall;

import walkingkooka.tree.json.JsonNode;
import walkingkooka.util.OptionalCurrency;

import java.util.Currency;

final class BasicJsonMarshallerTypedOptionalCurrency extends BasicJsonMarshallerTyped<OptionalCurrency> {

    static BasicJsonMarshallerTypedOptionalCurrency instance() {
        return new BasicJsonMarshallerTypedOptionalCurrency();
    }

    private BasicJsonMarshallerTypedOptionalCurrency() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<OptionalCurrency> type() {
        return OptionalCurrency.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(OptionalCurrency.class);
    }

    @Override
    OptionalCurrency unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    OptionalCurrency unmarshallNonNull(final JsonNode node,
                                       final JsonNodeUnmarshallContext context) {
        return OptionalCurrency.with(
            context.unmarshallOptional(
                node,
                Currency.class
            )
        );
    }

    @Override
    JsonNode marshallNonNull(final OptionalCurrency value,
                             final JsonNodeMarshallContext context) {
        return context.marshallOptional(value.value());
    }
}
