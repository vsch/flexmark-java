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

public class TextCollectingVisitor {
    private final StringBuilder out = new StringBuilder();
    private final NodeVisitor myVisitor;

    public TextCollectingVisitor() {
        myVisitor = new NodeVisitor(
                new VisitHandler<>(Text.class, TextCollectingVisitor.this::visit),
                new VisitHandler<>(TextBase.class, TextCollectingVisitor.this::visit),
                new VisitHandler<>(HtmlEntity.class, TextCollectingVisitor.this::visit),
                new VisitHandler<>(SoftLineBreak.class, TextCollectingVisitor.this::visit),
                new VisitHandler<>(HardLineBreak.class, TextCollectingVisitor.this::visit)
        );
    }

    public String getText() {
        return out.toString();
    }

    public void collect(Node node) {
        myVisitor.visit(node);
    }

    public String collectAndGetText(Node node) {
        myVisitor.visit(node);
        return out.toString();
    }

    private void visit(HtmlEntity node) {
        out.append(node.getChars().unescape());
    }

    private void visit(SoftLineBreak node) {
        out.append('\n');
    }

    private void visit(HardLineBreak node) {
        out.append('\n');
    }

    private void visit(Text node) {
        out.append(node.getChars());
    }
    
    private void visit(TextBase node) {
        out.append(node.getChars());
    }
}
