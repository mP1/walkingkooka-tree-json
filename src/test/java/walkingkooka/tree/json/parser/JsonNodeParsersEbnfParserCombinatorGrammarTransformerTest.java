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

import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ebnf.combinator.EbnfParserCombinatorGrammarTransformerTesting;

public final class JsonNodeParsersEbnfParserCombinatorGrammarTransformerTest implements ClassTesting2<JsonNodeParsersEbnfParserCombinatorGrammarTransformer>,
    EbnfParserCombinatorGrammarTransformerTesting<JsonNodeParsersEbnfParserCombinatorGrammarTransformer, ParserContext> {

    @Override
    public Class<JsonNodeParsersEbnfParserCombinatorGrammarTransformer> type() {
        return JsonNodeParsersEbnfParserCombinatorGrammarTransformer.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
