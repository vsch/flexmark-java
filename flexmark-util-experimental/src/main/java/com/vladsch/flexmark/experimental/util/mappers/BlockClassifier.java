package com.vladsch.flexmark.experimental.util.mappers;

import com.vladsch.flexmark.util.ast.Block;

import java.util.function.Function;

public class BlockClassifier implements Function<Block, Class<? extends Block>> {
    final public static BlockClassifier INSTANCE = new BlockClassifier();

    private BlockClassifier() {
    }

    @Override
    public Class<? extends Block> apply(Block value) {
        return value.getClass();
    }
}
