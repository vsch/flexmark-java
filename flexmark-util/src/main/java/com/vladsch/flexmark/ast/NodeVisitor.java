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

package com.vladsch.flexmark.ast;

import java.util.Collection;

/**
 * Node visitor that visits all children by default and allows configuring node handlers to handle specific classes.
 * <p>
 * Can be used to only process certain nodes. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
public class NodeVisitor extends NodeAdaptedVisitor<VisitHandler<?>> {
    public NodeVisitor(VisitHandler<?>... handlers) {
        super(handlers);
    }

    public NodeVisitor(VisitHandler<?>[]... handlers) {
        super(handlers);
    }

    public NodeVisitor(Collection<VisitHandler<?>> handlers) {
        super(handlers);
    }

    public void visitChildren(final Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            // A subclass of this visitor might modify the node, resulting in getNext returning a different node or no
            // node after visiting it. So get the next node before visiting.
            Node next = node.getNext();
            visit(node);
            node = next;
        }
    }

    public void visit(final Node node) {
        VisitHandler handler = myCustomHandlersMap.get(node.getClass());
        if (handler != null) {
            handler.visit(node);
        } else {
            visitChildren(node);
        }
    }

    public void visitNodeOnly(final Node node) {
        VisitHandler handler = myCustomHandlersMap.get(node.getClass());
        if (handler != null) {
            handler.visit(node);
        }
    }
}
