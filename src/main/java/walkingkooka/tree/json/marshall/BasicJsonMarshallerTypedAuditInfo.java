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

import walkingkooka.environment.AuditInfo;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;

import java.time.LocalDateTime;

final class BasicJsonMarshallerTypedAuditInfo extends BasicJsonMarshallerTyped<AuditInfo> {

    static BasicJsonMarshallerTypedAuditInfo instance() {
        return new BasicJsonMarshallerTypedAuditInfo();
    }

    private BasicJsonMarshallerTypedAuditInfo() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<AuditInfo> type() {
        return AuditInfo.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(AuditInfo.class);
    }

    @Override
    AuditInfo unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    AuditInfo unmarshallNonNull(final JsonNode node,
                                final JsonNodeUnmarshallContext context) {
        EmailAddress createdBy = null;
        LocalDateTime createdTimestamp = null;
        EmailAddress modifiedBy = null;
        LocalDateTime modifiedTimestamp = null;

        for (JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case CREATED_BY_PROPERTY_STRING:
                    createdBy = context.unmarshall(
                        child,
                        EmailAddress.class
                    );
                    break;
                case CREATED_TIMESTAMP_PROPERTY_STRING:
                    createdTimestamp = context.unmarshall(
                        child,
                        LocalDateTime.class
                    );
                    break;
                case MODIFIED_BY_PROPERTY_STRING:
                    modifiedBy = context.unmarshall(
                        child,
                        EmailAddress.class
                    );
                    break;
                case MODIFIED_TIMESTAMP_PROPERTY_STRING:
                    modifiedTimestamp = context.unmarshall(
                        child,
                        LocalDateTime.class
                    );
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
                    break;
            }
        }

        return AuditInfo.with(
            createdBy,
            createdTimestamp,
            modifiedBy,
            modifiedTimestamp
        );
    }

    @Override
    JsonNode marshallNonNull(final AuditInfo auditInfo,
                             final JsonNodeMarshallContext context) {
        return JsonNode.object()
            .set(CREATED_BY_PROPERTY, context.marshall(auditInfo.createdBy()))
            .set(CREATED_TIMESTAMP_PROPERTY, context.marshall(auditInfo.createdTimestamp()))
            .set(MODIFIED_BY_PROPERTY, context.marshall(auditInfo.modifiedBy()))
            .set(MODIFIED_TIMESTAMP_PROPERTY, context.marshall(auditInfo.modifiedTimestamp()));
    }

    private final static String CREATED_BY_PROPERTY_STRING = "createdBy";

    private final static String CREATED_TIMESTAMP_PROPERTY_STRING = "createdTimestamp";

    private final static String MODIFIED_BY_PROPERTY_STRING = "modifiedBy";

    private final static String MODIFIED_TIMESTAMP_PROPERTY_STRING = "modifiedTimestamp";

    final static JsonPropertyName CREATED_BY_PROPERTY = JsonPropertyName.with(CREATED_BY_PROPERTY_STRING);

    final static JsonPropertyName CREATED_TIMESTAMP_PROPERTY = JsonPropertyName.with(CREATED_TIMESTAMP_PROPERTY_STRING);

    final static JsonPropertyName MODIFIED_BY_PROPERTY = JsonPropertyName.with(MODIFIED_BY_PROPERTY_STRING);

    final static JsonPropertyName MODIFIED_TIMESTAMP_PROPERTY = JsonPropertyName.with(MODIFIED_TIMESTAMP_PROPERTY_STRING);
}
