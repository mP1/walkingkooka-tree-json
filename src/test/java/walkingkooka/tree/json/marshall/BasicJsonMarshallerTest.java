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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonString;
import walkingkooka.tree.json.UnsupportedTypeJsonNodeException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicJsonMarshallerTest extends BasicJsonMarshallerTestCase<BasicJsonMarshaller<Void>> {

    @AfterEach
    public void afterEach() {
        TestJsonNodeValue.unregister();
    }

    // register.........................................................................................................

    @SuppressWarnings("unchecked")
    @Test
    public void testRegisterNullTypeNameFails() {
        assertThrows(java.lang.NullPointerException.class, () -> BasicJsonMarshaller.register(null,
            TestJsonNodeValue::unmarshall,
            TestJsonNodeValue::marshall,
            TestJsonNodeValue.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRegisterEmptyTypeNameFails() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> BasicJsonMarshaller.register("",
            TestJsonNodeValue::unmarshall,
            TestJsonNodeValue::marshall,
            TestJsonNodeValue.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRegisterNullFromFunctionFails() {
        assertThrows(java.lang.NullPointerException.class, () -> BasicJsonMarshaller.register(TestJsonNodeValue.TYPE_NAME,
            null,
            TestJsonNodeValue::marshall,
            TestJsonNodeValue.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRegisterNullToFunctionFails() {
        assertThrows(java.lang.NullPointerException.class, () -> BasicJsonMarshaller.register(TestJsonNodeValue.TYPE_NAME,
            TestJsonNodeValue::unmarshall,
            null,
            TestJsonNodeValue.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRegisterNullTypeFails() {
        assertThrows(java.lang.NullPointerException.class, () -> BasicJsonMarshaller.register(TestJsonNodeValue.TYPE_NAME,
            TestJsonNodeValue::unmarshall,
            TestJsonNodeValue::marshall,
            null));
    }

    @Test
    public void testRegisterConcrete() {
        TestJsonNodeValue.register();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRegisterTwiceFails() {
        TestJsonNodeValue.register();

        assertThrows(java.lang.IllegalArgumentException.class, () -> BasicJsonMarshaller.register(TestJsonNodeValue.TYPE_NAME,
            TestJsonNodeValue::unmarshall,
            TestJsonNodeValue::marshall,
            TestJsonNodeValue.class));
    }

    @Test
    public void testRegisteredTypeNullFails() {
        assertThrows(java.lang.NullPointerException.class, () -> BasicJsonMarshaller.registeredType(null));
    }

    @Test
    public void testRegisteredType() {
        this.checkNotEquals(Optional.empty(), BasicJsonMarshaller.registeredType(JsonNode.string("big-decimal")));
    }

    @Test
    public void testRegisteredTypeUnknown() {
        this.checkEquals(Optional.empty(), BasicJsonMarshaller.registeredType(JsonNode.string("???")));
    }

    // typeName..........................................................................................................

    @Test
    public void testTypeNameNullClassFails() {
        assertThrows(java.lang.NullPointerException.class, () -> BasicJsonMarshaller.typeName(null));
    }

    @Test
    public void testTypeNameUnknown() {
        this.typeNameAndCheck(this.getClass(),
            Optional.empty());
    }

    @Test
    public void testTypeNameBigDecimal() {
        this.typeNameAndCheck(BigDecimal.class,
            Optional.of(JsonNode.string("big-decimal")));
    }

    @Test
    public void testTypeNameJsonObject() {
        this.typeNameAndCheck(JsonObject.class,
            Optional.of(JsonNode.string("json")));
    }

    private void typeNameAndCheck(final Class<?> type,
                                  final Optional<JsonString> typeName) {
        this.checkEquals(typeName,
            BasicJsonMarshaller.typeName(type),
            () -> "typeName of " + type.getName());
    }

    @Test
    public void testMarshallerOrFailStringUnknownTypeFails() {
        assertThrows(UnsupportedTypeJsonNodeException.class, () -> BasicJsonMarshaller.marshaller(Void.TYPE));
    }

    @Test
    public void testMarshallerOrFailClassUnknownTypeFails() {
        assertThrows(UnsupportedTypeJsonNodeException.class, () -> BasicJsonMarshaller.marshaller(this.getClass()));
    }

    // Expression.......................................................................................................

    @Test
    public void testExpressionAddition() {
        this.roundtripAndCheck(Expression::add);
    }

    @Test
    public void testExpressionAnd() {
        this.roundtripAndCheck(Expression::and);
    }

    @Test
    public void testExpressionBoolean() {
        this.roundtripAndCheck(
            Expression.value(true)
        );
    }

    @Test
    public void testExpressionCallNamedFunction() {
        this.roundtripAndCheck(
            Expression.call(
                Expression.namedFunction(
                    ExpressionFunctionName.with("function123")
                ),
                Lists.of(
                    Expression.value(true),
                    Expression.value("2b")
                )
            )
        );
    }

    @Test
    public void testExpressionDivision() {
        this.roundtripAndCheck(Expression::divide);
    }

    @Test
    public void testExpressionNumber() {
        this.roundtripAndCheck(
            Expression.value(EXPRESSION_NUMBER_KIND.create(99.5)
            )
        );
    }

    @Test
    public void testExpressionEquals() {
        this.roundtripAndCheck(Expression::equalsExpression);
    }

    @Test
    public void testExpressionGreaterThanEquals() {
        this.roundtripAndCheck(Expression::greaterThanEquals);
    }

    @Test
    public void testExpressionGreaterThan() {
        this.roundtripAndCheck(Expression::greaterThan);
    }

    @Test
    public void testExpressionLessThanEquals() {
        this.roundtripAndCheck(Expression::greaterThanEquals);
    }

    @Test
    public void testExpressionLessThan() {
        this.roundtripAndCheck(Expression::greaterThan);
    }

    @Test
    public void testExpressionLocalDate() {
        this.roundtripAndCheck(
            Expression.value(
                LocalDate.of(2000, 12, 31)
            )
        );
    }

    @Test
    public void testExpressionLocalDateTime() {
        this.roundtripAndCheck(
            Expression.value(
                LocalDateTime.of(2000, 12, 31, 6, 28, 29)
            )
        );
    }

    @Test
    public void testExpressionLocalTime() {
        this.roundtripAndCheck(
            Expression.value(
                LocalTime.of(6, 28, 29)
            )
        );
    }

    @Test
    public void testExpressionModulo() {
        this.roundtripAndCheck(Expression::modulo);
    }

    @Test
    public void testExpressionMultiplication() {
        this.roundtripAndCheck(Expression::multiply);
    }

    @Test
    public void testExpressionNegative() {
        this.roundtripAndCheck(Expression::negative);
    }

    @Test
    public void testExpressionNot() {
        this.roundtripAndCheck(Expression::not);
    }

    @Test
    public void testExpressionNotEquals() {
        this.roundtripAndCheck(Expression::notEquals);
    }

    @Test
    public void testExpressionOr() {
        this.roundtripAndCheck(Expression::or);
    }

    @Test
    public void testExpressionPower() {
        this.roundtripAndCheck(Expression::power);
    }

    @Test
    public void testExpressionSubtraction() {
        this.roundtripAndCheck(Expression::subtract);
    }

    @Test
    public void testExpressionText() {
        this.roundtripAndCheck(
            Expression.value("abc123")
        );
    }

    @Test
    public void testExpressionXor() {
        this.roundtripAndCheck(Expression::xor);
    }

    private void roundtripAndCheck(final Function<Expression, Expression> factory) {
        this.roundtripAndCheck(
            factory.apply(
                Expression.value("only-parameter")
            )
        );
    }

    private void roundtripAndCheck(final BiFunction<Expression, Expression, Expression> factory) {
        this.roundtripAndCheck(
            factory.apply(
                Expression.value(
                    EXPRESSION_NUMBER_KIND.create(1)
                ),
                Expression.value("parameter-2b")
            )
        );
    }

    private void roundtripAndCheck(final Object value) {
        final JsonNode json = JsonNodeMarshallContexts.basic().marshall(value);
        this.checkEquals(
            value,
            JsonNodeUnmarshallContexts.basic(
                EXPRESSION_NUMBER_KIND,
                MathContext.DECIMAL32
            ).unmarshall(json, value.getClass()),
            () -> "roundtrip " + value + "\n" + json);
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<BasicJsonMarshaller<Void>> type() {
        return Cast.to(BasicJsonMarshaller.class);
    }
}
