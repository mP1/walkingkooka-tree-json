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

import java.util.OptionalInt;

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

    @Test
    public void testMarshallTextAndPosition() {
        this.marshallAndCheck(
            new InvalidCharacterException(
                TEXT,
                POSITION,
                InvalidCharacterException.NO_COLUMN,
                InvalidCharacterException.NO_LINE,
                InvalidCharacterException.NO_APPEND_TO_MESSAGE,
                new NullPointerException() // cause ignored!
            ),
            JsonNode.parse(
                "{\n" +
                    "  \"text\": \"some-text-123\",\n" +
                    "  \"position\": 2\n" +
                    "}"
            )
        );
    }

    @Test
    public void testUnmarshallTextAndPosition() {
        this.unmarshallAndCheck(
            JsonNode.parse(
                "{\n" +
                    "  \"text\": \"some-text-123\",\n" +
                    "  \"position\": 2\n" +
                    "}"
            ),
            new InvalidCharacterException(
                TEXT,
                POSITION,
                InvalidCharacterException.NO_COLUMN,
                InvalidCharacterException.NO_LINE,
                InvalidCharacterException.NO_APPEND_TO_MESSAGE,
                new NullPointerException() // cause ignored!
            )
        );
    }

    @Override
    BasicJsonMarshallerTypedInvalidCharacterException marshaller() {
        return BasicJsonMarshallerTypedInvalidCharacterException.instance();
    }

    private final static String TEXT = "some-text-123";
    private final static int POSITION = 2;

    @Override
    walkingkooka.InvalidCharacterException value() {
        return new InvalidCharacterException(
            TEXT,
            POSITION,
            OptionalInt.of(3), // column
            OptionalInt.of(4), // line
            "appendToMessage5",
            new NullPointerException() // cause ignored!
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.parse(
            "{\n" +
                "  \"text\": \"some-text-123\",\n" +
                "  \"position\": 2,\n" +
                "  \"column\": 3,\n" +
                "  \"line\": 4,\n" +
                "  \"appendToMessage\": \"appendToMessage5\"\n" +
                "}"
        );
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
