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

package com.vladsch.flexmark.internal.util.ast;

import com.vladsch.flexmark.node.Node;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Node visitor that visits all children by default and allows configuring node handlers to handle specific classes.
 * <p>
 * Can be used to only process certain nodes. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
public class NodeVisitor extends NodeVisitorBase {
    final private Map<Class<?>, VisitHandler<?>> myCustomVisitorsMap;

    // Usage:
    //myVisitor = new NodeVisitor(
    //        new VisitHandler<>(Text.class, TextCollectingVisitor.this::visit),
    //        new VisitHandler<>(HtmlEntity.class, TextCollectingVisitor.this::visit),
    //        new VisitHandler<>(SoftLineBreak.class, TextCollectingVisitor.this::visit),
    //        new VisitHandler<>(HardLineBreak.class, TextCollectingVisitor.this::visit)
    //);
    public NodeVisitor(VisitHandler<?>... visitors) {
        HashMap<Class<?>, VisitHandler<?>> visitorMap = new HashMap<>();
        for (VisitHandler<?> visitor : visitors) {
            visitorMap.put(visitor.getNodeType(), visitor);
        }
        this.myCustomVisitorsMap = visitorMap;
    }

    public NodeVisitor(VisitHandler<?>[]... visitors) {
        HashMap<Class<?>, VisitHandler<?>> visitorMap = new HashMap<>();
        for (VisitHandler<?>[] moreVisitors : visitors) {
            for (VisitHandler<?> visitor : moreVisitors) {
                visitorMap.put(visitor.getNodeType(), visitor);
            }
        }
        this.myCustomVisitorsMap = visitorMap;
    }

    public NodeVisitor(Collection<VisitHandler<?>> visitors) {
        HashMap<Class<?>, VisitHandler<?>> visitorMap = new HashMap<>();
        for (VisitHandler<?> visitor : visitors) {
            visitorMap.put(visitor.getNodeType(), visitor);
        }
        this.myCustomVisitorsMap = visitorMap;
    }

    public void visit(Node node) {
        VisitHandler visitor = myCustomVisitorsMap.get(node.getClass());
        if (visitor != null) {
            visitor.visit(node);
        } else {
            visitChildren(node);
        }
    }

    public void visitNodeOnly(Node node) {
        VisitHandler visitor = myCustomVisitorsMap.get(node.getClass());
        if (visitor != null) {
            visitor.visit(node);
        }
    }
}
