package com.itextpdf.pdfdsl.pdfobjects;

import com.itextpdf.antlr.PdfStreamLexer;
import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.ThrowingErrorListener;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class StringTest {
    private static final int NO_OUTPUT = -1337;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "(Hello World)", true, "(Hello World)" },
                { "<AABB001122>", true, "<AABB001122>" },
                { "(Hello World", false, "(Hello World)" },
                { "<ABC>", false, "<ABC>" }
        });
    }

    private String syntaxToCheck;
    private boolean pass;
    private String value;

    public StringTest(String syntaxToCheck, boolean pass, String value) {
        this.syntaxToCheck = syntaxToCheck;
        this.pass = pass;
        this.value = value;
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

        PdfStreamParser.StringContext context = streamParser.string();
        int childCount = context.getChildCount();

        Assert.assertEquals(1, childCount);

        TerminalNode operand = (TerminalNode) context.getChild(0);
        String actual = operand.getSymbol().getText();

        Assert.assertEquals(this.value, actual);
    }

}