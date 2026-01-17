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

import walkingkooka.net.email.EmailAddress;
import walkingkooka.tree.json.JsonNode;

import java.util.Objects;

/**
 * A {@link BasicJsonMarshaller} that handles {@link EmailAddress}
 */
final class BasicJsonMarshallerTypedEmailAddress extends BasicJsonMarshallerTyped<EmailAddress> {

    static BasicJsonMarshallerTypedEmailAddress instance() {
        return new BasicJsonMarshallerTypedEmailAddress();
    }

    private BasicJsonMarshallerTypedEmailAddress() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<EmailAddress> type() {
        return EmailAddress.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(EmailAddress.class);
    }

    @Override
    EmailAddress unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    EmailAddress unmarshallNonNull(final JsonNode node,
                                   final JsonNodeUnmarshallContext context) {
        Objects.requireNonNull(node, "node");

        return EmailAddress.parse(
            node.stringOrFail()
        );
    }

    @Override
    JsonNode marshallNonNull(final EmailAddress value,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(value.toString());
    }
}
