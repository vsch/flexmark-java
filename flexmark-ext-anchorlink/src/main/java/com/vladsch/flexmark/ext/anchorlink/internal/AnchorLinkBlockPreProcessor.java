package com.vladsch.flexmark.ext.anchorlink.internal;

import com.vladsch.flexmark.ext.anchorlink.AnchorLink;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.Heading;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.block.BlockPreProcessor;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;

import java.util.Collections;
import java.util.Set;

public class AnchorLinkBlockPreProcessor implements BlockPreProcessor {
    final private GitHubHeaderIdGenerator generator;
    final private AnchorLinkOptions options;

    public AnchorLinkBlockPreProcessor(DataHolder options) {
        this.generator = new GitHubHeaderIdGenerator();
        this.options = new AnchorLinkOptions(options);
    }

    @Override
    public void preProcess(ParserState state, Block block) {
        if (block instanceof Heading) {
            Heading node = (Heading) block;
            if (node.getText().isNotNull()) {
                String headerId = generator.generate(node.getText());
                Node anchor = new AnchorLink(headerId);

                if (options.noWrap) {
                    if (node.getFirstChild() == null) {
                        node.appendChild(anchor);
                    } else {
                        node.getFirstChild().insertBefore(anchor);
                    }
                } else {
                    anchor.takeChildren(node);
                    node.appendChild(anchor);
                }

                state.added(anchor, false, false);
            }
        }
    }

    public static class Factory implements BlockPreProcessorFactory {
        @Override
        public Set<Class<? extends Block>> getBlockTypes() {
            return Collections.singleton(Heading.class);
        }

        @Override
        public Set<Class<? extends BlockPreProcessorFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<? extends BlockPreProcessorFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return true;
        }

        @Override
        public BlockPreProcessor create(ParserState state) {
            return new AnchorLinkBlockPreProcessor(state.getProperties());
        }
    }
}
