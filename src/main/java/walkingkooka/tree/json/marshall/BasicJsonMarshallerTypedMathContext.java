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

import walkingkooka.text.CharacterConstant;
import walkingkooka.tree.json.JsonNode;

import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Handles converting to and from json a {@link MathContext}. Logic is included to use constant names for the 4 available
 * {@link MathContext}, otherwise the precision comma {@link RoundingMode} is used.
 */
final class BasicJsonMarshallerTypedMathContext extends BasicJsonMarshallerTyped<MathContext> {

    static BasicJsonMarshallerTypedMathContext instance() {
        return new BasicJsonMarshallerTypedMathContext();
    }

    private BasicJsonMarshallerTypedMathContext() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<MathContext> type() {
        return MathContext.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(MathContext.class);
    }

    @Override
    MathContext unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    MathContext unmarshallNonNull(final JsonNode node,
                                  final JsonNodeUnmarshallContext context) {
        return unmarshallString(node.stringOrFail());
    }

    private MathContext unmarshallString(final String string) {
        MathContext mathContext;

        switch (string) {
            case DECIMAL32:
                mathContext = MathContext.DECIMAL32;
                break;
            case DECIMAL64:
                mathContext = MathContext.DECIMAL64;
                break;
            case DECIMAL128:
                mathContext = MathContext.DECIMAL128;
                break;
            case UNLIMITED:
                mathContext = MathContext.UNLIMITED;
                break;
            default:
                final int separator = string.indexOf(SEPARATOR.character());
                if (-1 == separator) {
                    throw new java.lang.IllegalArgumentException("Unknown constant and missing separator");
                }
                if (0 == separator) {
                    throw new java.lang.IllegalArgumentException("Missing precision");
                }
                if (string.length() == separator) {
                    throw new java.lang.IllegalArgumentException("Missing ROUNDING_MODE");
                }
                int precision;
                try {
                    precision = Integer.parseInt(string.substring(0, separator));
                } catch (final NumberFormatException cause) {
                    throw new java.lang.IllegalArgumentException("Invalid precision", cause);
                }
                mathContext = new MathContext(precision, RoundingMode.valueOf(string.substring(separator + 1)));
        }

        return mathContext;
    }

    @Override
    JsonNode marshallNonNull(final MathContext value,
                             final JsonNodeMarshallContext context) {
        return MathContext.DECIMAL32.equals(value) ?
            DECIMAL32_JSON :
            MathContext.DECIMAL64.equals(value) ?
                DECIMAL64_JSON :
                MathContext.DECIMAL128.equals(value) ?
                    DECIMAL128_JSON :
                    MathContext.UNLIMITED.equals(value) ?
                        UNLIMITED_JSON :
                        JsonNode.string(value.getPrecision() + SEPARATOR.string() + value.getRoundingMode().name());

    }

    private final String DECIMAL32 = "DECIMAL32";
    private final JsonNode DECIMAL32_JSON = JsonNode.string(DECIMAL32);

    private final String DECIMAL64 = "DECIMAL64";
    private final JsonNode DECIMAL64_JSON = JsonNode.string(DECIMAL64);

    private final String DECIMAL128 = "DECIMAL128";
    private final JsonNode DECIMAL128_JSON = JsonNode.string(DECIMAL128);

    private final String UNLIMITED = "UNLIMITED";
    private final JsonNode UNLIMITED_JSON = JsonNode.string(UNLIMITED);

    private final static CharacterConstant SEPARATOR = CharacterConstant.with(',');
}
