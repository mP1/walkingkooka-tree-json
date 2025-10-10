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

package walkingkooka.tree.json;

import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContexts;
import walkingkooka.tree.json.marshall.JsonNodeMarshallUnmarshallContexts;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;

import java.math.MathContext;

/**
 * This should be called before anything else to trigger registration of all default JSON marshall/unmarshallers
 */
public final class TreeJsonStartup implements PublicStaticHelper {

    static {
        JsonNodeMarshallUnmarshallContexts.basic(
            JsonNodeMarshallContexts.basic(),
            JsonNodeUnmarshallContexts.basic(
                ExpressionNumberKind.BIG_DECIMAL,
                MathContext.DECIMAL32
            )
        );
    }

    public static void init() {
        // trigger static initializer
    }

    private TreeJsonStartup() {
        throw new UnsupportedOperationException();
    }
}
