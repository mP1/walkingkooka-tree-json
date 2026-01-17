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

import walkingkooka.net.Url;
import walkingkooka.net.header.HttpHeaderName;
import walkingkooka.net.header.MediaType;
import walkingkooka.net.http.HttpEntity;
import walkingkooka.net.http.HttpMethod;
import walkingkooka.net.http.HttpProtocolVersion;
import walkingkooka.net.http.HttpTransport;
import walkingkooka.net.http.server.HttpRequest;
import walkingkooka.net.http.server.HttpRequests;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedHttpRequestTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedHttpRequest, HttpRequest> {

    @Override
    BasicJsonMarshallerTypedHttpRequest marshaller() {
        return BasicJsonMarshallerTypedHttpRequest.instance();
    }

    @Override
    HttpRequest value() {
        return HttpRequests.value(
            HttpTransport.SECURED,
            HttpMethod.POST,
            Url.parseRelative("/path1/file2?query3"),
            HttpProtocolVersion.VERSION_1_0,
            HttpEntity.EMPTY
                .addHeader(HttpHeaderName.CONTENT_LENGTH, 123L)
                .setContentType(MediaType.TEXT_PLAIN)
                .setBodyText("body-text-123")
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.string(
            this.value().toString()
        );
    }

    @Override
    HttpRequest jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "http-request";
    }

    @Override
    Class<HttpRequest> marshallerType() {
        return HttpRequest.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedHttpRequest> type() {
        return BasicJsonMarshallerTypedHttpRequest.class;
    }
}
