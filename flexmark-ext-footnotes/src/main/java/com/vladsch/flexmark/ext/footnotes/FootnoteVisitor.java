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

package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.ast.VisitHandler;

public interface FootnoteVisitor {
    static <V extends FootnoteVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
            new VisitHandler<>(FootnoteBlock.class, visitor::visit),
            new VisitHandler<>(Footnote.class, visitor::visit),
        };
    }

    void visit(final FootnoteBlock node);
    void visit(final Footnote node);
}
