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

package com.vladsch.flexmark.ext.typographic;

import com.vladsch.flexmark.ast.CustomNode;
import com.vladsch.flexmark.ast.DelimitedNode;
import com.vladsch.flexmark.ast.DoNotDecorate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SubSequence;

/**
 * A TypographicQuotes node
 */
public class TypographicQuotes extends CustomNode implements DelimitedNode, DoNotDecorate {
    protected BasedSequence openingMarker = SubSequence.NULL;
    protected BasedSequence text = SubSequence.NULL;
    protected BasedSequence closingMarker = SubSequence.NULL;
    protected String typographicOpening;
    protected String typographicClosing;

    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return new BasedSequence[] { openingMarker, text, closingMarker };
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        if (openingMarker.isNotNull()) out.append(" typographicOpening: ").append(typographicOpening).append(" ");
        if (closingMarker.isNotNull()) out.append(" typographicClosing: ").append(typographicClosing).append(" ");
        delimitedSegmentSpanChars(out, openingMarker, text, closingMarker, "text");
    }

    public TypographicQuotes() {
    }

    public TypographicQuotes(BasedSequence chars) {
        super(chars);
    }

    public TypographicQuotes(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
        super(new SubSequence(openingMarker.getBase(), openingMarker.getStartOffset(), closingMarker.getEndOffset()));
        this.openingMarker = openingMarker;
        this.text = text;
        this.closingMarker = closingMarker;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getText() {
        return text;
    }

    public void setText(BasedSequence text) {
        this.text = text;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }

    public String getTypographicOpening() {
        return typographicOpening;
    }

    public void setTypographicOpening(String typographicOpening) {
        this.typographicOpening = typographicOpening;
    }

    public String getTypographicClosing() {
        return typographicClosing;
    }

    public void setTypographicClosing(String typographicClosing) {
        this.typographicClosing = typographicClosing;
    }
}
