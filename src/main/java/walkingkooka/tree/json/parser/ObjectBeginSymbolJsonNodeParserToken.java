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

/**
 * Represents a open object (parens) symbol token.
 */
public final class ObjectBeginSymbolJsonNodeParserToken extends SymbolJsonNodeParserToken {

    static ObjectBeginSymbolJsonNodeParserToken with(final String value, final String text) {
        checkValue(value);
        checkText(text);

        return new ObjectBeginSymbolJsonNodeParserToken(value, text);
    }

    private ObjectBeginSymbolJsonNodeParserToken(final String value, final String text) {
        super(value, text);
    }

    // visitor .........................................................................................................

    @Override
    public void accept(final JsonNodeParserTokenVisitor visitor) {
        visitor.visit(this);
    }
}
