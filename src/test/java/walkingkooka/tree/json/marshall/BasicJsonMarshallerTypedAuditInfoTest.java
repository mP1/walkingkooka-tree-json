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
import walkingkooka.environment.AuditInfo;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.tree.json.JsonNode;

import java.time.LocalDateTime;

public final class BasicJsonMarshallerTypedAuditInfoTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedAuditInfo, AuditInfo> {

    @Test
    public void testMarshall2() {
        this.marshallAndCheck(
            this.value(),
            this.node()
        );
    }

    @Override
    BasicJsonMarshallerTypedAuditInfo marshaller() {
        return BasicJsonMarshallerTypedAuditInfo.instance();
    }

    @Override
    AuditInfo value() {
        return AuditInfo.with(
            EmailAddress.parse("created-by@example.com"),
            LocalDateTime.of(
                1999,
                12,
                31,
                12,
                58,
                59
            ),
            EmailAddress.parse("modified-by@example.com"),
            LocalDateTime.of(
                2000,
                1,
                2,
                12,
                58,
                59
            )
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.parse(
            "{\n" +
                "  \"createdBy\": \"created-by@example.com\",\n" +
                "  \"createdTimestamp\": \"1999-12-31T12:58:59\",\n" +
                "  \"modifiedBy\": \"modified-by@example.com\",\n" +
                "  \"modifiedTimestamp\": \"2000-01-02T12:58:59\"\n" +
                "}"
        );
    }

    @Override
    AuditInfo jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "audit-info";
    }

    @Override
    Class<AuditInfo> marshallerType() {
        return AuditInfo.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedAuditInfo> type() {
        return BasicJsonMarshallerTypedAuditInfo.class;
    }
}
