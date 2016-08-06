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

import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class NodeIterator implements ReversiblePeekingIterator<Node> {
    final Node firstNode;
    final Node lastNode;
    final boolean reversed;
    Node node;
    Node result;

    /**
     * iterate nodes until last ast is iterated over or null
     *
     * @param firstNode
     */
    public NodeIterator(Node firstNode) {
        this(firstNode, null, false);
    }

    /**
     * iterate nodes until last ast is iterated over or null
     *
     * @param firstNode
     */
    public NodeIterator(Node firstNode, boolean reversed) {
        this(firstNode, null, reversed);
    }

    /**
     * iterate nodes until last ast is iterated over or null
     *
     * @param firstNode
     */
    public NodeIterator(Node firstNode, Node lastNode) {
        this(firstNode, lastNode, false);
    }

    /**
     * iterate nodes until null or last ast is iterated over
     *
     * @param firstNode
     */
    public NodeIterator(Node firstNode, Node lastNode, boolean reversed) {
        Objects.requireNonNull(firstNode);

        this.firstNode = firstNode;
        this.lastNode = lastNode;
        this.reversed = reversed;
        this.node = reversed ? lastNode : firstNode;
    }

    @Override
    public boolean isReversed() {
        return reversed;
    }

    @Override
    public boolean hasNext() {
        return node != null;
    }

    @Override
    public Node next() {
        result = null;

        if (node == null) {
            throw new NoSuchElementException();
        }

        result = node;
        node = reversed ? node.getPrevious() : node.getNext();
        if (node == null || result == (reversed ? firstNode : lastNode)) node = null;
        return result;
    }

    public Node peek() {
        if (node != null) {
            return node;
        }
        return null;
    }

    @Override
    public void remove() {
        if (result == null) {
            throw new IllegalStateException("Either next() was not called yet or the ast was blockRemoved");
        }
        result.unlink();
        result = null;
    }

    @Override
    public void forEachRemaining(Consumer<? super Node> consumer) {
        Objects.requireNonNull(consumer);

        while (hasNext()) {
            consumer.accept(next());
        }
    }

    public final static ReversiblePeekingIterator<Node> EMPTY = new ReversiblePeekingIterator<Node>() {
        @Override
        public boolean isReversed() {
            return false;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Node next() {
            throw new NoSuchElementException();
        }

        @Override
        public Node peek() {
            return null;
        }
    };
}
