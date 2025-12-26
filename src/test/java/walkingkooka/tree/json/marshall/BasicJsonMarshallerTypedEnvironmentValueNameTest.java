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

import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedEnvironmentValueNameTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedEnvironmentValueName, EnvironmentValueName> {

    @Override
    BasicJsonMarshallerTypedEnvironmentValueName marshaller() {
        return BasicJsonMarshallerTypedEnvironmentValueName.instance();
    }

    @Override
    EnvironmentValueName value() {
        return EnvironmentValueName.LOCALE;
    }

    @Override
    JsonNode node() {
        return JsonNode.string(this.value().toString());
    }

    @Override
    EnvironmentValueName jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "environment-value-name";
    }

    @Override
    Class<EnvironmentValueName> marshallerType() {
        return EnvironmentValueName.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedEnvironmentValueName> type() {
        return BasicJsonMarshallerTypedEnvironmentValueName.class;
    }
}
