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

public final class BasicJsonMarshallerTypedInvalidTextLengthExceptionTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedInvalidTextLengthException, walkingkooka.InvalidTextLengthException> {

    @Test
    public void testFromBooleanFails() {
        this.unmarshallFailed(JsonNode.booleanNode(true), ClassCastException.class);
    }

    @Test
    public void testFromNumberFails() {
        this.unmarshallFailed(JsonNode.number(1.5), ClassCastException.class);
    }

    @Test
    public void testFromStringFails() {
        this.unmarshallFailed(JsonNode.string("string-123"), ClassCastException.class);
    }

    @Test
    public void testFromArrayFails() {
        this.unmarshallFailed(JsonNode.array(), ClassCastException.class);
    }

    @Test
    public void testFromJsonStackElements() {
        final walkingkooka.InvalidTextLengthException thrown = this.unmarshallContext()
                .unmarshall(this.node(), walkingkooka.InvalidTextLengthException.class);
        assertArrayEquals(new StackTraceElement[0], thrown.getStackTrace(), "stack trace");
    }

    @Override
    BasicJsonMarshallerTypedInvalidTextLengthException marshaller() {
        return BasicJsonMarshallerTypedInvalidTextLengthException.instance();
    }

    private final static String LABEL = "name-of-something-1";
    private final static String TEXT = "some-text-123";
    private final static int MIN = 2;
    private final static int MAX = 3;

    @Override
    walkingkooka.InvalidTextLengthException value() {
        return new InvalidTextLengthException(LABEL, TEXT, MIN, MAX);
    }

    @Override
    JsonNode node() {
        return JsonNode.parse("{ \"label\": \"name-of-something-1\", \"text\": \"some-text-123\", \"min\": 2, \"max\": 3}");
    }

    @Override
    walkingkooka.InvalidTextLengthException jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "invalid-text-length-exception";
    }

    @Override
    Class<walkingkooka.InvalidTextLengthException> marshallerType() {
        return walkingkooka.InvalidTextLengthException.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedInvalidTextLengthException> type() {
        return BasicJsonMarshallerTypedInvalidTextLengthException.class;
    }
}
