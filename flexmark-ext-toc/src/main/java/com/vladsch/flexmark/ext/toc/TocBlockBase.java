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

package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.SubSequence;
import com.vladsch.flexmark.node.CustomBlock;

/**
 * A TOC node
 */
public abstract class TocBlockBase<T> extends CustomBlock<T> {
    protected BasedSequence openingMarker = SubSequence.NULL;
    protected BasedSequence tocKeyword = SubSequence.NULL;
    protected BasedSequence style = SubSequence.NULL;
    protected BasedSequence closingMarker = SubSequence.NULL;

    @Override
    public void getAstExtra(StringBuilder out) {
        segmentSpan(out, openingMarker, "openingMarker");
        segmentSpan(out, tocKeyword, "tocKeyword");
        segmentSpan(out, style, "style");
        segmentSpan(out, closingMarker, "closingMarker");
    }

    @Override
    public BasedSequence[] getSegments() {
        BasedSequence[] nodeSegments = new BasedSequence[] { openingMarker, tocKeyword, style, closingMarker };
        if (lineSegments.size() == 0) return nodeSegments;
        BasedSequence[] allSegments = new BasedSequence[lineSegments.size() + nodeSegments.length];
        lineSegments.toArray(allSegments);
        System.arraycopy(allSegments, 0, allSegments, nodeSegments.length, lineSegments.size());
        return allSegments;
    }

    public TocBlockBase(BasedSequence chars) {
        this(chars, false);
    }

    public TocBlockBase(BasedSequence chars, boolean closingSimToc) {
        this(chars, null, closingSimToc);
    }

    public TocBlockBase(BasedSequence chars, BasedSequence styleChars) {
        this(chars, styleChars, false);
    }

    public TocBlockBase(BasedSequence chars, BasedSequence styleChars, boolean closingSimToc) {
        super(chars);
        openingMarker = chars.subSequence(0, 1);
        tocKeyword = chars.subSequence(1, 4);
        if (styleChars != null) {
            style = styleChars;
        }
        int closingPos = chars.indexOf(']', 4);
        if (closingSimToc && !(closingPos != -1 && closingPos + 1 < chars.length() && chars.charAt(closingPos + 1) == ':')) {
            throw new IllegalStateException("Invalid TOC block sequence");
        }
        closingMarker = chars.subSequence(closingPos, closingPos + (closingSimToc ? 2 : 1));
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public BasedSequence getTocKeyword() {
        return tocKeyword;
    }

    public BasedSequence getStyle() {
        return style;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }
}
