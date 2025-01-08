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

import walkingkooka.text.CharSequences;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeException;

import java.util.Objects;

/**
 * Used to report and wrap exceptions that occur during static unmarshall method calls.
 */
public class JsonNodeUnmarshallException extends JsonNodeException {

    private static final long serialVersionUID = 1L;

    protected JsonNodeUnmarshallException() {
        super();
    }

    public JsonNodeUnmarshallException(final String message, final JsonNode node) {
        super(message);
        this.node = checkNode(node);
    }

    public JsonNodeUnmarshallException(final String message, final JsonNode node, final Throwable cause) {
        super(checkMessage(message), cause);
        this.node = checkNode(node);
    }

    /**
     * Many cases simply wrap another operation in a try/catch and create a {@link JsonNodeUnmarshallException} from the message and cause.
     * The message could be null and this must be replaced with a default message to avoid null/empty checks in {@link walkingkooka.SystemException}
     */
    private static String checkMessage(final String message) {
        return CharSequences.isNullOrEmpty(message) ?
            DEFAULT_MESSAGE :
            message;
    }

    final static String DEFAULT_MESSAGE = "unmarshall failed";

    private static JsonNode checkNode(final JsonNode node) {
        Objects.requireNonNull(node, "node");
        return node;
    }

    public JsonNode node() {
        return this.node;
    }

    private JsonNode node;
}
