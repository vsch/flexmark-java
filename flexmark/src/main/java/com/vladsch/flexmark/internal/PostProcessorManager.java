package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.internal.util.collection.DataKey;
import com.vladsch.flexmark.internal.util.collection.OrderedSet;
import com.vladsch.flexmark.internal.util.dependency.DependencyResolver;
import com.vladsch.flexmark.internal.util.dependency.ResolvedDependencies;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.InlineParserFactory;
import com.vladsch.flexmark.parser.PostProcessorFactory;

import java.util.*;
import java.util.stream.Collectors;

public class PostProcessorManager {
    private static HashMap<DataKey<Boolean>, PostProcessorFactory> CORE_POST_PROCESSORS = new HashMap<>();
    static {
        //CORE_POST_PROCESSORS.put(Parser.REFERENCE_PARAGRAPH_PRE_PROCESSOR, new ReferencePreProcessorFactory());
    }

    private final DependentPostProcessorDependencies postProcessorDependencies;
    private OrderedSet<Node> allPostProcessNodes = new OrderedSet<>();
    private HashMap<Class<? extends Node>, BitSet> nodeClassIndices = new HashMap<>(); // stores the indices for the blocks in allPostProcessNodes by node class 

    public PostProcessorManager(Document document, DependentPostProcessorDependencies postProcessorDependencies) {
        // collect all nodes of interest
        this.postProcessorDependencies = postProcessorDependencies;

        // instantiate the needed post processors
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


    public static class DependentPostProcessorDependencyStage {
        final private Map<Class<? extends Node>, List<PostProcessorFactory>> factoryMap;
        final private List<PostProcessorFactory> dependents;

        public DependentPostProcessorDependencyStage(List<PostProcessorFactory> dependents) {
            // compute mappings
            HashMap<Class<? extends Node>, List<PostProcessorFactory>> map = new HashMap<>();

            for (PostProcessorFactory dependent : dependents) {
                Set<Class<? extends Node>> blockTypes = dependent instanceof PostProcessorFactory ? ((PostProcessorFactory) dependent).getNodeTypes() : Collections.EMPTY_SET;
                for (Class<? extends Node> blockType : blockTypes) {
                    List<PostProcessorFactory> factories = map.get(blockType);
                    if (factories == null) {
                        factories = new ArrayList<>();
                        map.put(blockType, factories);
                    }
                    factories.add(dependent);
                }
            }

            this.dependents = dependents;
            this.factoryMap = map;
        }
    }

    public static class DependentPostProcessorDependencies extends ResolvedDependencies<DependentPostProcessorDependencyStage> {
        final private Set<Class<? extends Node>> blockTypes;
        final private Set<PostProcessorFactory> DependentPostProcessorFactories;

        public DependentPostProcessorDependencies(List<DependentPostProcessorDependencyStage> dependentStages) {
            super(dependentStages);
            Set<Class<? extends Node>> blockTypes = new HashSet<>();
            Set<PostProcessorFactory> DependentPostProcessorFactories = new HashSet<>();
            for (DependentPostProcessorDependencyStage stage : dependentStages) {
                blockTypes.addAll(stage.factoryMap.keySet());
                DependentPostProcessorFactories.addAll(stage.dependents);
            }
            this.DependentPostProcessorFactories = DependentPostProcessorFactories;
            this.blockTypes = blockTypes;
        }

        public Set<Class<? extends Node>> getBlockTypes() {
            return blockTypes;
        }

        public Set<PostProcessorFactory> getDependentPostProcessorFactories() {
            return DependentPostProcessorFactories;
        }
    }

    private static class BlockDependencyResolver extends DependencyResolver<PostProcessorFactory, DependentPostProcessorDependencyStage, DependentPostProcessorDependencies> {
        @Override
        protected Class<? extends PostProcessorFactory> getDependentClass(PostProcessorFactory dependent) {
            return dependent.getClass();
        }

        @Override
        protected DependentPostProcessorDependencies createResolvedDependencies(List<DependentPostProcessorDependencyStage> stages) {
            return new DependentPostProcessorDependencies(stages);
        }

        @Override
        protected DependentPostProcessorDependencyStage createStage(List<PostProcessorFactory> dependents) {
            return new DependentPostProcessorDependencyStage(dependents);
        }
    }

    public static DependentPostProcessorDependencies calculateDependentPostProcessors(DataHolder options, List<PostProcessorFactory> blockPreProcessors, InlineParserFactory inlineParserFactory) {
        List<PostProcessorFactory> list = new ArrayList<>();
        // By having the custom factories come first, extensions are able to change behavior of core syntax.
        list.addAll(blockPreProcessors);

        // add core block preprocessors
        list.addAll(CORE_POST_PROCESSORS.keySet().stream().filter(options::get).map(key -> CORE_POST_PROCESSORS.get(key)).collect(Collectors.toList()));

        BlockDependencyResolver resolver = new BlockDependencyResolver();
        return resolver.resolveDependencies(list);
    }

}
