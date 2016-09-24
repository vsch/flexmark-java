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

package com.vladsch.flexmark.ext.escaped.character;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.escaped.character.internal.EscapedCharacterNodePostProcessor;
import com.vladsch.flexmark.ext.escaped.character.internal.EscapedCharacterNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

/**
 * Extension for escaped_characters
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed escaped_character text is turned into {@link EscapedCharacter} nodes.
 * </p>
 */
public class EscapedCharacterExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    // final public static DataKey<EscapedCharacterRepository> ESCAPED_CHARACTERS = new DataKey<>("ESCAPED_CHARACTERS", EscapedCharacterRepository::new); 
    // final public static DataKey<KeepType> ESCAPED_CHARACTERS_KEEP = new DataKey<>("ESCAPED_CHARACTERS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates 
    //final public static DataKey<Boolean> ESCAPED_CHARACTER_OPTION1 = new DataKey<>("ESCAPED_CHARACTER_OPTION1", false); 
    //final public static DataKey<String> ESCAPED_CHARACTER_OPTION2 = new DataKey<>("ESCAPED_CHARACTER_OPTION2", "default"); 
    //final public static DataKey<Integer> ESCAPED_CHARACTER_OPTION3 = new DataKey<>("ESCAPED_CHARACTER_OPTION3", Integer.MAX_VALUE); 

    private EscapedCharacterExtension() {
    }

    public static Extension create() {
        return new EscapedCharacterExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessorFactory(new EscapedCharacterNodePostProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("JIRA")) {
        } else if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(EscapedCharacterNodeRenderer::new);
            // rendererBuilder.linkResolverFactory(new EscapedCharacterLinkResolver.Factory());
        }
    }
}
