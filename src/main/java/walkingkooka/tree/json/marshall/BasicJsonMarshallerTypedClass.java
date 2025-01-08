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
import walkingkooka.text.CharSequences;
import walkingkooka.tree.json.JsonNode;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A {@link BasicJsonMarshaller} that only marshalls/unmarshalls {@link Class classes} that have been registered.
 */
final class BasicJsonMarshallerTypedClass extends BasicJsonMarshallerTyped<Class<?>> {

    static BasicJsonMarshallerTypedClass instance() {
        return new BasicJsonMarshallerTypedClass();
    }

    private BasicJsonMarshallerTypedClass() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<Class<?>> type() {
        return Cast.to(Class.class);
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(Class.class);
    }

    @Override
    Class<?> unmarshallNonNull(final JsonNode node,
                               final JsonNodeUnmarshallContext context) {
        final String className = node.stringOrFail();
        final BasicJsonMarshaller<?> marshaller = TYPENAME_TO_MARSHALLER.get(className);

        final Class<?> classs;

        if (null != marshaller) {
            classs = marshaller.type();
        } else {
            switch (className) {
                case "java.lang.Object":
                    classs = Object.class;
                    break;
                case "java.util.List":
                    classs = List.class;
                    break;
                case "java.util.Map":
                    classs = Map.class;
                    break;
                case "java.util.Set":
                    classs = Set.class;
                    break;
                default:
                    throw new IllegalStateException("Unknown class " + CharSequences.quote(className));
            }
        }
        return classs;
    }

    @Override
    Class<?> unmarshallNull(final JsonNodeUnmarshallContext context) {
        throw new NullPointerException();
    }

    @Override
    JsonNode marshallNonNull(final Class<?> classs,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(classToString(classs));
    }
}
