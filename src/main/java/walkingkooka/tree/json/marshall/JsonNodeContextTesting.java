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
            JsonNode.string(
                this.getClass().getName()
            ),
            Optional.empty()
        );
    }

    @Test
    default void testRegisteredTypeBoolean() {
        this.registeredTypeAndCheck(
            JsonNode.string("boolean"),
            Optional.of(Boolean.class)
        );
    }

    @Test
    default void testRegisteredTypeByte() {
        this.registeredTypeAndCheck(
            JsonNode.string("byte"),
            Optional.of(Byte.class)
        );
    }

    @Test
    default void testRegisteredTypeShort() {
        this.registeredTypeAndCheck(
            JsonNode.string("short"),
            Optional.of(Short.class)
        );
    }

    @Test
    default void testRegisteredTypeInteger() {
        this.registeredTypeAndCheck(
            JsonNode.string("int"),
            Optional.of(Integer.class)
        );
    }

    @Test
    default void testRegisteredTypeLong() {
        this.registeredTypeAndCheck(
            JsonNode.string("long"),
            Optional.of(Long.class)
        );
    }

    @Test
    default void testRegisteredTypeFloat() {
        this.registeredTypeAndCheck(
            JsonNode.string("float"),
            Optional.of(Float.class)
        );
    }

    @Test
    default void testRegisteredTypeDouble() {
        this.registeredTypeAndCheck(
            JsonNode.string("double"),
            Optional.of(Double.class)
        );
    }

    @Test
    default void testRegisteredTypeCharacter() {
        this.registeredTypeAndCheck(
            JsonNode.string("character"),
            Optional.of(Character.class)
        );
    }

    @Test
    default void testRegisteredTypeString() {
        this.registeredTypeAndCheck(
            JsonNode.string("string"),
            Optional.of(String.class)
        );
    }

    @Test
    default void testRegisteredTypeBigDecimal() {
        this.registeredTypeAndCheck(
            JsonNode.string("big-decimal"),
            Optional.of(BigDecimal.class)
        );
    }

    @Test
    default void testRegisteredTypeBigInteger() {
        this.registeredTypeAndCheck(
            JsonNode.string("big-integer"),
            Optional.of(BigInteger.class)
        );
    }

    @Test
    default void testRegisteredTypeLocalDate() {
        this.registeredTypeAndCheck(
            JsonNode.string("local-date"),
            Optional.of(LocalDate.class)
        );
    }

    @Test
    default void testRegisteredTypeLocalDateTime() {
        this.registeredTypeAndCheck(
            JsonNode.string("local-date-time"),
            Optional.of(LocalDateTime.class)
        );
    }

    @Test
    default void testRegisteredTypeLocalTime() {
        this.registeredTypeAndCheck(
            JsonNode.string("local-time"),
            Optional.of(LocalTime.class)
        );
    }

    @Test
    default void testRegisteredTypeLocale() {
        this.registeredTypeAndCheck(
            JsonNode.string("locale"),
            Optional.of(Locale.class)
        );
    }

    @Test
    default void testRegisteredTypeMathContext() {
        this.registeredTypeAndCheck(
            JsonNode.string("math-context"),
            Optional.of(MathContext.class)
        );
    }

    @Test
    default void testRegisteredTypeList() {
        this.registeredTypeAndCheck(
            JsonNode.string("list"),
            Optional.of(List.class)
        );
    }

    @Test
    default void testRegisteredTypeSet() {
        this.registeredTypeAndCheck(
            JsonNode.string("set"),
            Optional.of(Set.class)
        );
    }

    @Test
    default void testRegisteredTypeMap() {
        this.registeredTypeAndCheck(
            JsonNode.string("map"),
            Optional.of(Map.class)
        );
    }

    @Test
    default void testRegisteredTypeOptional() {
        this.registeredTypeAndCheck(
            JsonNode.string("optional"),
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
            this.getClass(),
            Optional.empty()
        );
    }

    @Test
    default void testTypeNameBoolean() {
        this.typeNameAndCheck(
            Boolean.class,
            Optional.of(
                JsonNode.string("boolean")
            )
        );
    }

    @Test
    default void testTypeNameByte() {
        this.typeNameAndCheck(
            Byte.class,
            Optional.of(
                JsonNode.string("byte")
            )
        );
    }

    @Test
    default void testTypeNameShort() {
        this.typeNameAndCheck(
            Short.class,
            Optional.of(
                JsonNode.string("short")
            )
        );
    }

    @Test
    default void testTypeNameInteger() {
        this.typeNameAndCheck(
            Integer.class,
            Optional.of(
                JsonNode.string("int")
            )
        );
    }

    @Test
    default void testTypeNameLong() {
        this.typeNameAndCheck(
            Long.class,
            Optional.of(
                JsonNode.string("long")
            )
        );
    }

    @Test
    default void testTypeNameFloat() {
        this.typeNameAndCheck(
            Float.class,
            Optional.of(
                JsonNode.string("float")
            )
        );
    }

    @Test
    default void testTypeNameDouble() {
        this.typeNameAndCheck(
            Double.class,
            Optional.of(
                JsonNode.string("double")
            )
        );
    }

    @Test
    default void testTypeNameCharacter() {
        this.typeNameAndCheck(
            Character.class,
            Optional.of(
                JsonNode.string("character")
            )
        );
    }

    @Test
    default void testTypeNameString() {
        this.typeNameAndCheck(
            String.class,
            Optional.of(
                JsonNode.string("string")
            )
        );
    }

    @Test
    default void testTypeNameBigDecimal() {
        this.typeNameAndCheck(
            BigDecimal.class,
            Optional.of(
                JsonNode.string("big-decimal")
            )
        );
    }

    @Test
    default void testTypeNameBigInteger() {
        this.typeNameAndCheck(
            BigInteger.class,
            Optional.of(
                JsonNode.string("big-integer")
            )
        );
    }

    @Test
    default void testTypeNameLocalDate() {
        this.typeNameAndCheck(
            LocalDate.class,
            Optional.of(
                JsonNode.string("local-date")
            )
        );
    }

    @Test
    default void testTypeNameLocalDateTime() {
        this.typeNameAndCheck(
            LocalDateTime.class,
            Optional.of(
                JsonNode.string("local-date-time")
            )
        );
    }

    @Test
    default void testTypeNameLocalTime() {
        this.typeNameAndCheck(
            LocalTime.class,
            Optional.of(
                JsonNode.string("local-time")
            )
        );
    }

    @Test
    default void testTypeNameLocale() {
        this.typeNameAndCheck(
            Locale.class,
            Optional.of(
                JsonNode.string("locale")
            )
        );
    }

    @Test
    default void testTypeNameMathContext() {
        this.typeNameAndCheck(
            MathContext.class,
            Optional.of(
                JsonNode.string("math-context")
            )
        );
    }

    @Test
    default void testTypeNameList() {
        this.typeNameAndCheck(
            List.class,
            Optional.of(
                JsonNode.string("list")
            )
        );
    }

    @Test
    default void testTypeNameMap() {
        this.typeNameAndCheck(
            Map.class,
            Optional.of(
                JsonNode.string("map")
            )
        );
    }

    @Test
    default void testTypeNameSet() {
        this.typeNameAndCheck(
            Set.class,
            Optional.of(
                JsonNode.string("set")
            )
        );
    }

    @Test
    default void testTypeNameOptional() {
        this.typeNameAndCheck(
            Optional.class,
            Optional.of(
                JsonNode.string("optional")
            )
        );
    }

    // registeredType....................................................................................................

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
