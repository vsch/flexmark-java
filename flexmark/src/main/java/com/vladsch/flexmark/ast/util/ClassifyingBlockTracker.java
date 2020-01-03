package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.parser.block.BlockParser;
import com.vladsch.flexmark.parser.block.BlockParserTracker;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockTracker;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeClassifier;
import com.vladsch.flexmark.util.collection.ClassificationBag;
import com.vladsch.flexmark.util.collection.CollectionHost;
import com.vladsch.flexmark.util.collection.OrderedMultiMap;
import com.vladsch.flexmark.util.collection.OrderedSet;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import com.vladsch.flexmark.util.misc.Paired;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClassifyingBlockTracker implements BlockTracker, BlockParserTracker {
    protected final ClassificationBag<Class<?>, Node> nodeClassifier = new ClassificationBag<>(NodeClassifier.INSTANCE);
    protected final OrderedMultiMap<BlockParser, Block> allBlockParsersMap = new OrderedMultiMap<>(new CollectionHost<Paired<BlockParser, Block>>() {
        @Override
        public void adding(int index, @Nullable Paired<BlockParser, Block> paired, @Nullable Object v) {
            Block block = paired.getSecond();
            if (block != null) nodeClassifier.add(block);
        }

        @Override
        public Object removing(int index, @Nullable Paired<BlockParser, Block> paired) {
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
            // ignore
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

    public Block getValue(BlockParser parser) {
        return allBlockParsersMap.getKeyValue(parser);
    }

    public BlockParser getKey(Block parser) {
        return allBlockParsersMap.getValueKey(parser);
    }

    public boolean containsKey(BlockParser parser) {
        return allBlockParsersMap.containsKey(parser);
    }

    public boolean containsValue(Block parser) {
        return allBlockParsersMap.containsValue(parser);
    }

    public ClassificationBag<Class<?>, Node> getNodeClassifier() {
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

    private void validateLinked(Node node) {
        if (node.getNext() == null && node.getParent() == null) {
            throw new IllegalStateException("Added block " + node + " is not linked into the AST");
        }
    }

    @Override
    public void blockAdded(@NotNull Block node) {
        validateLinked(node);
        allBlockParsersMap.putValueKey(node, null);
    }

    @Override
    public void blockAddedWithChildren(@NotNull Block node) {
        validateLinked(node);
        allBlockParsersMap.putValueKey(node, null);
        addBlocks(node.getChildren());
    }

    @Override
    public void blockAddedWithDescendants(@NotNull Block node) {
        validateLinked(node);
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

    private void validateUnlinked(Node node) {
        if (!(node.getNext() == null && node.getParent() == null)) {
            throw new IllegalStateException("Removed block " + node + " is still linked in the AST");
        }
    }

    @Override
    public void blockRemoved(@NotNull Block node) {
        validateUnlinked(node);
        allBlockParsersMap.removeValue(node);
    }

    @Override
    public void blockRemovedWithChildren(@NotNull Block node) {
        validateUnlinked(node);
        allBlockParsersMap.removeValue(node);
        removeBlocks(node.getChildren());
    }

    @Override
    public void blockRemovedWithDescendants(@NotNull Block node) {
        validateUnlinked(node);
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
