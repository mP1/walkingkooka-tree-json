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
import walkingkooka.ContextTesting;
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface JsonNodeContextTesting<C extends JsonNodeContext> extends ContextTesting<C>,
    TreePrintableTesting {

    // registeredType....................................................................................................

    @Test
    default void testRegisteredTypeNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .registeredType(null)
        );
    }

    @Test
    default void testRegisteredTypeUnknown() {
        this.registeredTypeAndCheck(
            this.getClass().getName(),
            Optional.empty()
        );
    }

    @Test
    default void testRegisteredTypeBoolean() {
        this.registeredTypeAndCheck(
            "boolean",
            Optional.of(Boolean.class)
        );
    }

    @Test
    default void testRegisteredTypeByte() {
        this.registeredTypeAndCheck(
            "byte",
            Optional.of(Byte.class)
        );
    }

    @Test
    default void testRegisteredTypeShort() {
        this.registeredTypeAndCheck(
            "short",
            Optional.of(Short.class)
        );
    }

    @Test
    default void testRegisteredTypeInteger() {
        this.registeredTypeAndCheck(
            "int",
            Optional.of(Integer.class)
        );
    }

    @Test
    default void testRegisteredTypeLong() {
        this.registeredTypeAndCheck(
            "long",
            Optional.of(Long.class)
        );
    }

    @Test
    default void testRegisteredTypeFloat() {
        this.registeredTypeAndCheck(
            "float",
            Optional.of(Float.class)
        );
    }

    @Test
    default void testRegisteredTypeDouble() {
        this.registeredTypeAndCheck(
            "double",
            Optional.of(Double.class)
        );
    }

    @Test
    default void testRegisteredTypeCharacter() {
        this.registeredTypeAndCheck(
            "character",
            Optional.of(Character.class)
        );
    }

    @Test
    default void testRegisteredTypeString() {
        this.registeredTypeAndCheck(
            "string",
            Optional.of(String.class)
        );
    }

    @Test
    default void testRegisteredTypeBigDecimal() {
        this.registeredTypeAndCheck(
            "big-decimal",
            Optional.of(BigDecimal.class)
        );
    }

    @Test
    default void testRegisteredTypeBigInteger() {
        this.registeredTypeAndCheck(
            "big-integer",
            Optional.of(BigInteger.class)
        );
    }

    @Test
    default void testRegisteredTypeLocalDate() {
        this.registeredTypeAndCheck(
            "local-date",
            Optional.of(LocalDate.class)
        );
    }

    @Test
    default void testRegisteredTypeLocalDateTime() {
        this.registeredTypeAndCheck(
            "local-date-time",
            Optional.of(LocalDateTime.class)
        );
    }

    @Test
    default void testRegisteredTypeLocalTime() {
        this.registeredTypeAndCheck(
            "local-time",
            Optional.of(LocalTime.class)
        );
    }

    @Test
    default void testRegisteredTypeLocale() {
        this.registeredTypeAndCheck(
            "locale",
            Optional.of(Locale.class)
        );
    }

    @Test
    default void testRegisteredTypeMathContext() {
        this.registeredTypeAndCheck(
            "math-context",
            Optional.of(MathContext.class)
        );
    }

    @Test
    default void testRegisteredTypeList() {
        this.registeredTypeAndCheck(
            "list",
            Optional.of(List.class)
        );
    }

    @Test
    default void testRegisteredTypeSet() {
        this.registeredTypeAndCheck(
            "set",
            Optional.of(Set.class)
        );
    }

    @Test
    default void testRegisteredTypeMap() {
        this.registeredTypeAndCheck(
            "map",
            Optional.of(Map.class)
        );
    }

    @Test
    default void testRegisteredTypeOptional() {
        this.registeredTypeAndCheck(
            "optional",
            Optional.of(Optional.class)
        );
    }

    // typeName ........................................................................................................

    @Test
    default void testTypeNameNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext().typeName(null)
        );
    }

    @Test
    default void testTypeNameUnknown() {
        this.typeNameAndCheck(
            this.getClass()
        );
    }

    @Test
    default void testTypeNameBoolean() {
        this.typeNameAndCheck(
            Boolean.class,
            "boolean"
        );
    }

    @Test
    default void testTypeNameByte() {
        this.typeNameAndCheck(
            Byte.class,
            "byte"
        );
    }

    @Test
    default void testTypeNameShort() {
        this.typeNameAndCheck(
            Short.class,
            "short"
        );
    }

    @Test
    default void testTypeNameInteger() {
        this.typeNameAndCheck(
            Integer.class,
            "int"
        );
    }

    @Test
    default void testTypeNameLong() {
        this.typeNameAndCheck(
            Long.class,
            "long"
        );
    }

    @Test
    default void testTypeNameFloat() {
        this.typeNameAndCheck(
            Float.class,
            "float"
        );
    }

    @Test
    default void testTypeNameDouble() {
        this.typeNameAndCheck(
            Double.class,
            "double"
        );
    }

    @Test
    default void testTypeNameCharacter() {
        this.typeNameAndCheck(
            Character.class,
            "character"
        );
    }

    @Test
    default void testTypeNameString() {
        this.typeNameAndCheck(
            String.class,
            "string"
        );
    }

    @Test
    default void testTypeNameBigDecimal() {
        this.typeNameAndCheck(
            BigDecimal.class,
            "big-decimal"
        );
    }

    @Test
    default void testTypeNameBigInteger() {
        this.typeNameAndCheck(
            BigInteger.class,
            "big-integer"
        );
    }

    @Test
    default void testTypeNameLocalDate() {
        this.typeNameAndCheck(
            LocalDate.class,
            "local-date"
        );
    }

    @Test
    default void testTypeNameLocalDateTime() {
        this.typeNameAndCheck(
            LocalDateTime.class,
            "local-date-time"
        );
    }

    @Test
    default void testTypeNameLocalTime() {
        this.typeNameAndCheck(
            LocalTime.class,
            "local-time"
        );
    }

    @Test
    default void testTypeNameLocale() {
        this.typeNameAndCheck(
            Locale.class,
            "locale"
        );
    }

    @Test
    default void testTypeNameMathContext() {
        this.typeNameAndCheck(
            MathContext.class,
            "math-context"
        );
    }

    @Test
    default void testTypeNameList() {
        this.typeNameAndCheck(
            List.class,
            "list"
        );
    }

    @Test
    default void testTypeNameMap() {
        this.typeNameAndCheck(
            Map.class,
            "map"
        );
    }

    @Test
    default void testTypeNameSet() {
        this.typeNameAndCheck(
            Set.class,
            "set"
        );
    }

    @Test
    default void testTypeNameOptional() {
        this.typeNameAndCheck(
            Optional.class,
            "optional"
        );
    }

    // registeredType....................................................................................................

    default void registeredTypeAndCheck(final String name,
                                        final Optional<Class<?>> type) {
        this.registeredTypeAndCheck(
            this.createContext(),
            JsonNode.string(name),
            type
        );
    }

    default void registeredTypeAndCheck(final JsonString name,
                                        final Optional<Class<?>> type) {
        this.registeredTypeAndCheck(
            this.createContext(),
            name,
            type
        );
    }

    default void registeredTypeAndCheck(final JsonNodeContext context,
                                        final JsonString name,
                                        final Optional<Class<?>> type) {
        this.checkEquals(
            type,
            context.registeredType(name),
            () -> context + " registeredType " + name
        );
    }

    // typeName ........................................................................................................

    default void typeNameAndCheck(final Class<?> type) {
        this.typeNameAndCheck(
            type,
            Optional.empty()
        );
    }

    default void typeNameAndCheck(final Class<?> type,
                                  final String expected) {
        this.typeNameAndCheck(
            type,
            JsonNode.string(expected)
        );
    }

    default void typeNameAndCheck(final Class<?> type,
                                  final JsonString expected) {
        this.typeNameAndCheck(
            this.createContext(),
            type,
            Optional.of(expected)
        );
    }

    default void typeNameAndCheck(final Class<?> type,
                                  final Optional<JsonString> expected) {
        this.typeNameAndCheck(this.createContext(),
            type,
            expected);
    }

    default void typeNameAndCheck(final JsonNodeContext context,
                                  final Class<?> type,
                                  final Optional<JsonString> expected) {
        this.checkEquals(
            expected,
            context.typeName(type),
            () -> context + " typeName " + type.getName()
        );
    }
}
