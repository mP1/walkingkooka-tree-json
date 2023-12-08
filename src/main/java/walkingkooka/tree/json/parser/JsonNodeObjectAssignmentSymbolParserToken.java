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
 * Represents a close / right parens symbol token.
 */
public final class JsonNodeObjectAssignmentSymbolParserToken extends JsonNodeSymbolParserToken {

    static JsonNodeObjectAssignmentSymbolParserToken with(final String value, final String text) {
        checkValue(value);
        checkText(text);

        return new JsonNodeObjectAssignmentSymbolParserToken(value, text);
    }

    private JsonNodeObjectAssignmentSymbolParserToken(final String value, final String text) {
        super(value, text);
    }

    // removeFirstIf....................................................................................................

    @Override
    public Optional<JsonNodeObjectAssignmentSymbolParserToken> removeFirstIf(final Predicate<ParserToken> predicate) {
        return ParserToken.removeFirstIfLeaf(
                this,
                predicate,
                JsonNodeObjectAssignmentSymbolParserToken.class
        );
    }
    
    // removeIf.........................................................................................................

    @Override
    public Optional<JsonNodeObjectAssignmentSymbolParserToken> removeIf(final Predicate<ParserToken> predicate) {
        return ParserToken.removeIfLeaf(
                this,
                predicate,
                JsonNodeObjectAssignmentSymbolParserToken.class
        );
    }

    // replaceFirstIf...................................................................................................

    @Override
    public JsonNodeObjectAssignmentSymbolParserToken replaceFirstIf(final Predicate<ParserToken> predicate,
                                                                    final Function<ParserToken, ParserToken> mapper) {
        return ParserToken.replaceFirstIf(
                this,
                predicate,
                mapper,
                JsonNodeObjectAssignmentSymbolParserToken.class
        );
    }

    // replaceIf........................................................................................................

    @Override
    public JsonNodeObjectAssignmentSymbolParserToken replaceIf(final Predicate<ParserToken> predicate,
                                                          final ParserToken token) {
        return ParserToken.replaceIf(
                this,
                predicate,
                token,
                JsonNodeObjectAssignmentSymbolParserToken.class
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
        return other instanceof JsonNodeObjectAssignmentSymbolParserToken;
    }
}
