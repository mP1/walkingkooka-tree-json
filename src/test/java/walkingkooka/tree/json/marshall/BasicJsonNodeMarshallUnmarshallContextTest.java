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
import walkingkooka.ToStringTesting;
import walkingkooka.tree.expression.ExpressionNumberKind;

import java.math.MathContext;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicJsonNodeMarshallUnmarshallContextTest implements JsonNodeMarshallUnmarshallContextTesting<BasicJsonNodeMarshallUnmarshallContext>,
    ToStringTesting<BasicJsonNodeMarshallUnmarshallContext> {

    @Test
    public void testWithNullJsonNodeMarshallContextFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> BasicJsonNodeMarshallUnmarshallContext.with(
                null,
                JsonNodeUnmarshallContexts.fake()
            )
        );
    }

    @Test
    public void testWithNullJsonNodeUnmarshallContextFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> BasicJsonNodeMarshallUnmarshallContext.with(
                JsonNodeMarshallContexts.fake(),
                null
            )
        );
    }

    @Override
    public BasicJsonNodeMarshallUnmarshallContext createContext() {
        return BasicJsonNodeMarshallUnmarshallContext.with(
            JsonNodeMarshallContexts.basic(),
            JsonNodeUnmarshallContexts.basic(
                (String cc) -> Optional.ofNullable(
                    Currency.getInstance(cc)
                ),
                (lt) -> Optional.of(
                    Locale.forLanguageTag(lt)
                ),
                ExpressionNumberKind.BIG_DECIMAL,
                MathContext.DECIMAL32
            )
        );
    }

    // ToString.........................................................................................................

    @Test
    public void testToString() {
        final JsonNodeMarshallContext marshallContext = JsonNodeMarshallContexts.fake();
        final JsonNodeUnmarshallContext unmarshallContext = JsonNodeUnmarshallContexts.fake();

        this.toStringAndCheck(
            BasicJsonNodeMarshallUnmarshallContext.with(
                marshallContext,
                unmarshallContext
            ),
            marshallContext + " " + unmarshallContext
        );
    }

    // class............................................................................................................

    @Override
    public Class<BasicJsonNodeMarshallUnmarshallContext> type() {
        return BasicJsonNodeMarshallUnmarshallContext.class;
    }
}
