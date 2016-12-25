/*
 * Copyright (c) 2015-2016 Vladimir Schneider <vladimir.schneider@gmail.com>, all rights reserved.
 *
 * This code is private property of the copyright holder and cannot be used without
 * having obtained a license or prior written permission of the of the copyright holder.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package com.vladsch.flexmark.ext.escaped.character.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacter;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.NodeTracker;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import com.vladsch.flexmark.util.sequence.ReplacedTextRegion;

import java.util.ArrayList;

public class EscapedCharacterNodePostProcessor extends NodePostProcessor {
    public EscapedCharacterNodePostProcessor(Document document) {
    }

    @Override
    public void process(NodeTracker state, Node node) {
        BasedSequence original = node.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
        BasedSequence literal = Escaping.unescape(original, textMapper);

        int lastEscaped = 0;
        boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
        TextBase textBase = wrapInTextBase ? null : (TextBase) node.getParent();

        ArrayList<ReplacedTextRegion> replacedRegions = textMapper.getRegions();

        for (ReplacedTextRegion region : replacedRegions) {
            //String found = m.group();
            int startOffset = region.getOriginalRange().getStart() - original.getStartOffset();
            int endOffset = region.getOriginalRange().getEnd() - original.getStartOffset();

            if (original.charAt(startOffset) == '\\' && region.getReplacedRange().length() == 1
                    // fix for #19, ArrayIndexOutOfBounds while parsing markdown with backslash as last character of text block
                    && startOffset + 1 < original.length()) {
                if (wrapInTextBase) {
                    wrapInTextBase = false;
                    textBase = new TextBase(original);
                    node.insertBefore(textBase);
                    state.nodeAdded(textBase);
                }

                if (startOffset != lastEscaped) {
                    if (startOffset > original.length() || lastEscaped > original.length()) {
                        int tmp = 0;
                    }
                    BasedSequence escapedChars = original.subSequence(lastEscaped, startOffset);
                    Node node1 = new Text(escapedChars);
                    textBase.appendChild(node1);
                    state.nodeAdded(node1);
                }

                BasedSequence origToDecorateText = original.subSequence(startOffset, endOffset);
                BasedSequence text = origToDecorateText.subSequence(1);
                EscapedCharacter decorationNode = new EscapedCharacter(origToDecorateText.subSequence(0, 1), text);
                textBase.appendChild(decorationNode);
                //Text undecoratedTextNode = new Text(origToDecorateText);
                //decorationNode.appendChild(undecoratedTextNode);
                //state.nodeAddedWithChildren(decorationNode);
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

        @Override
        public NodePostProcessor create(Document document) {
            return new EscapedCharacterNodePostProcessor(document);
        }
    }
}
