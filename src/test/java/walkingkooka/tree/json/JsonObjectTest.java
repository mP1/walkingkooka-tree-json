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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.MapTesting;
import walkingkooka.collect.map.Maps;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.Printers;
import walkingkooka.visit.Visiting;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class JsonObjectTest extends JsonParentNodeTestCase<JsonObject, JsonObjectList>
        implements MapTesting<Map<JsonPropertyName, JsonNode>, JsonPropertyName, JsonNode> {

    private final static String KEY1 = "key1";
    private final static String KEY2 = "key2";
    private final static String KEY3 = "key3";
    private final static String KEY4 = "key4";

    @Override
    public void testAppendChild2() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testRemoveChildFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testRemoveChildLast() {
        throw new UnsupportedOperationException();
    }

    @Test
    @Override
    public void testSetDifferentChildren() {
        final JsonObject parent = JsonNode.object();

        final JsonString child1 = JsonNode.string("a1");
        final JsonNumber child2 = JsonNode.number(2);

        final JsonNode parent1 = this.setChildrenAndCheck(parent, child1, child2);

        final JsonObject child3 = JsonNode.object();
        final JsonNode parent2 = this.setChildrenAndCheck(parent1, child3);

        this.childCountCheck(parent2, child3);
    }

    @Test
    public void testSetChildrenSame() {
        final JsonPropertyName key1 = this.key1();
        final JsonString value1 = this.value1();
        final JsonObject empty = JsonNode.object();

        final JsonObject object = empty.set(key1, value1);
        this.childrenCheck(object);
        this.getAndCheck(object, key1, VALUE1);

        assertSame(object, object.setChildren(object.children()));
    }

    @Test
    public void testSetChildrenEquivalent() {
        final JsonPropertyName key1 = this.key1();
        final JsonString value1 = this.value1();
        final JsonObject empty = JsonNode.object();

        final JsonObject object = empty.set(key1, value1);
        this.childrenCheck(object);
        this.getAndCheck(object, key1, VALUE1);

        assertSame(object, object.setChildren(Lists.of(value1.setName(key1))));
    }

    @Test
    public void testSetChildrenSameDifferentOrder() {
        final JsonPropertyName key1 = this.key1();
        final JsonString value1 = this.value1();

        final JsonPropertyName key2 = this.key2();
        final JsonString value2 = this.value2();


        final JsonObject object = JsonNode.object()
                .set(key1, value1)
                .set(key2, value2);
        this.childrenCheck(object);
        this.getAndCheck(object, key1, VALUE1);

        final JsonObject object2 = JsonNode.object()
                .set(key2, value2)
                .set(key1, value1);
        this.childrenCheck(object);
        this.getAndCheck(object, key2, VALUE2);

        final List<JsonNode> differentOrder = Lists.array();
        differentOrder.addAll(object2.children());

        assertSame(object, object.setChildren(differentOrder));
    }

    // set and get

    @Test
    public void testGetAbsent() {
        this.checkEquals(Optional.empty(), this.createJsonNode().get(key1()));
    }

    @Test
    public void testGetOrFailFails() {
        assertThrows(IllegalArgumentException.class, () -> this.createJsonNode().getOrFail(JsonPropertyName.with("unknown-property")));
    }

    @Test
    public void testSetAndGet() {
        final JsonPropertyName key1 = this.key1();
        final JsonString value1 = this.value1();
        final JsonObject empty = JsonNode.object();

        final JsonObject object = empty.set(key1, value1);
        this.childrenCheck(object);
        this.childCountCheck(object, 1);
        this.getAndCheck(object, key1, VALUE1);

        // verify originals were not mutated.
        this.parentMissingCheck(value1);

        this.childCountCheck(empty, 0);
    }

    @Test
    public void testGetAbsent2() {
        final JsonPropertyName key1 = this.key1();
        final JsonString value1 = this.value1();
        final JsonObject object = JsonNode.object()
                .set(key1, value1);

        this.checkEquals(Optional.empty(), object.get(key2()));
    }

    @Test
    public void testSetNullNameFails() {
        assertThrows(NullPointerException.class, () -> JsonNode.object().set(
                null,
                this.value1()));
    }

    @Test
    public void testSetNullValueFails() {
        assertThrows(NullPointerException.class, () -> JsonNode.object().set(
                this.key1(),
                null));
    }

    @Test
    public void testSetSame() {
        final JsonPropertyName key1 = this.key1();
        final JsonString value1 = this.value1();
        final JsonObject empty = JsonNode.object();

        final JsonObject object = empty.set(key1, value1);
        this.childrenCheck(object);
        this.getAndCheck(object, key1, VALUE1);

        assertSame(object, object.set(key1, value1));
    }

    @Test
    public void testSetAndGet2() {
        final JsonPropertyName key1 = this.key1();
        final JsonString value1 = this.value1();

        final JsonPropertyName key2 = this.key2();
        final JsonString value2 = this.value2();

        final JsonObject empty = JsonNode.object();

        final JsonObject object = empty.set(key1, value1)
                .set(key2, value2);
        this.childrenCheck(object);
        this.childCountCheck(object, 2);
        this.getAndCheck(object, key1, VALUE1);
        this.getAndCheck(object, key2, VALUE2);

        // verify originals were not mutated.
        this.parentMissingCheck(value1);
        this.parentMissingCheck(value2);

        this.childCountCheck(empty, 0);
    }

    @Test
    public void testSetReplacesAndGet() {
        final JsonPropertyName key1 = this.key1();
        final JsonString value1 = this.value1();

        final JsonPropertyName key2 = this.key2();
        final JsonString value2 = this.value2();
        final JsonString value3 = this.value3();

        final JsonObject empty = JsonNode.object();

        final JsonObject object = empty.set(key1, value1)
                .set(key2, value2)
                .set(key1, value3);

        this.childrenCheck(object);
        this.childCountCheck(object, 2);
        this.getAndCheck(object, key1, VALUE3);
        this.getAndCheck(object, key2, VALUE2);

        // verify originals were not mutated.
        this.parentMissingCheck(value1);
        this.parentMissingCheck(value2);

        this.childCountCheck(empty, 0);
    }

    @Test
    public void testSetReplacesAndGet2() {
        final JsonPropertyName key1 = this.key1();
        final JsonString value1 = this.value1();

        final JsonPropertyName key2 = this.key2();
        final JsonString value2 = this.value2();
        final JsonString value3 = this.value3();

        final JsonObject empty = JsonNode.object();

        final JsonObject object = empty.set(key1, value1)
                .set(key2, value2)
                .set(key2, value3);

        this.childrenCheck(object);
        this.childCountCheck(object, 2);
        this.getAndCheck(object, key1, VALUE1);
        this.getAndCheck(object, key2, VALUE3);

        // verify originals were not mutated.
        this.parentMissingCheck(value1);
        this.parentMissingCheck(value2);

        this.childCountCheck(empty, 0);
    }

    @Test
    public void testSetReplacesAndGet3() {
        final JsonPropertyName key1 = this.key1();
        final JsonString value1 = this.value1();

        final JsonPropertyName key2 = this.key2();
        final JsonString value2 = this.value2();

        final JsonPropertyName key3 = this.key3();
        final JsonString value3 = this.value3();

        final JsonString value4 = this.value4();

        final JsonObject empty = JsonNode.object();

        final JsonObject object = empty.set(key1, value1)
                .set(key2, value2)
                .set(key3, value3)
                .set(key3, value4);

        this.childrenCheck(object);
        this.childCountCheck(object, 3);
        this.getAndCheck(object, key1, VALUE1);
        this.getAndCheck(object, key2, VALUE2);
        this.getAndCheck(object, key3, VALUE4);

        // verify originals were not mutated.
        this.parentMissingCheck(value1);
        this.parentMissingCheck(value2);
        this.parentMissingCheck(value3);
        this.parentMissingCheck(value4);

        this.childCountCheck(empty, 0);
    }

    @Test
    public void testSetChildrenSameKeyDifferentValueType() {
        final JsonPropertyName key1 = this.key1();
        final JsonNode value1 = this.value1();

        final JsonObject object = JsonNode.object()
                .set(key1, value1);

        final JsonNode value2 = JsonNode.object();
        final JsonObject object2 = JsonNode.object()
                .set(key1, value2);

        assertNotSame(object, object2);
    }

    private void getAndCheck(final JsonObject object, final JsonPropertyName key, final String value) {
        final Optional<JsonNode> got = object.get(key);
        this.checkNotEquals(Optional.empty(), got, "expected value for key " + key);

        // JsonString retrieved will include the key component so a new JsonString cant be created and assertEqual'd.
        this.checkEquals(value,
                object.get(key).get().cast(JsonString.class).value(),
                "incorrect string value for get key=" + key);

        this.checkEquals(value,
                object.getOrFail(key).cast(JsonString.class).value(),
                "incorrect JsonNode for getOrFail " + key);
    }

    // remove

    @Test
    public void testRemoveNullKeyFails() {
        assertThrows(NullPointerException.class, () -> this.createJsonNode().remove(null));
    }

    @Test
    public void testRemoveUnknownKey() {
        final JsonObject object = this.createJsonNode();
        assertSame(object, object.remove(key1()));
    }

    @Test
    public void testRemove() {
        final JsonPropertyName key1 = this.key1();
        final JsonString value1 = this.value1();

        final JsonPropertyName key2 = this.key2();
        final JsonString value2 = this.value2();

        final JsonObject empty = JsonNode.object();

        final JsonObject object = empty.set(key1, value1)
                .set(key2, value2)
                .remove(key1);
        this.childrenCheck(object);
        this.childCountCheck(object, 1);

        this.getAndCheck(object, key2, VALUE2);

        // verify originals were not mutated.
        this.parentMissingCheck(value1);
        this.parentMissingCheck(value2);

        this.childCountCheck(empty, 0);
    }

    @Test
    public void testRemove2() {
        final JsonPropertyName key1 = this.key1();
        final JsonString value1 = this.value1();

        final JsonPropertyName key2 = this.key2();
        final JsonString value2 = this.value2();

        final JsonObject empty = JsonNode.object();

        final JsonObject object = empty.set(key1, value1)
                .set(key2, value2)
                .remove(key1)
                .remove(key2);
        this.childrenCheck(object);
        this.childCountCheck(object, 0);

        // verify originals were not mutated.
        this.parentMissingCheck(value1);
        this.parentMissingCheck(value2);

        this.childCountCheck(empty, 0);
    }

    @Test
    public void testChildReplaced() {
        final JsonPropertyName key1 = this.key1();
        final JsonPropertyName key2 = this.key2();
        final JsonPropertyName key3 = this.key3();

        final JsonObject root = JsonNode.object()
                .set(key2, this.value2())
                .set(key3, JsonNode.object()
                        .set(key1, this.value1()));
        this.childCountCheck(root, 2);

        final JsonObject nested = root.get(key3).get().cast(JsonObject.class);
        final JsonObject updated = nested.set(key1, value3());
        final JsonObject updatedRoot = updated.root().cast(JsonObject.class);

        this.childCountCheck(updatedRoot, 2);
        this.getAndCheck(updatedRoot, key2, VALUE2);
    }

    @Test
    public void testReplaceChild() {
        final JsonPropertyName key1 = this.key1();

        final JsonObject root = JsonNode.object()
                .set(key1, this.value1());
        this.childCountCheck(root, 1);

        final JsonPropertyName key2 = this.key2();
        final JsonString value2 = this.value2().setName(key2);
        final JsonObject updated = root.replaceChild(root.get(key1).get(), value2)
                .cast(JsonObject.class);

        this.childCountCheck(updated, 1);
        this.getAndCheck(updated, key2, VALUE2);
    }

    @Test
    public void testContainsAbsent() {
        this.containsAndCheck(JsonNode.object()
                        .set(key1(), this.value1()),
                JsonPropertyName.with("different-prop"),
                false);
    }

    @Test
    public void testContainsPresent() {
        final JsonPropertyName key = this.key1();
        this.containsAndCheck(JsonNode.object()
                        .set(key1(), this.value1())
                        .set(key, this.value2()),
                key,
                true);
    }

    private void containsAndCheck(final JsonObject object, final JsonPropertyName name, final boolean contains) {
        this.checkEquals(contains, object.contains(name), () -> object + " contains " + name);
    }

    // replace ....................................................................................................

    @Test
    public void testReplaceRoot() {
        this.replaceAndCheck(JsonNode.object()
                        .set(this.key1(), this.value1()),
                JsonNode.object()
                        .set(this.key2(), this.value2()));
    }

    @Test
    public void testReplace() {
        final JsonObject root = JsonNode.object()
                .set(this.key1(), this.value1());

        final JsonObject value2 = JsonNode.object()
                .set(this.key3(), this.value3());

        final JsonObject replacement2 = JsonNode.object()
                .set(this.key4(), this.value4());

        final JsonPropertyName key2 = this.key2();

        this.replaceAndCheck(root.set(key2, value2).getOrFail(key2),
                replacement2,
                root.set(key2, replacement2).getOrFail(key2));
    }

    // merge............................................................................................................

    @Test
    public void testMergeWithNullFails() {
        assertThrows(
                NullPointerException.class,
                () -> JsonNode.object().merge(null)
        );
    }

    @Test
    public void testMergeWithEmptyObject() {
        final JsonObject object = JsonNode.object()
                .set(JsonPropertyName.with("a"), JsonNode.number(123.5));

        assertSame(
                object,
                object.merge(JsonNode.object())
        );
    }

    @Test
    public void testMergeSame() {
        final JsonObject object = JsonNode.object()
                .set(JsonPropertyName.with("a"), JsonNode.number(123.5));

        assertSame(
                object,
                object.merge(object)
        );
    }

    @Test
    public void testMerge() {
        final JsonPropertyName a = JsonPropertyName.with("a");
        final JsonString string = JsonNode.string("abc");
        final JsonPropertyName b = JsonPropertyName.with("b");
        final JsonNumber number = JsonNode.number(123);

        this.mergeAndCheck(
                JsonNode.object()
                        .set(a, string),
                JsonNode.object()
                        .set(b, number),
                JsonNode.object()
                        .set(a, string)
                        .set(b, number)
        );
    }

    @Test
    public void testMergeReplaced() {
        final JsonPropertyName a = JsonPropertyName.with("a");
        final JsonString string = JsonNode.string("abc");

        final JsonPropertyName b = JsonPropertyName.with("b");
        final JsonNumber number = JsonNode.number(123);

        final JsonPropertyName c = JsonPropertyName.with("b");
        final JsonBoolean bool = JsonNode.booleanNode(true);

        this.mergeAndCheck(
                JsonNode.object()
                        .set(a, string)
                        .set(b, number),
                JsonNode.object()
                        .set(b, number)
                        .set(c, bool),
                JsonNode.object()
                        .set(a, string)
                        .set(b, number)
                        .set(c, bool)
        );
    }

    private void mergeAndCheck(final JsonObject before,
                               final JsonObject merge,
                               final JsonObject expected) {
        this.checkEquals(
                expected,
                before.merge(merge),
                before + " merge " + merge
        );
    }

    // asMap............................................................................................................

    @Test
    public void testMapWhenEmpty() {
        this.checkEqualsAndHashCode(Maps.empty(), JsonNode.object().asMap());
    }

    @Test
    public void testMapWhenReadonly() {
        assertThrows(UnsupportedOperationException.class, () -> JsonNode.object().asMap().put(this.key1(), this.value1()));
    }

    @Test
    public void testMapWhenReadonly2() {
        final JsonPropertyName key1 = this.key1();
        final JsonObject value1 = JsonNode.object()
                .set(key1, this.value1());

        assertThrows(UnsupportedOperationException.class, () -> value1.asMap().remove(key1));
    }

    @Test
    public void testMapContainsKey() {
        final JsonPropertyName key1 = key1();
        final JsonNode value1 = this.value1();

        final JsonObject object = JsonNode.object()
                .set(key1, value1)
                .set(this.key2(), this.value2());
        this.containsKeyAndCheck(object.asMap(), key1);
    }

    @Test
    public void testMapContainsKeyAbsent() {
        final JsonPropertyName key1 = key1();
        final JsonNode value1 = this.value1();

        final JsonObject object = JsonNode.object()
                .set(key1, value1)
                .set(this.key2(), this.value2());
        this.containsKeyAndCheckAbsent(object.asMap(), JsonPropertyName.with("absent-property"));
    }

    @Test
    public void testMapContainsValue() {
        final JsonPropertyName key1 = key1();
        final JsonNode value1 = this.value1();

        final JsonObject object = JsonNode.object()
                .set(key1, value1)
                .set(this.key2(), this.value2());
        this.containsValueAndCheck(object.asMap(),
                object.getOrFail(key1));
    }

    @Test
    public void testMapContainsValueAbsent() {
        final JsonPropertyName key1 = key1();
        final JsonNode value1 = this.value1();

        final JsonObject object = JsonNode.object()
                .set(key1, value1)
                .set(this.key2(), this.value2());
        this.containsValueAndCheckAbsent(object.asMap(),
                value1); // match fails because value gotten has a parent the enclosing object
    }

    @Test
    public void testMapGet() {
        final JsonPropertyName key1 = key1();
        final JsonNode value1 = this.value1();

        final JsonObject object = JsonNode.object()
                .set(key1, value1)
                .set(this.key2(), this.value2());
        this.getAndCheck(object.asMap(), key1, object.getOrFail(key1));
    }

    @Test
    public void testMapGetAbsentKey() {
        final JsonPropertyName key = key1();
        final JsonNode value = this.value1();

        final JsonObject object = JsonNode.object()
                .set(key, value)
                .set(this.key2(), this.value2());
        this.getAndCheckAbsent(object.asMap(), JsonPropertyName.with("absent-property"));
    }

    // isFalseLike......................................................................................................

    @Test
    public void testIsFalseLikeWhenEmpty() {
        this.isFalseLike(
                JsonNode.object(),
                true
        );
    }

    @Test
    public void testIsFalseLikeWhenNonEmpty() {
        this.isFalseLike(
                JsonNode.object()
                        .appendChild(
                                JsonNode.booleanNode(false)
                                        .setName(JsonPropertyName.with("a"))
                        ),
                false
        );
    }

    // toBoolean......................................................................................................

    @Test
    public void testToBooleanEmpty() {
        this.toBooleanAndCheck(
                JsonObject.EMPTY,
                false
        );
    }

    @Test
    public void testToBooleanNonEmpty() {
        this.toBooleanAndCheck(
                JsonObject.EMPTY
                        .set(
                                JsonPropertyName.with("child"),
                                JsonObject.EMPTY
                        ),
                true
        );
    }

    // removeFalseLike..................................................................................................

    @Test
    public void testRemoveFalseLikeEmpty() {
        this.removeFalseLikeAndCheckNothing(
                JsonObject.EMPTY
        );
    }

    @Test
    public void testRemoveFalseLikeInitiallyNotEmptyThenEmpty() {
        this.removeFalseLikeAndCheckNothing(
                JsonObject.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.nullNode()
                        )
                )
        );
    }

    @Test
    public void testRemoveFalseLikeInitiallyNotEmptyThenEmpty2() {
        this.removeFalseLikeAndCheckNothing(
                JsonObject.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.nullNode(),
                                JsonNode.booleanNode(false),
                                JsonNode.number(0),
                                JsonNode.string("")
                        )
                )
        );
    }

    @Test
    public void testRemoveFalseLikeEmptyArray() {
        this.removeFalseLikeAndCheckNothing(
                JsonObject.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.array()
                        )
                )
        );
    }

    @Test
    public void testRemoveFalseLikeEmptyObject() {
        this.removeFalseLikeAndCheckNothing(
                JsonObject.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.object()
                        )
                )
        );
    }

    @Test
    public void testRemoveFalseLikeArrayWithFalse() {
        this.removeFalseLikeAndCheckNothing(
                JsonObject.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.array()
                                        .setChildren(
                                                Lists.of(
                                                        JsonNode.booleanNode(false)
                                                )
                                        )
                        )
                )
        );
    }

    @Test
    public void testRemoveFalseLikeObjectWithFalse() {
        this.removeFalseLikeAndCheckNothing(
                JsonObject.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.object()
                                        .setChildren(
                                                Lists.of(
                                                        JsonNode.booleanNode(false)
                                                )
                                        )
                        )
                )
        );
    }

    @Test
    public void testRemoveFalseLikeSomeChildrenFiltered() {
        this.removeFalseLikeAndCheck(
                JsonObject.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.nullNode(),
                                JsonNode.booleanNode(true)

                        )
                ),
                JsonObject.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.booleanNode(true)
                        )
                )
        );
    }

    @Test
    public void testRemoveFalseLikeSomeChildrenFiltered2() {
        this.removeFalseLikeAndCheck(
                JsonObject.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.nullNode(),
                                JsonNode.booleanNode(true),
                                JsonNode.number(0),
                                JsonNode.string("abc123")
                        )
                ),
                JsonObject.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.booleanNode(true),
                                JsonNode.string("abc123")
                        )
                )
        );
    }

    @Test
    public void testRemoveFalseLikeGraph() {
        this.removeFalseLikeAndCheck(
                JsonObject.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.object()
                                        .setChildren(
                                                Lists.of(
                                                        JsonNode.nullNode(),
                                                        JsonNode.array()
                                                                .setChildren(
                                                                        Lists.of(
                                                                                JsonNode.booleanNode(false),
                                                                                JsonNode.number(0)
                                                                        )
                                                                ),
                                                        JsonNode.array()
                                                                .setChildren(
                                                                        Lists.of(
                                                                                JsonNode.booleanNode(true),
                                                                                JsonNode.number(0),
                                                                                JsonNode.string("abc123")
                                                                        )
                                                                )
                                                )
                                        )
                        )
                ),
                JsonObject.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.object()
                                        .setChildren(
                                                Lists.of(
                                                        JsonNode.array()
                                                                .setChildren(
                                                                        Lists.of(
                                                                                JsonNode.booleanNode(true),
                                                                                JsonNode.string("abc123")
                                                                        )
                                                                )
                                                )
                                        )
                        )
                )
        );
    }

    // equals.......................................................................................................

    @Test
    public void testEqualsDifferentChildren() {
        this.checkNotEquals(JsonNode.object().set(property(), JsonNode.string("different")));
    }

    @Test
    public void testEqualsDifferentChildren2() {
        this.checkNotEquals(JsonNode.object().set(property(), JsonNode.string("child1")),
                JsonNode.object().set(property(), JsonNode.string("child2")));
    }

    @Test
    public void testEqualsDifferentChildren3() {
        final JsonNode value = this.value1();

        this.checkNotEquals(JsonNode.object().set(property(), value),
                JsonNode.object().set(JsonPropertyName.with("different"), value));
    }

    @Test
    public void testEqualsArray() {
        this.checkNotEquals(JsonNode.object().set(property(), JsonNode.string("child1")),
                JsonNode.array().appendChild(JsonNode.string("child2")));
    }

    @Test
    public void testEqualsArrayPropertyValue() {
        final JsonObject object = JsonNode.object();
        final JsonPropertyName property = this.property();

        this.checkNotEquals(
                object.set(property, JsonNode.object().set(JsonPropertyName.with("p"), JsonNode.string("child1"))),
                object.set(property, JsonNode.array().appendChild(JsonNode.string("child2"))));
    }

    @Test
    public void testEqualsSameKeyDifferentValueType() {
        final JsonObject object = JsonNode.object();
        final JsonPropertyName property = this.property();

        this.checkNotEquals(
                object.set(property, JsonNode.object()),
                object.set(property, JsonNode.string("string")));
    }

    @Test
    public void testEqualsSameKeyDifferentValueType2() {
        final JsonObject object = JsonNode.object();
        final JsonPropertyName property = this.property();

        this.checkNotEquals(
                object.set(property, JsonNode.object()),
                object.set(property, JsonNode.array()));
    }

    private JsonPropertyName property() {
        return JsonPropertyName.with("property");
    }

    @Test
    public void testArrayOrFail() {
        assertThrows(ClassCastException.class, () -> this.createJsonNode().arrayOrFail());
    }

    // Text ...........................................................................................................

    @Test
    public void testText() {
        this.textAndCheck(JsonObject.EMPTY, "");
    }

    @Test
    public void testText2() {
        this.textAndCheck(JsonObject.EMPTY
                        .set(JsonPropertyName.with("a1"), JsonNode.string("v1"))
                        .set(JsonPropertyName.with("b2"), JsonNode.booleanNode(true))
                        .set(JsonPropertyName.with("c3"), JsonNode.number(333)),
                "v1true333");
    }

    // HasTextOffset...................................................................................................

    @Test
    public void testTextOffset2() {
        this.textOffsetAndCheck(this.createNode(), "");
    }

    @Test
    public void testTextOffset3() {
        final JsonPropertyName key = JsonPropertyName.with("b2");

        this.textOffsetAndCheck(JsonObject.EMPTY
                        .set(JsonPropertyName.with("a1"), JsonNode.string("v1"))
                        .set(key, JsonObject.EMPTY
                                .set(JsonPropertyName.with("c3"), JsonNode.string("v3")))
                        .set(JsonPropertyName.with("d4"), JsonNode.number(444))
                        .getOrFail(key),
                2);
    }

    // Visitor..........................................................................................................

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final List<JsonNode> visited = Lists.array();

        final JsonObject object = this.createJsonNode()
                .set(key1(), value1())
                .set(key2(), value2());
        final JsonString string1 = object.children().get(0).cast(JsonString.class);
        final JsonString string2 = object.children().get(1).cast(JsonString.class);

        new FakeJsonNodeVisitor() {
            @Override
            protected Visiting startVisit(final JsonNode n) {
                b.append("1");
                visited.add(n);
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final JsonNode n) {
                b.append("2");
                visited.add(n);
            }

            @Override
            protected Visiting startVisit(final JsonObject t) {
                assertSame(object, t);
                b.append("5");
                visited.add(t);
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final JsonObject t) {
                assertSame(object, t);
                b.append("6");
                visited.add(t);
            }

            @Override
            protected void visit(final JsonString t) {
                b.append("7");
                visited.add(t);
            }
        }.accept(object);
        this.checkEquals("1517217262", b.toString());
        this.checkEquals(Lists.of(object, object,
                        string1, string1, string1,
                        string2, string2, string2,
                        object, object),
                visited,
                "visited");
    }

    @Test
    public void testSelectorUsage() {
        final JsonObject object = JsonNode.object()
                .set(key1(), JsonNode.booleanNode(true))
                .set(key2(), JsonNode.number(2))
                .set(key3(), JsonNode.string("third"));
        final JsonPropertyName key2 = this.key2();

        this.selectorApplyAndCheck(object,
                JsonNode.absoluteNodeSelector()
                        .descendant()
                        .named(key2),
                object.get(key2).get());
    }

    @Test
    public void testSelectorMap() {
        final JsonObject object = JsonNode.object()
                .set(key1(), JsonNode.booleanNode(true))
                .set(key2(), JsonNode.number(2))
                .set(key3(), JsonNode.string("third"));
        final JsonPropertyName key2 = this.key2();
        final JsonNode replaced = JsonNode.string("*");

        this.selectorApplyMapAndCheck(object,
                JsonNode.absoluteNodeSelector()
                        .descendant()
                        .named(key2),
                (n) -> replaced,
                object.set(key2, replaced));
    }

    @Test
    public void testPrintJsonWithoutIndentationAndNoneLineEnding() {
        final JsonObject object = JsonNode.object()
                .set(key1(), JsonNode.booleanNode(true))
                .set(key2(), JsonNode.number(2))
                .set(key3(), JsonNode.string("third"));

        final StringBuilder b = new StringBuilder();
        final IndentingPrinter printer = Printers.stringBuilder(b, LineEnding.NONE).indenting(Indentation.EMPTY);
        object.printJson(printer);

        this.checkEquals("{\"key1\": true,\"key2\": 2,\"key3\": \"third\"}", b.toString());
    }

    @Test
    public void testTextWithoutChildren() {
        this.checkEquals("", JsonNode.object().text());
    }

    @Test
    public void testTextWithChildren() {
        final JsonObject object = JsonNode.object()
                .set(key1(), JsonNode.booleanNode(true))
                .set(key2(), JsonNode.number(2))
                .set(key3(), JsonNode.string("third"));

        this.checkEquals("true2third", object.text());
    }

    @Test
    public void testTextWithChildren2() {
        final JsonObject object = JsonNode.object()
                .set(key1(), JsonNode.booleanNode(true))
                .set(key2(), JsonNode.number(2.5))
                .set(key3(), JsonNode.string("third"));

        this.checkEquals("true2.5third", object.text());
    }

    @Test
    public void testToStringEmpty() {
        this.toStringAndCheck(
                this.createJsonNode(),
                "{}"
        );
    }

    @Test
    public void testToStringWithChildren() {
        final JsonObject object = JsonNode.object()
                .set(key1(), JsonNode.booleanNode(true))
                .set(key2(), JsonNode.number(2))
                .set(key3(), JsonNode.string("third"));

        this.toStringAndCheck(
                object,
                "{\n  \"key1\": true,\n  \"key2\": 2,\n  \"key3\": \"third\"\n}"
        );
    }

    @Test
    public void testToStringNestedObject() {
        final JsonObject nested = JsonNode.object()
                .set(key1(), JsonNode.booleanNode(true))
                .set(key2(), JsonNode.number(2));
        final JsonObject object = JsonNode.object()
                .set(key3(), nested);

        this.toStringAndCheck(
                object,
                "{\n  \"key3\": {\n    \"key1\": true,\n    \"key2\": 2\n  }\n}"
        );
    }

    @Test
    public void testToStringNestedArray() {
        final JsonArray nested = JsonNode.array()
                .appendChild(JsonNode.booleanNode(true))
                .appendChild(JsonNode.number(2))
                .appendChild(JsonNode.string("third"));
        final JsonObject object = JsonNode.object()
                .set(key3(), nested);

        this.toStringAndCheck(
                object,
                "{\n" +
                        "  \"key3\": [\n" +
                        "    true,\n" +
                        "    2,\n" +
                        "    \"third\"\n" +
                        "  ]\n" +
                        "}"
        );
    }

    @Override
    JsonObject createJsonNode() {
        return JsonNode.object();
    }

    private JsonPropertyName key1() {
        return JsonPropertyName.with(KEY1);
    }

    private JsonPropertyName key2() {
        return JsonPropertyName.with(KEY2);
    }

    private JsonPropertyName key3() {
        return JsonPropertyName.with(KEY3);
    }

    private JsonPropertyName key4() {
        return JsonPropertyName.with(KEY4);
    }

    @Override
    Class<JsonObject> jsonNodeType() {
        return JsonObject.class;
    }

    @Override
    List<String> propertiesNeverReturnNullSkipProperties() {
        return Lists.of(ARRAY_OR_FAIL,
                BOOLEAN_OR_FAIL,
                CHARACTER_OR_FAIL,
                UNMARSHALL_LIST,
                UNMARSHALL_SET,
                UNMARSHALL_MAP,
                UNMARSHALL,
                NUMBER_OR_FAIL,
                PARENT_OR_FAIL,
                STRING_OR_FAIL,
                VALUE);
    }

    // MapTesting..........................................................................................

    @Override
    public Map<JsonPropertyName, JsonNode> createMap() {
        return JsonNode.object().asMap();
    }
}
