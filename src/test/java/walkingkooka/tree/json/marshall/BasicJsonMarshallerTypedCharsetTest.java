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

import walkingkooka.tree.json.JsonNode;

import java.nio.charset.Charset;

public final class BasicJsonMarshallerTypedCharsetTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedCharset, Charset> {

    @Override
    BasicJsonMarshallerTypedCharset marshaller() {
        return BasicJsonMarshallerTypedCharset.instance();
    }

    @Override
    Charset value() {
        return CHARSET;
    }

    @Override
    JsonNode node() {
        return JsonNode.string("UTF-8");
    }

    @Override
    Charset jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "charset";
    }

    @Override
    Class<Charset> marshallerType() {
        return Charset.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedCharset> type() {
        return BasicJsonMarshallerTypedCharset.class;
    }
}
