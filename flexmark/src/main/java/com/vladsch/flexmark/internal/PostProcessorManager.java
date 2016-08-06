package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.parser.PostProcessor;
import com.vladsch.flexmark.parser.PostProcessorFactory;
import com.vladsch.flexmark.util.collection.ClassifyingNodeTracker;
import com.vladsch.flexmark.util.collection.NodeClassifierVisitor;
import com.vladsch.flexmark.util.collection.OrderedSet;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import com.vladsch.flexmark.util.dependency.DependencyHandler;
import com.vladsch.flexmark.util.dependency.DependentItem;
import com.vladsch.flexmark.util.dependency.DependentItemMap;
import com.vladsch.flexmark.util.dependency.ResolvedDependencies;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class PostProcessorManager {
    private static HashMap<DataKey<Boolean>, PostProcessorFactory> CORE_POST_PROCESSORS = new HashMap<>();
    static {
        //CORE_POST_PROCESSORS.put(Parser.REFERENCE_PARAGRAPH_PRE_PROCESSOR, new ReferencePreProcessorFactory());
    }

    private final PostProcessorDependencies postProcessorDependencies;
    private OrderedSet<Node> allPostProcessNodes = new OrderedSet<>();

    public PostProcessorManager(PostProcessorDependencies postProcessorDependencies) {
        this.postProcessorDependencies = postProcessorDependencies;
    }

    public static PostProcessorDependencies calculatePostProcessors(DataHolder options, List<PostProcessorFactory> postProcessorFactories) {
        List<PostProcessorFactory> list = new ArrayList<>();
        // By having the custom factories come first, extensions are able to change behavior of core syntax.
        list.addAll(postProcessorFactories);

        // add core block preprocessors
        list.addAll(CORE_POST_PROCESSORS.keySet().stream().filter(options::get).map(key -> CORE_POST_PROCESSORS.get(key)).collect(Collectors.toList()));

        PostProcessDependencyHandler resolver = new PostProcessDependencyHandler();
        return resolver.resolveDependencies(list);
    }

    public static Document processDocument(Document document, PostProcessorDependencies processorDependencies) {
        if (!processorDependencies.isEmpty()) {
            PostProcessorManager manager = new PostProcessorManager(processorDependencies);
            document = manager.postProcess(document);
        }
        return document;
    }

    public Document postProcess(Document document) {
        // first initialize ast tracker if 
        ClassifyingNodeTracker classifyingNodeTracker = null;

        classifyingNodeTracker = null;
        for (PostProcessorDependencyStage stage : postProcessorDependencies.getDependentStages()) {
            // idiosyncrasy of post processors the last dependency can be global, in which case it processes the whole document and no ancestry info is 
            // provided
            //new ClassifyingNodeTracker()
            boolean hadGlobal = false;
            for (PostProcessorFactory dependent : stage.dependents) {
                if (dependent.affectsGlobalScope()) {
                    document = dependent.create(document).processDocument(document);
                    hadGlobal = true;
                    // assume it no longer reflects reality;
                    classifyingNodeTracker = null;
                } else {
                    if (hadGlobal) {
                        int tmp = 0;
                    }
                    assert !hadGlobal;

                    if (classifyingNodeTracker == null) {
                        // build the ast type information by traversing the document tree
                        classifyingNodeTracker = new NodeClassifierVisitor(stage.myNodeMap).classify(document);
                    }

                    Map<Class<? extends Node>, Set<Class<?>>> dependentNodeTypes = dependent.getNodeTypes();
                    PostProcessor postProcessor = dependent.create(document);
                    BitSet exclusionSet = new BitSet();
                    for (Set<Class<?>> excluded : dependentNodeTypes.values()) {
                        BitSet mapped = classifyingNodeTracker.getExclusionSet().indexBitSet(excluded);
                        exclusionSet.or(mapped);
                    }

                    ReversibleIterable<Node> nodes = classifyingNodeTracker.getCategoryItems(Node.class, dependentNodeTypes.keySet());
                    for (Node node : nodes) {
                        if (node.getParent() == null) continue; // was already removed 
                        // now we need to get the bitset for the excluded ancestors of the ast, then intersect it with the actual ancestors of this factory
                        int index = -1;
                        BitSet nodeAncestors = null;
                        BitSet nodeExclusions = null;
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

        return document;
    }

    public static class PostProcessorDependencyStage {
        final private Map<Class<? extends Node>, Set<Class<?>>> myNodeMap;
        final private boolean myWithExclusions;
        final private List<PostProcessorFactory> dependents;

        public PostProcessorDependencyStage(List<PostProcessorFactory> dependents) {
            // compute mappings
            HashMap<Class<? extends Node>, Set<Class<?>>> nodeMap = new HashMap<>();
            final boolean[] haveExclusions = { false };

            for (PostProcessorFactory dependent : dependents) {
                Map<Class<? extends Node>, Set<Class<?>>> types = dependent.getNodeTypes();
                if ((types == null || types.isEmpty()) && !dependent.affectsGlobalScope()) {
                    throw new IllegalStateException("PostProcessorFactory " + dependent + " is not document post processor and has empty ast map, does nothing, should not be registered.");
                }

                if (types != null) {
                    for (Map.Entry<Class<? extends Node>, Set<Class<?>>> entry : types.entrySet()) {
                        nodeMap.merge(entry.getKey(), entry.getValue(), new BiFunction<Set<Class<?>>, Set<Class<?>>, Set<Class<?>>>() {
                            @Override
                            public Set<Class<?>> apply(Set<Class<?>> classes, Set<Class<?>> classes2) {
                                classes.addAll(classes2);
                                if (!classes.isEmpty()) haveExclusions[0] = true;
                                return classes;
                            }
                        });
                    }
                }
            }

            this.dependents = dependents;
            this.myNodeMap = nodeMap;
            this.myWithExclusions = haveExclusions[0];
        }
    }

    public static class PostProcessorDependencies extends ResolvedDependencies<PostProcessorDependencyStage> {
        private final boolean myWithExclusions;

        public PostProcessorDependencies(List<PostProcessorDependencyStage> dependentStages) {
            super(dependentStages);
            boolean haveExclusions = false;
            for (PostProcessorDependencyStage stage : dependentStages) {
                if (stage.myWithExclusions) {
                    haveExclusions = true;
                    break;
                }
            }
            myWithExclusions = haveExclusions;
        }

        public boolean isWithExclusions() {
            return myWithExclusions;
        }
    }

    private static class PostProcessDependencyHandler extends DependencyHandler<PostProcessorFactory, PostProcessorDependencyStage, PostProcessorDependencies> {
        @Override
        protected Class<? extends PostProcessorFactory> getDependentClass(PostProcessorFactory dependent) {
            return dependent.getClass();
        }

        @Override
        protected PostProcessorDependencies createResolvedDependencies(List<PostProcessorDependencyStage> stages) {
            return new PostProcessorDependencies(stages);
        }

        @Override
        protected PostProcessorDependencyStage createStage(List<PostProcessorFactory> dependents) {
            return new PostProcessorDependencyStage(dependents);
        }

        @Override
        protected DependentItemMap<PostProcessorFactory> prioritize(final DependentItemMap<PostProcessorFactory> dependentMap) {
            // put globals last
            List<DependentItemMap.Entry<Class<? extends PostProcessorFactory>, DependentItem<PostProcessorFactory>>> prioritized = dependentMap.entries(); 
            prioritized.sort(new Comparator<DependentItemMap.Entry<Class<? extends PostProcessorFactory>, DependentItem<PostProcessorFactory>>>() {
                @Override
                public int compare(DependentItemMap.Entry<Class<? extends PostProcessorFactory>, DependentItem<PostProcessorFactory>> e1, DependentItemMap.Entry<Class<? extends PostProcessorFactory>, DependentItem<PostProcessorFactory>> e2) {
                    int g1 = e1.getValue().isGlobalScope ? 1 : 0; 
                    int g2 = e2.getValue().isGlobalScope ? 1 : 0; 
                    return g1 - g2;
                }
            });
            
            BitSet dependentMapSet = dependentMap.keySet().keyDifferenceBitSet(prioritized);
            if (dependentMapSet.isEmpty()) {
                return dependentMap;
            } 
            
            DependentItemMap<PostProcessorFactory> prioritizedMap = new DependentItemMap<>(prioritized.size());
            prioritizedMap.addAll(prioritized);
            
            return prioritizedMap;
        }
    }
}
