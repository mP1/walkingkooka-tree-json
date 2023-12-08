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
package walkingkooka.tree.json.parser;

import walkingkooka.text.cursor.parser.ParserToken;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a close array (parens) symbol token.
 */
public final class JsonNodeArrayEndSymbolParserToken extends JsonNodeSymbolParserToken {

    static JsonNodeArrayEndSymbolParserToken with(final String value, final String text) {
        checkValue(value);
        checkText(text);

        return new JsonNodeArrayEndSymbolParserToken(value, text);
    }

    private JsonNodeArrayEndSymbolParserToken(final String value, final String text) {
        super(value, text);
    }
    
    // removeFirstIf....................................................................................................

    @Override
    public Optional<JsonNodeArrayEndSymbolParserToken> removeFirstIf(final Predicate<ParserToken> predicate) {
        return ParserToken.removeFirstIfLeaf(
                this,
                predicate,
                JsonNodeArrayEndSymbolParserToken.class
        );
    }

    // removeIf.........................................................................................................

    @Override
    public Optional<JsonNodeArrayEndSymbolParserToken> removeIf(final Predicate<ParserToken> predicate) {
        return ParserToken.removeIfLeaf(
                this,
                predicate,
                JsonNodeArrayEndSymbolParserToken.class
        );
    }

    // replaceFirstIf...................................................................................................

    @Override
    public JsonNodeArrayEndSymbolParserToken replaceFirstIf(final Predicate<ParserToken> predicate,
                                                            final Function<ParserToken, ParserToken> mapper) {
        return ParserToken.replaceFirstIf(
                this,
                predicate,
                mapper,
                JsonNodeArrayEndSymbolParserToken.class
        );
    }

    // replaceIf........................................................................................................

    @Override
    public JsonNodeArrayEndSymbolParserToken replaceIf(final Predicate<ParserToken> predicate,
                                                       final Function<ParserToken, ParserToken> mapper) {
        return ParserToken.replaceIf(
                this,
                predicate,
                mapper,
                JsonNodeArrayEndSymbolParserToken.class
        );
    }

    // visitor .........................................................................................................

    @Override
    public void accept(final JsonNodeParserTokenVisitor visitor) {
        visitor.visit(this);
    }

    // Object ...............................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof JsonNodeArrayEndSymbolParserToken;
    }
}
