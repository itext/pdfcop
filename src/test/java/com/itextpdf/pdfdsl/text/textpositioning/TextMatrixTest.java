package com.itextpdf.pdfdsl.text.textpositioning;

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
public class TextMatrixTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "10 0 0 10 64.417 693.424 Tm", true, new float[] {10, 0, 0, 10, 64.417f, 693.424f}, "Tm" },
                { "0 1 2 3 4 5 Tm", true, new float[] {0, 1, 2, 3, 4, 5}, "Tm" },
                { "0 1 2 Tm", false, new float[] {0, 1, 2, 3, 4, 5}, "Tm" }
        });
    }


    private String syntaxToCheck;
    private boolean pass;
    private float[] operands;
    private String operator;

    public TextMatrixTest(String syntaxToCheck, boolean pass, float[] operands, String operator) {
        this.syntaxToCheck = syntaxToCheck;
        this.pass = pass;
        this.operands = operands;
        this.operator = operator;
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

        PdfStreamParser.TextMatrixContext  context = streamParser.textMatrix();
        int childCount = context.getChildCount();

        Assert.assertEquals(7, childCount);

        for ( int i = 0; i < 6; i++ ) {
            TerminalNode operand = (TerminalNode) context.getChild(i);
            String actual = operand.getSymbol().getText();
            Assert.assertEquals(this.operands[i], Float.parseFloat(actual), 0.0d);
        }

        TerminalNode operator = (TerminalNode) context.getChild(6);
        String actualOperator = operator.getSymbol().getText();
        Assert.assertEquals(this.operator, actualOperator);
    }

}