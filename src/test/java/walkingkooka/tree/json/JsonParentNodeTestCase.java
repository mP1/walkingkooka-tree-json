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
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.naming.Name;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.Node;
import walkingkooka.tree.ParentNodeTesting;
import walkingkooka.tree.Traversable;

import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class JsonParentNodeTestCase<N extends JsonParentNode<C>, C extends List<JsonNode>>
    extends JsonNodeTestCase<N>
    implements ParentNodeTesting<JsonNode, JsonPropertyName, Name, Object> {

    final static String VALUE1 = "value1";
    final static String VALUE2 = "value2";
    final static String VALUE3 = "value3";
    final static String VALUE4 = "value4";

    JsonParentNodeTestCase() {
        super();
    }

    @Test
    public final void testCreate() {
        final N parent = this.createJsonNode();
        this.childCountCheck(parent, 0);
    }

    @SuppressWarnings("unchecked") @Test
    @Override
    public final void testSetNameDifferent() {
        final N node = this.createJsonNode();
        final JsonPropertyName originalName = node.name();
        final List<JsonNode> value = node.children();

        final JsonPropertyName differentName = JsonPropertyName.with("different");
        final N different = (N) node.setName(differentName);
        this.checkEquals(differentName, different.name(), "name");
        this.checkChildren(different, value);

        this.checkEquals(originalName, node.name(), "original name");
        this.checkChildren(node, value);
    }

    @Test
    public final void testChildrenIncludesNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createNode()
                .setChildren(
                    Lists.of(
                        null,
                        JsonNode.string("string")
                    )
                )
        );
    }

    @Override
    public final void testReplaceChildDifferentParent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void testSetSameAttributes() {
        throw new UnsupportedOperationException();
    }

    @Test
    public final void testValueFails() {
        assertThrows(
            UnsupportedOperationException.class,
            () -> this.createJsonNode().value()
        );
    }

    @Override
    public void testParentWithoutChild() {
    }

    final JsonString value1() {
        return JsonNode.string(VALUE1);
    }

    final JsonString value2() {
        return JsonNode.string(VALUE2);
    }

    final JsonString value3() {
        return JsonNode.string(VALUE3);
    }

    final JsonString value4() {
        return JsonNode.string(VALUE4);
    }

    final void checkChildren(final N node, final List<JsonNode> children) {
        this.checkEquals(children, node.children(), "children");
    }

    // NodeTesting..............................................................................

    @Override
    public final void childrenCheck(final Traversable<?> node) {
        for (; ; ) {
            if (node instanceof JsonArray) {
                this.childrenArrayCheck(Cast.to(node));
                break;
            }
            if (node instanceof JsonObject) {
                this.childrenObjectCheck(Cast.to(node));
                break;
            }
            super.childrenCheck(node);
            break;
        }
    }

    final void childrenArrayCheck(final JsonArray node) {
        final Optional<JsonNode> nodeAsParent = Optional.of(node);

        int i = 0;
        for (Node<?, ?, ?, ?> child : node.children()) {
            this.checkEquals(i, child.index(), () -> "Incorrect index of " + child);
            this.checkEquals(JsonPropertyName.index(i), child.name(), () -> "child name" + child);

            final int j = i;
            this.checkEquals(nodeAsParent, child.parent(), () -> "Incorrect parent of child " + j + "=" + child);

            this.childrenCheck(child);
            i++;
        }
    }

    final void childrenObjectCheck(final JsonObject node) {
        final Optional<JsonNode> nodeAsParent = Optional.of(node);

        int i = 0;
        for (Entry<JsonPropertyName, JsonNode> keyAndValue : node.children.nameToValues.entrySet()) {
            final Node<?, ?, ?, ?> child = keyAndValue.getValue();

            this.checkEquals(i, child.index(), () -> "Incorrect index of " + child);
            this.checkEquals(keyAndValue.getKey(), child.name(), () -> "child name" + child);

            final int j = i;
            this.checkEquals(nodeAsParent, child.parent(), () -> "Incorrect parent of child " + j + "=" + child);

            this.childrenCheck(child);
            i++;
        }
    }

    // ToStringTesting..................................................................................................

    public final void toStringAndCheck(final N node,
                                       final String expected) {
        super.toStringAndCheck(
            node,
            expected.replace(LineEnding.SYSTEM, LineEnding.NL)
        );
    }
}
