package com.vladsch.flexmark.core.test.util.html;

import com.vladsch.flexmark.parser.PostProcessorFactory;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.util.ast.Document;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

abstract class NodePostProcessorFactory implements PostProcessorFactory {
  private final Map<Class<?>, Set<Class<?>>> nodeMap = new HashMap<>();

  NodePostProcessorFactory() {}

  @Override
  public Set<Class<?>> getAfterDependents() {
    return null;
  }

  @Override
  public Set<Class<?>> getBeforeDependents() {
    return null;
  }

  @Override
  public final boolean affectsGlobalScope() {
    return false;
  }

  final void addNodes(Class<?>... nodeTypes) {
    for (Class<?> nodeType : nodeTypes) {
      nodeMap.put(nodeType, Collections.emptySet());
    }
  }

  @Override
  public final Map<Class<?>, Set<Class<?>>> getNodeTypes() {
    return nodeMap;
  }

  @Override
  public abstract NodePostProcessor apply(Document document);
}
