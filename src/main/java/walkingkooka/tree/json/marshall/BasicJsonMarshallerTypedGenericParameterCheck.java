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

import walkingkooka.reflect.ClassAttributes;
import walkingkooka.text.CharSequences;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The method included below has been extracted and will be replaced by {@link walkingkooka.tree.json.marshall.support.BasicJsonMarshallerTypedGenericParameterCheck}
 */
final class BasicJsonMarshallerTypedGenericParameterCheck {

    static <T> void checkTypes(final Class<T> type,
                               final List<Class<?>> all) {
        if (false == ClassAttributes.ABSTRACT.is(type)) {
            throw new IllegalArgumentException("Class " + CharSequences.quoteAndEscape(type.getName()) + " must be abstract");
        }
        final String notSubclasses = all.stream()
                .skip(1)
                .filter(t -> !type.isAssignableFrom(t))
                .map(t -> CharSequences.quoteAndEscape(t.getName()))
                .collect(Collectors.joining(", "));
        if (!notSubclasses.isEmpty()) {
            throw new IllegalArgumentException("Several classes " + notSubclasses + " are not sub classes of " + CharSequences.quoteAndEscape(type.getName()));
        }
    }
}
