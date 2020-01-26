package com.vladsch.flexmark.util.format.options;

public enum ListSpacing {
    AS_IS,    // as is, leave all items in their state with blank lines as they are
    LOOSEN,   // if list has a loose item then loosen the list by adding blank lines to all items
    TIGHTEN,  // if list has no loose items then tighten list by removing unnecessary blank lines
    LOOSE,    // make loose by adding blank lines to all items
    TIGHT,    // make tight by removing all unnecessary blank lines from all items
}
