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
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.JsonNode;

import java.util.Arrays;

public final class BasicJsonMarshallerTypedExpressionNumberKindTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedExpressionNumberKind, ExpressionNumberKind> {

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
    public void testUnmarshallString() {
        Arrays.stream(ExpressionNumberKind.values())
            .forEach(r -> this.unmarshallAndCheck(JsonNode.string(r.name()), r));
    }

    @Test
    public void testMarshallExpressionNumberKindValues() {
        Arrays.stream(ExpressionNumberKind.values())
            .forEach(r -> this.marshallAndCheck(r, JsonNode.string(r.name())));
    }

    @Override
    BasicJsonMarshallerTypedExpressionNumberKind marshaller() {
        return BasicJsonMarshallerTypedExpressionNumberKind.instance();
    }

    @Override
    ExpressionNumberKind value() {
        return ExpressionNumberKind.BIG_DECIMAL;
    }

    @Override
    JsonNode node() {
        return JsonNode.string(this.value().name());
    }

    @Override
    ExpressionNumberKind jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "expression-number-kind";
    }

    @Override
    Class<ExpressionNumberKind> marshallerType() {
        return ExpressionNumberKind.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedExpressionNumberKind> type() {
        return BasicJsonMarshallerTypedExpressionNumberKind.class;
    }
}
