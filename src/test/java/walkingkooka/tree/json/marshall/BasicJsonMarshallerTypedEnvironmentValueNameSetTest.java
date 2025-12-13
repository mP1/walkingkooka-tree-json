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
import walkingkooka.environment.EnvironmentValueNameSet;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedEnvironmentValueNameSetTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedEnvironmentValueNameSet, EnvironmentValueNameSet> {

    @Override
    BasicJsonMarshallerTypedEnvironmentValueNameSet marshaller() {
        return BasicJsonMarshallerTypedEnvironmentValueNameSet.instance();
    }

    @Override
    EnvironmentValueNameSet value() {
        return EnvironmentValueNameSet.EMPTY.concat(
            EnvironmentValueName.LINE_ENDING
        ).concat(
            EnvironmentValueName.LOCALE
        ).concat(
            EnvironmentValueName.USER
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.string("lineEnding,locale,user");
    }

    @Override
    EnvironmentValueNameSet jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "environment-value-name-set";
    }

    @Override
    Class<EnvironmentValueNameSet> marshallerType() {
        return EnvironmentValueNameSet.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedEnvironmentValueNameSet> type() {
        return BasicJsonMarshallerTypedEnvironmentValueNameSet.class;
    }
}
