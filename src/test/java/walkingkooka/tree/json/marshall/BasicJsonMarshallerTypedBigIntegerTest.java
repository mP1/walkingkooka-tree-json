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

import walkingkooka.tree.json.JsonNode;

import java.math.BigInteger;

public final class BasicJsonMarshallerTypedBigIntegerTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedBigInteger, BigInteger> {

    @Override
    BasicJsonMarshallerTypedBigInteger marshaller() {
        return BasicJsonMarshallerTypedBigInteger.instance();
    }

    @Override
    BigInteger value() {
        return new BigInteger("123");
    }

    @Override
    JsonNode node() {
        return JsonNode.string(this.value().toString());
    }

    @Override
    BigInteger jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "big-integer";
    }

    @Override
    Class<BigInteger> marshallerType() {
        return BigInteger.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedBigInteger> type() {
        return BasicJsonMarshallerTypedBigInteger.class;
    }
}
