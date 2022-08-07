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
import walkingkooka.Cast;
import walkingkooka.tree.expression.AddExpression;
import walkingkooka.tree.expression.DivideExpression;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedClassTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedClass, Class<?>> {

    @Test
    @Override
    public void testFromJsonNodeJsonNullNode() {
        this.unmarshallFailed(
                JsonNode.nullNode(),
                java.lang.NullPointerException.class
        );
    }

    @Test
    public void testFromString() {
        this.unmarshallClassAndCheck(String.class);
    }

    @Test
    public void testFromAddExpression() {
        this.unmarshallClassAndCheck(AddExpression.class);
    }

    private void unmarshallClassAndCheck(final Class<?> classs) {
        this.unmarshallAndCheck(
                JsonNode.string(BasicJsonMarshaller.classToString(classs)),
                classs
        );
    }

    @Test
    public void testTo() {
        this.marshallAndCheck(
                DivideExpression.class,
                JsonNode.string(BasicJsonMarshaller.classToString(DivideExpression.class))
        );
    }

    @Override
    BasicJsonMarshallerTypedClass marshaller() {
        return BasicJsonMarshallerTypedClass.instance();
    }

    @Override
    Class<?> value() {
        return String.class;
    }

    @Override
    JsonNode node() {
        return JsonNode.string(
                BasicJsonMarshaller.classToString(this.value())
        );
    }

    @Override
    Class<?> jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "class";
    }

    @Override
    Class<Class<?>> marshallerType() {
        return Cast.to(Class.class);
    }

    @Override
    public Class<BasicJsonMarshallerTypedClass> type() {
        return BasicJsonMarshallerTypedClass.class;
    }
}
