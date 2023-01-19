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
public class NumberTest {

    private static final int NO_OUTPUT = -1337;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "0", true, 0 },
                { "1111", true, 1111 },
                { "23", true, 23 },
                { "3", true, 3 },
                { "-3", true, -3 },
                { "3.0", true, 3 },
                { ".3", true, 0.3f },
                { "-.3", true, -0.3f },
                { "0.62745", true, 0.62745f },
                { "A", false, NO_OUTPUT }
        });
    }

    private String syntaxToCheck;
    private boolean pass;
    private float operand;

    public NumberTest(String syntaxToCheck, boolean pass, float operand) {
        this.syntaxToCheck = syntaxToCheck;
        this.pass = pass;
        this.operand = operand;
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

        PdfStreamParser.NumberContext context = streamParser.number();
        int childCount = context.getChildCount();

        Assert.assertEquals(1, childCount);

        TerminalNode operand = (TerminalNode) context.getChild(0);

        String actual = operand.getSymbol().getText();

        Assert.assertEquals(this.operand, Float.parseFloat(actual), 0.0d);
    }
}