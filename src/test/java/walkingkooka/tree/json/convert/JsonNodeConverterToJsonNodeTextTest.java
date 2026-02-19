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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.Either;
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.JsonString;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContexts;

import java.text.DateFormatSymbols;
import java.util.Locale;
import java.util.Optional;

public final class JsonNodeConverterToJsonNodeTextTest extends JsonNodeConverterTestCase<JsonNodeConverterToJsonNodeText<FakeJsonNodeConverterContext>, FakeJsonNodeConverterContext> {

    @Test
    public void testConvertStringToJsonNodeFails() {
        this.convertFails(
            "{}",
            JsonNode.class
        );
    }

    @Test
    public void testConvertDateTimeSymbolsToCharSequence() {
        this.convertAndCheck(
            DateTimeSymbols.fromDateFormatSymbols(
                new DateFormatSymbols(Locale.ENGLISH)
            ),
            CharSequence.class,
            "{\n" +
                "  \"ampms\": [\n" +
                "    \"AM\",\n" +
                "    \"PM\"\n" +
                "  ],\n" +
                "  \"monthNames\": [\n" +
                "    \"January\",\n" +
                "    \"February\",\n" +
                "    \"March\",\n" +
                "    \"April\",\n" +
                "    \"May\",\n" +
                "    \"June\",\n" +
                "    \"July\",\n" +
                "    \"August\",\n" +
                "    \"September\",\n" +
                "    \"October\",\n" +
                "    \"November\",\n" +
                "    \"December\"\n" +
                "  ],\n" +
                "  \"monthNameAbbreviations\": [\n" +
                "    \"Jan\",\n" +
                "    \"Feb\",\n" +
                "    \"Mar\",\n" +
                "    \"Apr\",\n" +
                "    \"May\",\n" +
                "    \"Jun\",\n" +
                "    \"Jul\",\n" +
                "    \"Aug\",\n" +
                "    \"Sep\",\n" +
                "    \"Oct\",\n" +
                "    \"Nov\",\n" +
                "    \"Dec\"\n" +
                "  ],\n" +
                "  \"weekDayNames\": [\n" +
                "    \"Sunday\",\n" +
                "    \"Monday\",\n" +
                "    \"Tuesday\",\n" +
                "    \"Wednesday\",\n" +
                "    \"Thursday\",\n" +
                "    \"Friday\",\n" +
                "    \"Saturday\"\n" +
                "  ],\n" +
                "  \"weekDayNameAbbreviations\": [\n" +
                "    \"Sun\",\n" +
                "    \"Mon\",\n" +
                "    \"Tue\",\n" +
                "    \"Wed\",\n" +
                "    \"Thu\",\n" +
                "    \"Fri\",\n" +
                "    \"Sat\"\n" +
                "  ]\n" +
                "}"
        );
    }

    @Test
    public void testConvertDateTimeSymbolsToCharSequence2() {
        this.convertAndCheck(
            this.createConverter(),
            DateTimeSymbols.fromDateFormatSymbols(
                new DateFormatSymbols(Locale.ENGLISH)
            ),
            CharSequence.class,
            this.createContext(
                Indentation.SPACES4,
                LineEnding.CR
            ),
            "{\r" +
                "    \"ampms\": [\r" +
                "        \"AM\",\r" +
                "        \"PM\"\r" +
                "    ],\r" +
                "    \"monthNames\": [\r" +
                "        \"January\",\r" +
                "        \"February\",\r" +
                "        \"March\",\r" +
                "        \"April\",\r" +
                "        \"May\",\r" +
                "        \"June\",\r" +
                "        \"July\",\r" +
                "        \"August\",\r" +
                "        \"September\",\r" +
                "        \"October\",\r" +
                "        \"November\",\r" +
                "        \"December\"\r" +
                "    ],\r" +
                "    \"monthNameAbbreviations\": [\r" +
                "        \"Jan\",\r" +
                "        \"Feb\",\r" +
                "        \"Mar\",\r" +
                "        \"Apr\",\r" +
                "        \"May\",\r" +
                "        \"Jun\",\r" +
                "        \"Jul\",\r" +
                "        \"Aug\",\r" +
                "        \"Sep\",\r" +
                "        \"Oct\",\r" +
                "        \"Nov\",\r" +
                "        \"Dec\"\r" +
                "    ],\r" +
                "    \"weekDayNames\": [\r" +
                "        \"Sunday\",\r" +
                "        \"Monday\",\r" +
                "        \"Tuesday\",\r" +
                "        \"Wednesday\",\r" +
                "        \"Thursday\",\r" +
                "        \"Friday\",\r" +
                "        \"Saturday\"\r" +
                "    ],\r" +
                "    \"weekDayNameAbbreviations\": [\r" +
                "        \"Sun\",\r" +
                "        \"Mon\",\r" +
                "        \"Tue\",\r" +
                "        \"Wed\",\r" +
                "        \"Thu\",\r" +
                "        \"Fri\",\r" +
                "        \"Sat\"\r" +
                "    ]\r" +
                "}"
        );
    }

    @Test
    public void testConvertEmailAddressToString() {
        final EmailAddress emailAddress = EmailAddress.parse("test@example.com");

        this.convertAndCheck(
            emailAddress,
            String.class,
            JsonNodeMarshallContexts.basic()
                .marshall(emailAddress)
                .toString()
        );
    }

    @Test
    public void testConvertEmailAddressToCharSequence() {
        final EmailAddress emailAddress = EmailAddress.parse("test@example.com");

        this.convertAndCheck(
            emailAddress,
            CharSequence.class,
            JsonNodeMarshallContexts.basic()
                .marshall(emailAddress)
                .toString()
        );
    }

    @Test
    public void testConvertJsonObjectToCharSequence() {
        final JsonNode object = JsonNode.object()
            .set(
                JsonPropertyName.with("Hello"),
                JsonNode.string("World")
            );

        this.convertAndCheck(
            object,
            CharSequence.class,
            object.toString()
        );
    }

    @Override
    public JsonNodeConverterToJsonNodeText<FakeJsonNodeConverterContext> createConverter() {
        return JsonNodeConverterToJsonNodeText.instance();
    }

    @Override
    public FakeJsonNodeConverterContext createContext() {
        return this.createContext(
            Indentation.SPACES2,
            LineEnding.NL
        );
    }

    public FakeJsonNodeConverterContext createContext(final Indentation indentation,
                                                      final LineEnding lineEnding) {
        return new FakeJsonNodeConverterContext() {

            @Override
            public Indentation indentation() {
                return indentation;
            }

            @Override
            public LineEnding lineEnding() {
                return lineEnding;
            }

            @Override
            public boolean canConvert(final Object value,
                                      final Class<?> type) {
                return this.converter.canConvert(
                    value,
                    type,
                    this
                );
            }

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return this.converter.convert(
                    value,
                    target,
                    this
                );
            }

            private final Converter<FakeJsonNodeConverterContext> converter = Converters.collection(
                Lists.of(
                    JsonNodeConverters.toJsonNode(),
                    Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString()
                )
            );

            @Override
            public Optional<JsonString> typeName(final Class<?> type) {
                return this.marshallContext.typeName(type);
            }

            @Override
            public JsonNode marshall(final Object value) {
                return this.marshallContext.marshall(value);
            }

            private final JsonNodeMarshallContext marshallContext = JsonNodeMarshallContexts.basic();
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createConverter(),
            "* to JSON text"
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonNodeConverterToJsonNodeText<FakeJsonNodeConverterContext>> type() {
        return Cast.to(JsonNodeConverterToJsonNodeText.class);
    }
}
