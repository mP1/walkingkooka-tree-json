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

import walkingkooka.text.MultiLineText;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedMultiLineTextTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedMultiLineText, MultiLineText> {

    @Override
    BasicJsonMarshallerTypedMultiLineText marshaller() {
        return BasicJsonMarshallerTypedMultiLineText.instance();
    }

    @Override
    MultiLineText value() {
        return MultiLineText.with("{\n  \"Hello\": \"World123\"\n}");
    }

    @Override
    JsonNode node() {
        return JsonNode.string("{\n  \"Hello\": \"World123\"\n}");
    }

    @Override
    MultiLineText jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "multi-line-text";
    }

    @Override
    Class<MultiLineText> marshallerType() {
        return MultiLineText.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedMultiLineText> type() {
        return BasicJsonMarshallerTypedMultiLineText.class;
    }
}
