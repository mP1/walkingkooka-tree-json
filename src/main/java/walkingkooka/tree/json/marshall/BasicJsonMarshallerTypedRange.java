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
import walkingkooka.collect.Range;
import walkingkooka.collect.RangeBound;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;

final class BasicJsonMarshallerTypedRange extends BasicJsonMarshallerTyped<Range<?>> {

    static BasicJsonMarshallerTypedRange instance() {
        return new BasicJsonMarshallerTypedRange();
    }

    private BasicJsonMarshallerTypedRange() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<Range<?>> type() {
        return Cast.to(Range.class);
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(Range.class);
    }

    @Override
    Range<?> unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    /**
     * Accepts a json string holding a range definition.
     * <pre>
     *  {
     * 	lowerBound {
     * 		Exclusive {
     * 			type=big
     * 			value=123
     *        }
     *    },
     * 	upperBound {
     * 		Inclusive {
     * 			type=big
     * 			value=123
     *        }
     *    }
     * }
     * </pre>
     */
    @Override @SuppressWarnings("unchecked")
    Range<?> unmarshallNonNull(final JsonNode node,
                               final JsonNodeUnmarshallContext context) {
        RangeBound<?> lower = RangeBound.all();
        RangeBound<?> upper = RangeBound.all();

        for (JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case LOWER_BOUND:
                    lower = unmarshallRangeBound(child, context);
                    break;
                case UPPER_BOUND:
                    upper = unmarshallRangeBound(child, context);
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
            }
        }

        return Range.with(Cast.to(lower), Cast.to(upper));
    }

    private static RangeBound<?> unmarshallRangeBound(final JsonNode node,
                                                      final JsonNodeUnmarshallContext context) {
        RangeBound<?> bound = RangeBound.all();

        for (JsonNode child : node.children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case EXCLUSIVE:
                    bound = RangeBound.exclusive(context.unmarshallWithType(child));
                    break;
                case INCLUSIVE:
                    bound = RangeBound.inclusive(context.unmarshallWithType(child));
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
            }
        }
        return bound;
    }

    @SuppressWarnings("unchecked")
    @Override
    JsonNode marshallNonNull(final Range<?> value,
                             final JsonNodeMarshallContext context) {
        return BasicJsonMarshallerTypedRangeRangeVisitor.marshall(value, context);
    }

    final static String LOWER_BOUND = "lower-bound";
    final static String UPPER_BOUND = "upper-bound";

    final static JsonPropertyName LOWER_BOUND_PROPERTY = JsonPropertyName.with(LOWER_BOUND);
    final static JsonPropertyName UPPER_BOUND_PROPERTY = JsonPropertyName.with(UPPER_BOUND);

    final static String EXCLUSIVE = "exclusive";
    final static String INCLUSIVE = "inclusive";
    final static JsonPropertyName EXCLUSIVE_PROPERTY = JsonPropertyName.with(EXCLUSIVE);
    final static JsonPropertyName INCLUSIVE_PROPERTY = JsonPropertyName.with(INCLUSIVE);
}
