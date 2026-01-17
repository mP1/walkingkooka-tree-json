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
import walkingkooka.net.header.Link;
import walkingkooka.net.header.LinkParameterName;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;

import java.util.Map.Entry;
import java.util.Objects;

/**
 * A {@link BasicJsonMarshaller} that handles {@link Link}
 */
final class BasicJsonMarshallerTypedLink extends BasicJsonMarshallerTyped<Link> {

    static BasicJsonMarshallerTypedLink instance() {
        return new BasicJsonMarshallerTypedLink();
    }

    private BasicJsonMarshallerTypedLink() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<Link> type() {
        return Link.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(Link.class);
    }

    @Override
    Link unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    Link unmarshallNonNull(final JsonNode node,
                                   final JsonNodeUnmarshallContext context) {
        Objects.requireNonNull(node, "node");

        Url href = null;

        for (JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case HREF:
                    href = context.unmarshall(
                        child,
                        Url.class
                    );
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
            }
        }

        if (null == href) {
            JsonNodeUnmarshallContext.missingProperty(HREF_JSON_PROPERTY, node);
        }
        return Link.with(href);
    }

    @Override
    JsonNode marshallNonNull(final Link link,
                             final JsonNodeMarshallContext context) {
        JsonObject json = JsonNode.object()
            .set(
                HREF_JSON_PROPERTY,
                context.marshall(link.value())
            );

        for (Entry<LinkParameterName<?>, Object> parameterNameAndValue : link.parameters().entrySet()) {
            final LinkParameterName<?> name = parameterNameAndValue.getKey();

            json = json.set(
                JsonPropertyName.with(name.value()),
                name.toText(
                    parameterNameAndValue.getValue()
                )
            );
        }


        return json;
    }

    private final static String HREF = "href";

    /**
     * The attribute on the json object which will hold the {@link Link#value()}.
     */
    // @VisibleForTesting
    final static JsonPropertyName HREF_JSON_PROPERTY = JsonPropertyName.with(HREF);
}
