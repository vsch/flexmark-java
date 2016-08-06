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

package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.tables.TableCell;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.StringSequence;

/**
 * Table cell separator only used during parsing, not part of the AST, use the {@link TableCell#getOpeningMarker()} and {@link TableCell#getClosingMarker()}
 * 
 */
class TableColumnSeparator extends Node {
    public TableColumnSeparator() {
    }

    public TableColumnSeparator(BasedSequence chars) {
        super(chars);
    }

    public TableColumnSeparator(String chars) {
        super(new StringSequence(chars));
    }

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        astExtraChars(out);
    }

    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }

}
