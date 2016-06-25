package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzBlock;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.BulletListItem;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.block.BlockPreProcessor;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;

import java.util.Collections;
import java.util.Set;

public class ZzzzzzBlockPreProcessorFactory implements BlockPreProcessorFactory {
    @Override
    public Set<Class<? extends Block>> getBlockTypes() {
        return Collections.singleton(BulletListItem.class);
    }

    @Override
    public Set<Class<? extends BlockPreProcessorFactory>> getAfterDependents() {
        return null;
    }

    @Override
    public Set<Class<? extends BlockPreProcessorFactory>> getBeforeDependents() {
        return null;
    }

    @Override
    public boolean affectsGlobalScope() {
        return false;
    }

    @Override
    public BlockPreProcessor create(ParserState state) {
        return new BlockPreProcessor() {
            @Override
            public Block preProcess(Block block) {
                Node taskListMarker = block.getFirstChild();
                if (taskListMarker instanceof Zzzzzz) {
                    // convert it to a task list item
                    return new ZzzzzzBlock(taskListMarker);
                }
                return block;
            }
        };
    }
}
