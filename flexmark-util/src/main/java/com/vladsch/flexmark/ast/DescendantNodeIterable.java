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

package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;

public class DescendantNodeIterable implements ReversiblePeekingIterable<Node> {
    private ReversiblePeekingIterable<Node> iterable;

    /**
     * iterate nodes, with descendants, depth first until all are done
     *
     * @param iterable
     */
    public DescendantNodeIterable(ReversiblePeekingIterable<Node> iterable) {
        if (iterable instanceof DescendantNodeIterable) {
            this.iterable = ((DescendantNodeIterable) iterable).iterable;
        } else {
            this.iterable = iterable;
        }
    }

    @Override
    public ReversiblePeekingIterator<Node> iterator() {
        return new DescendantNodeIterator(iterable.iterator());
    }

    @Override
    public ReversiblePeekingIterable<Node> reversed() {
        return new DescendantNodeIterable(iterable.reversed());
    }

    @Override
    public ReversiblePeekingIterator<Node> reversedIterator() {
        return new DescendantNodeIterator(iterable.reversedIterator());
    }

    @Override
    public boolean isReversed() {
        return iterable.isReversed();
    }
}
