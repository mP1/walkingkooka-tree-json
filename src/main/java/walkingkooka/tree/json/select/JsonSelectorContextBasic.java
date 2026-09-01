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

import walkingkooka.naming.Name;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.select.NodeSelectorContext;
import walkingkooka.tree.select.NodeSelectorContextDelegator;

import java.util.Objects;

final class JsonSelectorContextBasic implements JsonSelectorContext,
    NodeSelectorContextDelegator<JsonNode, JsonPropertyName, Name, Object> {

    static JsonSelectorContextBasic with(final NodeSelectorContext<JsonNode, JsonPropertyName, Name, Object> context) {
       return new JsonSelectorContextBasic(
           Objects.requireNonNull(context, "context")
       );
    }

    private JsonSelectorContextBasic(final NodeSelectorContext<JsonNode, JsonPropertyName, Name, Object> context) {
        super();

        this.context = context;
    }

    // NodeSelectorContextDelegator.....................................................................................

    @Override
    public NodeSelectorContext<JsonNode, JsonPropertyName, Name, Object> nodeSelectorContext() {
        return this.context;
    }

    private final NodeSelectorContext<JsonNode, JsonPropertyName, Name, Object> context;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.context.toString();
    }
}
