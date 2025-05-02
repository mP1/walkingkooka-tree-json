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
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class InvalidCharacterExceptionTest implements ClassTesting<InvalidCharacterException> {

    @Test
    public void testGetStackTrace() {
        final InvalidCharacterException exception = new InvalidCharacterException(
            "text",
            2, // position
            InvalidCharacterException.NO_COLUMN,
            InvalidCharacterException.NO_LINE,
            InvalidCharacterException.NO_APPEND_TO_MESSAGE,
            null
        );
        assertArrayEquals(
            new StackTraceElement[0],
            exception.getStackTrace(),
            "stack trace"
        );
    }

    // class............................................................................................................

    @Override
    public Class<InvalidCharacterException> type() {
        return InvalidCharacterException.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
