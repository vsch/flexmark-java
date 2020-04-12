package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.TextContainer;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import org.jetbrains.annotations.NotNull;

import static com.vladsch.flexmark.util.misc.BitFieldSet.any;

/**
 * Inline HTML element.
 *
 * @see <a href="http://spec.commonmark.org/0.24/#raw-html">CommonMark Spec</a>
 */
public class HtmlEntity extends Node implements TextContainer {
    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        if (!getChars().isEmpty()) out.append(" \"").append(getChars()).append("\"");
    }

    // TODO: add opening and closing marker with intermediate text so that completions can be easily done
    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public HtmlEntity() {
    }

    public HtmlEntity(BasedSequence chars) {
        super(chars);
    }

    @Override
    public boolean collectText(ISequenceBuilder<? extends ISequenceBuilder<?, BasedSequence>, BasedSequence> out, int flags, NodeVisitor nodeVisitor) {
        if (any(flags, F_NODE_TEXT)) {
            out.append(getChars());
        } else {
            ReplacedTextMapper textMapper = new ReplacedTextMapper(getChars());
            BasedSequence unescaped = Escaping.unescape(getChars(), textMapper);
            out.append(unescaped);
        }
        return false;
    }
}
