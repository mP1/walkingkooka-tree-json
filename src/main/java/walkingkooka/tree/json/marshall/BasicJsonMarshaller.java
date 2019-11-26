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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.expression.AddExpression;
import walkingkooka.tree.expression.AndExpression;
import walkingkooka.tree.expression.BigDecimalExpression;
import walkingkooka.tree.expression.BigIntegerExpression;
import walkingkooka.tree.expression.BooleanExpression;
import walkingkooka.tree.expression.DivideExpression;
import walkingkooka.tree.expression.DoubleExpression;
import walkingkooka.tree.expression.EqualsExpression;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.GreaterThanEqualsExpression;
import walkingkooka.tree.expression.GreaterThanExpression;
import walkingkooka.tree.expression.LessThanEqualsExpression;
import walkingkooka.tree.expression.LessThanExpression;
import walkingkooka.tree.expression.LocalDateExpression;
import walkingkooka.tree.expression.LocalDateTimeExpression;
import walkingkooka.tree.expression.LocalTimeExpression;
import walkingkooka.tree.expression.LongExpression;
import walkingkooka.tree.expression.ModuloExpression;
import walkingkooka.tree.expression.MultiplyExpression;
import walkingkooka.tree.expression.NegativeExpression;
import walkingkooka.tree.expression.NotEqualsExpression;
import walkingkooka.tree.expression.NotExpression;
import walkingkooka.tree.expression.OrExpression;
import walkingkooka.tree.expression.PowerExpression;
import walkingkooka.tree.expression.StringExpression;
import walkingkooka.tree.expression.SubtractExpression;
import walkingkooka.tree.expression.XorExpression;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonString;
import walkingkooka.tree.json.UnsupportedTypeJsonNodeException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * A marshaller that converts values to {@link JsonNode} and back.
 */
abstract class BasicJsonMarshaller<T> {

    /**
     * All factory registrations for all {@link BasicJsonMarshaller marshaller}.
     */
    // @VisibleForTesting
    final static Map<String, BasicJsonMarshaller<?>> TYPENAME_TO_MARSHALLER = Maps.concurrent();

    /**
     * Returns the marshaller for the given {@link Class}.
     */
    static <T> BasicJsonMarshaller<T> marshaller(final Class<T> type) {
        return marshaller(
                List.class.isAssignableFrom(type) ? "list" :
                        Set.class.isAssignableFrom(type) ? "set" :
                                Map.class.isAssignableFrom(type) ? "marshall" :
                                        type.getName());
    }

    /**
     * Returns the {@link BasicJsonMarshaller} for the given type name.
     */
    static <T> BasicJsonMarshaller<T> marshaller(final String type) {
        final BasicJsonMarshaller<T> marshaller = Cast.to(TYPENAME_TO_MARSHALLER.get(type));
        if (null == marshaller) {
            throw new UnsupportedTypeJsonNodeException("Type " + CharSequences.quote(type) + " not supported, currently: " + TYPENAME_TO_MARSHALLER.keySet());
        }
        return marshaller;
    }

    // register.........................................................................................................

    /*
     * To avoid race conditions register {@link JsonNode} here, all instance methods create the singleton
     * rather than referencing private static which would be null at this time because their super class
     * this is now running.
     */
    static {
        Lists.of(BasicJsonMarshallerBoolean.instance(),
                BasicJsonMarshallerDouble.instance(),
                BasicJsonMarshallerNumber.instance(),
                BasicJsonMarshallerString.instance(),
                BasicJsonMarshallerTypedBigDecimal.instance(),
                BasicJsonMarshallerTypedBigInteger.instance(),
                BasicJsonMarshallerTypedCharacter.instance(),
                BasicJsonMarshallerTypedExpression.binary(Expression::add, AddExpression.class),
                BasicJsonMarshallerTypedExpression.binary(Expression::and, AndExpression.class),
                BasicJsonMarshallerTypedExpression.value(Expression::bigDecimal, BigDecimalExpression.class, BigDecimal.class),
                BasicJsonMarshallerTypedExpression.value(Expression::bigInteger, BigIntegerExpression.class, BigInteger.class),
                BasicJsonMarshallerTypedExpression.value(Expression::booleanExpression, BooleanExpression.class, Boolean.class),
                BasicJsonMarshallerTypedExpression.binary(Expression::divide, DivideExpression.class),
                BasicJsonMarshallerTypedExpression.value(Expression::doubleExpression, DoubleExpression.class, Double.class),
                BasicJsonMarshallerTypedExpression.binary(Expression::equalsExpression, EqualsExpression.class),
                BasicJsonMarshallerTypedExpression.function(),
                BasicJsonMarshallerTypedExpression.binary(Expression::greaterThan, GreaterThanExpression.class),
                BasicJsonMarshallerTypedExpression.binary(Expression::greaterThanEquals, GreaterThanEqualsExpression.class),
                BasicJsonMarshallerTypedExpression.binary(Expression::lessThan, LessThanExpression.class),
                BasicJsonMarshallerTypedExpression.binary(Expression::lessThanEquals, LessThanEqualsExpression.class),
                BasicJsonMarshallerTypedExpression.value(Expression::localDate, LocalDateExpression.class, LocalDate.class),
                BasicJsonMarshallerTypedExpression.value(Expression::localDateTime, LocalDateTimeExpression.class, LocalDateTime.class),
                BasicJsonMarshallerTypedExpression.value(Expression::localTime, LocalTimeExpression.class, LocalTime.class),
                BasicJsonMarshallerTypedExpression.value(Expression::longExpression, LongExpression.class, Long.class),
                BasicJsonMarshallerTypedExpression.binary(Expression::modulo, ModuloExpression.class),
                BasicJsonMarshallerTypedExpression.binary(Expression::multiply, MultiplyExpression.class),
                BasicJsonMarshallerTypedExpression.unary(Expression::negative, NegativeExpression.class),
                BasicJsonMarshallerTypedExpression.unary(Expression::not, NotExpression.class),
                BasicJsonMarshallerTypedExpression.binary(Expression::notEquals, NotEqualsExpression.class),
                BasicJsonMarshallerTypedExpression.binary(Expression::or, OrExpression.class),
                BasicJsonMarshallerTypedExpression.binary(Expression::power, PowerExpression.class),
                BasicJsonMarshallerTypedExpression.reference(),
                BasicJsonMarshallerTypedExpression.binary(Expression::subtract, SubtractExpression.class),
                BasicJsonMarshallerTypedExpression.value(Expression::string, StringExpression.class, String.class),
                BasicJsonMarshallerTypedExpression.binary(Expression::xor, XorExpression.class),
                BasicJsonMarshallerTypedJsonNode.instance(),
                BasicJsonMarshallerTypedJsonPropertyName.instance(),
                BasicJsonMarshallerTypedCollectionList.instance(),
                BasicJsonMarshallerTypedLocalDate.instance(),
                BasicJsonMarshallerTypedLocalDateTime.instance(),
                BasicJsonMarshallerTypedLocale.instance(),
                BasicJsonMarshallerTypedLocalTime.instance(),
                BasicJsonMarshallerTypedMap.instance(),
                BasicJsonMarshallerTypedMathContext.instance(),
                BasicJsonMarshallerTypedNodeSelector.instance(),
                BasicJsonMarshallerTypedNumberByte.instance(),
                BasicJsonMarshallerTypedNumberShort.instance(),
                BasicJsonMarshallerTypedNumberInteger.instance(),
                BasicJsonMarshallerTypedNumberLong.instance(),
                BasicJsonMarshallerTypedNumberFloat.instance(),
                BasicJsonMarshallerTypedOptional.instance(),
                BasicJsonMarshallerTypedRange.instance(),
                BasicJsonMarshallerTypedRoundingMode.instance(),
                BasicJsonMarshallerTypedStringName.instance(),
                BasicJsonMarshallerTypedCollectionSet.instance()
        ).forEach(BasicJsonMarshaller::register);
    }

    /**
     * Registers the {@link BasicJsonMarshaller} for the given types.
     */
    @SafeVarargs
    static synchronized <T> Runnable register(final String typeName,
                                              final BiFunction<JsonNode, JsonNodeUnmarshallContext, T> from,
                                              final BiFunction<T, JsonNodeMarshallContext, JsonNode> to,
                                              final Class<T> type,
                                              final Class<? extends T>... types) {
        return BasicJsonMarshallerTypedGeneric.with(typeName, from, to, type, types)
                .registerGeneric();
    }

    /**
     * Returns the {@link Class} for the given type name.
     */
    static Optional<Class<?>> registeredType(final JsonString name) {
        Objects.requireNonNull(name, "name");

        return Optional.ofNullable(TYPENAME_TO_MARSHALLER.get(name.value())).map(BasicJsonMarshaller::type);
    }

    /**
     * Returns the type name identifying the given {@link Class} providing it is registered.
     */
    static Optional<JsonString> typeName(final Class<?> type) {
        Objects.requireNonNull(type, "type");

        return Optional.ofNullable(TYPENAME_TO_MARSHALLER.get(type.getName())).map(m -> JsonNode.string(m.toString()));
    }

    // instance.........................................................................................................

    /**
     * Package private to limit sub classing.
     */
    BasicJsonMarshaller() {
        super();
    }

    // register........................................................................................................

    /**
     * Registers this {@link BasicJsonMarshaller}.
     */
    abstract void register();

    abstract Class<T> type();

    abstract String typeName();

    final void registerTypeNameAndType() {
        registerWithTypeName(this.typeName());
        registerWithTypeName(this.type().getName());
    }

    /**
     * Registers a {@link BasicJsonMarshaller} for a {@link String type name}.
     */
    final void registerWithTypeName(final String typeName) {
        final BasicJsonMarshaller<?> previous = TYPENAME_TO_MARSHALLER.get(typeName);
        if (null != previous) {
            throw new IllegalArgumentException("Type " + CharSequences.quote(typeName) + " already registered to " + CharSequences.quoteAndEscape(previous.toString()) + " all=" + TYPENAME_TO_MARSHALLER.keySet());
        }

        TYPENAME_TO_MARSHALLER.putIfAbsent(typeName, this);
    }

    // unmarshall.....................................................................................................

    /**
     * Returns the value from its {@link JsonNode} representation.
     */
    final T unmarshall(final JsonNode node,
                       final JsonNodeUnmarshallContext context) {
        try {
            return node.isNull() ?
                    this.unmarshallNull(context) :
                    this.unmarshallNonNull(node, context);
        } catch (final JsonNodeUnmarshallException | NullPointerException cause) {
            throw cause;
        } catch (final RuntimeException cause) {
            throw new JsonNodeUnmarshallException("Failed to unmarshall node", node, cause);
        }
    }

    abstract T unmarshallNull(final JsonNodeUnmarshallContext context);

    abstract T unmarshallNonNull(final JsonNode node,
                                 final JsonNodeUnmarshallContext context);

    // marshall.......................................................................................................

    /**
     * Creates the {@link JsonNode} representation of the given value without any type/value enclosing object.
     */
    final JsonNode marshall(final T value,
                            final JsonNodeMarshallContext context) {
        return null == value ?
                JsonNode.nullNode() :
                this.marshallNonNull(value, context);
    }

    abstract JsonNode marshallNonNull(final T value,
                                      final JsonNodeMarshallContext context);

    /**
     * Creates the {@link JsonNode} with the type representation of the given value.
     */
    final JsonNode marshallWithType(final T value,
                                    final JsonNodeMarshallContext context) {
        return null == value ?
                JsonNode.nullNode() :
                this.marshallWithTypeNonNull(value, context);
    }

    abstract JsonNode marshallWithTypeNonNull(final T value,
                                              final JsonNodeMarshallContext context);

    // toString.........................................................................................................

    @Override
    public final String toString() {
        return this.typeName();
    }
}
