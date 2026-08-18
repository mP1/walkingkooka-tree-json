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

import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.tree.json.JsonString;

import java.util.Optional;

public interface JsonNodeContextTesting extends TreePrintableTesting {

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
