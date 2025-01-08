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

import java.util.Objects;

/**
 * A sub class of {@link java.lang.IllegalArgumentException} with an empty stacktrace. This is only returned by {@link BasicJsonMarshallerTypedIllegalArgumentException}
 */
final class IllegalArgumentException extends java.lang.IllegalArgumentException {

    private static final long serialVersionUID = 1;

    IllegalArgumentException() {
    }

    IllegalArgumentException(final String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    // hashCode & equals added to assist test assertions

    @Override
    public int hashCode() {
        return Objects.hash(this.getMessage());
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof java.lang.IllegalArgumentException && this.equals0((java.lang.IllegalArgumentException) other);
    }

    private boolean equals0(final java.lang.IllegalArgumentException other) {
        return Objects.equals(this.getMessage(), other.getMessage());
    }
}
