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

package walkingkooka.tree.json.select;

import org.junit.jupiter.api.Test;
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.FakeExpressionFunction;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.util.BiFunctionTesting;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class JsonSelectorTest implements BiFunctionTesting<JsonSelector, JsonNode, JsonSelectorContext, List<JsonNode>>,
    ClassTesting2<JsonSelector>,
    HashCodeEqualsDefinedTesting2<JsonSelector>,
    ToStringTesting<JsonSelector>,
    TreePrintableTesting {

    // with.............................................................................................................

    @Test
    public void testParseNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> JsonSelector.parse(null)
        );
    }

    // apply............................................................................................................

    @Test
    public void testApplyAbsoluteSelector() {
        this.applyAndCheck(
            JsonSelector.parse("/hello/hello2"),
            JsonNode.parse("{\"hello\": {\"hello2\": \"World\"}, \"ignored\": 222}"),
            this.createContext(),
            Lists.of(
                JsonNode.string("World")
            )
        );
    }

    @Test
    public void testApplySelectorWithFunction() {
        this.applyAndCheck(
            JsonSelector.parse("//*[isWorld()]"),
            JsonNode.parse("{\"hello\": {\"hello2\": \"World\"}, \"ignored\": 222}"),
            this.createContext(),
            Lists.of(
                JsonNode.string("World")
            )
        );
    }

    @Override
    public JsonSelector createBiFunction() {
        return JsonSelector.parse("/hello");
    }

    public JsonSelectorContext createContext() {
        return new JsonSelectorContext() {

            @Override
            public ExpressionEvaluationContext expressionEvaluationContext(final JsonNode node) {
                return new FakeExpressionEvaluationContext() {
                    @Override
                    public ExpressionFunction<?, ExpressionEvaluationContext> expressionFunction(final ExpressionFunctionName name) {
                        if(name.equals(NAME)) {
                            return new FakeExpressionFunction<>() {

                                @Override
                                public List<ExpressionFunctionParameter<?>> parameters(final int count) {
                                    return NO_PARAMETERS;
                                }

                                @Override
                                public Object apply(final List<Object> parameters,
                                                    final ExpressionEvaluationContext context) {
                                    return node.isString() &&
                                        node.stringOrFail()
                                            .equals("World");
                                }

                                @Override
                                public Optional<ExpressionFunctionName> name() {
                                    return Optional.of(NAME);
                                }
                            };
                        }

                        throw name.unknownExpressionFunctionException();
                    }

                    private final ExpressionFunctionName NAME = ExpressionFunctionName.with("isWorld");
                };
            }
        };
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferent() {
        this.checkNotEquals(
            JsonSelector.parse("/different")
        );
    }

    @Override
    public JsonSelector createObject() {
        return this.createBiFunction();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createBiFunction(),
            "/hello"
        );
    }

    // TreePrintable....................................................................................................

    @Test
    public void testTreePrintable() {
        this.treePrintAndCheck(
            JsonSelector.parse("/hello/world[\"true\"]"),
            "JsonSelector\n" +
                "  CustomToStringNodeSelector\n" +
                "    \"/hello/world[\\\"true\\\"]\"\n" +
                "      Absolute\n" +
                "        Children\n" +
                "          Named\n" +
                "            Children\n" +
                "              Named\n" +
                "                Expression\n" +
                "                  ValueExpression \"true\" (java.lang.String)\n" +
                "                  Terminal\n"
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonSelector> type() {
        return JsonSelector.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
