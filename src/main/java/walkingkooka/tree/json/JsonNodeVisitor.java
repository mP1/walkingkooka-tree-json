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

import walkingkooka.visit.Visiting;
import walkingkooka.visit.Visitor;

import java.util.Objects;

/**
 * A {@link Visitor} for a graph of {@link JsonNode}.
 */
public abstract class JsonNodeVisitor extends Visitor<JsonNode> {

    protected JsonNodeVisitor() {
        super();
    }


    // JsonNode.......................................................................

    public final void accept(final JsonNode node) {
        Objects.requireNonNull(node, "node");

        if (Visiting.CONTINUE == this.startVisit(node)) {
            node.accept(this);
        }
        this.endVisit(node);
    }

    protected Visiting startVisit(final JsonNode node) {
        return Visiting.CONTINUE;
    }

    protected void endVisit(final JsonNode node) {
        // nop
    }

    protected void visit(final JsonBoolean node) {
        // nop
    }

    protected void visit(final JsonNull node) {
        // nop
    }

    protected void visit(final JsonNumber node) {
        // nop
    }

    protected void visit(final JsonString node) {
        // nop
    }

    protected Visiting startVisit(final JsonArray node) {
        return Visiting.CONTINUE;
    }

    protected void endVisit(final JsonArray node) {
        // nop
    }

    protected Visiting startVisit(final JsonObject node) {
        return Visiting.CONTINUE;
    }

    protected void endVisit(final JsonObject node) {
        // nop
    }
}
