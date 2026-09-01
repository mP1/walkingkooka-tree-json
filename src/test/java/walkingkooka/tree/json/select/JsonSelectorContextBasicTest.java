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

import org.junit.jupiter.api.Test;
import walkingkooka.naming.Name;
import walkingkooka.predicate.Predicates;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.select.NodeSelectorContext;
import walkingkooka.tree.select.NodeSelectorContexts;
import walkingkooka.tree.select.NodeSelectorExpressionEvaluationContexts;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class JsonSelectorContextBasicTest implements JsonSelectorContextTesting2<JsonSelectorContextBasic> {

    private final static NodeSelectorContext<JsonNode, JsonPropertyName, Name, Object> NODE_SELECTOR_CONTEXT = NodeSelectorContexts.basic(
        () -> false,
        Predicates.always(), // filter
        Function.identity(), // mapper
        (JsonNode json) -> NodeSelectorExpressionEvaluationContexts.fake(),
        JsonNode.class
    );

    @Test
    public void testWithNullNodeSelectorContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> JsonSelectorContextBasic.with(null)
        );
    }

    @Override
    public JsonSelectorContextBasic createContext() {
        return JsonSelectorContextBasic.with(NODE_SELECTOR_CONTEXT);
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createContext(),
            NODE_SELECTOR_CONTEXT.toString()
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonSelectorContextBasic> type() {
        return JsonSelectorContextBasic.class;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
