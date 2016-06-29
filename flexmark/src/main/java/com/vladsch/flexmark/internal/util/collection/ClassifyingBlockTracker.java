package com.vladsch.flexmark.internal.util.collection;

import com.vladsch.flexmark.internal.util.BlockParserTracker;
import com.vladsch.flexmark.internal.util.BlockTracker;
import com.vladsch.flexmark.internal.util.mappers.NodeClassifier;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.block.BlockParser;

public class ClassifyingBlockTracker implements BlockTracker, BlockParserTracker {
    final protected ClassificationBag<Class<? extends Node>, Node> nodeClassifier = new ClassificationBag<>(NodeClassifier.INSTANCE);
    final protected OrderedMultiMap<BlockParser, Block> allBlockParsersMap = new OrderedMultiMap<>(new CollectionHost<Paired<BlockParser, Block>>() {
        @Override
        public void adding(int index, Paired<BlockParser, Block> paired, Object v) {
            Block block = paired.getSecond();
            if (block != null) nodeClassifier.add(block);
        }

        @Override
        public Object removing(int index, Paired<BlockParser, Block> paired) {
            Block block = paired.getSecond();
            if (block != null) nodeClassifier.remove(block);
            return paired;
        }

        @Override
        public void clearing() {
            nodeClassifier.clear();
        }

        @Override
        public void addingNulls(int index) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean skipHostUpdate() {
            return false;
        }

        @Override
        public int getIteratorModificationCount() {
            return allBlockParsersMap.getModificationCount();
        }
    });

    public OrderedSet<BlockParser> allBlockParsers() {
        return allBlockParsersMap.keySet();
    }

    public OrderedSet<Block> allBlocks() {
        return allBlockParsersMap.valueSet();
    }

    public boolean containsKey(BlockParser parser) {
        return allBlockParsersMap.containsKey(parser);
    }

    public boolean containsValue(Block parser) {
        return allBlockParsersMap.containsValue(parser);
    }

    public ClassificationBag<Class<? extends Node>, Node> getNodeClassifier() {
        return nodeClassifier;
    }

    @Override
    public void blockParserAdded(BlockParser blockParser) {
        allBlockParsersMap.putKeyValue(blockParser, blockParser.getBlock());
    }

    @Override
    public void blockParserRemoved(BlockParser blockParser) {
        allBlockParsersMap.removeKey(blockParser);
    }

    @Override
    public void blockAdded(Block node) {
        allBlockParsersMap.putValueKey(node, null);
    }

    @Override
    public void blockAddedWithChildren(Block node) {
        allBlockParsersMap.putValueKey(node, null);
        addBlocks(node.getChildren());
    }

    @Override
    public void blockAddedWithDescendants(Block node) {
        allBlockParsersMap.putValueKey(node, null);
        addBlocks(node.getDescendants());
    }

    private void addBlocks(ReversiblePeekingIterable<Node> nodes) {
        for (Node child : nodes) {
            if (child instanceof Block) {
                allBlockParsersMap.putValueKey((Block) child, null);
            }
        }
    }

    @Override
    public void blockRemoved(Block node) {
        allBlockParsersMap.removeValue(node);
    }

    @Override
    public void blockRemovedWithChildren(Block node) {
        allBlockParsersMap.removeValue(node);
        removeBlocks(node.getChildren());
    }

    @Override
    public void blockRemovedWithDescendants(Block node) {
        allBlockParsersMap.removeValue(node);
        removeBlocks(node.getDescendants());
    }

    private void removeBlocks(ReversiblePeekingIterable<Node> nodes) {
        for (Node child : nodes) {
            if (child instanceof Block) {
                allBlockParsersMap.removeValue(child);
            }
        }
    }
}
