package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.internal.BlockContinueImpl;
import com.vladsch.flexmark.internal.BlockNoneImpl;

/**
 * Result object for continuing parsing of a block, see static methods for constructors.
 */
public class BlockContinue {

    protected BlockContinue() {
    }

    public static BlockContinue none() {
        return null;
    }

    public static BlockContinue noneAtIndex(int newIndex) {
        return new BlockNoneImpl(newIndex, -1);
    }

    public static BlockContinue noneAtColumn(int newColumn) {
        return new BlockNoneImpl(-1, newColumn);
    }

    public static BlockContinue atIndex(int newIndex) {
        return new BlockContinueImpl(newIndex, -1, false);
    }

    public static BlockContinue atColumn(int newColumn) {
        return new BlockContinueImpl(-1, newColumn, false);
    }

    public static BlockContinue finished() {
        return new BlockContinueImpl(-1, -1, true);
    }

    public static BlockContinue finishedAtColumn(int newColumn) {
        return new BlockContinueImpl(-1, newColumn, true);
    }

    public static BlockContinue finishedAtIndex(int newIndex) {
        return new BlockContinueImpl(newIndex, -1, true);
    }
}
