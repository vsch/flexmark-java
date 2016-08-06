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

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataKey;

/**
 * Extension for typographics
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed typographic text is turned into {@link TypographicQuotes} and {@link TypographicSmarts} nodes.
 * </p>
 */
public class TypographicExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    // final public static DataKey<TypographicRepository> TYPOGRAPHICS = new DataKey<>("TYPOGRAPHICS", TypographicRepository::new); 
    // final public static DataKey<KeepType> TYPOGRAPHICS_KEEP = new DataKey<>("TYPOGRAPHICS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates 
    final public static DataKey<Boolean> TYPOGRAPHIC_QUOTES = new DataKey<>("TYPOGRAPHIC_QUOTES", true); 
    final public static DataKey<Boolean> TYPOGRAPHIC_SMARTS = new DataKey<>("TYPOGRAPHIC_SMARTS", true); 

    private TypographicExtension() {
    }

    public static Extension create() {
        return new TypographicExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        //parserBuilder.postProcessorFactory(new TypographicNodePostProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        //rendererBuilder.nodeRendererFactory(TypographicNodeRenderer::new);
        // rendererBuilder.linkResolverFactory(new TypographicLinkResolver.Factory());
    }
}
