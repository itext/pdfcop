package com.itextpdf.pdfdsl.pdfobjects;

import com.itextpdf.antlr.PdfStreamLexer;
import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.ThrowingErrorListener;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CommentTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "% This is a comment", true },
                { "This is not a comment", false },
                { "% Comment 0 G 1 0 0 RG 0 0 0 0 K", true}
        });
    }

    private String syntaxToCheck;
    private boolean pass;
    private String value;

    public CommentTest(String syntaxToCheck, boolean pass) {
        this.syntaxToCheck = syntaxToCheck;
        this.pass = pass;
        this.value = syntaxToCheck;
    }

    @Test
    public void test() {
        if (! this.pass) {
            this.expectedException.expect(ParseCancellationException.class);
        }

        PdfStreamLexer streamLexer = new PdfStreamLexer(CharStreams.fromString(this.syntaxToCheck));
        streamLexer.removeErrorListeners();
        streamLexer.addErrorListener(ThrowingErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(streamLexer);
        PdfStreamParser streamParser = new PdfStreamParser(tokens);
        streamParser.removeErrorListeners();
        streamParser.addErrorListener(ThrowingErrorListener.INSTANCE);

        ParserRuleContext context = streamParser.comment();
        int childCount = context.getChildCount();

        Assert.assertEquals(1, childCount);

        TerminalNode operand = (TerminalNode) context.getChild(0);

        String actual = operand.getSymbol().getText();

        Assert.assertEquals(this.value, actual);
    }

}