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

import walkingkooka.math.DecimalNumberSymbols;
import walkingkooka.math.OptionalDecimalNumberSymbols;
import walkingkooka.tree.json.JsonNode;

final class BasicJsonMarshallerTypedOptionalDecimalNumberSymbols extends BasicJsonMarshallerTyped<OptionalDecimalNumberSymbols> {

    static BasicJsonMarshallerTypedOptionalDecimalNumberSymbols instance() {
        return new BasicJsonMarshallerTypedOptionalDecimalNumberSymbols();
    }

    private BasicJsonMarshallerTypedOptionalDecimalNumberSymbols() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<OptionalDecimalNumberSymbols> type() {
        return OptionalDecimalNumberSymbols.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(OptionalDecimalNumberSymbols.class);
    }

    @Override
    OptionalDecimalNumberSymbols unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    OptionalDecimalNumberSymbols unmarshallNonNull(final JsonNode node,
                                                   final JsonNodeUnmarshallContext context) {
        return OptionalDecimalNumberSymbols.with(
            context.unmarshallOptional(
                node,
                DecimalNumberSymbols.class
            )
        );
    }

    @Override
    JsonNode marshallNonNull(final OptionalDecimalNumberSymbols value,
                             final JsonNodeMarshallContext context) {
        return context.marshallOptional(
            value.value()
        );
    }
}
