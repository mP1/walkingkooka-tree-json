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

import org.junit.jupiter.api.Test;
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.ImmutableListTesting;
import walkingkooka.collect.map.Maps;

public final class JsonObjectListTest implements ImmutableListTesting<JsonObjectList, JsonNode>,
        ToStringTesting<JsonObjectList> {

    @Test
    public void testJsonObject() {
        JsonObject object = JsonNode.object();

        final JsonPropertyName first = JsonPropertyName.with("first");
        final JsonPropertyName second = JsonPropertyName.with("second");

        final JsonNode value1 = JsonNode.booleanNode(true);
        final JsonNode value2 = JsonNode.number(22);

        object = object.set(
                first,
                value1
        ).set(
                second,
                value2
        );

        this.checkEquals(
                JsonObjectList.with(
                        Maps.of(
                                first,
                                value1.setName(first),
                                second,
                                value2.setName(second)
                        )
                ),
                object.children
        );

        this.checkNotEquals(
                JsonObjectList.with(
                        Maps.of(
                                first,
                                value1,
                                second,
                                value2
                        )
                ),
                object.children,
                () -> "same name/value but values without name should not be equalb f"
        );
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createList(), "[first=true, second=false]");
    }

    @Override
    public JsonObjectList createList() {
        final JsonPropertyName first = JsonPropertyName.with("first");
        final JsonPropertyName second = JsonPropertyName.with("second");

        return JsonObjectList.with(
                Maps.of(
                        first,
                        JsonNode.booleanNode(true)
                                .setName(first),
                        second,
                        JsonNode.booleanNode(false)
                                .setName(second)
                )
        );
    }

    @Override
    public Class<JsonObjectList> type() {
        return JsonObjectList.class;
    }
}
