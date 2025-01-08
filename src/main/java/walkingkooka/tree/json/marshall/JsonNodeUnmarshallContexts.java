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

import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.tree.expression.ExpressionNumberKind;

import java.math.MathContext;

/**
 * Collection of static factory methods for numerous {@link JsonNodeUnmarshallContext}.
 */
public final class JsonNodeUnmarshallContexts implements PublicStaticHelper {

    /**
     * {@see BasicJsonNodeUnmarshallContext}
     */
    public static JsonNodeUnmarshallContext basic(final ExpressionNumberKind kind,
                                                  final MathContext mathContext) {
        return BasicJsonNodeUnmarshallContext.with(
            kind,
            mathContext
        );
    }

    /**
     * {@see FakeJsonNodeUnmarshallContext}
     */
    public static JsonNodeUnmarshallContext fake() {
        return new FakeJsonNodeUnmarshallContext();
    }

    /**
     * Stops creation
     */
    private JsonNodeUnmarshallContexts() {
        throw new UnsupportedOperationException();
    }
}
