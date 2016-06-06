package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.spec.SpecReaderFactory;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class DumpAstSpecTest  implements SpecReaderFactory {
    private static final Parser PARSER = Parser.builder().build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder().percentEncodeUrls(true).build();
    
    private DumpSpecReader dumpSpecReader;

    static class DumpSpecReader extends SpecReader {
        StringBuilder sb = new StringBuilder();

        public DumpSpecReader(InputStream stream) {
            super(stream);
        }

        @Override
        protected void addSpecLine(String line) {
            sb.append(line).append("\n");
        }

        protected String ast(Node node) {
            AstVisitor astVisitor = new AstVisitor();
            node.accept(astVisitor);
            return astVisitor.getAst();
        }
        

        @Override
        protected void addSpecExample(SpecExample example) {
            Node node = PARSER.parse(example.getSource());
            String html = example.getHtml(); //RENDERER.render(node);
            String ast = ast(node);

            // include source for better assertion errors
            String actual = SpecReader.EXAMPLE_START + "\n" + RenderingTestCase.showTabs(source + SpecReader.TYPE_BREAK + "\n" + html) + SpecReader.TYPE_BREAK + "\n" + ast + SpecReader.EXAMPLE_BREAK + "\n";
            sb.append(actual);
        }
    }

    @Override
    public SpecReader create(InputStream inputStream) {
        dumpSpecReader = new DumpSpecReader(inputStream);
        return dumpSpecReader;
    }

    @Test
    public void dumpAstSpec() throws Exception {
        List<SpecExample> examples = SpecReader.readExamples("/commonmark_ast_spec.txt", this);
        
        System.out.println(dumpSpecReader.sb.toString());
    }
}
