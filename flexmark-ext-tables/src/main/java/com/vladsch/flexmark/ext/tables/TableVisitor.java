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

package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.ast.VisitHandler;

public interface TableVisitor {
    static <V extends TableVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(TableBlock.class, visitor::visit),
                new VisitHandler<>(TableHead.class, visitor::visit),
                new VisitHandler<>(TableSeparator.class, visitor::visit),
                new VisitHandler<>(TableBody.class, visitor::visit),
                new VisitHandler<>(TableRow.class, visitor::visit),
                new VisitHandler<>(TableCell.class, visitor::visit),
                new VisitHandler<>(TableCaption.class, visitor::visit),
        };
    }

    void visit(final TableBlock node);
    void visit(final TableHead node);
    void visit(final TableSeparator node);
    void visit(final TableBody node);
    void visit(final TableRow node);
    void visit(final TableCell node);
    void visit(final TableCaption node);
}
