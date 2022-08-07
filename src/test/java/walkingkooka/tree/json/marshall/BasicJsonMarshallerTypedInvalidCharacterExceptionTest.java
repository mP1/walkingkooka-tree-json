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

public final class BasicJsonMarshallerTypedInvalidCharacterExceptionTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedInvalidCharacterException, walkingkooka.InvalidCharacterException> {

    @Test
    public void testUnmarshallBooleanFails() {
        this.unmarshallFailed(JsonNode.booleanNode(true), ClassCastException.class);
    }

    @Test
    public void testUnmarshallNumberFails() {
        this.unmarshallFailed(JsonNode.number(1.5), ClassCastException.class);
    }

    @Test
    public void testUnmarshallStringFails() {
        this.unmarshallFailed(JsonNode.string("string-123"), ClassCastException.class);
    }

    @Test
    public void testUnmarshallArrayFails() {
        this.unmarshallFailed(JsonNode.array(), ClassCastException.class);
    }

    @Test
    public void testUnmarshallStackElements() {
        final walkingkooka.InvalidCharacterException thrown = this.unmarshallContext()
                .unmarshall(this.node(), walkingkooka.InvalidCharacterException.class);
        assertArrayEquals(new StackTraceElement[0], thrown.getStackTrace(), "stack trace");
    }

    @Override
    BasicJsonMarshallerTypedInvalidCharacterException marshaller() {
        return BasicJsonMarshallerTypedInvalidCharacterException.instance();
    }

    private final static String TEXT = "some-text-123";
    private final static int POSITION = 2;

    @Override
    walkingkooka.InvalidCharacterException value() {
        return new InvalidCharacterException(TEXT, POSITION);
    }

    @Override
    JsonNode node() {
        return JsonNode.parse("{ \"text\": \"some-text-123\", \"position\": 2}");
    }

    @Override
    walkingkooka.InvalidCharacterException jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "invalid-character-exception";
    }

    @Override
    Class<walkingkooka.InvalidCharacterException> marshallerType() {
        return walkingkooka.InvalidCharacterException.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedInvalidCharacterException> type() {
        return BasicJsonMarshallerTypedInvalidCharacterException.class;
    }
}
