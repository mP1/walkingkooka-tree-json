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

import org.junit.jupiter.api.Test;
import walkingkooka.locale.LocaleContextTesting;
import walkingkooka.math.DecimalNumberSymbolsList;
import walkingkooka.math.HasDecimalNumberSymbolsTesting;
import walkingkooka.tree.json.JsonNode;

import java.util.stream.Collectors;

public final class BasicJsonMarshallerTypedDecimalNumberSymbolsListTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedDecimalNumberSymbolsList, DecimalNumberSymbolsList>
    implements HasDecimalNumberSymbolsTesting,
    LocaleContextTesting {

    @Test
    public void testUnmarshallSeveral() {
        final DecimalNumberSymbolsList list = DecimalNumberSymbolsList.EMPTY.concat(DECIMAL_NUMBER_SYMBOLS)
            .concat(DIFFERENT_DECIMAL_NUMBER_SYMBOLS);

        this.unmarshallAndCheck(
            BasicJsonMarshallerTypedDecimalNumberSymbolsList.instance(),
            JsonNode.array()
                .setChildren(
                    list.csvStringList()
                        .stream()
                        .map(JsonNode::string)
                        .collect(Collectors.toList())
                ),
            JSON_NODE_UNMARSHALL_CONTEXT,
            list
        );
    }

    @Override
    BasicJsonMarshallerTypedDecimalNumberSymbolsList marshaller() {
        return BasicJsonMarshallerTypedDecimalNumberSymbolsList.instance();
    }

    @Override
    DecimalNumberSymbolsList value() {
        return DecimalNumberSymbolsList.EMPTY
            .concat(DECIMAL_NUMBER_SYMBOLS)
            .concat(DIFFERENT_DECIMAL_NUMBER_SYMBOLS);
    }

    @Override
    JsonNode node() {
        return JsonNode.parse(
            "[\n" +
                "  \"-,+,0,$,.,e,\\\",\\\",∞,.,NaN,%,‰\",\n" +
                "  \"-,+,0,$,.,E,\\\",\\\",∞,.,NaN,%,‰\"\n" +
                "]"
        );
    }

    @Override
    DecimalNumberSymbolsList jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "decimal-number-symbols-list";
    }

    @Override
    Class<DecimalNumberSymbolsList> marshallerType() {
        return DecimalNumberSymbolsList.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedDecimalNumberSymbolsList> type() {
        return BasicJsonMarshallerTypedDecimalNumberSymbolsList.class;
    }
}
