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

import walkingkooka.collect.map.Maps;
import walkingkooka.net.header.MediaType;
import walkingkooka.net.header.MediaTypeParameterName;
import walkingkooka.tree.json.JsonNode;

import java.util.Optional;

public final class BasicJsonMarshallerTypedMediaTypeTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedMediaType, MediaType> {

    @Override
    BasicJsonMarshallerTypedMediaType marshaller() {
        return BasicJsonMarshallerTypedMediaType.instance();
    }

    @Override
    MediaType value() {
        return MediaType.APPLICATION_JAVASCRIPT.setSuffix(
            Optional.of("suffix")
        ).setParameters(
            Maps.of(
                MediaTypeParameterName.Q,
                0.5f
            )
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.string(
            this.value()
                .toString()
        );
    }

    @Override
    MediaType jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "media-type";
    }

    @Override
    Class<MediaType> marshallerType() {
        return MediaType.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedMediaType> type() {
        return BasicJsonMarshallerTypedMediaType.class;
    }
}
