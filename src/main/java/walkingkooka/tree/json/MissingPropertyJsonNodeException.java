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

package walkingkooka.tree.json;

/**
 * Used to report a required property was missing in some json. Typically this happens during unmarshalling.
 */
public final class MissingPropertyJsonNodeException extends PropertyJsonNodeException {

    private static final long serialVersionUID = 1L;

    protected MissingPropertyJsonNodeException() {
        this(null, null);
    }

    public MissingPropertyJsonNodeException(final JsonPropertyName name,
                                            final JsonNode node) {
        super(name, node);
    }

    @Override
    String messagePrefix() {
        return "Missing property";
    }
}
