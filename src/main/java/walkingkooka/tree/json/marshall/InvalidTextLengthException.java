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

/**
 * A sub class of {@link walkingkooka.InvalidTextLengthException} with an empty stacktrace. This is only returned by {@link BasicJsonMarshallerTypedInvalidTextLengthException}
 */
final class InvalidTextLengthException extends walkingkooka.InvalidTextLengthException {

    private static final long serialVersionUID = 1;

    InvalidTextLengthException(final String label,
                                      final String text,
                                      final int min,
                                      final int max) {
        super(label, text, min, max);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
