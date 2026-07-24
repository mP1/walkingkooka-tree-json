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

import walkingkooka.Cast;
import walkingkooka.naming.Name;
import walkingkooka.predicate.Predicates;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserReporters;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.HasExpressionNumberKind;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.select.NodeSelector;
import walkingkooka.tree.select.NodeSelectorContext;
import walkingkooka.tree.select.parser.ExpressionNodeSelectorParserToken;
import walkingkooka.tree.select.parser.NodeSelectorParserContext;
import walkingkooka.tree.select.parser.NodeSelectorParserContexts;
import walkingkooka.tree.select.parser.NodeSelectorParsers;

import java.math.MathContext;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Wraps a {@link NodeSelector}, supporting XPATH selection of {@link JsonNode}.
 */
public final class JsonSelector implements BiFunction<JsonNode, JsonSelectorContext, List<JsonNode>>,
    TreePrintable {

    private final static ExpressionNumberKind EXPRESSION_NUMBER_KIND = ExpressionNumberKind.DEFAULT;

    private final static HasExpressionNumberKind HAS_EXPRESSION_NUMBER_KIND = () -> EXPRESSION_NUMBER_KIND;

    private final static MathContext MATH_CONTEXT = MathContext.UNLIMITED;

    private final static Parser<NodeSelectorParserContext> PARSER = NodeSelectorParsers.expression()
        .orReport(ParserReporters.basic())
        .cast();

    /**
     * Parsers the given selector text into a {@link JsonSelector}.
     */
    public static JsonSelector parse(final String selector) {
        final ParserToken token = PARSER.parseText(
            selector,
            NodeSelectorParserContexts.basic(
                EXPRESSION_NUMBER_KIND,
                MATH_CONTEXT
            )
        );

        return new JsonSelector(
            JsonNode.nodeSelectorExpressionParserToken(
                token.cast(ExpressionNodeSelectorParserToken.class),
                Predicates.always(), // function name validator
                HAS_EXPRESSION_NUMBER_KIND
            )
        );
    }

    private JsonSelector(final NodeSelector<JsonNode, JsonPropertyName, Name, Object> nodeSelector) {
        super();

        this.nodeSelector = nodeSelector;
    }

    // BiFunction.......................................................................................................


    @Override
    public List<JsonNode> apply(final JsonNode node,
                                final JsonSelectorContext context) {
        return this.nodeSelector.stream(
            node,
            (NodeSelectorContext<JsonNode, JsonPropertyName, Name, Object> nodeSelectorContext) -> context.expressionEvaluationContext(
                nodeSelectorContext.node()
            ),
            JsonNode.class
        ).collect(Collectors.toList());
    }

    private final NodeSelector<JsonNode, JsonPropertyName, Name, Object> nodeSelector;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.nodeSelector.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof JsonSelector &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final JsonSelector other) {
        return this.nodeSelector.equals(other.nodeSelector);
    }

    @Override
    public String toString() {
        return this.nodeSelector.toString();
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName());
        printer.indent();
        {
            this.nodeSelector.printTree(printer);
        }
        printer.outdent();
    }
}
