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

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.DoNotDecorate;
import com.vladsch.flexmark.node.Text;

/**
 * A TypographicSmarts node
 */
public class TypographicSmarts extends Text implements DoNotDecorate {
    private String typographicText;

    public TypographicSmarts() {
    }

    public TypographicSmarts(BasedSequence chars) {
        super(chars);
    }

    public TypographicSmarts(String typographicText) {
        this.typographicText = typographicText;
    }

    public TypographicSmarts(BasedSequence chars, String typographicText) {
        super(chars);
        this.typographicText = typographicText;
    }

    public TypographicSmarts(String chars, String typographicText) {
        super(chars);
        this.typographicText = typographicText;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        out.append(" typographic: ").append(typographicText).append(" ");
    }

    public String getTypographicText() {
        return typographicText;
    }

    public void setTypographicText(String typographicText) {
        this.typographicText = typographicText;
    }
}
