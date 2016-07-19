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

package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.internal.util.ast.VisitHandler;

public interface SpecExampleVisitor {
    static <V extends SpecExampleVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(SpecExampleAst.class, visitor::visit),
                new VisitHandler<>(SpecExampleBlock.class, visitor::visit),
                new VisitHandler<>(SpecExampleHtml.class, visitor::visit),
                new VisitHandler<>(SpecExampleOption.class, visitor::visit),
                new VisitHandler<>(SpecExampleOptionSeparator.class, visitor::visit),
                new VisitHandler<>(SpecExampleOptionsList.class, visitor::visit),
                new VisitHandler<>(SpecExampleSeparator.class, visitor::visit),
                new VisitHandler<>(SpecExampleSource.class, visitor::visit),
        };
    }

    void visit(SpecExampleAst node);
    void visit(SpecExampleBlock node);
    void visit(SpecExampleHtml node);
    void visit(SpecExampleOption node);
    void visit(SpecExampleOptionSeparator node);
    void visit(SpecExampleOptionsList node);
    void visit(SpecExampleSeparator node);
    void visit(SpecExampleSource node);
}
