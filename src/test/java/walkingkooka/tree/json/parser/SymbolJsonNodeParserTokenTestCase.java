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

import org.junit.jupiter.api.Test;

import java.util.Optional;

public abstract class SymbolJsonNodeParserTokenTestCase<T extends SymbolJsonNodeParserToken> extends LeafJsonNodeParserTokenTestCase<T, String> {

    SymbolJsonNodeParserTokenTestCase() {
        super();
    }

    @Test
    @Override
    public final void testMarshallNode() {
        final T token = this.createToken();
        this.checkEquals(Optional.empty(), token.toJsonNode());
    }
}
