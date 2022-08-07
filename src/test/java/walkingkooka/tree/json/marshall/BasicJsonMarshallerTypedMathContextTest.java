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

import org.junit.jupiter.api.Test;
import walkingkooka.tree.json.JsonNode;

import java.math.MathContext;
import java.math.RoundingMode;

public final class BasicJsonMarshallerTypedMathContextTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedMathContext, MathContext> {

    @Test
    public void testUnmarshallBooleanFails() {
        this.unmarshallFailed(JsonNode.booleanNode(true), ClassCastException.class);
    }

    @Test
    public void testUnmarshallNumberFails() {
        this.unmarshallFailed(JsonNode.number(1.5), ClassCastException.class);
    }

    @Test
    public void testUnmarshallObjectFails() {
        this.unmarshallFailed(JsonNode.object(), ClassCastException.class);
    }

    @Test
    public void testUnmarshallArrayFails() {
        this.unmarshallFailed(JsonNode.array(), ClassCastException.class);
    }

    @Test
    public void testUnmarshallStringEmptyFails() {
        this.unmarshallFailed(JsonNode.string(""), java.lang.IllegalArgumentException.class);
    }

    @Test
    public void testUnmarshallStringEmptyPrecisionFails() {
        this.unmarshallFailed(JsonNode.string(",DECIMAL32"), java.lang.IllegalArgumentException.class);
    }

    @Test
    public void testUnmarshallStringInvalidPrecisionNumberFails() {
        this.unmarshallFailed(JsonNode.string("X,DECIMAL32"), java.lang.IllegalArgumentException.class);
    }

    @Test
    public void testUnmarshallStringEmptyRoundingModeFails() {
        this.unmarshallFailed(JsonNode.string("9,"), java.lang.IllegalArgumentException.class);
    }

    @Test
    public void testUnmarshallStringUnknownRoundingModeFails() {
        this.unmarshallFailed(JsonNode.string("9,?UNKNOWN?"), java.lang.IllegalArgumentException.class);
    }

    @Test
    public void testUnmarshallStringInvalidPrecisionFails() {
        this.unmarshallFailed(JsonNode.string("-9,DECIMAL32"), java.lang.IllegalArgumentException.class);
    }

    @Test
    public void testUnmarshallDecimal32() {
        this.unmarshallAndCheck(JsonNode.string("DECIMAL32"), MathContext.DECIMAL32);
    }

    @Test
    public void testMarshallDecimal32() {
        this.marshallAndCheck(MathContext.DECIMAL32, JsonNode.string("DECIMAL32"));
    }

    @Test
    public void testUnmarshallDecimal64() {
        this.unmarshallAndCheck(JsonNode.string("DECIMAL64"), MathContext.DECIMAL64);
    }

    @Test
    public void testMarshallDecimal64() {
        this.marshallAndCheck(MathContext.DECIMAL64, JsonNode.string("DECIMAL64"));
    }

    @Test
    public void testUnmarshallDecimal128() {
        this.unmarshallAndCheck(JsonNode.string("DECIMAL128"), MathContext.DECIMAL128);
    }

    @Test
    public void testMarshallDecimal128() {
        this.marshallAndCheck(MathContext.DECIMAL128, JsonNode.string("DECIMAL128"));
    }

    @Test
    public void testUnmarshallUnlimited() {
        this.unmarshallAndCheck(JsonNode.string("UNLIMITED"), MathContext.UNLIMITED);
    }

    @Test
    public void testMarshallUnlimited() {
        this.marshallAndCheck(MathContext.UNLIMITED, JsonNode.string("UNLIMITED"));
    }

    @Override
    BasicJsonMarshallerTypedMathContext marshaller() {
        return BasicJsonMarshallerTypedMathContext.instance();
    }

    @Override
    MathContext value() {
        return new MathContext(5, RoundingMode.FLOOR);
    }

    @Override
    JsonNode node() {
        return JsonNode.string("5,FLOOR");
    }

    @Override
    MathContext jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "math-context";
    }

    @Override
    Class<MathContext> marshallerType() {
        return MathContext.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedMathContext> type() {
        return BasicJsonMarshallerTypedMathContext.class;
    }
}
