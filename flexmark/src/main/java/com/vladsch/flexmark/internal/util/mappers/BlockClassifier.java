package com.vladsch.flexmark.internal.util.mappers;

import com.vladsch.flexmark.internal.util.collection.Computable;
import com.vladsch.flexmark.node.Block;

public class BlockClassifier implements Computable<Class<? extends Block>, Block> {
    final public static BlockClassifier INSTANCE = new BlockClassifier();

    private BlockClassifier() {
    }

    @Override
    public Class<? extends Block> compute(Block value) {
        return value.getClass();
    }
}
