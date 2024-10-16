package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.parser.PostProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class NodePostProcessorFactory implements PostProcessorFactory {
  private final Map<Class<?>, Set<Class<?>>> nodeMap = new HashMap<>();

  // added to force constructor
  public NodePostProcessorFactory(boolean ignored) {}

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
  public final boolean affectsGlobalScope() {
    return false;
  }

  protected final void addNodes(Class<?>... nodeTypes) {
    for (Class<?> nodeType : nodeTypes) {
      nodeMap.put(nodeType, Collections.emptySet());
    }
  }

  @Override
  public final Map<Class<?>, Set<Class<?>>> getNodeTypes() {
    return nodeMap;
  }

  @NotNull
  @Override
  public abstract NodePostProcessor apply(@NotNull Document document);
}
