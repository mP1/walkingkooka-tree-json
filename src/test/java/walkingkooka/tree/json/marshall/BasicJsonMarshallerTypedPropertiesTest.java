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

import walkingkooka.props.Properties;
import walkingkooka.props.PropertiesPath;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;

public final class BasicJsonMarshallerTypedPropertiesTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedProperties, Properties> {

    @Override
    BasicJsonMarshallerTypedProperties marshaller() {
        return BasicJsonMarshallerTypedProperties.instance();
    }

    @Override
    Properties value() {
        return Properties.EMPTY.set(
            PropertiesPath.parse("key.111"),
            "value111"
        ).set(
            PropertiesPath.parse("key.222"),
            "value222"
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.object()
            .set(
                JsonPropertyName.with("key.111"),
                JsonNode.string("value111")
            ).set(
                JsonPropertyName.with("key.222"),
                JsonNode.string("value222")
            );
    }

    @Override
    Properties jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "properties";
    }

    @Override
    Class<Properties> marshallerType() {
        return Properties.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedProperties> type() {
        return BasicJsonMarshallerTypedProperties.class;
    }
}
