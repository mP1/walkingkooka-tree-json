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
import walkingkooka.locale.LocaleLanguageTagSet;
import walkingkooka.tree.json.JsonNode;

import java.util.stream.Collectors;

public final class BasicJsonMarshallerTypedLocaleLanguageTagSetTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedLocaleLanguageTagSet, LocaleLanguageTagSet> {

    @Test
    public void testUnmarshallAllLocales() {
        final LocaleLanguageTagSet set = LocaleLanguageTagSet.EMPTY.setElements(
            LOCALE_CONTEXT.availableLocales()
                .stream()
                .map(LocaleLanguageTag::fromLocale)
                .collect(Collectors.toList())
        );

        this.unmarshallAndCheck(
            BasicJsonMarshallerTypedLocaleLanguageTagSet.instance(),
            JsonNode.string(set.text()),
            JsonNodeUnmarshallContexts.fake(),
            set
        );
    }

    @Override
    BasicJsonMarshallerTypedLocaleLanguageTagSet marshaller() {
        return BasicJsonMarshallerTypedLocaleLanguageTagSet.instance();
    }

    @Override
    LocaleLanguageTagSet value() {
        return LocaleLanguageTagSet.parse("en-AU,en-NZ");
    }

    @Override
    JsonNode node() {
        return JsonNode.string("en-AU,en-NZ");
    }

    @Override
    LocaleLanguageTagSet jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "locale-language-tag-set";
    }

    @Override
    Class<LocaleLanguageTagSet> marshallerType() {
        return LocaleLanguageTagSet.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedLocaleLanguageTagSet> type() {
        return BasicJsonMarshallerTypedLocaleLanguageTagSet.class;
    }
}
