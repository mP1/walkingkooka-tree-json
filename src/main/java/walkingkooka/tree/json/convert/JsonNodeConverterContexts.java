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

import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallUnmarshallContext;

/**
 * A collection of {@link JsonNodeConverterContext}
 */
public final class JsonNodeConverterContexts implements PublicStaticHelper {

    /**
     * {@see BasicJsonNodeConverterContext}
     */
    public static JsonNodeConverterContext basic(final ExpressionNumberConverterContext converterContext,
                                                 final JsonNodeMarshallUnmarshallContext marshallUnmarshallContext) {
        return BasicJsonNodeConverterContext.with(
            converterContext,
            marshallUnmarshallContext
        );
    }

    /**
     * {@see FakeJsonNodeConverterContext}
     */
    public static JsonNodeConverterContext fake() {
        return new FakeJsonNodeConverterContext();
    }

    /**
     * Stop creation
     */
    private JsonNodeConverterContexts() {
        throw new UnsupportedOperationException();
    }
}
