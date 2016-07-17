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

package com.vladsch.flexmark.ext.typographic.internal;

import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.internal.util.options.DataHolder;

class TypographicOptions {
    final public boolean typographicQuotes;
    final public boolean typographicSmarts;

    public TypographicOptions(DataHolder options) {
        this.typographicQuotes = options.get(TypographicExtension.TYPOGRAPHIC_QUOTES);
        this.typographicSmarts = options.get(TypographicExtension.TYPOGRAPHIC_SMARTS);
    }
}
