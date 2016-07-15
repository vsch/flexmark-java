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

package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.internal.util.Computable;
import com.vladsch.flexmark.internal.util.NodeVisitHandler;

public interface AbbreviationVisitor {
    Computable<NodeVisitHandler<?>[], Object> VISIT_HANDLERS = visitor -> new NodeVisitHandler<?>[] {
            new NodeVisitHandler<>(AbbreviationBlock.class, ((AbbreviationVisitor) visitor)::visit),
            new NodeVisitHandler<>(Abbreviation.class, ((AbbreviationVisitor) visitor)::visit),
    };

    void visit(AbbreviationBlock node);
    void visit(Abbreviation node);
}
