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

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.typographic.TypographicSmarts;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.NodeTracker;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypographicNodePostProcessor extends NodePostProcessor {
    public static final Pattern SMARTS_PATTERN = Pattern.compile("(\\?)(\\.\\.\\.|\\. \\. \\.|--|---|')");
    private final TypographicOptions myOptions;

    public TypographicNodePostProcessor(Document document) {
        myOptions = new TypographicOptions(document);
    }

    @Override
    public void process(NodeTracker state, Node node) {
        BasedSequence original = node.getChars();
        BasedSequence literal = original;

        Matcher m = SMARTS_PATTERN.matcher(literal);
        int lastEscaped = 0;
        boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
        TextBase textBase = wrapInTextBase ? null : (TextBase) node.getParent();

        while (m.find()) {
            //String found = m.group();
            if (m.group(1) == null) {
                BasedSequence toDecorateText = literal.subSequence(m.start(2), m.end(2));

                int startOffset = m.start(2);
                int endOffset = m.end(2);

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

                String typographicSmarts;

                if (toDecorateText.matches("--")) {
                    typographicSmarts = "&ndash;";
                } else if (toDecorateText.matches("---")) {
                    typographicSmarts = "&mdash;";
                } else if (toDecorateText.matches("'")) {
                    typographicSmarts = "&rsquo;";
                } else {
                    typographicSmarts = "&hellip;";
                }

                BasedSequence origToDecorateText = original.subSequence(startOffset, endOffset);
                TypographicSmarts decorationNode = new TypographicSmarts(toDecorateText, typographicSmarts);
                decorationNode.setTypographicText(typographicSmarts);
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
            return new TypographicNodePostProcessor(document);
        }
    }
}
