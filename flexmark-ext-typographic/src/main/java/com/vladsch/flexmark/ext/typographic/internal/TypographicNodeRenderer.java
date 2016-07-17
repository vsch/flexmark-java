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

import com.vladsch.flexmark.ext.typographic.TypographicQuotes;
import com.vladsch.flexmark.ext.typographic.TypographicSmarts;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.internal.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TypographicNodeRenderer implements NodeRenderer
        // , PhasedNodeRenderer 
{
    private final TypographicOptions options;

    public TypographicNodeRenderer(DataHolder options) {
        this.options = new TypographicOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(TypographicSmarts.class, this::render),
                new NodeRenderingHandler<>(TypographicQuotes.class, this::render)
        ));
    }

    private void render(TypographicQuotes node, NodeRendererContext context, HtmlWriter html) {
        if (node.getTypographicOpening() != null && !node.getTypographicOpening().isEmpty()) html.raw(node.getTypographicOpening());
        context.renderChildren(node);
        if (node.getTypographicClosing() != null && !node.getTypographicClosing().isEmpty()) html.raw(node.getTypographicClosing());
    }

    private void render(TypographicSmarts node, NodeRendererContext context, HtmlWriter html) {
        html.raw(node.getTypographicText());
    }
}
