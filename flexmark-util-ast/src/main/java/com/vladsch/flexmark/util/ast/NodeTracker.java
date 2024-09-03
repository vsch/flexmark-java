package com.vladsch.flexmark.util.ast;

import org.jetbrains.annotations.NotNull;

public interface NodeTracker {
  void nodeAdded(@NotNull Node node);

  void nodeAddedWithChildren(@NotNull Node node);

  void nodeAddedWithDescendants(@NotNull Node node);

  void nodeRemoved(@NotNull Node node);

  void nodeRemovedWithChildren(@NotNull Node node);

  void nodeRemovedWithDescendants(@NotNull Node node);
}
