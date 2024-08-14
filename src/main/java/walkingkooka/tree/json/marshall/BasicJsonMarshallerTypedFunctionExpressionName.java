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

import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.json.JsonNode;

final class BasicJsonMarshallerTypedExpressionFunctionName extends BasicJsonMarshallerTyped<ExpressionFunctionName> {

    static BasicJsonMarshallerTypedExpressionFunctionName instance() {
        return new BasicJsonMarshallerTypedExpressionFunctionName();
    }

    private BasicJsonMarshallerTypedExpressionFunctionName() {
        super();
    }

    @Override
    void register() {
        JsonNodeContext.register(
                this.typeName(),
                this::unmarshall,
                this::marshall,
                ExpressionFunctionName.class
        );
    }

    @Override
    Class<ExpressionFunctionName> type() {
        return ExpressionFunctionName.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(ExpressionFunctionName.class);
    }

    @Override
    ExpressionFunctionName unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    ExpressionFunctionName unmarshallNonNull(final JsonNode node,
                                             final JsonNodeUnmarshallContext context) {
        return ExpressionFunctionName.with(node.stringOrFail());
    }

    @Override
    JsonNode marshallNonNull(final ExpressionFunctionName value,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(value.toString());
    }
}
