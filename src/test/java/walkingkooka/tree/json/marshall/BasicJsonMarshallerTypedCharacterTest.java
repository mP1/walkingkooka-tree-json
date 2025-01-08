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

public final class BasicJsonMarshallerTypedCharacterTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedCharacter, Character> {

    @Test
    @Override
    public void testUnmarshallJsonNullNode() {
        this.unmarshallFailed(
            JsonNode.nullNode(),
            java.lang.NullPointerException.class
        );
    }

    @Test
    public void testUnmarshallMoreThanOneLengthStringFails() {
        this.unmarshallFailed(JsonNode.string("abc"), java.lang.IllegalArgumentException.class);
    }

    @Test
    public void testUnmarshallA() {
        this.unmarshallAndCheck(
            JsonNode.string("A"),
            'A'
        );
    }

    @Test
    public void testUnmarshallTab() {
        this.unmarshallAndCheck(
            JsonNode.string("\t"),
            '\t'
        );
    }

    @Test
    public void testMarshallZ() {
        this.marshallAndCheck(
            'Z',
            JsonNode.string("Z")
        );
    }

    @Override
    BasicJsonMarshallerTypedCharacter marshaller() {
        return BasicJsonMarshallerTypedCharacter.instance();
    }

    @Override
    Character value() {
        return 'Q';
    }

    @Override
    JsonNode node() {
        return JsonNode.string(this.value().toString());
    }

    @Override
    Character jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "character";
    }

    @Override
    Class<Character> marshallerType() {
        return Character.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedCharacter> type() {
        return BasicJsonMarshallerTypedCharacter.class;
    }
}
