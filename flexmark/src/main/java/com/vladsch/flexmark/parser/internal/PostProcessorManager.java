package com.vladsch.flexmark.parser.internal;

import com.vladsch.flexmark.parser.PostProcessor;
import com.vladsch.flexmark.parser.PostProcessorFactory;
import com.vladsch.flexmark.util.ast.ClassifyingNodeTracker;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeClassifierVisitor;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import com.vladsch.flexmark.util.dependency.DependencyResolver;
import com.vladsch.flexmark.util.dependency.DependentItem;
import com.vladsch.flexmark.util.dependency.DependentItemMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PostProcessorManager {
  private final List<PostProcessorDependencyStage> postProcessorDependencies;

  private PostProcessorManager(List<PostProcessorDependencyStage> postProcessorDependencies) {
    this.postProcessorDependencies = postProcessorDependencies;
  }

  public static List<PostProcessorDependencyStage> calculatePostProcessors(
      List<PostProcessorFactory> postProcessorFactories) {
    List<List<PostProcessorFactory>> resolveDependencies =
        DependencyResolver.resolveDependencies(
            postProcessorFactories, PostProcessorManager::prioritizePostProcessors, null);
    List<PostProcessorDependencyStage> dependencyStages =
        new ArrayList<>(resolveDependencies.size());
    for (List<PostProcessorFactory> dependencies : resolveDependencies) {
      dependencyStages.add(new PostProcessorDependencyStage(dependencies));
    }

    return dependencyStages;
  }

  public static Document processDocument(
      Document document, List<PostProcessorDependencyStage> processorDependencies) {
    if (!processorDependencies.isEmpty()) {
      PostProcessorManager manager = new PostProcessorManager(processorDependencies);
      document = manager.postProcess(document);
    }
    return document;
  }

  private Document postProcess(Document document) {
    // first initialize node tracker if
    ClassifyingNodeTracker classifyingNodeTracker;

    classifyingNodeTracker = null;
    for (PostProcessorDependencyStage stage : postProcessorDependencies) {
      // idiosyncrasy of post processors the last dependency can be global, in which case it
      // processes the whole document and no ancestry info is
      // provided
      // new ClassifyingNodeTracker()
      for (PostProcessorFactory dependent : stage.dependents) {
        if (dependent.affectsGlobalScope()) {
          document = dependent.apply(document).processDocument(document);
          // assume it no longer reflects reality;
          classifyingNodeTracker = null;
        } else {
          if (classifyingNodeTracker == null) {
            // build the node type information by traversing the document tree
            classifyingNodeTracker = new NodeClassifierVisitor(stage.myNodeMap).classify(document);
          }

          Map<Class<?>, Set<Class<?>>> dependentNodeTypes = dependent.getNodeTypes();
          PostProcessor postProcessor = dependent.apply(document);
          BitSet exclusionSet = new BitSet();
          if (dependentNodeTypes != null) {
            for (Set<Class<?>> excluded : dependentNodeTypes.values()) {
              BitSet mapped = classifyingNodeTracker.getExclusionSet().indexBitSet(excluded);
              exclusionSet.or(mapped);
            }

            ReversibleIterable<Node> nodes =
                classifyingNodeTracker.getCategoryItems(dependentNodeTypes.keySet());
            for (Node node : nodes) {
              if (node.getParent() == null) {
                continue;
              }
              // now we need to get the bitset for the excluded ancestors of the node, then
              // intersect it with the actual ancestors of this factory
              int index;
              BitSet nodeAncestors;
              BitSet nodeExclusions;
              Set<Class<?>> excluded = dependentNodeTypes.get(node.getClass());
              if (excluded != null) {
                index = classifyingNodeTracker.getItems().indexOf(node);
                if (index != -1) {
                  nodeAncestors = classifyingNodeTracker.getNodeAncestryMap().get(index);
                  if (nodeAncestors != null) {
                    nodeExclusions = classifyingNodeTracker.getExclusionSet().indexBitSet(excluded);
                    nodeExclusions.and(nodeAncestors);
                    if (!nodeExclusions.isEmpty()) {
                      // has excluded ancestor
                      continue;
                    }
                  }
                }
              }
              postProcessor.process(classifyingNodeTracker, node);
            }
          }
        }
      }
    }

    return document;
  }

  private static DependentItemMap<PostProcessorFactory> prioritizePostProcessors(
      DependentItemMap<PostProcessorFactory> dependentMap) {
    // put globals last
    List<Map.Entry<Class<?>, DependentItem<PostProcessorFactory>>> prioritized =
        dependentMap.entries();
    prioritized.sort(
        (e1, e2) -> {
          int g1 = e1.getValue().isGlobalScope ? 1 : 0;
          int g2 = e2.getValue().isGlobalScope ? 1 : 0;
          return g1 - g2;
        });

    BitSet dependentMapSet = dependentMap.keySet().keyDifferenceBitSet(prioritized);
    if (dependentMapSet.isEmpty()) {
      return dependentMap;
    }

    DependentItemMap<PostProcessorFactory> prioritizedMap =
        new DependentItemMap<>(prioritized.size());
    prioritizedMap.addAll(prioritized);

    return prioritizedMap;
  }

  public static class PostProcessorDependencyStage {
    private final Map<Class<? extends Node>, Set<Class<?>>> myNodeMap;
    private final List<PostProcessorFactory> dependents;

    private PostProcessorDependencyStage(List<PostProcessorFactory> dependents) {
      // compute mappings
      Map<Class<? extends Node>, Set<Class<?>>> nodeMap = new HashMap<>();

      for (PostProcessorFactory dependent : dependents) {
        Map<Class<?>, Set<Class<?>>> types = dependent.getNodeTypes();
        if ((types == null || types.isEmpty()) && !dependent.affectsGlobalScope()) {
          throw new IllegalStateException(
              "PostProcessorFactory "
                  + dependent
                  + " is not document post processor and has empty node map, does nothing, should not be registered.");
        }

        if (types != null) {
          for (Map.Entry<Class<?>, Set<Class<?>>> entry : types.entrySet()) {
            if (Node.class.isAssignableFrom(entry.getKey())) {
              Set<Class<?>> classes = nodeMap.get(entry.getKey());
              Set<Class<?>> value = entry.getValue();
              if (classes == null) {
                // copy so it is not modified by additional dependencies injecting other exclusions
                // by mistake
                classes = new HashSet<>(value);
                nodeMap.put((Class<? extends Node>) entry.getKey(), classes);
              } else {
                try {
                  classes.addAll(value);
                } catch (UnsupportedOperationException e) {
                  classes = new HashSet<>(classes);
                  classes.addAll(value);
                }
              }
            }
          }
        }
      }

      this.dependents = dependents;
      this.myNodeMap = nodeMap;
    }
  }
}
