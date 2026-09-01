package walkingkooka.tree.json.select;

import walkingkooka.text.printer.TreePrintableTesting;

public interface HasJsonSelectorContextTesting extends TreePrintableTesting {

    default void jsonSelectorContextAndCheck(final HasJsonSelectorContext has,
                                             final JsonSelectorContext expected) {
        this.checkEquals(
            has.jsonSelectorContext(),
            expected,
            has::toString
        );
    }
}
