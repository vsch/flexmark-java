package com.vladsch.flexmark.ext.escaped.character.internal;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.TextBase;
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacter;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeTracker;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import com.vladsch.flexmark.util.sequence.ReplacedTextRegion;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EscapedCharacterNodePostProcessor extends NodePostProcessor {
    public EscapedCharacterNodePostProcessor(Document document) {
    }

    @Override
    public void process(@NotNull NodeTracker state, @NotNull Node node) {
        BasedSequence original = node.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
        // NOTE: needed for its side-effects
        Escaping.unescape(original, textMapper);

        int lastEscaped = 0;
        boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
        TextBase textBase = wrapInTextBase ? null : (TextBase) node.getParent();

        ArrayList<ReplacedTextRegion> replacedRegions = textMapper.getRegions();

        for (ReplacedTextRegion region : replacedRegions) {
            int startOffset = region.getOriginalRange().getStart();
            int endOffset = region.getOriginalRange().getEnd();

            if (original.charAt(startOffset) == '\\' && region.getReplacedRange().getSpan() == 1
                    // fix for #19, ArrayIndexOutOfBounds while parsing markdown with backslash as last character of text block
                    && startOffset + 1 < original.length()) {
                if (wrapInTextBase) {
                    wrapInTextBase = false;
                    textBase = new TextBase(original);
                    node.insertBefore(textBase);
                    state.nodeAdded(textBase);
                }

                if (startOffset != lastEscaped) {
                    BasedSequence escapedChars = original.subSequence(lastEscaped, startOffset);
                    Node node1 = new Text(escapedChars);
                    textBase.appendChild(node1);
                    state.nodeAdded(node1);
                }

                BasedSequence origToDecorateText = original.subSequence(startOffset, endOffset);
                BasedSequence text = origToDecorateText.subSequence(1);
                EscapedCharacter decorationNode = new EscapedCharacter(origToDecorateText.subSequence(0, 1), text);
                textBase.appendChild(decorationNode);
                state.nodeAdded(decorationNode);

                lastEscaped = endOffset;
            }
        }

        if (lastEscaped > 0) {
            if (lastEscaped != original.length()) {
                BasedSequence escapedChars = original.subSequence(lastEscaped, original.length());
                Node node1 = new Text(escapedChars);
                textBase.appendChild(node1);
                state.nodeAdded(node1);
            }

            node.unlink();
            state.nodeRemoved(node);
        }
    }

    public static class Factory extends NodePostProcessorFactory {
        public Factory() {
            super(false);
            addNodeWithExclusions(Text.class, DoNotDecorate.class);
        }

        @NotNull
        @Override
        public NodePostProcessor apply(@NotNull Document document) {
            return new EscapedCharacterNodePostProcessor(document);
        }
    }
}
