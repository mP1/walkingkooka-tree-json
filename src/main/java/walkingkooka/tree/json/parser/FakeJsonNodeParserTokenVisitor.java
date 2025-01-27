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

import walkingkooka.test.Fake;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.visit.Visiting;

public class FakeJsonNodeParserTokenVisitor extends JsonNodeParserTokenVisitor implements Fake {

    protected FakeJsonNodeParserTokenVisitor() {
        super();
    }

    @Override
    protected Visiting startVisit(final ArrayJsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void endVisit(final ArrayJsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Visiting startVisit(final ObjectJsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void endVisit(final ObjectJsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final ArrayBeginSymbolJsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final ArrayEndSymbolJsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final BooleanJsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final NullJsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final NumberJsonNodeParserToken token) {
        super.visit(token);
    }

    @Override
    protected void visit(final ObjectAssignmentSymbolJsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final ObjectBeginSymbolJsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final ObjectEndSymbolJsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final SeparatorSymbolJsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final StringJsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final WhitespaceJsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Visiting startVisit(ParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void endVisit(ParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Visiting startVisit(final JsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void endVisit(final JsonNodeParserToken token) {
        throw new UnsupportedOperationException();
    }
}
