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

import walkingkooka.Cast;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.json.JsonArray;
import walkingkooka.tree.json.JsonBoolean;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeVisitor;
import walkingkooka.tree.json.JsonNull;
import walkingkooka.tree.json.JsonNumber;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonString;
import walkingkooka.visit.Visiting;

import java.util.Objects;

/**
 * A {@link JsonNodeVisitor} that does the right thing for the different {@link JsonNode} types.
 */
final class BasicJsonNodeUnmarshallContextJsonNodeVisitor extends JsonNodeVisitor {

    static <T> T value(final JsonNode node,
                       final JsonNodeUnmarshallContext context) {
        Objects.requireNonNull(node, "node");

        final BasicJsonNodeUnmarshallContextJsonNodeVisitor visitor = new BasicJsonNodeUnmarshallContextJsonNodeVisitor(context);
        visitor.accept(node);
        return Cast.to(visitor.value);
    }

    BasicJsonNodeUnmarshallContextJsonNodeVisitor(final JsonNodeUnmarshallContext context) {
        super();
        this.context = context;
    }

    @Override
    protected void visit(final JsonBoolean node) {
        this.value = node.value();
    }

    @Override
    protected void visit(final JsonNull node) {
        this.value = node.value();
    }

    @Override
    protected void visit(final JsonNumber node) {
        this.value = node.numberValueOrFail();
    }

    @Override
    protected void visit(final JsonString node) {
        this.value = node.stringValueOrFail();
    }

    @Override
    protected Visiting startVisit(final JsonArray node) {
        throw new JsonNodeUnmarshallException("arrays never hold typed values", node);
    }

    @Override
    protected Visiting startVisit(final JsonObject node) {
        try {
            final JsonNode type = node.getOrFail(BasicJsonNodeContext.TYPE);
            if (!type.isString()) {
                throw new JsonNodeUnmarshallException("Invalid type", node);
            }

            final JsonNodeUnmarshallContext context = this.context;
            this.value = context.unmarshall(node.getOrFail(BasicJsonNodeContext.VALUE),
                    context.registeredType((JsonString) type).orElseThrow(() -> new JsonNodeUnmarshallException("Unknown type", node)));
        } catch (final java.lang.NullPointerException | JsonNodeUnmarshallException cause) {
            throw cause;
        } catch (final RuntimeException cause) {
            throw new JsonNodeUnmarshallException("Failed to unmarshall json object", node, cause);
        }
        return Visiting.SKIP;
    }

    private final JsonNodeUnmarshallContext context;

    Object value;

    @Override
    public String toString() {
        return CharSequences.quoteIfChars(this.value).toString();
    }
}
