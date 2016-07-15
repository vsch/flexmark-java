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

package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.internal.util.Computable;
import com.vladsch.flexmark.internal.util.NodeVisitHandler;

public interface TableVisitor {
    Computable<NodeVisitHandler<?>[], Object> VISIT_HANDLERS = visitor -> new NodeVisitHandler<?>[] {
            new NodeVisitHandler<>(TableBlock.class, ((TableVisitor) visitor)::visit),
            new NodeVisitHandler<>(TableHead.class, ((TableVisitor) visitor)::visit),
            new NodeVisitHandler<>(TableSeparator.class, ((TableVisitor) visitor)::visit),
            new NodeVisitHandler<>(TableBody.class, ((TableVisitor) visitor)::visit),
            new NodeVisitHandler<>(TableRow.class, ((TableVisitor) visitor)::visit),
            new NodeVisitHandler<>(TableCell.class, ((TableVisitor) visitor)::visit),
    };

    void visit(TableBlock node);
    void visit(TableHead node);
    void visit(TableSeparator node);
    void visit(TableBody node);
    void visit(TableRow node);
    void visit(TableCell node);
}
