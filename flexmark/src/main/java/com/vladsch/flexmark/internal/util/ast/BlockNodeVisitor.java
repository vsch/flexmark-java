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

import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.Node;

import java.util.Collection;

/**
 * Used to visit only nodes, non block nodes or children of non-block nodes are not visited
 * <p>
 * Can be used to only process certain nodes. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
public class BlockNodeVisitor extends NodeVisitor {
    public BlockNodeVisitor(VisitHandler<?>... visitors) {
        super(visitors);
    }

    public BlockNodeVisitor(Collection<VisitHandler<?>> visitors) {
        super(visitors);
    }

    //@SafeVarargs
    //public <V> BlockNodeVisitor(V visitor, Computable<VisitHandler<?>[], V>... computables) {
    //    super(visitor, computables);
    //}

    @Override
    public void visit(Node node) {
        if (node instanceof Block) {
            super.visit(node);
        }
    }
}
