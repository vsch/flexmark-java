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

package com.vladsch.flexmark.ext.typographic.internal;

import com.vladsch.flexmark.ext.typographic.TypographicSmarts;
import com.vladsch.flexmark.internal.util.NodeTracker;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypographicNodePostProcessor extends NodePostProcessor {
    final static public Pattern SMARTS_PATTERN = Pattern.compile("(\\?)(\\.\\.\\.|\\. \\. \\.|--|---|')");
    final private TypographicOptions myOptions;

    public TypographicNodePostProcessor(Document document) {
        myOptions = new TypographicOptions(document);
    }

    @Override
    public void process(NodeTracker state, Node node) {
        BasedSequence original = node.getChars();
        BasedSequence literal = original;

        Matcher m = SMARTS_PATTERN.matcher(literal);
        Node lastNode = node;
        int lastEscaped = 0;
        boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
        TextBase textBase = null;

        while (m.find()) {
            //String found = m.group();
            if (m.group(1) == null) {
                BasedSequence smart = literal.subSequence(m.start(2), m.end(2));

                int startOffset = smart.getStartOffset();
                int endOffset = smart.getEndOffset();

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

                String typographicSmarts;

                if (smart.matches("--")) {
                    typographicSmarts = "&ndash;";
                } else if (smart.matches("---")) {
                    typographicSmarts = "&mdash;";
                } else if (smart.matches("'")) {
                    typographicSmarts = "&rsquo;";
                } else {
                    typographicSmarts = "&hellip;";
                }

                TypographicSmarts smartsNode = new TypographicSmarts(smart, typographicSmarts);
                smartsNode.setTypographicText(typographicSmarts);

                lastNode.insertAfter(smartsNode);
                lastNode = smartsNode;
                Text node1 = new Text(smart);
                smartsNode.appendChild(node1);
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
            return new TypographicNodePostProcessor(document);
        }
    }
}
