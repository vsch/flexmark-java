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

import com.vladsch.flexmark.node.Node;

public class VisitHandler<N extends Node> implements Visitor<N> {
    final Class<? extends N> myClass;
    final Visitor<N> myVisitor;

    public VisitHandler(Class<? extends N> aClass, Visitor<N> visitor) {
        myClass = aClass;
        myVisitor = visitor;
    }

    public Class<? extends N> getNodeType() {
        return myClass;
    }

    public Visitor getNodeVisitor() {
        return myVisitor;
    }

    @Override
    public void visit(Node node) {
        //noinspection unchecked
        myVisitor.visit((N)node);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VisitHandler other = (VisitHandler) o;

        if (myClass != other.myClass) return false;
        return myVisitor == other.myVisitor;
    }

    @Override
    public int hashCode() {
        int result = myClass.hashCode();
        result = 31 * result + myVisitor.hashCode();
        return result;
    }
}
