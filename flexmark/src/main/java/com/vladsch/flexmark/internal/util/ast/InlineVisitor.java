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

import com.vladsch.flexmark.node.*;

public interface InlineVisitor {
    static <V extends InlineVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(AutoLink.class, visitor::visit),
                new VisitHandler<>(Code.class, visitor::visit),
                new VisitHandler<>(Emphasis.class, visitor::visit),
                new VisitHandler<>(HardLineBreak.class, visitor::visit),
                new VisitHandler<>(HtmlEntity.class, visitor::visit),
                new VisitHandler<>(HtmlInline.class, visitor::visit),
                new VisitHandler<>(HtmlInlineComment.class, visitor::visit),
                new VisitHandler<>(Image.class, visitor::visit),
                new VisitHandler<>(ImageRef.class, visitor::visit),
                new VisitHandler<>(Link.class, visitor::visit),
                new VisitHandler<>(LinkRef.class, visitor::visit),
                new VisitHandler<>(MailLink.class, visitor::visit),
                new VisitHandler<>(SoftLineBreak.class, visitor::visit),
                new VisitHandler<>(StrongEmphasis.class, visitor::visit),
                new VisitHandler<>(Text.class, visitor::visit),
        };
    }

    void visit(AutoLink node);
    void visit(Code node);
    void visit(Emphasis node);
    void visit(HardLineBreak node);
    void visit(HtmlEntity node);
    void visit(HtmlInline node);
    void visit(HtmlInlineComment node);
    void visit(Image node);
    void visit(ImageRef node);
    void visit(Link node);
    void visit(LinkRef node);
    void visit(MailLink node);
    void visit(SoftLineBreak node);
    void visit(StrongEmphasis node);
    void visit(Text node);
}
