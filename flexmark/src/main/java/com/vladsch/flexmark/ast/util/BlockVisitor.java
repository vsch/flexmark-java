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

package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;

public interface BlockVisitor {
    static <V extends BlockVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(BlockQuote.class, visitor::visit),
                new VisitHandler<>(BulletList.class, visitor::visit),
                new VisitHandler<>(Document.class, visitor::visit),
                new VisitHandler<>(FencedCodeBlock.class, visitor::visit),
                new VisitHandler<>(Heading.class, visitor::visit),
                new VisitHandler<>(HtmlBlock.class, visitor::visit),
                new VisitHandler<>(HtmlCommentBlock.class, visitor::visit),
                new VisitHandler<>(IndentedCodeBlock.class, visitor::visit),
                new VisitHandler<>(BulletListItem.class, visitor::visit),
                new VisitHandler<>(OrderedListItem.class, visitor::visit),
                new VisitHandler<>(OrderedList.class, visitor::visit),
                new VisitHandler<>(Paragraph.class, visitor::visit),
                new VisitHandler<>(Reference.class, visitor::visit),
                new VisitHandler<>(ThematicBreak.class, visitor::visit)
        };
    }

    void visit(BlockQuote node);
    void visit(BulletList node);
    void visit(Document node);
    void visit(FencedCodeBlock node);
    void visit(Heading node);
    void visit(HtmlBlock node);
    void visit(HtmlCommentBlock node);
    void visit(IndentedCodeBlock node);
    void visit(BulletListItem node);
    void visit(OrderedListItem node);
    void visit(OrderedList node);
    void visit(Paragraph node);
    void visit(Reference node);
    void visit(ThematicBreak node);
}
