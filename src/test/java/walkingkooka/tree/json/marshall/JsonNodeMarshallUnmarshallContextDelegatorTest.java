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

import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.marshall.JsonNodeMarshallUnmarshallContextDelegatorTest.TestJsonNodeMarshallUnmarshallContextDelegator;

import java.math.MathContext;

public final class JsonNodeMarshallUnmarshallContextDelegatorTest implements JsonNodeMarshallUnmarshallContextTesting<TestJsonNodeMarshallUnmarshallContextDelegator> {

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }

    // JsonNodeMarshallUnmarshallContext................................................................................

    @Override
    public TestJsonNodeMarshallUnmarshallContextDelegator createContext() {
        return new TestJsonNodeMarshallUnmarshallContextDelegator();
    }

    // class............................................................................................................

    @Override
    public Class<TestJsonNodeMarshallUnmarshallContextDelegator> type() {
        return TestJsonNodeMarshallUnmarshallContextDelegator.class;
    }

    static class TestJsonNodeMarshallUnmarshallContextDelegator implements JsonNodeMarshallUnmarshallContextDelegator {

        @Override
        public JsonNodeMarshallUnmarshallContext jsonNodeMarshallUnmarshallContext() {
            return JsonNodeMarshallUnmarshallContexts.basic(
                JsonNodeMarshallContexts.basic(),
                JsonNodeUnmarshallContexts.basic(
                    ExpressionNumberKind.BIG_DECIMAL,
                    MathContext.DECIMAL64
                )
            );
        }

        @Override
        public String toString() {
            return this.jsonNodeMarshallUnmarshallContext().toString();
        }
    }
}
