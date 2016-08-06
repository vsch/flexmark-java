package com.vladsch.flexmark.util.mappers;

import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.util.Computable;

public class BlockClassifier implements Computable<Class<? extends Block>, Block> {
    final public static BlockClassifier INSTANCE = new BlockClassifier();

    private BlockClassifier() {
    }

    @Override
    public Class<? extends Block> compute(Block value) {
        return value.getClass();
    }
}
