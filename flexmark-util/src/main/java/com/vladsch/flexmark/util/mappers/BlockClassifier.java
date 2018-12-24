package com.vladsch.flexmark.util.mappers;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.Computable;

public class BlockClassifier implements Computable<Class<? extends Block>, Block> {
    public static final BlockClassifier INSTANCE = new BlockClassifier();

    private BlockClassifier() {
    }

    @Override
    public Class<? extends Block> compute(Block value) {
        return value.getClass();
    }
}
