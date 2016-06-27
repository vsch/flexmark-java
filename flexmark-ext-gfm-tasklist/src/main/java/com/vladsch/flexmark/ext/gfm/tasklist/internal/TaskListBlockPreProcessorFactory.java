package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItemMarker;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.BulletListItem;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.block.BlockPreProcessor;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;

import java.util.Collections;
import java.util.Set;

public class TaskListBlockPreProcessorFactory implements BlockPreProcessorFactory {
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
            public Block preProcess(ParserState state, Block block) {
                Node taskListMarker = block.getFirstChild();
                if (taskListMarker instanceof TaskListItemMarker) {
                    // convert it to a task list item
                    return new TaskListItem((BulletListItem) block);
                }
                return block;
            }
        };
    }
}
