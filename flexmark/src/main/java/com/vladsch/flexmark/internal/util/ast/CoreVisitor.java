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

public interface CoreVisitor extends BlockVisitor, InlineVisitor {
    static <V extends CoreVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        VisitHandler<?>[] blockHandlers = BlockVisitor.VISIT_HANDLERS(visitor);
        VisitHandler<?>[] inlineHandlers = BlockVisitor.VISIT_HANDLERS(visitor);
        VisitHandler<?>[] handlers = new VisitHandler<?>[blockHandlers.length + inlineHandlers.length];
        System.arraycopy(blockHandlers, 0, handlers, 0, blockHandlers.length);
        System.arraycopy(inlineHandlers, 0, handlers, blockHandlers.length, inlineHandlers.length);
        return handlers;        
    }
}
