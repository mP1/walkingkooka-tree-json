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
import walkingkooka.visit.Visiting;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class JsonArrayTest extends JsonParentNodeTestCase<JsonArray, List<JsonNode>> {

    // append

    @Test
    public void testAppendEmptyArray() {
        this.appendChildAndCheck(this.createJsonNode(), JsonNode.array());
    }

    @Test
    public void testAppendBoolean() {
        this.appendChildAndCheck(this.createJsonNode(), JsonNode.booleanNode(true));
    }

    @Test
    public void testAppendNumber() {
        this.appendChildAndCheck(this.createJsonNode(), JsonNode.number(123));
    }

    @Test
    public void testAppendNull() {
        this.appendChildAndCheck(this.createJsonNode(), JsonNode.nullNode());
    }

    @Test
    public void testAppendEmptyObject() {
        this.appendChildAndCheck(this.createJsonNode(), JsonNode.object());
    }

    @Test
    public void testAppendString() {
        this.appendChildAndCheck(this.createJsonNode(), JsonNode.string("string-abc-123"));
    }

    // append and get

    @Test
    public void testGet() {
        final String[] values = new String[]{"A1", "B2"};
        final JsonNode first = JsonNode.string(values[0]);
        final JsonNode second = JsonNode.string(values[1]);

        final JsonArray array = JsonNode.array().appendChild(first).appendChild(second);
        this.childrenCheck(array);

        for (int i = 0; i < 2; i++) {
            final JsonString element = array.children().get(i).cast(JsonString.class);
            this.checkEquals(values[i], element.value(), "value");
        }

        // verify originals were not mutated.
        this.parentMissingCheck(first);
        this.parentMissingCheck(second);
    }

    @Test
    public void testSetChildrenSame() {
        final JsonArray array = JsonNode.array()
                .appendChild(this.value1())
                .appendChild(this.value2());
        assertSame(array, array.setChildren(array.children()));
    }

    @Test
    public void testSetChildrenEquivalent() {
        final JsonString value1 = this.value1();
        final JsonString value2 = this.value2();

        final JsonArray array = JsonNode.array()
                .appendChild(value1)
                .appendChild(value2);
        assertSame(array, array.setChildren(Lists.of(value1, value2)));
    }

    @Test
    public void testSetSame() {
        final JsonString value1 = this.value1();
        final JsonString value2 = this.value2();

        final JsonArray array = JsonNode.array()
                .appendChild(value1)
                .appendChild(value2);
        assertSame(array, array.set(0, value1));
        assertSame(array, array.set(1, value2));
    }

    @Test
    public void testSetSame2() {
        final JsonString value1 = this.value1();
        final JsonString value2 = this.value2();

        final JsonArray array = JsonNode.array()
                .appendChild(value1)
                .appendChild(value2);
        assertSame(array, array.set(0, array.get(0)));
        assertSame(array, array.set(1, array.get(1)));

    }

    @Test
    public void testSetDifferent() {
        final JsonString value1 = this.value1();
        final JsonString value2 = this.value2();

        final JsonArray array = JsonNode.array()
                .appendChild(value1)
                .appendChild(value2);

        final String differentValue = "different-0";
        final JsonNode differentChild = JsonNode.string(differentValue);
        final JsonArray different = array.set(0, differentChild);

        assertNotSame(array, different);
        this.childrenCheck(different);
        this.checkEquals(differentValue, different.children().get(0).cast(JsonString.class).value(), "child[0].value");
        this.checkEquals(VALUE2, different.children().get(1).cast(JsonString.class).value(), "child[1].value");

        // verify originals were not mutated.
        this.parentMissingCheck(value1);
        this.parentMissingCheck(value2);
        this.parentMissingCheck(differentChild);
    }

    @Test
    public void testSetDifferent2() {
        final JsonString value1 = this.value1();
        final JsonString value2 = this.value2();

        final JsonArray array = JsonNode.array()
                .appendChild(value1)
                .appendChild(value2);

        final String differentValue = "different-1";
        final JsonNode differentChild = JsonNode.string(differentValue);
        final JsonArray different = array.set(1, differentChild);

        assertNotSame(array, different);
        this.childrenCheck(different);
        this.checkEquals(VALUE1, different.children().get(0).cast(JsonString.class).value(), "child[0].value");
        this.checkEquals(differentValue, different.children().get(1).cast(JsonString.class).value(), "child[1].value");

        // verify originals were not mutated.
        this.parentMissingCheck(value1);
        this.parentMissingCheck(value2);
        this.parentMissingCheck(differentChild);
    }

    @Test
    public void testSetExpands() {
        final JsonNode first = JsonNode.nullNode();
        final JsonNode second = JsonNode.nullNode();
        final JsonNode third = this.value3();

        this.checkEqualsAndHashCode(JsonNode.array().appendChild(first).appendChild(second).appendChild(third),
                JsonNode.array().set(2, third));
    }

    @Test
    public void testSetExpands2() {
        final JsonNode first = this.value1();

        this.checkEqualsAndHashCode(JsonNode.array().appendChild(first),
                JsonNode.array().set(0, first));
    }

    @Test
    public void testSetExpands3() {
        final JsonNode first = this.value1();
        final JsonNode second = JsonNode.nullNode();
        final JsonNode third = this.value3();

        final JsonArray array = JsonNode.array().appendChild(first);

        this.checkEqualsAndHashCode(JsonNode.array().appendChild(first).appendChild(second).appendChild(third),
                array.set(2, third));
    }

    @Test
    public void testSetExpands4() {
        final JsonNode first = this.value1();
        final JsonNode second = JsonNode.nullNode();
        final JsonNode third = JsonNode.nullNode();
        final JsonNode fourth = this.value4();

        final JsonArray array = JsonNode.array().appendChild(first);

        this.checkEqualsAndHashCode(JsonNode.array().appendChild(first).appendChild(second).appendChild(third).appendChild(fourth),
                array.set(3, fourth));
    }

    // remove

    @Test
    public void testRemoveNegativeIndexFails() {
        assertThrows(IndexOutOfBoundsException.class, () -> this.createJsonNode().remove(-1));
    }

    @Test
    public void testRemoveInvalidIndexFails() {
        assertThrows(IndexOutOfBoundsException.class, () -> this.createJsonNode().remove(0));
    }

    @Test
    public void testRemove() {
        final JsonNode first = this.value1();
        final JsonNode second = this.value2();

        final JsonArray array = JsonNode.array()
                .appendChild(first)
                .appendChild(second);
        final JsonArray removed = array.remove(0);

        assertNotSame(array, removed);
        this.childrenCheck(removed);
        this.checkEquals(VALUE2, removed.children().get(0).cast(JsonString.class).value(), "child[0].value");

        // verify originals were not mutated.
        this.parentMissingCheck(first);
        this.parentMissingCheck(second);
    }

    @Test
    public void testRemoveInvalidIndexFails2() {
        final JsonArray array = JsonNode.array()
                .appendChild(this.value1())
                .appendChild(this.value2());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(2));
        this.childrenCheck(array);
        this.childCountCheck(array, 2);
    }

    @Test
    public void testReplaceChild() {
        final JsonArray root = JsonNode.array()
                .appendChild(this.value1());
        this.childCountCheck(root, 1);

        final JsonArray updated = root.replaceChild(root.get(0), this.value2())
                .cast(JsonArray.class);

        this.childCountCheck(updated, 1);
        this.checkEquals(VALUE2, updated.get(0).cast(JsonString.class).value());
    }

    @Test
    public void testReplaceChild2() {
        final JsonNode child1 = JsonNode.string("C1");

        final JsonArray root = JsonNode.array()
                .appendChild(child1)
                .appendChild(JsonNode.array()
                        .appendChild(JsonNode.string("GC1-old")));
        this.childCountCheck(root, 2);

        final JsonNode grandChild1New = JsonNode.string("GC1-new");
        final JsonArray updatedChild = root.get(1)
                .setChildren(Lists.of(grandChild1New))
                .cast(JsonArray.class);
        this.childCountCheck(updatedChild, 1);

        final JsonArray updatedRoot = updatedChild.parentOrFail().cast(JsonArray.class);
        this.childCountCheck(updatedRoot, 2);
        this.checkEquals(JsonNode.array()
                        .appendChild(child1)
                        .appendChild(JsonNode.array()
                                .appendChild(grandChild1New)),
                updatedRoot,
                "updatedRoot");
    }

    // setLength ...........................................................................................

    @Test
    public void testSetLength() {
        final JsonArray array = JsonNode.array();

        assertSame(array, array.setLength(0));
    }

    @Test
    public void testSetLength2() {
        final JsonArray array = JsonNode.array()
                .appendChild(this.value1())
                .appendChild(this.value2());

        assertSame(array, array.setLength(2));
    }

    @Test
    public void testSetLengthShorter() {
        final JsonArray array = JsonNode.array()
                .appendChild(this.value1())
                .appendChild(this.value2())
                .appendChild(this.value3());

        this.setLengthAndCheck(array,
                0);
    }

    @Test
    public void testSetLengthShorter2() {
        final JsonNode child1 = this.value1();
        final JsonNode child2 = this.value2();
        final JsonNode child3 = this.value3();

        final JsonArray array = JsonNode.array()
                .appendChild(child1)
                .appendChild(child2)
                .appendChild(child3);

        this.setLengthAndCheck(array,
                2,
                child1, child2);
    }

    @Test
    public void testSetLengthLonger() {
        this.setLengthAndCheck(JsonArray.array(),
                1,
                JsonNode.nullNode());
    }

    @Test
    public void testSetLengthLonger2() {
        this.setLengthAndCheck(JsonArray.array(),
                2,
                JsonNode.nullNode(), JsonNode.nullNode());
    }

    @Test
    public void testSetLengthLonger3() {
        final JsonNode child1 = this.value1();
        final JsonNode child2 = this.value2();

        final JsonArray array = JsonNode.array()
                .appendChild(child1)
                .appendChild(child2);

        this.setLengthAndCheck(array,
                3,
                child1, child2, JsonNode.nullNode());
    }

    @Test
    public void testSetLengthLonger4() {
        final JsonNode child1 = this.value1();
        final JsonNode child2 = this.value2();

        final JsonArray array = JsonNode.array()
                .appendChild(child1)
                .appendChild(child2);

        this.setLengthAndCheck(array,
                4,
                child1, child2, JsonNode.nullNode(), JsonNode.nullNode());
    }

    private void setLengthAndCheck(final JsonArray array,
                                   final int length,
                                   final JsonNode... children) {
        this.checkEquals(length, children.length, "expected children length doesnt match setLength length");
        this.checkEquals(JsonNode.array().setChildren(Lists.of(children)),
                array.setLength(length),
                () -> "setLength " + length + " on " + array);
    }

    // replace .......................................................................................................

    @Test
    public void testReplaceRoot() {
        final JsonArray node = JsonNode.array();
        this.replaceAndCheck(node, node);
    }

    @Test
    public void testReplace() {
        final JsonNode value4 = this.value4();

        final JsonArray node = JsonNode.array()
                .appendChild(JsonNode.array()
                        .appendChild(this.value1()))
                .appendChild(this.value2());

        final int index = 0;
        this.replaceAndCheck(node.get(index),
                value4,
                node.set(index, value4).get(index));
    }

    @Test
    public void testReplace2() {
        final JsonNode value4 = this.value4();

        final JsonArray node = JsonNode.array()
                .appendChild(this.value1())
                .appendChild(JsonNode.array()
                        .appendChild(this.value2()));

        final int index = 1;
        this.replaceAndCheck(node.get(index),
                value4,
                node.set(index, value4).get(index));
    }

    // HasText ..........................................................................................................

    @Test
    public void testText() {
        this.textAndCheck(JsonArray.EMPTY, "");
    }

    @Test
    public void testText2() {
        this.textAndCheck(JsonArray.EMPTY
                        .appendChild(JsonNode.string("a1"))
                        .appendChild(JsonNode.string("b2")),
                "a1b2");
    }

    // HasTextOffset...................................................................................................

    @Test
    public void testTextOffset2() {
        this.textOffsetAndCheck(this.createNode(), "");
    }

    @Test
    public void testTextOffset3() {
        this.textOffsetAndCheck(JsonArray.EMPTY
                        .appendChild(JsonNode.string("a1"))
                        .appendChild(JsonArray.EMPTY
                                .appendChild(JsonNode.string("b2!")))
                        .appendChild(JsonNode.string("c3!!"))
                        .get(1),
                2);
    }

    // Visitor ...........................................................................................

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final List<JsonNode> visited = Lists.array();

        final JsonArray array = this.createJsonNode()
                .appendChild(this.value1())
                .appendChild(this.value2());
        final JsonString string1 = array.get(0)
                .cast(JsonString.class);
        final JsonString string2 = array.get(1)
                .cast(JsonString.class);

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
            protected Visiting startVisit(final JsonArray t) {
                assertSame(array, t);
                b.append("5");
                visited.add(t);
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final JsonArray t) {
                assertSame(array, t);
                b.append("6");
                visited.add(t);
            }

            @Override
            protected void visit(final JsonString t) {
                b.append("7");
                visited.add(t);
            }
        }.accept(array);
        this.checkEquals("1517217262", b.toString());
        this.checkEquals(Lists.of(array, array,
                        string1, string1, string1,
                        string2, string2, string2,
                        array, array),
                visited,
                "visited");
    }

    @Test
    public void testTextWithoutChildren() {
        this.checkEquals("", JsonNode.array().text());
    }

    @Test
    public void testTextWithChildren() {
        final JsonArray array = JsonNode.array()
                .appendChild(JsonNode.booleanNode(true))
                .appendChild(JsonNode.number(2))
                .appendChild(JsonNode.string("third"));
        this.checkEquals("true2third", array.text());
    }

    @Test
    public void testTextWithChildren2() {
        final JsonArray array = JsonNode.array()
                .appendChild(JsonNode.booleanNode(true))
                .appendChild(JsonNode.number(2.75))
                .appendChild(JsonNode.string("third"));
        this.checkEquals("true2.75third", array.text());
    }

    @Test
    public void testSelectorUsage() {
        final JsonArray array = JsonNode.array()
                .appendChild(JsonNode.booleanNode(true))
                .appendChild(JsonNode.number(2))
                .appendChild(JsonNode.string("third"));

        final JsonNode expected = array.get(1);

        this.selectorApplyAndCheck(array,
                JsonNode.absoluteNodeSelector()
                        .descendant()
                        .named(expected.name()),
                expected);
    }

    @Test
    public void testSelectorMap() {
        final JsonArray array = JsonNode.array()
                .appendChild(JsonNode.booleanNode(true))
                .appendChild(JsonNode.number(2))
                .appendChild(JsonNode.string("third"))
                .appendChild(JsonNode.string("fourth"));

        final JsonNode replaced = JsonNode.number(999);

        this.selectorApplyMapAndCheck(array,
                JsonNode.absoluteNodeSelector()
                        .descendant()
                        .named(array.get(1).name()),
                (n) -> replaced,
                array.set(1, replaced));
    }

    // isFalseLike......................................................................................................

    @Test
    public void testIsFalseLikeWhenEmpty() {
        this.isFalseLike(
                JsonNode.array(),
                true
        );
    }

    @Test
    public void testIsFalseLikeWhenNonEmpty() {
        this.isFalseLike(
                JsonNode.array()
                        .appendChild(
                                JsonNode.booleanNode(false)
                        ),
                false
        );
    }

    // toBoolean......................................................................................................

    @Test
    public void testToBooleanEmpty() {
        this.toBooleanAndCheck(
                JsonArray.EMPTY,
                false
        );
    }

    @Test
    public void testToBooleanNonEmpty() {
        this.toBooleanAndCheck(
                JsonArray.EMPTY
                        .appendChild(
                                JsonNode.booleanNode(true)
                        ),
                true
        );
    }

    // removeFalseLike..................................................................................................

    @Test
    public void testRemoveFalseLikeEmpty() {
        this.removeFalseLikeAndCheckNothing(
                JsonArray.EMPTY
        );
    }

    @Test
    public void testRemoveFalseLikeInitiallyNotEmptyThenEmpty() {
        this.removeFalseLikeAndCheckNothing(
                JsonArray.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.nullNode()
                        )
                )
        );
    }

    @Test
    public void testRemoveFalseLikeInitiallyNotEmptyThenEmpty2() {
        this.removeFalseLikeAndCheckNothing(
                JsonArray.EMPTY.setChildren(
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
                JsonArray.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.array()
                        )
                )
        );
    }

    @Test
    public void testRemoveFalseLikeEmptyObject() {
        this.removeFalseLikeAndCheckNothing(
                JsonArray.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.object()
                        )
                )
        );
    }

    @Test
    public void testRemoveFalseLikeArrayWithFalse() {
        this.removeFalseLikeAndCheckNothing(
                JsonArray.EMPTY.setChildren(
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
                JsonArray.EMPTY.setChildren(
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
                JsonArray.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.nullNode(),
                                JsonNode.booleanNode(true)

                        )
                ),
                JsonArray.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.booleanNode(true)
                        )
                )
        );
    }

    @Test
    public void testRemoveFalseLikeSomeChildrenFiltered2() {
        this.removeFalseLikeAndCheck(
                JsonArray.EMPTY.setChildren(
                        Lists.of(
                                JsonNode.nullNode(),
                                JsonNode.booleanNode(true),
                                JsonNode.number(0),
                                JsonNode.string("abc123")
                        )
                ),
                JsonArray.EMPTY.setChildren(
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
                JsonArray.EMPTY.setChildren(
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
                JsonArray.EMPTY.setChildren(
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

    // HashCodeAnddEqualityDefined.......................................................

    @Test
    public void testEqualsDifferentChildren() {
        this.checkNotEquals(JsonNode.array().appendChild(JsonNode.string("different")));
    }

    @Test
    public void testEqualsDifferentChildren2() {
        this.checkNotEquals(JsonNode.array().appendChild(JsonNode.string("child1")),
                JsonNode.array().appendChild(JsonNode.string("child2")));
    }

    @Test
    public void testEqualsArray() {
        this.checkNotEquals(JsonNode.array().appendChild(JsonNode.string("child2")),
                JsonNode.object().set(JsonPropertyName.with("prop"), JsonNode.string("child1")));
    }

    @Test
    public void testEqualsArrayAndObjectElement() {
        this.checkNotEquals(
                JsonNode.array().appendChild(JsonNode.array().appendChild(JsonNode.string("element1"))),
                JsonNode.array().appendChild(JsonNode.object().set(JsonPropertyName.with("element-prop"), JsonNode.string("element-value"))));
    }

    // toString .......................................................................................

    @Test
    public void testToStringEmpty() {
        this.toStringAndCheck(this.createJsonNode(), "[]");
    }

    @Test
    public void testToStringWithOneChildBoolean() {
        final JsonArray array = JsonNode.array()
                .appendChild(JsonNode.booleanNode(true));

        this.toStringAndCheck(
                array,
                "[\n" +
                        "  true\n" +
                        "]"
        );
    }

    @Test
    public void testToStringWithOneChildObject() {
        final JsonArray array = JsonNode.array()
                .appendChild(JsonNode.object());

        this.toStringAndCheck(
                array,
                "[\n" +
                        "  {}\n" +
                        "]"
        );
    }

    @Test
    public void testToStringWithOneChildObjectWithProperties() {
        final JsonArray array = JsonNode.array()
                .appendChild(
                        JsonNode.object()
                                .set(
                                        JsonPropertyName.with("x"),
                                        JsonNode.booleanNode(true)
                                )
                );

        this.toStringAndCheck(
                array,
                "[\n" +
                        "  {\n" +
                        "    \"x\": true\n" +
                        "  }\n" +
                        "]"
        );
    }

    @Test
    public void testToStringWithSeveralChildren() {
        final JsonArray array = JsonNode.array()
                .appendChild(JsonNode.booleanNode(true))
                .appendChild(JsonNode.number(2))
                .appendChild(JsonNode.string("third"));

        this.toStringAndCheck(
                array,
                "[\n" +
                        "  true,\n" +
                        "  2,\n" +
                        "  \"third\"\n" +
                        "]"
        );
    }

    @Override
    JsonArray createJsonNode() {
        return JsonArray.array();
    }

    @Override
    Class<JsonArray> jsonNodeType() {
        return JsonArray.class;
    }

    // ParentNodeTesting................................................................................................

    @Override
    public JsonArray appendChildAndCheck(final JsonNode parent,
                                         final JsonNode child) {
        final JsonArray newParent = parent.appendChild(child)
                .cast(JsonArray.class);
        assertNotSame(newParent, parent, "appendChild must not return the same node");

        final List<JsonNode> children = newParent.children();
        this.checkNotEquals(0, children.size(), "children must have at least 1 child");

        final int lastIndex = children.size() - 1;
        this.checkEquals(JsonPropertyName.index(lastIndex), children.get(lastIndex).name(), "last child must be the added child");

        this.childrenParentCheck(newParent);
        this.parentMissingCheck(child);

        return newParent;
    }

    @Override
    List<String> propertiesNeverReturnNullSkipProperties() {
        return Lists.of(BOOLEAN_OR_FAIL,
                CHARACTER_OR_FAIL,
                UNMARSHALL_LIST,
                UNMARSHALL_SET,
                UNMARSHALL_MAP,
                UNMARSHALL,
                NUMBER_OR_FAIL,
                OBJECT_OR_FAIL,
                PARENT_OR_FAIL,
                STRING_OR_FAIL,
                VALUE);
    }
}
