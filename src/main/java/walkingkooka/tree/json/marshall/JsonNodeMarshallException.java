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

import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeException;

/**
 * Used to report or wrap any exceptions thrown while performing {@link JsonNode} operations.
 */
public class JsonNodeMarshallException extends JsonNodeException {

    private static final long serialVersionUID = 1L;

    protected JsonNodeMarshallException() {
        super();
    }

    public JsonNodeMarshallException(final String message) {
        super(message);
    }

    public JsonNodeMarshallException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
