package com.vladsch.flexmark.util.misc;

import java.util.Collection;
import java.util.Collections;

/**
 * Base interface for a parser/renderer extension.
 * <p>
 * Doesn't have any methods itself, but has specific sub interfaces to
 * configure parser/renderer. This base interface is for convenience,
 * so that a list of extensions can be built and then used for configuring
 * both the parser and renderer in the same way.
 * <p>
 * Bor convenience and by convention, classes that implement this method
 * also have a static create() method that returns an instance of the extension.
 */
public interface Extension {
    Collection<Extension> EMPTY_LIST = Collections.emptyList();
}
