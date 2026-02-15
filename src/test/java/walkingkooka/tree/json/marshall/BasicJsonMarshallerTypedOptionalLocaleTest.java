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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.util.OptionalLocale;

import java.util.Locale;
import java.util.Optional;

public final class BasicJsonMarshallerTypedOptionalLocaleTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedOptionalLocale, OptionalLocale> {

    @Test
    public void testUnmarshallBooleanFails() {
        this.unmarshallFailed(
            JsonNode.booleanNode(true),
            ClassCastException.class
        );
    }

    @Test
    public void testUnmarshallNumberFails() {
        this.unmarshallFailed(
            JsonNode.number(123),
            ClassCastException.class
        );
    }

    @Test
    public void testMarshallEmptyOptionalLocale() {
        this.marshallAndCheck(
            OptionalLocale.EMPTY,
            JsonNode.nullNode()
        );
    }

    @Test
    public void testMarshall2() {
        this.marshallAndCheck(
            this.value(),
            JsonNode.string("en-AU")
        );
    }

    @Override
    BasicJsonMarshallerTypedOptionalLocale marshaller() {
        return BasicJsonMarshallerTypedOptionalLocale.instance();
    }

    @Override
    OptionalLocale value() {
        return LOCALE;
    }

    @Override
    JsonNode node() {
        return JsonNode.string("en-AU");
    }

    private final static OptionalLocale LOCALE = OptionalLocale.with(
        Optional.of(
            Locale.forLanguageTag("en-AU")
        )
    );

    @SuppressWarnings("OptionalLocaleAssignedToNull")
    @Override
    OptionalLocale jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "optional-locale";
    }

    @Override
    Class<OptionalLocale> marshallerType() {
        return Cast.to(OptionalLocale.class);
    }

    @Override
    public Class<BasicJsonMarshallerTypedOptionalLocale> type() {
        return BasicJsonMarshallerTypedOptionalLocale.class;
    }
}
