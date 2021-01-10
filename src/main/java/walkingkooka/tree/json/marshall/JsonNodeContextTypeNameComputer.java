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

import walkingkooka.reflect.StaticHelper;

final class JsonNodeContextTypeNameComputer implements StaticHelper {

    static String compute(final String simpleClassName) {
        final StringBuilder b = new StringBuilder();

        for (final char c : simpleClassName.toCharArray()) {
            if (Character.isLetter(c) & Character.isUpperCase(c)) {
                if (b.length() > 0) {
                    b.append('-');
                }
                b.append(Character.toLowerCase(c));
            } else {
                b.append(c);
            }
        }

        return b.toString();
    }

    /**
     * Stop creation
     */
    private JsonNodeContextTypeNameComputer() {
        throw new UnsupportedOperationException();
    }
}
