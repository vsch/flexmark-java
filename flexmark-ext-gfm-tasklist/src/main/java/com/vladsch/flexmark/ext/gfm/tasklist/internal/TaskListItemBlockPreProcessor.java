package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ast.BulletListItem;
import com.vladsch.flexmark.ast.ListItem;
import com.vladsch.flexmark.ast.OrderedListItem;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.parser.block.BlockPreProcessor;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.util.HashSet;
import java.util.Set;

public class TaskListItemBlockPreProcessor implements BlockPreProcessor {
  private TaskListItemBlockPreProcessor() {}

  @Override
  public void preProcess(ParserState state, Block block) {
    if (block instanceof BulletListItem || block instanceof OrderedListItem) {
      // we chop up the previous paragraph into definition terms and add the definition item to the
      // last one
      // we add all these to the previous DefinitionList or add a new one if there isn't one
      final ListItem listItem = (ListItem) block;

      final BasedSequence markerSuffix = listItem.getMarkerSuffix();

      if (markerSuffix.matches("[ ]")
          || markerSuffix.matches("[x]")
          || markerSuffix.matches("[X]")) {
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

    @Override
    public Set<Class<? extends Block>> getBlockTypes() {
      Set<Class<? extends Block>> set = new HashSet<>();
      set.add(BulletListItem.class);
      set.add(OrderedListItem.class);
      return set;
    }

    @Override
    public Set<Class<?>> getAfterDependents() {
      return null;
    }

    @Override
    public Set<Class<?>> getBeforeDependents() {
      return null;
    }

    @Override
    public boolean affectsGlobalScope() {
      return true;
    }

    @Override
    public BlockPreProcessor apply(ParserState state) {
      return new TaskListItemBlockPreProcessor();
    }
  }
}
