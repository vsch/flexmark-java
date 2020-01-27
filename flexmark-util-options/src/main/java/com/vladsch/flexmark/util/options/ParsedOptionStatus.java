package com.vladsch.flexmark.util.options;

public enum ParsedOptionStatus {
    VALID(0),
    IGNORED(1),
    WEAK_WARNING(2),
    WARNING(3),
    ERROR(4);

    final private int level;

    ParsedOptionStatus(int level) {
        this.level = level;
    }

    ParsedOptionStatus escalate(ParsedOptionStatus other) {
        return level < other.level ? other : this;
    }
}
