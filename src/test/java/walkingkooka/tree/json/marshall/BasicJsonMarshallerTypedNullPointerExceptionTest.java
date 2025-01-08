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

import org.junit.jupiter.api.Test;
import walkingkooka.tree.json.JsonNode;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public final class BasicJsonMarshallerTypedNullPointerExceptionTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedNullPointerException, java.lang.NullPointerException> {

    @Test
    public void testUnmarshallBooleanFails() {
        this.unmarshallFailed(JsonNode.booleanNode(true), ClassCastException.class);
    }

    @Test
    public void testUnmarshallNumberFails() {
        this.unmarshallFailed(JsonNode.number(1.5), ClassCastException.class);
    }

    @Test
    public void testUnmarshallObjectFails() {
        this.unmarshallFailed(JsonNode.object(), ClassCastException.class);
    }

    @Test
    public void testUnmarshallArrayFails() {
        this.unmarshallFailed(JsonNode.array(), ClassCastException.class);
    }

    @Test
    public void testUnmarshallStackElements() {
        final java.lang.NullPointerException thrown = this.unmarshallContext()
            .unmarshall(JsonNode.string("message 123"), java.lang.NullPointerException.class);
        assertArrayEquals(new StackTraceElement[0], thrown.getStackTrace(), "stack trace");
    }

    @Test
    public void testMarshallNullMessage() {
        this.marshallAndCheck(new java.lang.NullPointerException(), JsonNode.number(0));
    }

    @Test
    public void testMarshallEmptyMessage() {
        final String message = "";
        this.marshallAndCheck(new java.lang.NullPointerException(message), JsonNode.string(message));
    }

    @Override
    BasicJsonMarshallerTypedNullPointerException marshaller() {
        return BasicJsonMarshallerTypedNullPointerException.instance();
    }

    private final static String MESSAGE = "null pointer message 123";

    @Override
    java.lang.NullPointerException value() {
        return new NullPointerException(MESSAGE);
    }

    @Override
    JsonNode node() {
        return JsonNode.string(MESSAGE);
    }

    @Override
    java.lang.NullPointerException jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "null-pointer-exception";
    }

    @Override
    Class<java.lang.NullPointerException> marshallerType() {
        return java.lang.NullPointerException.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedNullPointerException> type() {
        return BasicJsonMarshallerTypedNullPointerException.class;
    }
}
