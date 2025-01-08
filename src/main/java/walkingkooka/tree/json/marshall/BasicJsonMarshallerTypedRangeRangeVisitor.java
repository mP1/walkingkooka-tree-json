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

import walkingkooka.collect.Range;
import walkingkooka.collect.RangeVisitor;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;

/**
 * A {@link RangeVisitor} that converts a {@link Range} into {@link JsonNode}.
 */
final class BasicJsonMarshallerTypedRangeRangeVisitor<C extends Comparable<C>> extends RangeVisitor<C> {

    @SuppressWarnings("unchecked")
    static <C extends Comparable<C>> JsonNode marshall(final Range<C> range,
                                                       final JsonNodeMarshallContext context) {
        final BasicJsonMarshallerTypedRangeRangeVisitor<C> visitor = new BasicJsonMarshallerTypedRangeRangeVisitor<>(context);
        visitor.accept(range);
        return visitor.jsonNode;
    }

    BasicJsonMarshallerTypedRangeRangeVisitor(final JsonNodeMarshallContext context) {
        super();
        this.context = context;
    }

    @Override
    protected void all() {
    }

    @Override
    protected void singleton(final C value) {
        super.singleton(value);
    }

    @Override
    protected void lowerBoundAll() {
    }

    @Override
    protected void lowerBoundExclusive(final C value) {
        this.typeWithValue(BasicJsonMarshallerTypedRange.LOWER_BOUND_PROPERTY,
            BasicJsonMarshallerTypedRange.EXCLUSIVE_PROPERTY,
            value);
    }

    @Override
    protected void lowerBoundInclusive(final C value) {
        this.typeWithValue(BasicJsonMarshallerTypedRange.LOWER_BOUND_PROPERTY,
            BasicJsonMarshallerTypedRange.INCLUSIVE_PROPERTY,
            value);
    }

    @Override
    protected void upperBoundAll() {
    }

    @Override
    protected void upperBoundExclusive(final C value) {
        this.typeWithValue(BasicJsonMarshallerTypedRange.UPPER_BOUND_PROPERTY,
            BasicJsonMarshallerTypedRange.EXCLUSIVE_PROPERTY,
            value);
    }

    @Override
    protected void upperBoundInclusive(final C value) {
        this.typeWithValue(BasicJsonMarshallerTypedRange.UPPER_BOUND_PROPERTY,
            BasicJsonMarshallerTypedRange.INCLUSIVE_PROPERTY,
            value);
    }

    /**
     * <pre>
     *  "inclusive": {
     *    "type": "int"
     *    "value": 22
     *  }
     * </pre>
     */
    private void typeWithValue(final JsonPropertyName lowerOrUpper,
                               final JsonPropertyName inclusiveOrExclusive,
                               final C value) {
        this.jsonNode = this.jsonNode.set(lowerOrUpper,
            JsonNode.object()
                .set(inclusiveOrExclusive, this.context.marshallWithType(value))
        );
    }

    private final JsonNodeMarshallContext context;

    private JsonObject jsonNode = JsonNode.object();
}
