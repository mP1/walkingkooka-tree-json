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

import walkingkooka.collect.map.Maps;
import walkingkooka.collect.set.Sets;
import walkingkooka.predicate.character.CharPredicates;
import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.cursor.TextCursors;
import walkingkooka.text.cursor.parser.CharacterParserToken;
import walkingkooka.text.cursor.parser.DoubleParserToken;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserReporters;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.cursor.parser.Parsers;
import walkingkooka.text.cursor.parser.StringParserToken;
import walkingkooka.text.cursor.parser.ebnf.EbnfGrammarParserToken;
import walkingkooka.text.cursor.parser.ebnf.EbnfIdentifierName;
import walkingkooka.text.cursor.parser.ebnf.EbnfParserContexts;
import walkingkooka.text.cursor.parser.ebnf.EbnfParserToken;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Holds numerous {@link Parser parsers} that return individual json nodes including an entire {@link JsonNodeObjectParserToken}.
 */
public final class JsonNodeParsers implements PublicStaticHelper {

    private static final Parser<ParserContext> ARRAY_BEGIN_SYMBOL = symbol('[', JsonNodeParserToken::arrayBeginSymbol, JsonNodeArrayBeginSymbolParserToken.class);
    private static final Parser<ParserContext> ARRAY_END_SYMBOL = symbol(']', JsonNodeParserToken::arrayEndSymbol, JsonNodeArrayEndSymbolParserToken.class);
    private static final Parser<ParserContext> OBJECT_ASSIGNMENT_SYMBOL = symbol(':', JsonNodeParserToken::objectAssignmentSymbol, JsonNodeObjectAssignmentSymbolParserToken.class);
    private static final Parser<ParserContext> OBJECT_BEGIN_SYMBOL = symbol('{', JsonNodeParserToken::objectBeginSymbol, JsonNodeObjectBeginSymbolParserToken.class);
    private static final Parser<ParserContext> OBJECT_END_SYMBOL = symbol('}', JsonNodeParserToken::objectEndSymbol, JsonNodeObjectEndSymbolParserToken.class);
    private static final Parser<ParserContext> SEPARATOR_SYMBOL = symbol(',', JsonNodeParserToken::separatorSymbol, JsonNodeSeparatorSymbolParserToken.class);

    static final EbnfIdentifierName ARRAY_IDENTIFIER = EbnfIdentifierName.with("ARRAY");

    private static final EbnfIdentifierName ARRAY_BEGIN_SYMBOL_IDENTIFIER = EbnfIdentifierName.with("ARRAY_BEGIN");
    private static final EbnfIdentifierName ARRAY_END_SYMBOL_IDENTIFIER = EbnfIdentifierName.with("ARRAY_END");

    private static final EbnfIdentifierName BOOLEAN_IDENTIFIER = EbnfIdentifierName.with("BOOLEAN");
    private static final EbnfIdentifierName NULL_IDENTIFIER = EbnfIdentifierName.with("NULL");
    private static final EbnfIdentifierName NUMBER_IDENTIFIER = EbnfIdentifierName.with("NUMBER");

    static final EbnfIdentifierName OBJECT_IDENTIFIER = EbnfIdentifierName.with("OBJECT");

    private static final EbnfIdentifierName OBJECT_ASSIGNMENT_SYMBOL_IDENTIFIER = EbnfIdentifierName.with("OBJECT_ASSIGNMENT");
    private static final EbnfIdentifierName OBJECT_BEGIN_SYMBOL_IDENTIFIER = EbnfIdentifierName.with("OBJECT_BEGIN");
    private static final EbnfIdentifierName OBJECT_END_SYMBOL_IDENTIFIER = EbnfIdentifierName.with("OBJECT_END");

    static final EbnfIdentifierName SEPARATOR_SYMBOL_IDENTIFIER = EbnfIdentifierName.with("SEPARATOR");

    private static final EbnfIdentifierName STRING_IDENTIFIER = EbnfIdentifierName.with("STRING");

    private static final EbnfIdentifierName WHITESPACE_IDENTIFIER = EbnfIdentifierName.with("WHITESPACE");

    private static final EbnfIdentifierName VALUE_IDENTIFIER = EbnfIdentifierName.with("VALUE");

    // required by JsonNodeEbnfParserCombinatorSyntaxTreeTransformer
    static final Set<EbnfIdentifierName> REPORT_FAILURE_IDENTIFIER_NAMES = Sets.of(OBJECT_ASSIGNMENT_SYMBOL_IDENTIFIER);

    /**
     * BOOLEAN
     */
    public static Parser<ParserContext> booleanParser() {
        return BOOLEAN;
    }

    private final static Parser<ParserContext> BOOLEAN = Parsers.string("false", CaseSensitivity.SENSITIVE)
            .transform(JsonNodeParsers::transformBooleanFalse)
            .or(Parsers.string("true", CaseSensitivity.SENSITIVE)
                    .transform(JsonNodeParsers::transformBooleanTrue))
            .setToString(JsonNodeBooleanParserToken.class.getSimpleName());

    private static ParserToken transformBooleanFalse(final ParserToken token, final ParserContext context) {
        return JsonNodeParserToken.booleanParserToken(false, token.text());
    }

    private static ParserToken transformBooleanTrue(final ParserToken token, final ParserContext context) {
        return JsonNodeParserToken.booleanParserToken(true, token.text());
    }

    /**
     * NULL
     */
    public static Parser<ParserContext> nullParser() {
        return NULL;
    }

    private final static Parser<ParserContext> NULL = Parsers.string("null", CaseSensitivity.SENSITIVE)
            .transform(JsonNodeParsers::transformNull)
            .setToString(JsonNodeNullParserToken.class.getSimpleName());

    private static ParserToken transformNull(final ParserToken token, final ParserContext context) {
        return JsonNodeParserToken.nullParserToken(token.text());
    }

    /**
     * NUMBER
     */
    public static Parser<ParserContext> number() {
        return NUMBER;
    }

    private final static Parser<ParserContext> NUMBER = Parsers.doubleParser()
            .transform(JsonNodeParsers::transformNumber)
            .setToString(JsonNodeNumberParserToken.class.getSimpleName());

    private static ParserToken transformNumber(final ParserToken token, final ParserContext context) {
        return JsonNodeParserToken.number(token.cast(DoubleParserToken.class).value(), token.text());
    }

    /**
     * String
     */
    public static Parser<ParserContext> string() {
        return JsonNodeParsersStringParser.INSTANCE;
    }

    /**
     * Whitespace
     */
    public static Parser<ParserContext> whitespace() {
        return WHITESPACE;
    }

    private final static Parser<ParserContext> WHITESPACE = Parsers.stringCharPredicate(CharPredicates.whitespace(), 1, Integer.MAX_VALUE)
            .transform(JsonNodeParsers::transformWhitespace)
            .setToString(JsonNodeWhitespaceParserToken.class.getSimpleName());

    private static ParserToken transformWhitespace(final ParserToken token, final ParserContext context) {
        return JsonNodeParserToken.whitespace(token.cast(StringParserToken.class).value(), token.text());
    }

    /**
     * Factory that parsers and returns a sub class of {@link JsonNodeSymbolParserToken}
     */
    private static Parser<ParserContext> symbol(final char c,
                                                final BiFunction<String, String, ParserToken> factory,
                                                final Class<? extends JsonNodeSymbolParserToken> tokenClass) {
        return Parsers.character(CharPredicates.is(c))
                .transform((charParserToken, context) -> factory.apply(charParserToken.cast(CharacterParserToken.class).value().toString(), charParserToken.text()))
                .setToString(tokenClass.getSimpleName());
    }

    /**
     * Returns a {@link Parser} that returns any of the json values, such as array, boolean, null, number, object.
     */
    public static Parser<ParserContext> value() {
        return VALUE;
    }

    private final static Parser<ParserContext> VALUE = value0();

    private static Parser<ParserContext> value0() {
        try {
            final TextCursor grammarFile = TextCursors.charSequence(new JsonNodeParsersGrammarProvider().text());

            final Map<EbnfIdentifierName, Parser<ParserContext>> predefined = Maps.ordered();
            predefined.put(ARRAY_BEGIN_SYMBOL_IDENTIFIER, ARRAY_BEGIN_SYMBOL);
            predefined.put(ARRAY_END_SYMBOL_IDENTIFIER, ARRAY_END_SYMBOL);

            predefined.put(BOOLEAN_IDENTIFIER, booleanParser());
            predefined.put(NULL_IDENTIFIER, nullParser());
            predefined.put(NUMBER_IDENTIFIER, number());

            predefined.put(OBJECT_ASSIGNMENT_SYMBOL_IDENTIFIER, OBJECT_ASSIGNMENT_SYMBOL);
            predefined.put(OBJECT_BEGIN_SYMBOL_IDENTIFIER, OBJECT_BEGIN_SYMBOL);
            predefined.put(OBJECT_END_SYMBOL_IDENTIFIER, OBJECT_END_SYMBOL);

            predefined.put(SEPARATOR_SYMBOL_IDENTIFIER, SEPARATOR_SYMBOL);

            predefined.put(STRING_IDENTIFIER, string());

            predefined.put(WHITESPACE_IDENTIFIER, whitespace());

            final Map<EbnfIdentifierName, Parser<ParserContext>> parsers = EbnfParserToken.grammarParser()
                    .orFailIfCursorNotEmpty(ParserReporters.basic())
                    .parse(grammarFile, EbnfParserContexts.basic())
                    .orElseThrow(() -> new IllegalStateException("Unable to parse JsonNode parsers grammar file."))
                    .cast(EbnfGrammarParserToken.class)
                    .combinator(predefined, JsonNodeEbnfParserCombinatorSyntaxTreeTransformer.INSTANCE);

            return parsers.get(VALUE_IDENTIFIER);
        } catch (final JsonNodeParserException rethrow) {
            throw rethrow;
        } catch (final Exception cause) {
            throw new JsonNodeParserException("Failed to return parsers from JsonNode grammar file, message: " + cause.getMessage(), cause);
        }
    }

    /**
     * Stop construction
     */
    private JsonNodeParsers() {
        throw new UnsupportedOperationException();
    }
}
