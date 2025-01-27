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
import walkingkooka.text.cursor.parser.ParserTokenVisitor;
import walkingkooka.visit.Visiting;

public abstract class JsonNodeParserTokenVisitor extends ParserTokenVisitor {

    // ArrayJsonNodeParserToken....................................................................................

    protected Visiting startVisit(final ArrayJsonNodeParserToken token) {
        return Visiting.CONTINUE;
    }

    protected void endVisit(final ArrayJsonNodeParserToken token) {
        // nop
    }

    // ObjectJsonNodeParserToken....................................................................................

    protected Visiting startVisit(final ObjectJsonNodeParserToken token) {
        return Visiting.CONTINUE;
    }

    protected void endVisit(final ObjectJsonNodeParserToken token) {
        // nop
    }

    // LeafJsonNodeParserToken ....................................................................................

    protected void visit(final ArrayBeginSymbolJsonNodeParserToken token) {
        // nop
    }

    protected void visit(final ArrayEndSymbolJsonNodeParserToken token) {
        // nop
    }

    protected void visit(final BooleanJsonNodeParserToken token) {
        // nop
    }

    protected void visit(final NullJsonNodeParserToken token) {
        // nop
    }

    protected void visit(final NumberJsonNodeParserToken token) {
        // nop
    }

    protected void visit(final ObjectAssignmentSymbolJsonNodeParserToken token) {
        // nop
    }

    protected void visit(final ObjectBeginSymbolJsonNodeParserToken token) {
        // nop
    }

    protected void visit(final ObjectEndSymbolJsonNodeParserToken token) {
        // nop
    }

    protected void visit(final SeparatorSymbolJsonNodeParserToken token) {
        // nop
    }

    protected void visit(final StringJsonNodeParserToken token) {
        // nop
    }

    protected void visit(final WhitespaceJsonNodeParserToken token) {
        // nop
    }

    // ParserToken.......................................................................

    @Override
    protected Visiting startVisit(final ParserToken token) {
        return Visiting.CONTINUE;
    }

    @Override
    protected void endVisit(final ParserToken token) {
        // nop
    }

    // JsonNodeParserToken.......................................................................

    protected Visiting startVisit(final JsonNodeParserToken token) {
        return Visiting.CONTINUE;
    }

    protected void endVisit(final JsonNodeParserToken token) {
        // nop
    }
}
