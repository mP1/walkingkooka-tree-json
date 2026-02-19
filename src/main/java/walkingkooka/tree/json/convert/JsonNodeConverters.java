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

package walkingkooka.tree.json.convert;

import walkingkooka.convert.Converter;
import walkingkooka.reflect.PublicStaticHelper;

/**
 * A collection of {@link Converter} for walkingkooka.tree.json
 */
public final class JsonNodeConverters implements PublicStaticHelper {

    /**
     * {@see JsonNodeConverterJsonNodeTo}
     */
    public static <C extends JsonNodeConverterContext> Converter<C> jsonNodeTo() {
        return JsonNodeConverterJsonNodeTo.instance();
    }

    /**
     * {@see JsonNodeConverterTextToObject}
     */
    public static <C extends JsonNodeConverterContext> Converter<C> textToObject() {
        return JsonNodeConverterTextToObject.instance();
    }

    /**
     * {@see JsonNodeConverterTextToJsonNode}
     */
    public static <C extends JsonNodeConverterContext> Converter<C> textToJsonNode() {
        return JsonNodeConverterTextToJsonNode.instance();
    }

    /**
     * {@see JsonNodeConverterToJsonNode}
     */
    public static <C extends JsonNodeConverterContext> Converter<C> toJsonNode() {
        return JsonNodeConverterToJsonNode.instance();
    }

    /**
     * {@see JsonNodeConverterToJsonNodeText}
     */
    public static <C extends JsonNodeConverterContext> Converter<C> toJsonText() {
        return JsonNodeConverterToJsonNodeText.instance();
    }

    /**
     * Stop creation
     */
    private JsonNodeConverters() {
        throw new UnsupportedOperationException();
    }
}
