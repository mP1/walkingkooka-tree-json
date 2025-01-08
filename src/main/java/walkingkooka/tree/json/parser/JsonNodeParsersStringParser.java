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

import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.cursor.TextCursorLineInfo;
import walkingkooka.text.cursor.TextCursorSavePoint;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserToken;

import java.util.Optional;

/**
 * A parser that consumes JSON strings.
 * https://www.crockford.com/mckeeman.html
 */
final class JsonNodeParsersStringParser implements Parser<ParserContext> {

    /**
     * Singleton
     */
    final static JsonNodeParsersStringParser INSTANCE = new JsonNodeParsersStringParser();

    /**
     * Stop sub classing
     */
    private JsonNodeParsersStringParser() {
        super();
    }

    @Override
    public Optional<ParserToken> parse(final TextCursor cursor, final ParserContext context) {
        JsonNodeParserToken token = null;

        if (!cursor.isEmpty()) {
            final char first = cursor.at();
            if (DOUBLE_QUOTE == first) {
                final TextCursorSavePoint save = cursor.save();
                final StringBuilder decoded = new StringBuilder();
                int mode = 0;
                int unicode = 0;
                int unicodeLength = 0;

                cursor.next();

                Exit:
                for (; ; ) {
                    if (cursor.isEmpty()) {
                        throw new JsonNodeParserException("Unterminated string");
                    }

                    final char c = cursor.at();

                    switch (mode) {
                        case MODE_LITERAL:
                            switch (c) {
                                case BACKSLASH:
                                    mode = MODE_BACKSLASH;
                                    break;
                                case DOUBLE_QUOTE:
                                    cursor.next();
                                    token = JsonNodeParserToken.string(
                                        decoded.toString(),
                                        save.textBetween().toString()
                                    );
                                    break Exit;
                                default:
                                    decoded.append(c);
                                    break;
                            }
                            break;
                        case MODE_BACKSLASH:
                            mode = MODE_LITERAL; // all unescaping will change mode=MODE_LITERAL except unicode=MODE_UNICODE

                            switch (c) {
                                case 'b':
                                    decoded.append('\b');
                                    break;
                                case 'f':
                                    decoded.append('\f');
                                    break;
                                case 'n':
                                    decoded.append('\n');
                                    break;
                                case 'r':
                                    decoded.append('\r');
                                    break;
                                case 't':
                                    decoded.append('\t');
                                    break;
                                case 'u':
                                    unicode = 0;
                                    mode = MODE_UNICODE;
                                    break;
                                default:
                                    // also handles decoding DOUBLE_QUOTE
                                    decoded.append(c);
                                    break;
                            }
                            break;
                        case MODE_UNICODE:
                            final int hexValue = Character.digit(c, 16);
                            if (-1 == hexValue) {
                                final TextCursorLineInfo info = cursor.lineInfo();
                                throw new JsonNodeParserException("Invalid unicode escape sequence" + info.summary());
                            }
                            unicode = unicode * 16 + hexValue;
                            unicodeLength++;
                            if (unicodeLength == 4) {
                                mode = MODE_LITERAL;
                                decoded.append((char) unicode);
                            }
                            break;
                    } // switch

                    cursor.next();
                }
            }
        }

        return Optional.ofNullable(token);
    }

    final static char DOUBLE_QUOTE = '"';
    final static char BACKSLASH = '\\';

    private final static int MODE_LITERAL = 0;
    private final static int MODE_BACKSLASH = MODE_LITERAL + 1;
    private final static int MODE_UNICODE = MODE_BACKSLASH + 1;

    @Override
    public String toString() {
        return "STRING";
    }
}