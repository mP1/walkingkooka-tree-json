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

import walkingkooka.net.http.server.HttpResponse;
import walkingkooka.net.http.server.HttpResponses;
import walkingkooka.tree.json.JsonNode;

import java.util.Objects;

/**
 * A {@link BasicJsonMarshaller} that handles {@link HttpResponse}
 */
final class BasicJsonMarshallerTypedHttpResponse extends BasicJsonMarshallerTyped<HttpResponse> {

    static BasicJsonMarshallerTypedHttpResponse instance() {
        return new BasicJsonMarshallerTypedHttpResponse();
    }

    private BasicJsonMarshallerTypedHttpResponse() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();

        this.registerWithTypeName(
            classToString(
                HttpResponses.recording()
                    .getClass()
            )
        );
    }

    @Override
    Class<HttpResponse> type() {
        return HttpResponse.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(HttpResponse.class);
    }

    @Override
    HttpResponse unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    HttpResponse unmarshallNonNull(final JsonNode node,
                                   final JsonNodeUnmarshallContext context) {
        Objects.requireNonNull(node, "node");

        return HttpResponses.parse(
            node.stringOrFail()
        );
    }

    @Override
    JsonNode marshallNonNull(final HttpResponse value,
                             final JsonNodeMarshallContext context) {
        if (false == value.version().isPresent()) {
            throw new JsonNodeMarshallException("Version missing " + value);
        }
        if (false == value.status().isPresent()) {
            throw new JsonNodeMarshallException("Status missing " + value);
        }
        return JsonNode.string(
            value.toString()
        );
    }
}
