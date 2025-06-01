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

import java.util.Objects;

final class BasicJsonNodeMarshallUnmarshallContext implements JsonNodeMarshallUnmarshallContext,
    JsonNodeMarshallContextDelegator,
    JsonNodeUnmarshallContextDelegator {

    static BasicJsonNodeMarshallUnmarshallContext with(final JsonNodeMarshallContext jsonNodeMarshallContext,
                                                       final JsonNodeUnmarshallContext jsonNodeUnmarshallContext) {
        return new BasicJsonNodeMarshallUnmarshallContext(
            Objects.requireNonNull(jsonNodeMarshallContext, "jsonNodeMarshallContext"),
            Objects.requireNonNull(jsonNodeUnmarshallContext, "jsonNodeUnmarshallContext")
        );
    }

        private BasicJsonNodeMarshallUnmarshallContext(final JsonNodeMarshallContext jsonNodeMarshallContext,
                                                       final JsonNodeUnmarshallContext jsonNodeUnmarshallContext) {
        this.jsonNodeMarshallContext = jsonNodeMarshallContext;
        this.jsonNodeUnmarshallContext = jsonNodeUnmarshallContext;
    }

    @Override
    public JsonNodeUnmarshallContext setPreProcessor(final JsonNodeUnmarshallContextPreProcessor processor) {
        final JsonNodeUnmarshallContext unmarshallContext = this.jsonNodeUnmarshallContext()
            .setPreProcessor(processor);
        return this.jsonNodeUnmarshallContext.equals(unmarshallContext) ?
            this :
            new BasicJsonNodeMarshallUnmarshallContext(
                this.jsonNodeMarshallContext,
                unmarshallContext
            );
    }

    @Override
    public JsonNodeMarshallContext jsonNodeMarshallContext() {
        return this.jsonNodeMarshallContext;
    }

    private final JsonNodeMarshallContext jsonNodeMarshallContext;

    @Override
    public JsonNodeUnmarshallContext jsonNodeUnmarshallContext() {
        return this.jsonNodeUnmarshallContext;
    }

    private final JsonNodeUnmarshallContext jsonNodeUnmarshallContext;

    @Override
    public JsonNodeContext jsonNodeContext() {
        return this.jsonNodeMarshallContext;
    }

    @Override
    public String toString() {
        return this.jsonNodeMarshallContext + " " + this.jsonNodeUnmarshallContext;
    }
}
