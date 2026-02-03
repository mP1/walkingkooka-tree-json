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

import java.time.ZoneOffset;

public final class BasicJsonMarshallerTypedZoneOffsetTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedZoneOffset, ZoneOffset> {

    @Test
    public void testUnmarshallInvalidZoneOffsetFails() {
        this.unmarshallFailed(
            JsonNode.string("ABC123"),
            IllegalArgumentException.class
        );
    }

    @Override
    BasicJsonMarshallerTypedZoneOffset marshaller() {
        return BasicJsonMarshallerTypedZoneOffset.instance();
    }

    @Override
    ZoneOffset value() {
        return ZoneOffset.ofHours(10);
    }

    @Override
    JsonNode node() {
        return JsonNode.string("+10:00");
    }

    @Override
    ZoneOffset jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "zone-offset";
    }

    @Override
    Class<ZoneOffset> marshallerType() {
        return ZoneOffset.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedZoneOffset> type() {
        return BasicJsonMarshallerTypedZoneOffset.class;
    }
}
