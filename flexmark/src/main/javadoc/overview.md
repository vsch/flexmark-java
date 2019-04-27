**Java implementation of CommonMark for parsing markdown and rendering to HTML (core library)**

Example:

```java
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;

class Main {
    static void main(String[] args) {
        MutableDataSet options = new MutableDataSet();
        //Optionally Select Emulation Profile: 
        //     COMMONMARK (default), FIXED_INDENT, KRAMDOWN, 
        //     MARKDOWN, GITHUB_DOC, MULTI_MARKDOWN, PEGDOWN
        // options.setFrom(ParserEmulationProfile.KRAMDOWN);
        
        // Optionally, tweak behavior
        options.set(HtmlRenderer.ESCAPE_HTML, true);
        
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse("This is *Sparta*");
        renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
    }
}        
```

See the following packages for details:

* {@link com.vladsch.flexmark.parser} for parsing input text to AST nodes
* {@link com.vladsch.flexmark.ast} for AST node types and visitors
* {@link com.vladsch.flexmark.html} for HTML rendering
