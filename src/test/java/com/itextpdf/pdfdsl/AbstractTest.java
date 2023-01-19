package com.itextpdf.pdfdsl;

import com.itextpdf.antlr.PdfStreamLexer;
import com.itextpdf.antlr.PdfStreamParser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public abstract class AbstractTest implements IPdfDslTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    protected boolean pass;
    protected String syntaxToCheck;

    protected PdfStreamLexer streamLexer;
    protected PdfStreamParser streamParser;
    protected ParserRuleContext context;

    AbstractTest(boolean pass, String syntaxToCheck) {
        this.pass = pass;
        this.syntaxToCheck = syntaxToCheck;
    }

    @Before
    public void setUp() {
        if (! this.pass) {
            this.expectedException.expect(ParseCancellationException.class);
        }

        this.streamLexer = new PdfStreamLexer(CharStreams.fromString(this.syntaxToCheck));
        this.streamLexer.removeErrorListeners();
        this.streamLexer.addErrorListener(ThrowingErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(this.streamLexer);
        this.streamParser = new PdfStreamParser(tokens);
        this.streamParser.removeErrorListeners();
        this.streamParser.addErrorListener(ThrowingErrorListener.INSTANCE);

        this.context = getContext(streamParser);
    }

    protected String getValue(final int childIndex) {
        TerminalNode name = (TerminalNode) this.context.getChild(childIndex);
        return name.getSymbol().getText();
    }
}