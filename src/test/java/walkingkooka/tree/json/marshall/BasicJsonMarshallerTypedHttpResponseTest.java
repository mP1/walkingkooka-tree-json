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
import walkingkooka.net.header.HttpHeaderName;
import walkingkooka.net.header.MediaType;
import walkingkooka.net.http.HttpEntity;
import walkingkooka.net.http.HttpProtocolVersion;
import walkingkooka.net.http.HttpStatusCode;
import walkingkooka.net.http.server.HttpResponse;
import walkingkooka.net.http.server.HttpResponses;
import walkingkooka.tree.json.JsonNode;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicJsonMarshallerTypedHttpResponseTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedHttpResponse, HttpResponse> {

    @Test
    public void testMarshallVersionMissingJsonFails() {
        final HttpResponse response = HttpResponses.recording();
        response.setStatus(HttpStatusCode.withCode(987).setMessage("Custom Status Message"));
        response.setEntity(HttpEntity.EMPTY);

        assertThrows(
            JsonNodeMarshallException.class,
            () -> this.marshaller()
                .marshall(
                    response,
                    JsonNodeMarshallContexts.fake()
                )
        );
    }

    @Test
    public void testMarshallStatusMissingJsonFails() {
        final HttpResponse response = HttpResponses.recording();
        response.setVersion(HttpProtocolVersion.VERSION_1_0);
        response.setEntity(HttpEntity.EMPTY);

        assertThrows(
            JsonNodeMarshallException.class,
            () -> this.marshaller()
                .marshall(
                    response,
                    JsonNodeMarshallContexts.fake()
                )
        );
    }

    @Override
    BasicJsonMarshallerTypedHttpResponse marshaller() {
        return BasicJsonMarshallerTypedHttpResponse.instance();
    }

    @Override
    HttpResponse value() {
        final HttpResponse response = HttpResponses.recording();
        response.setVersion(HttpProtocolVersion.VERSION_1_0);
        response.setStatus(HttpStatusCode.withCode(987).setMessage("Custom Status Message"));
        response.setEntity(
            HttpEntity.EMPTY
                .addHeader(HttpHeaderName.CONTENT_LENGTH, 123L)
                .setContentType(MediaType.TEXT_PLAIN)
                .setBodyText("body-text-123")
        );
        return response;
    }

    @Override
    JsonNode node() {
        return JsonNode.string(
            this.value().toString()
        );
    }

    @Override
    HttpResponse jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "http-response";
    }

    @Override
    Class<HttpResponse> marshallerType() {
        return HttpResponse.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedHttpResponse> type() {
        return BasicJsonMarshallerTypedHttpResponse.class;
    }
}
