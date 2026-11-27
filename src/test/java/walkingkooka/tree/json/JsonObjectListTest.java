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

import java.util.Map;

public final class JsonObjectListTest implements ImmutableListTesting<JsonObjectList, JsonNode>,
    ToStringTesting {

    private final static JsonPropertyName PROPERTY_NAME1 = JsonPropertyName.with("propertyName111");
    private final static JsonPropertyName PROPERTY_NAME2 = JsonPropertyName.with("propertyName222");
    private final static JsonPropertyName PROPERTY_NAME3 = JsonPropertyName.with("propertyName333");
    private final static JsonPropertyName PROPERTY_NAME4 = JsonPropertyName.with("propertyName444");
    private final static JsonPropertyName PROPERTY_NAME5 = JsonPropertyName.with("propertyName555");

    private final static JsonNode VALUE1 = JsonNode.booleanNode(true);
    private final static JsonNode VALUE2 = JsonNode.number(222);
    private final static JsonNode VALUE3 = JsonNode.number(333);
    private final static JsonNode VALUE4 = JsonNode.number(444);
    private final static JsonNode VALUE5 = JsonNode.number(555);


    @Test
    public void testJsonObject() {
        JsonObject object = JsonNode.object();

        object = object.set(
            PROPERTY_NAME1,
            VALUE1
        ).set(
            PROPERTY_NAME2,
            VALUE2
        );

        this.checkEquals(
            JsonObjectList.with(
                Maps.of(
                    PROPERTY_NAME1,
                    VALUE1.setName(PROPERTY_NAME1),
                    PROPERTY_NAME2,
                    VALUE2.setName(PROPERTY_NAME2)
                )
            ),
            object.children
        );
    }

    // subList..........................................................................................................

    private final static Map<JsonPropertyName, JsonNode> ELEMENTS = Maps.of(
        PROPERTY_NAME1,
        VALUE1,
        PROPERTY_NAME2,
        VALUE2,
        PROPERTY_NAME3,
        VALUE3,
        PROPERTY_NAME4,
        VALUE4,
        PROPERTY_NAME5,
        VALUE5
    );

    @Test
    public void testSubList() {
        this.subListAndCheck(
            new JsonObjectList(ELEMENTS),
            0,
            2,
            new JsonObjectList(
                Maps.of(
                    PROPERTY_NAME1,
                    VALUE1,
                    PROPERTY_NAME2,
                    VALUE2
                )
            )
        );
    }

    @Test
    public void testSubList2() {
        this.subListAndCheck(
            new JsonObjectList(ELEMENTS),
            2,
            4,
            new JsonObjectList(
                Maps.of(
                    PROPERTY_NAME3,
                    VALUE3,
                    PROPERTY_NAME4,
                    VALUE4
                )
            )
        );
    }

    @Test
    public void testSubListSubList() {
        this.subListAndCheck(
            new JsonObjectList(ELEMENTS)
                .subList(
                    1,
                    4
                ), // 2, 3, 4
            1,
            3,
            new JsonObjectList(
                Maps.of(
                    PROPERTY_NAME3,
                    VALUE3,
                    PROPERTY_NAME4,
                    VALUE4
                )
            )
        );
    }

    @Override
    public JsonObjectList createList() {
        return JsonObjectList.with(
            Maps.of(
                PROPERTY_NAME1,
                VALUE1.setName(PROPERTY_NAME1),
                PROPERTY_NAME2,
                VALUE1.setName(PROPERTY_NAME2)
            )
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createList(),
            "[propertyName111=true, propertyName222=true]"
        );
    }

    // firstOrEmpty.....................................................................................................

    @Test
    public void testFirstOrEmptyWhenEmpty() {
        this.firstOrEmptyAndCheck(
            JsonObjectList.EMPTY
        );
    }

    @Test
    public void testFirstOrEmptyWhenNotEmpty() {
        final JsonNode first = JsonNode.string("111");

        this.firstOrEmptyAndCheck(
            JsonObjectList.EMPTY.concat(first),
            first
        );
    }

    // reversed.........................................................................................................

    @Test
    @Override
    public void testReversed() {
        this.checkEquals(
            JsonObjectList.EMPTY
                .concat(
                    JsonNode.booleanNode(false)
                        .setName(
                            JsonPropertyName.with("first")
                        )
                ).concat(
                    JsonNode.booleanNode(true)
                        .setName(
                            JsonPropertyName.with("second")
                        )
                ).reverse(),
            JsonObjectList.EMPTY
                .concat(
                    JsonNode.booleanNode(false)
                        .setName(
                            JsonPropertyName.with("first")
                        )
                ).concat(
                    JsonNode.booleanNode(true)
                        .setName(
                            JsonPropertyName.with("second")
                        )
                )
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonObjectList> type() {
        return JsonObjectList.class;
    }
}
