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
import walkingkooka.environment.Environment;
import walkingkooka.environment.EnvironmentContextTesting;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;

public final class BasicJsonMarshallerTypedEnvironmentTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedEnvironment, Environment>
    implements EnvironmentContextTesting {

    @Test
    public void testMarshallEnvironmentContext() {
        this.marshallAndCheck(
            ENVIRONMENT_CONTEXT.environment(),
            JsonNode.parse(
                "{\n" +
                    "  \"charset\": {\n" +
                    "    \"type\": \"charset\",\n" +
                    "    \"value\": \"UTF-8\"\n" +
                    "  },\n" +
                    "  \"currency\": {\n" +
                    "    \"type\": \"currency\",\n" +
                    "    \"value\": \"AUD\"\n" +
                    "  },\n" +
                    "  \"indentation\": {\n" +
                    "    \"type\": \"indentation\",\n" +
                    "    \"value\": \"  \"\n" +
                    "  },\n" +
                    "  \"lineEnding\": {\n" +
                    "    \"type\": \"line-ending\",\n" +
                    "    \"value\": \"NL\"\n" +
                    "  },\n" +
                    "  \"locale\": {\n" +
                    "    \"type\": \"locale\",\n" +
                    "    \"value\": \"en-AU\"\n" +
                    "  },\n" +
                    "  \"now\": {\n" +
                    "    \"type\": \"local-date-time\",\n" +
                    "    \"value\": \"1999-12-31T12:58:59\"\n" +
                    "  },\n" +
                    "  \"timeOffset\": {\n" +
                    "    \"type\": \"zone-offset\",\n" +
                    "    \"value\": \"Z\"\n" +
                    "  },\n" +
                    "  \"user\": {\n" +
                    "    \"type\": \"email-address\",\n" +
                    "    \"value\": \"user123@example.com\"\n" +
                    "  }\n" +
                    "}"
            )
        );
    }

    @Override
    BasicJsonMarshallerTypedEnvironment marshaller() {
        return BasicJsonMarshallerTypedEnvironment.instance();
    }

    @Override
    Environment value() {
        return Environment.empty()
            .set(
                EnvironmentValueName.CURRENCY,
                CURRENCY
            ).set(
                EnvironmentValueName.LOCALE,
                LOCALE
            );
    }

    //",
    @Override
    JsonNode node() {
        return JsonNode.object()
            .set(
                JsonPropertyName.with("currency"),
                typeAndValue("currency", JsonNode.string("AUD"))
            ).set(
                JsonPropertyName.with("locale"),
                typeAndValue("locale", JsonNode.string("en-AU"))
            );
    }

    @Override
    Environment jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "environment";
    }

    @Override
    Class<Environment> marshallerType() {
        return Environment.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedEnvironment> type() {
        return BasicJsonMarshallerTypedEnvironment.class;
    }
}
