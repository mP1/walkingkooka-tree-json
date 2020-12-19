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

public final class BasicJsonMarshallerTypedIllegalArgumentExceptionTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedIllegalArgumentException, java.lang.IllegalArgumentException> {

    @Test
    public void testFromBooleanFails() {
        this.unmarshallFailed(JsonNode.booleanNode(true), ClassCastException.class);
    }

    @Test
    public void testFromNumberFails() {
        this.unmarshallFailed(JsonNode.number(1.5), ClassCastException.class);
    }

    @Test
    public void testFromObjectFails() {
        this.unmarshallFailed(JsonNode.object(), ClassCastException.class);
    }

    @Test
    public void testFromArrayFails() {
        this.unmarshallFailed(JsonNode.array(), ClassCastException.class);
    }

    @Test
    public void testFromJsonStackElements() {
        final java.lang.IllegalArgumentException thrown = this.unmarshallContext()
                .unmarshall(JsonNode.string("message 123"), java.lang.IllegalArgumentException.class);
        assertArrayEquals(new StackTraceElement[0], thrown.getStackTrace(), "stack trace");
    }

    @Test
    public void testToJsonNullMessage() {
        this.marshallAndCheck(new java.lang.IllegalArgumentException(), JsonNode.number(0));
    }

    @Test
    public void testToJsonEmptyMessage() {
        final String message = "";
        this.marshallAndCheck(new java.lang.IllegalArgumentException(message), JsonNode.string(message));
    }

    @Override
    BasicJsonMarshallerTypedIllegalArgumentException marshaller() {
        return BasicJsonMarshallerTypedIllegalArgumentException.instance();
    }

    private final static String MESSAGE = "illegal argument exception message 123";

    @Override
    java.lang.IllegalArgumentException value() {
        return new IllegalArgumentException(MESSAGE);
    }

    @Override
    JsonNode node() {
        return JsonNode.string(MESSAGE);
    }

    @Override
    java.lang.IllegalArgumentException jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "illegal-argument-exception";
    }

    @Override
    Class<java.lang.IllegalArgumentException> marshallerType() {
        return java.lang.IllegalArgumentException.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedIllegalArgumentException> type() {
        return BasicJsonMarshallerTypedIllegalArgumentException.class;
    }
}
