package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ast.BulletListItem;
import com.vladsch.flexmark.ast.ListItem;
import com.vladsch.flexmark.ast.OrderedListItem;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.parser.block.BlockPreProcessor;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class TaskListItemBlockPreProcessor implements BlockPreProcessor {

    public TaskListItemBlockPreProcessor(DataHolder options) {
    }

    @Override
    public void preProcess(ParserState state, Block block) {
        if (block instanceof BulletListItem || block instanceof OrderedListItem) {
            // we chop up the previous paragraph into definition terms and add the definition item to the last one
            // we add all these to the previous DefinitionList or add a new one if there isn't one
            final ListItem listItem = (ListItem) block;

            final BasedSequence markerSuffix = listItem.getMarkerSuffix();

            if (markerSuffix.matches("[ ]") || markerSuffix.matches("[x]") || markerSuffix.matches("[X]")) {
                TaskListItem taskListItem = new TaskListItem(listItem);
                taskListItem.setTight(listItem.isOwnTight());
                listItem.insertBefore(taskListItem);
                listItem.unlink();
                state.blockAdded(taskListItem);
                state.blockRemoved(listItem);
            }
        }
    }

    public static class Factory implements BlockPreProcessorFactory {
        @NotNull
        @Override
        public Set<Class<? extends Block>> getBlockTypes() {
            HashSet<Class<? extends Block>> set = new HashSet<>();
            set.add(BulletListItem.class);
            set.add(OrderedListItem.class);
            return set;
        }

        @Nullable
        @Override
        public Set<Class<?>> getAfterDependents() {
            return null;
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return true;
        }

        @NotNull
        @Override
        public BlockPreProcessor apply(@NotNull ParserState state) {
            return new TaskListItemBlockPreProcessor(state.getProperties());
        }
    }
}
