package com.vladsch.flexmark.internal.util.collection;

import com.vladsch.flexmark.internal.util.BlockParserTracker;
import com.vladsch.flexmark.internal.util.BlockTracker;
import com.vladsch.flexmark.internal.util.mappers.NodeClassifier;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.block.BlockParser;

public class ClassifiedBlockTracker implements BlockTracker, BlockParserTracker {
    final protected ClassifiedBag<Class<? extends Node>, Node> classifiedNodeBag = new ClassifiedBag<Class<? extends Node>, Node>(NodeClassifier.INSTANCE);
    final protected OrderedMultiMap<BlockParser, Block> allBlocksParserMap = new OrderedMultiMap<>(new CollectionHost<Paired<BlockParser, Block>>() {
        @Override
        public void adding(int index, Paired<BlockParser, Block> paired, Object v) {
            Block block = paired.getSecond();
            if (block != null) classifiedNodeBag.add(block);
        }

        @Override
        public Object removing(int index, Paired<BlockParser, Block> paired) {
            Block block = paired.getSecond();
            if (block != null) classifiedNodeBag.remove(block);
            return paired;
        }

        @Override
        public void clearing() {
            classifiedNodeBag.clear();
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
            return allBlocksParserMap.getModificationCount();
        }
    });

    @Override
    public void blockParserAdded(BlockParser blockParser) {
        allBlocksParserMap.putKeyValue(blockParser, blockParser.getBlock());
    }

    @Override
    public void blockParserRemoved(BlockParser blockParser) {
        allBlocksParserMap.removeKey(blockParser);
    }
    
    @Override
    public void blockAdded(Block node) {
        allBlocksParserMap.putValueKey(node, null);
    }

    @Override
    public void blockAddedWithChildren(Block node) {
        allBlocksParserMap.putValueKey(node, null);
        addBlocks(node.getChildren());
    }

    @Override
    public void blockAddedWithDescendants(Block node) {
        allBlocksParserMap.putValueKey(node, null);
        addBlocks(node.getDescendants());
    }

    private void addBlocks(ReversiblePeekingIterable<Node> nodes) {
        for (Node child : nodes) {
            if (child instanceof Block) {
                allBlocksParserMap.putValueKey((Block) child, null);
            }
        }
    }

    @Override
    public void blockRemoved(Block node) {
        allBlocksParserMap.removeValue(node);
    }

    @Override
    public void blockRemovedWithChildren(Block node) {
        allBlocksParserMap.removeValue(node);
        removeBlocks(node.getChildren());
    }

    @Override
    public void blockRemovedWithDescendants(Block node) {
        allBlocksParserMap.removeValue(node);
        removeBlocks(node.getDescendants());
    }

    private void removeBlocks(ReversiblePeekingIterable<Node> nodes) {
        for (Node child : nodes) {
            if (child instanceof Block) {
                allBlocksParserMap.removeValue(child);
            }
        }
    }

}
