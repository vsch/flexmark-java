package com.vladsch.flexmark.util.sequence.edit;

public enum TrackerDirection {
    NONE,  // not moving, pad around it
    LEFT,  // left leaning, ie. was moving left so pad after it
    RIGHT; // right leaning, ie. was moving right so pad before it
}
