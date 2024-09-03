package com.vladsch.flexmark.util.ast;

import org.jetbrains.annotations.NotNull;

public interface BlockTracker {
  void blockAdded(@NotNull Block node);

  void blockAddedWithChildren(@NotNull Block node);

  void blockAddedWithDescendants(@NotNull Block node);

  void blockRemoved(@NotNull Block node);

  void blockRemovedWithChildren(@NotNull Block node);

  void blockRemovedWithDescendants(@NotNull Block node);
}
