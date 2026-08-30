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

package walkingkooka.tree.json.select;

import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.expression.JsonNodeExpressionEvaluationContext;
import walkingkooka.tree.json.select.JsonSelectorContextTesting2Test.TestJsonSelectorContext;

import java.util.Objects;

public final class JsonSelectorContextTesting2Test implements JsonSelectorContextTesting2<TestJsonSelectorContext> {
    @Override
    public TestJsonSelectorContext createContext() {
        return new TestJsonSelectorContext();
    }

    final static class TestJsonSelectorContext implements JsonSelectorContext {
        @Override
        public JsonNodeExpressionEvaluationContext jsonNodeExpressionEvaluationContext(final JsonNode node) {
            Objects.requireNonNull(node, "node");

            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }

    @Override
    public Class<TestJsonSelectorContext> type() {
        return TestJsonSelectorContext.class;
    }
}
