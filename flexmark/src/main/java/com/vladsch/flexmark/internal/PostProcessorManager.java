package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.collection.*;
import com.vladsch.flexmark.internal.util.dependency.DependencyResolver;
import com.vladsch.flexmark.internal.util.dependency.ResolvedDependencies;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.PostProcessor;
import com.vladsch.flexmark.parser.PostProcessorFactory;

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

        PostProcessDependencyResolver resolver = new PostProcessDependencyResolver();
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
        // first initialize node tracker if 
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
                    assert !hadGlobal;

                    if (classifyingNodeTracker == null) {
                        // build the node type information by traversing the document tree
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
                        // now we need to get the bitset for the excluded ancestors of the node, then intersect it with the actual ancestors of this factory
                        Set<Class<?>> excluded = dependentNodeTypes.get(node.getClass());
                        if (excluded != null) {
                            int index = classifyingNodeTracker.getItems().indexOf(node.getClass());
                            if (index != -1) {
                                BitSet nodeAncestors = classifyingNodeTracker.getNodeAncestryMap().get(index);
                                if (nodeAncestors != null) {
                                    BitSet nodeExclusions = classifyingNodeTracker.getExclusionSet().indexBitSet(excluded);
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

    private void preProcessBlocks() {
        //// here we run preProcessing stages
        //if (allPostProcessNodes.size() > 0) {
        //    HashMap<PostProcessorFactory, PostProcessor> blockPreProcessors = new HashMap<>(postProcessorDependencies.getDependentStages().size());
        //
        //    for (DependentPostProcessorDependencyStage preProcessorStage : postProcessorDependencies.getDependentStages()) {
        //        for (Map.Entry<Class<? extends Node>, List<PostProcessorFactory>> entry : preProcessorStage.factoryMap.entrySet()) {
        //            List<Node> blockList = allPostProcessNodes.get(entry.getKey());
        //            List<PostProcessorFactory> factoryList = entry.getValue();
        //
        //            if (blockList != null && factoryList != null) {
        //                for (Node block : blockList) {
        //                    if (allBlocksParserMap.containsKey(block)) {
        //                        for (PostProcessorFactory factory : factoryList) {
        //                            PostProcessor blockPreProcessor = blockPreProcessors.get(factory);
        //                            if (blockPreProcessor == null) {
        //                                blockPreProcessor = factory.create(this);
        //                                blockPreProcessors.put(factory, blockPreProcessor);
        //                            }
        //
        //                            Block newBlock = blockPreProcessor.preProcess(this, block);
        //
        //                            if (newBlock != block) {
        //                                // needs to be replaced
        //                                BlockParser blockParser = allBlocksParserMap.get(block);
        //                                if (blockParser != null) {
        //                                    allBlockParsers.removeIndex(blockParser);
        //                                }
        //
        //                                block.insertAfter(newBlock);
        //                                added(newBlock, null);
        //                                blockRemoved(block);
        //
        //                                if (block.getClass() != newBlock.getClass()) {
        //                                    // class changed, we will rerun for this one
        //                                    break;
        //                                }
        //
        //                                block = newBlock;
        //                            }
        //                        }
        //                    }
        //                }
        //            }
        //        }
        //    }
        //}
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
                    throw new IllegalStateException("PostProcessorFactory " + dependent + " is not document post processor and has empty node map, does nothing, should not be registered.");
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

    private static class PostProcessDependencyResolver extends DependencyResolver<PostProcessorFactory, PostProcessorDependencyStage, PostProcessorDependencies> {
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
    }
}
