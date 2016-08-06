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

package com.vladsch.flexmark.spec;

import com.vladsch.flexmark.IRender;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.io.IOException;

public abstract class IRenderBase implements IRender {
    final public static IRender NullRenderer = new IRenderBase() {
        @Override
        public void render(Node node, Appendable output) {

        }

        @Override
        public IRender withOptions(DataHolder options) {
            return this;
        }
    };

    final public static IRender TextRenderer = new IRenderBase() {
        @Override
        public void render(Node node, Appendable output) {
            try {
                output.append(node.getChars());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public IRender withOptions(DataHolder options) {
            return this;
        }
    };

    private final MutableDataSet myOptions;

    public IRenderBase() {
        this(null);
    }

    public IRenderBase(DataHolder options) {
        myOptions = options != null ? new MutableDataSet(options) : new MutableDataSet();
    }

    @Override
    public String render(Node node) {
        StringBuilder out = new StringBuilder();
        render(node, out);
        return out.toString();
    }

    public MutableDataSet getOptions() {
        return myOptions;
    }
}
