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

package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.node.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract visitor that visits all children by default.
 * <p>
 * Can be used to only process certain nodes. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
class AbstractCustomVisitorBase extends AbstractVisitor {
    final private Map<Class<?>, NodeVisitHandler<?>> myCustomVisitorsMap;

    public AbstractCustomVisitorBase(NodeVisitHandler<?>... visitors) {
        HashMap<Class<?>, NodeVisitHandler<?>> visitorMap = new HashMap<>();
        for (NodeVisitHandler<?> visitor : visitors) {
            visitorMap.put(visitor.getNodeType(), visitor);
        }
        this.myCustomVisitorsMap = visitorMap;
    }

    public AbstractCustomVisitorBase(Collection<NodeVisitHandler<?>> visitors) {
        HashMap<Class<?>, NodeVisitHandler<?>> visitorMap = new HashMap<>();
        for (NodeVisitHandler<?> visitor : visitors) {
            visitorMap.put(visitor.getNodeType(), visitor);
        }
        this.myCustomVisitorsMap = visitorMap;
    }

    public AbstractCustomVisitorBase(Computable<NodeVisitHandler<?>[], Object>... visitors) {
        HashMap<Class<?>, NodeVisitHandler<?>> visitorMap = new HashMap<>();
        for (Computable<NodeVisitHandler<?>[], Object> visitor : visitors) {
            for (NodeVisitHandler handler : visitor.compute(this)) {
                visitorMap.put(handler.getNodeType(), (NodeVisitHandler<?>) handler);
            }
        }
        this.myCustomVisitorsMap = visitorMap;
    }

    //protected void customVisit(Node node) {
    //    NodeVisitHandler visitor = myCustomVisitorsMap.get(node.getClass());
    //    if (visitor != null) {
    //        visitor.visit(node);
    //    } else {
    //        visitChildren(node);
    //    }
    //}

    private void customVisitNode(Node node) {
        NodeVisitHandler visitor = myCustomVisitorsMap.get(node.getClass());
        if (visitor != null) {
            visitor.visit(node);
        } else {
            visitChildren(node);
        }
    }

    // @formatter:off
    // blocks
    @Override public void visit(BlockQuote node) { customVisitNode(node); } 
    @Override public void visit(BulletList node) { customVisitNode(node); } 
    @Override public void visit(BulletListItem node) { customVisitNode(node); }
    @Override public void visit(CustomBlock node) { customVisitNode(node); } 
    @Override public void visit(Document node) { customVisitNode(node); } 
    @Override public void visit(FencedCodeBlock node) { customVisitNode(node); } 
    @Override public void visit(Heading node) { customVisitNode(node); } 
    @Override public void visit(HtmlBlock node) { customVisitNode(node); } 
    @Override public void visit(HtmlCommentBlock node) { customVisitNode(node); } 
    @Override public void visit(IndentedCodeBlock node) { customVisitNode(node); } 
    @Override public void visit(OrderedList node) { customVisitNode(node); } 
    @Override public void visit(OrderedListItem node) { customVisitNode(node); } 
    @Override public void visit(Paragraph node) { customVisitNode(node); } 
    @Override public void visit(ThematicBreak node) { customVisitNode(node); }
    
    // inlines
    @Override public void visit(AutoLink node) { customVisitNode(node); } 
    @Override public void visit(Code node) { customVisitNode(node); } 
    @Override public void visit(CustomNode node) { customVisitNode(node); } 
    @Override public void visit(Emphasis node) { customVisitNode(node); } 
    @Override public void visit(HardLineBreak node) { customVisitNode(node); } 
    @Override public void visit(HtmlEntity node) { customVisitNode(node); } 
    @Override public void visit(HtmlInline node) { customVisitNode(node); } 
    @Override public void visit(HtmlInlineComment node) { customVisitNode(node); } 
    @Override public void visit(Image node) { customVisitNode(node); } 
    @Override public void visit(ImageRef node) { customVisitNode(node); } 
    @Override public void visit(Link node) { customVisitNode(node); } 
    @Override public void visit(LinkRef node) { customVisitNode(node); }
    @Override public void visit(MailLink node) { customVisitNode(node); } 
    @Override public void visit(Reference node) { customVisitNode(node); } 
    @Override public void visit(SoftLineBreak node) { customVisitNode(node); } 
    @Override public void visit(StrongEmphasis node) { customVisitNode(node); } 
    @Override public void visit(TextBase node) { customVisitNode(node); } 
    // @formatter:on
    
    //@Override
    //public void visitChildren(Node parent) {
    //    Node node = parent.getFirstChild();
    //    while (node != null) {
    //        // A subclass of this visitor might modify the node, resulting in getNext returning a different node or no
    //        // node after visiting it. So get the next node before visiting.
    //        Node next = node.getNext();
    //        customVisitNode(node);
    //        node = next;
    //    }
    //}

}
