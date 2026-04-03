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
import walkingkooka.locale.LocaleLanguageTag;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedLocaleLanguageTagTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedLocaleLanguageTag, LocaleLanguageTag> {

    @Test
    public void testUnmarshall2() {
        final LocaleLanguageTag localeLanguageTag = LocaleLanguageTag.parse("en-NZ");

        this.unmarshallAndCheck(
            BasicJsonMarshallerTypedLocaleLanguageTag.instance(),
            JsonNode.string(localeLanguageTag.value()),
            JsonNodeUnmarshallContexts.fake(),
            localeLanguageTag
        );
    }

    @Override
    BasicJsonMarshallerTypedLocaleLanguageTag marshaller() {
        return BasicJsonMarshallerTypedLocaleLanguageTag.instance();
    }

    @Override
    LocaleLanguageTag value() {
        return LocaleLanguageTag.parse("en-AU");
    }

    @Override
    JsonNode node() {
        return JsonNode.string(
            this.value()
                .toString()
        );
    }

    @Override
    LocaleLanguageTag jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "locale-language-tag";
    }

    @Override
    Class<LocaleLanguageTag> marshallerType() {
        return LocaleLanguageTag.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedLocaleLanguageTag> type() {
        return BasicJsonMarshallerTypedLocaleLanguageTag.class;
    }
}
