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

import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.JsonNode;

/**
 * Handles converting to and from json a {@link ExpressionNumberKind} enum.
 */
final class BasicJsonMarshallerTypedExpressionNumberKind extends BasicJsonMarshallerTyped<ExpressionNumberKind> {

    static BasicJsonMarshallerTypedExpressionNumberKind instance() {
        return new BasicJsonMarshallerTypedExpressionNumberKind();
    }

    private BasicJsonMarshallerTypedExpressionNumberKind() {
        super();
    }

    @Override
    void register() {
        this.registerEnum(ExpressionNumberKind.values()); // necessary because sub classes will be different types
    }

    @Override
    Class<ExpressionNumberKind> type() {
        return ExpressionNumberKind.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(ExpressionNumberKind.class);
    }

    @Override
    ExpressionNumberKind unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    ExpressionNumberKind unmarshallNonNull(final JsonNode node,
                                           final JsonNodeUnmarshallContext context) {
        return ExpressionNumberKind.valueOf(node.stringOrFail());
    }

    @Override
    JsonNode marshallNonNull(final ExpressionNumberKind mode,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(mode.name());
    }
}
