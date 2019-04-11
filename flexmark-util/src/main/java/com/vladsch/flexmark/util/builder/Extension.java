package com.vladsch.flexmark.util.builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Base interface for a parser/renderer extension.
 * <p>
 * Doesn't have any methods itself, but has specific sub interfaces to
 * configure parser/renderer. This base interface is for convenience,
 * so that a list of extensions can be built and then used for configuring
 * both the parser and renderer in the same way.
 *
 * Bor convenience and by convention, classes that implement this method
 * also have a static create() method that returns an instance of the extension.
 *
 */
public interface Extension {
    Set<Extension> EMPTY_SET = new HashSet<Extension>();
    Iterable<Extension> EMPTY_LIST = new ArrayList<Extension>();
}
