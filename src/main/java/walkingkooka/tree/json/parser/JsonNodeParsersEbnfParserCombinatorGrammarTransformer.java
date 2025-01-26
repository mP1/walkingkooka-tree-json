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

import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserReporters;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.cursor.parser.SequenceParserToken;
import walkingkooka.text.cursor.parser.ebnf.AlternativeEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.ConcatenationEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.EbnfIdentifierName;
import walkingkooka.text.cursor.parser.ebnf.ExceptionEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.GroupEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.IdentifierEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.OptionalEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.RangeEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.RepeatedEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.RuleEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.TerminalEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.combinator.EbnfParserCombinatorGrammarTransformer;

import java.util.List;

/**
 * A {@link EbnfParserCombinatorGrammarTransformer} that only transforms terminal and ranges into their corresponding
 * {@link JsonNodeParserToken} equivalents. Processing of other tokens will be done after this process completes.
 */
final class JsonNodeParsersEbnfParserCombinatorGrammarTransformer implements EbnfParserCombinatorGrammarTransformer<ParserContext> {

    /**
     * Singleton
     */
    static final JsonNodeParsersEbnfParserCombinatorGrammarTransformer INSTANCE = new JsonNodeParsersEbnfParserCombinatorGrammarTransformer();

    private JsonNodeParsersEbnfParserCombinatorGrammarTransformer() {
        super();
    }

    @Override
    public Parser<ParserContext> alternatives(final AlternativeEbnfParserToken token,
                                              final Parser<ParserContext> parser) {
        return parser;
    }

    @Override
    public Parser<ParserContext> concatenation(final ConcatenationEbnfParserToken token,
                                               final Parser<ParserContext> parser) {
        return parser;
    }

    @Override
    public Parser<ParserContext> exception(final ExceptionEbnfParserToken token,
                                           final Parser<ParserContext> parser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Parser<ParserContext> group(final GroupEbnfParserToken token,
                                       final Parser<ParserContext> parser) {
        return parser;
    }

    @Override
    public Parser<ParserContext> optional(final OptionalEbnfParserToken token,
                                          final Parser<ParserContext> parser) {
        return parser;
    }

    @Override
    public Parser<ParserContext> range(final RangeEbnfParserToken token,
                                       final String beginText,
                                       final String endText) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Parser<ParserContext> repeated(final RepeatedEbnfParserToken token,
                                          final Parser<ParserContext> parser) {
        return parser;
    }

    @Override
    public Parser<ParserContext> rule(final RuleEbnfParserToken token,
                                      final Parser<ParserContext> parser) {
        return parser;
    }

    @Override
    public Parser<ParserContext> terminal(final TerminalEbnfParserToken token,
                                          final Parser<ParserContext> parser) {
        throw new UnsupportedOperationException(token.toString());
    }

    // identifier ......................................................................................................

    /**
     * If the identifier name ends in "REQUIRED" mark the parser so that it reports a failure.
     */
    @Override
    public Parser<ParserContext> identifier(final IdentifierEbnfParserToken token,
                                            final Parser<ParserContext> parser) {
        final EbnfIdentifierName name = token.value();
        return name.equals(JsonNodeParsers.ARRAY_IDENTIFIER) ?
            parser.transform(JsonNodeParsersEbnfParserCombinatorGrammarTransformer::array) :
            name.equals(JsonNodeParsers.OBJECT_IDENTIFIER) ?
                parser.transform(JsonNodeParsersEbnfParserCombinatorGrammarTransformer::object) :
                this.requiredCheck(name, parser);
    }

    private static ParserToken array(final ParserToken token,
                                     final ParserContext context) {
        return JsonNodeParserToken.array(JsonNodeParsersEbnfParserCombinatorGrammarTransformer.clean(token),
            token.text());
    }

    private static ParserToken object(final ParserToken token,
                                      final ParserContext context) {
        return JsonNodeParserToken.object(JsonNodeParsersEbnfParserCombinatorGrammarTransformer.clean(token),
            token.text());
    }

    private static List<ParserToken> clean(final ParserToken token) {
        return token.cast(SequenceParserToken.class)
            .flat()
            .value();
    }

    private Parser<ParserContext> requiredCheck(final EbnfIdentifierName name,
                                                final Parser<ParserContext> parser) {
        return name.value().endsWith("REQUIRED") || JsonNodeParsers.REPORT_FAILURE_IDENTIFIER_NAMES.contains(name) ?
            parser.orReport(ParserReporters.basic()) :
            parser; // leave as is...
    }
}
