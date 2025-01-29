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
import walkingkooka.collect.list.Lists;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.IsMethodTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.PublicStaticFactoryTesting;
import walkingkooka.text.CharSequences;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.cursor.parser.ParserTokenTesting;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class JsonNodeParserTokenTestCase<T extends JsonNodeParserToken> implements ClassTesting2<T>,
    IsMethodTesting<T>,
    ParserTokenTesting<T> {

    JsonNodeParserTokenTestCase() {
        super();
    }

    @Test
    @Override
    public final void testPublicStaticFactoryMethod() {
        PublicStaticFactoryTesting.checkFactoryMethods(
            JsonNodeParserToken.class,
            "",
            JsonNodeParserToken.class.getSimpleName(),
            this.type()
        );
    }

    @Test
    public final void testEmptyTextFails() {
        assertThrows(IllegalArgumentException.class, () -> this.createToken(""));
    }

    @Test
    public final void testAccept2() {
        new JsonNodeParserTokenVisitor() {
        }.accept(this.createToken());
    }

    final JsonNodeParserToken arrayBegin() {
        return JsonNodeParserToken.arrayBeginSymbol("[", "[");
    }

    final JsonNodeParserToken arrayEnd() {
        return JsonNodeParserToken.arrayEndSymbol("]", "]");
    }

    final JsonNodeParserToken array(final JsonNodeParserToken... tokens) {
        return JsonNodeParserToken.array(Lists.of(tokens), text(tokens));
    }

    final JsonNodeParserToken booleanFalse() {
        return booleanToken(false);
    }

    final JsonNodeParserToken booleanTrue() {
        return booleanToken(true);
    }

    final JsonNodeParserToken booleanToken(final boolean value) {
        return JsonNodeParserToken.booleanJsonNodeParserToken(value, String.valueOf(value));
    }

    final JsonNodeParserToken nul() {
        return JsonNodeParserToken.nullJsonNodeParserToken("null");
    }

    final JsonNodeParserToken number(final int value) {
        // accept only int, keeps the creation of the matching text simple.
        return JsonNodeParserToken.number(value, String.valueOf(value));
    }

    final JsonNodeParserToken objectAssignment() {
        return JsonNodeParserToken.objectAssignmentSymbol(":", ":");
    }

    final JsonNodeParserToken objectBegin() {
        return JsonNodeParserToken.objectBeginSymbol("{", "{");
    }

    final JsonNodeParserToken objectEnd() {
        return JsonNodeParserToken.objectEndSymbol("}", "}");
    }

    final JsonNodeParserToken object(final JsonNodeParserToken... tokens) {
        return JsonNodeParserToken.object(Lists.of(tokens), text(tokens));
    }

    final JsonNodeParserToken separator() {
        return JsonNodeParserToken.separatorSymbol(",", ",");
    }

    final JsonNodeParserToken string(final String value) {
        return JsonNodeParserToken.string(value, CharSequences.quoteAndEscape(value).toString());
    }

    final JsonNodeParserToken whitespace() {
        return JsonNodeParserToken.whitespace("  ", "  ");
    }

    private static String text(final JsonNodeParserToken... tokens) {
        return ParserToken.text(Lists.of(tokens));
    }

    // isMethodTesting2.....................................................................................

    @Override
    public final T createIsMethodObject() {
        return this.createToken();
    }

    @Override
    public final Predicate<String> isMethodIgnoreMethodFilter() {
        return (m) -> m.equals("isLeaf") ||
            m.equals("isNoise") ||
            m.equals("isParent") ||
            m.equals("isSymbol") ||
            m.equals("isEmpty") ||
            m.equals("isNotEmpty"
            );
    }

    @Override
    public final String toIsMethodName(final String typeName) {
        return this.toIsMethodNameWithPrefixSuffix(
            typeName,
            "", // prefix
            JsonNodeParserToken.class.getSimpleName() // suffix
        );
    }

    // class............................................................................................................

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
