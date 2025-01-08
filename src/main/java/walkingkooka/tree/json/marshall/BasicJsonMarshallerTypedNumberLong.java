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
import walkingkooka.tree.json.NumericLossJsonNodeException;

final class BasicJsonMarshallerTypedNumberLong extends BasicJsonMarshallerTyped<Long> {

    static BasicJsonMarshallerTypedNumberLong instance() {
        return new BasicJsonMarshallerTypedNumberLong();
    }

    private BasicJsonMarshallerTypedNumberLong() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<Long> type() {
        return Long.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(Long.class);
    }

    @Override
    Long unmarshallNonNull(final JsonNode node,
                           final JsonNodeUnmarshallContext context) {
        return node.isNumber() ?
            this.unmarshallNumber(node.numberOrFail()) :
            this.unmarshallString(node);
    }

    private Long unmarshallNumber(final Number number) {
        final double doubleValue = number.doubleValue();
        final long longValue = number.longValue();
        if (doubleValue != longValue) {
            throw new NumericLossJsonNodeException("Unable to convert " + number + " to Long");
        }
        return longValue;
    }

    private Long unmarshallString(final JsonNode node) {
        final String text = node.stringOrFail();
        return Long.parseLong(text);
    }

    @Override
    Long unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    JsonNode marshallNonNull(final Long value,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(value.toString());
    }
}
