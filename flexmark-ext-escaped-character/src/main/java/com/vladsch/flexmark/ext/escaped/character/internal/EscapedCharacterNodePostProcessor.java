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

import com.vladsch.flexmark.ext.escaped.character.EscapedCharacter;
import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.internal.util.NodeTracker;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.ReplacedTextMapper;
import com.vladsch.flexmark.internal.util.sequence.ReplacedTextRegion;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;

import java.util.ArrayList;

public class EscapedCharacterNodePostProcessor extends NodePostProcessor {
    public EscapedCharacterNodePostProcessor(Document document) {
    }

    @Override
    public void process(NodeTracker state, Node node) {
        BasedSequence original = node.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
        BasedSequence literal = Escaping.unescape(original, textMapper);

        Node lastNode = node;
        int lastEscaped = 0;
        boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
        TextBase textBase = null;

        ArrayList<ReplacedTextRegion> replacedRegions = textMapper.getRegions();
        
        for (ReplacedTextRegion region : replacedRegions) {
            //String found = m.group();
            if (original.charAt(region.getOriginal().getStartOffset()) == '\\') {
                int startOffset = region.getOriginal().getStartOffset();
                int endOffset = region.getOriginal().getEndOffset();

                if (wrapInTextBase) {
                    wrapInTextBase = false;
                    textBase = new TextBase(original);
                    node.insertBefore(textBase);
                    textBase.appendChild(node);
                    state.nodeAdded(textBase);
                }

                if (startOffset != lastEscaped) {
                    BasedSequence escapedChars = original.subSequence(lastEscaped, startOffset);
                    Node node1 = new Text(escapedChars);
                    lastNode.insertAfter(node1);
                    lastNode = node1;
                    state.nodeAdded(lastNode);
                }

                BasedSequence originalEscaped = original.subSequence(startOffset, endOffset);
                BasedSequence text = originalEscaped.subSequence(1);
                EscapedCharacter escapedCharacter = new EscapedCharacter(originalEscaped.subSequence(0, 1), text);
                lastNode.insertAfter(escapedCharacter);
                lastNode = escapedCharacter;
                Text node1 = new Text(text);
                node1.appendChild(node1);
                state.nodeAddedWithChildren(lastNode);

                lastEscaped = endOffset;
            }
        }

        if (lastEscaped != original.length()) {
            BasedSequence escapedChars = original.subSequence(lastEscaped, original.length());
            Node node1 = new Text(escapedChars);
            lastNode.insertAfter(node1);
            state.nodeAdded(lastNode);
        }

        lastNode = node.getNext();
        node.unlink();
        state.nodeRemoved(node);
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
