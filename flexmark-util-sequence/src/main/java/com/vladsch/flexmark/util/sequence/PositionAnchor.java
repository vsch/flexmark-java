package com.vladsch.flexmark.util.sequence;

public enum PositionAnchor {
    CURRENT,    // not leaning, pad around it
    PREVIOUS,   // left leaning, ie. was moving left so pad after it
    NEXT;       // right leaning, ie. was moving right so pad before it
}
