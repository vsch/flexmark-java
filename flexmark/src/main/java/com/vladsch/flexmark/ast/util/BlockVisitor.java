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
    void visit(final BlockQuote node);
    void visit(final BulletList node);
    void visit(final Document node);
    void visit(final FencedCodeBlock node);
    void visit(final Heading node);
    void visit(final HtmlBlock node);
    void visit(final HtmlCommentBlock node);
    void visit(final IndentedCodeBlock node);
    void visit(final BulletListItem node);
    void visit(final OrderedListItem node);
    void visit(final OrderedList node);
    void visit(final Paragraph node);
    void visit(final Reference node);
    void visit(final ThematicBreak node);
}
