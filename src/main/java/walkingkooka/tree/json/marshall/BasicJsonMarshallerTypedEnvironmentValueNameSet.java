package walkingkooka.tree.json.marshall;

import walkingkooka.environment.EnvironmentValueNameSet;
import walkingkooka.tree.json.JsonNode;

/**
 * A {@link BasicJsonMarshaller} that handles {@link EnvironmentValueNameSet}.
 * Note the {@link walkingkooka.environment.EnvironmentValueName} are marshalled but not the types of each, instead they
 * will be unmarshalled with {@link Object}.
 */
final class BasicJsonMarshallerTypedEnvironmentValueNameSet extends BasicJsonMarshallerTyped<EnvironmentValueNameSet> {

    static BasicJsonMarshallerTypedEnvironmentValueNameSet instance() {
        return new BasicJsonMarshallerTypedEnvironmentValueNameSet();
    }

    private BasicJsonMarshallerTypedEnvironmentValueNameSet() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<EnvironmentValueNameSet> type() {
        return EnvironmentValueNameSet.class;
    }

    @Override
    String typeName() {
        return JsonNodeContext.computeTypeName(EnvironmentValueNameSet.class);
    }

    @Override
    EnvironmentValueNameSet unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    EnvironmentValueNameSet unmarshallNonNull(final JsonNode node,
                                              final JsonNodeUnmarshallContext context) {
        return EnvironmentValueNameSet.parse(
            node.stringOrFail()
        );
    }

    @Override
    JsonNode marshallNonNull(final EnvironmentValueNameSet set,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(
            set.text()
        );
    }
}
