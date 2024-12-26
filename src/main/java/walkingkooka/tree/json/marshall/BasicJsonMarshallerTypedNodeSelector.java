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

package walkingkooka.tree.json.marshall;

import walkingkooka.Cast;
import walkingkooka.NeverError;
import walkingkooka.collect.list.Lists;
import walkingkooka.naming.Name;
import walkingkooka.naming.Names;
import walkingkooka.predicate.Predicates;
import walkingkooka.text.CharSequences;
import walkingkooka.text.cursor.TextCursors;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserReporters;
import walkingkooka.tree.Node;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.json.JsonArray;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.select.NodeSelector;
import walkingkooka.tree.select.parser.NodeSelectorParserContext;
import walkingkooka.tree.select.parser.NodeSelectorParserContexts;
import walkingkooka.tree.select.parser.NodeSelectorParsers;
import walkingkooka.tree.select.parser.NodeSelectorPredicateParserToken;

import java.util.function.BiFunction;
import java.util.stream.Collectors;

final class BasicJsonMarshallerTypedNodeSelector extends BasicJsonMarshallerTyped<NodeSelector<?, ?, ?, ?>> {

    static BasicJsonMarshallerTypedNodeSelector instance() {
        return new BasicJsonMarshallerTypedNodeSelector();
    }

    private BasicJsonMarshallerTypedNodeSelector() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();

        this.registerTypes(Lists.of(NodeSelector.absolute(),
                NodeSelector.relative().ancestor(),
                NodeSelector.relative().ancestorOrSelf(),
                NodeSelector.relative().children(),
                NodeSelector.relative().self().setToString("custom-123"),
                NodeSelector.relative().descendant(),
                NodeSelector.relative().descendantOrSelf(),
                NodeSelector.relative().expression(Expression.value("text")),
                NodeSelector.relative().firstChild(),
                NodeSelector.relative().following(),
                NodeSelector.relative().followingSibling(),
                NodeSelector.relative().lastChild(),
                NodeSelector.relative().named(Names.string("ignored")),
                NodeSelector.relative().parent(),
                NodeSelector.relative().preceding(),
                NodeSelector.relative().precedingSibling(),
                NodeSelector.relative().predicate(Predicates.fake()),
                NodeSelector.relative().self())
                .stream()
                .map(s -> s.getClass())
                .collect(Collectors.toList()));
    }

    @Override
    Class<NodeSelector<?, ?, ?, ?>> type() {
        return Cast.to(NodeSelector.class);
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(NodeSelector.class);
    }

    @Override
    NodeSelector<?, ?, ?, ?> unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    NodeSelector<?, ?, ?, ?> unmarshallNonNull(final JsonNode node,
                                               final JsonNodeUnmarshallContext context) {
        JsonArray components = null;

        for (JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case NAME_TYPE:
                    child.stringOrFail();
                    break;
                case COMPONENTS:
                    components = child.arrayOrFail();
                    break;
                default:
                    NeverError.unhandledCase(name, NAME_TYPE, COMPONENTS);
            }
        }

        if (null == components) {
            JsonNodeUnmarshallContext.missingProperty(COMPONENTS_PROPERTY, node);
        }

        return unmarshallNonNull0(
                context.unmarshallWithType(NAME_TYPE_PROPERTY, node.objectOrFail(), Name.class),
                components,
                context);
    }

    /**
     * Accepts a selector and returns its JSON representation.
     */
    static <N extends Node<N, NAME, ANAME, AVALUE>,
            NAME extends Name,
            ANAME extends Name,
            AVALUE> NodeSelector<N, NAME, ANAME, AVALUE> unmarshallNonNull0(final BiFunction<JsonNode, JsonNodeUnmarshallContext, NAME> nameFactory,
                                                                            final JsonArray components,
                                                                            final JsonNodeUnmarshallContext context) {
        NodeSelector<N, NAME, ANAME, AVALUE> selector = NodeSelector.relative();

        for (JsonNode component : components.children()) {
            final String string = component.stringOrFail();

            switch (string) {
                case ABSOLUTE:
                    selector = NodeSelector.absolute();
                    break;
                case ANCESTOR:
                    selector = selector.ancestor();
                    break;
                case ANCESTOR_OR_SELF:
                    selector = selector.ancestorOrSelf();
                    break;
                case CHILD:
                    selector = selector.children();
                    break;
                case DESCENDANT:
                    selector = selector.descendant();
                    break;
                case DESCENDANT_OR_SELF:
                    selector = selector.descendantOrSelf();
                    break;
                case FIRST_CHILD:
                    selector = selector.firstChild();
                    break;
                case FOLLOWING:
                    selector = selector.following();
                    break;
                case FOLLOWING_SIBLING:
                    selector = selector.followingSibling();
                    break;
                case LAST_CHILD:
                    selector = selector.lastChild();
                    break;
                case PARENT:
                    selector = selector.parent();
                    break;
                case PRECEDING:
                    selector = selector.preceding();
                    break;
                case PRECEDING_SIBLING:
                    selector = selector.precedingSibling();
                    break;
                case SELF:
                    selector = selector.self();
                    break;
                default:
                    final int separator = string.indexOf(SEPARATOR);
                    if (-1 == separator) {
                        reportUnknownComponent(string, component.parentOrFail());
                    }
                    final String left = string.substring(0, separator);
                    final String right = string.substring(separator + 1);

                    switch (left) {
                        case CUSTOM:
                            selector = selector.setToString(right);
                            break;
                        case EXPRESSION:
                            selector = selector.expression(parseExpression(right, context));
                            break;
                        case NAMED:
                            selector = selector.named(nameFactory.apply(JsonNode.string(right), context));
                            break;
                        case PREDICATE:
                            selector = selector.expression(parseExpression(right, context));
                            break;
                        default:
                            reportUnknownComponent(string, component.parentOrFail());
                            break;
                    }
            }
        }

        return selector;
    }

    final static String ABSOLUTE = "absolute";
    final static String ANCESTOR = "ancestor";
    final static String ANCESTOR_OR_SELF = "ancestor-or-self";
    final static String CHILD = "child";
    final static String DESCENDANT = "descendant";
    final static String DESCENDANT_OR_SELF = "descendant-or-self";
    final static String FIRST_CHILD = "first-child";
    final static String FOLLOWING = "following";
    final static String FOLLOWING_SIBLING = "following-sibling";
    final static String LAST_CHILD = "last-child";
    final static String PARENT = "parent";
    final static String PRECEDING = "preceding";
    final static String PRECEDING_SIBLING = "preceding-sibling";
    final static String SELF = "self";
    final static String CUSTOM = "custom";
    final static String EXPRESSION = "expression";
    final static String NAMED = "named";
    final static String PREDICATE = "predicate";
    final static char SEPARATOR = ':';

    /**
     * Creates a {@link Parser} and parses the {@link String expression} into a {@link NodeSelectorPredicateParserToken} and then that into an {@link Expression}.
     */
    private static Expression parseExpression(final String expression,
                                              final JsonNodeUnmarshallContext context) {
        final Parser<NodeSelectorParserContext> parser = NodeSelectorParsers.predicate()
                .orReport(ParserReporters.basic())
                .cast();
        return parser.parse(TextCursors.charSequence(expression), NodeSelectorParserContexts.basic(context.expressionNumberKind(), context.mathContext()))
                .get()
                .cast(NodeSelectorPredicateParserToken.class)
                .toExpression(Predicates.always());
    }

    /**
     * Reports an invalid component in the components array of the JSON representation.
     */
    private static void reportUnknownComponent(final String component, final JsonNode from) {
        throw new java.lang.IllegalArgumentException("Unknown component: " + CharSequences.quoteAndEscape(component) + " in " + from);
    }

    @SuppressWarnings("unchecked")
    @Override
    JsonNode marshallNonNull(final NodeSelector<?, ?, ?, ?> value,
                             final JsonNodeMarshallContext context) {
        return BasicJsonMarshallerTypedNodeSelectorNodeSelectorVisitor.marshall(value, context);
    }

    final static String NAME_TYPE = "name-type";
    final static String COMPONENTS = "components";

    final static JsonPropertyName NAME_TYPE_PROPERTY = JsonPropertyName.with(NAME_TYPE);
    final static JsonPropertyName COMPONENTS_PROPERTY = JsonPropertyName.with(COMPONENTS);
}
