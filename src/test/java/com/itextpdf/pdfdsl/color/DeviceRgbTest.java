package com.itextpdf.pdfdsl.color;


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
public class DeviceRgbTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "0 1 0 RG", true, new float[] {0, 1, 0}, "RG" },
                { "0 0 1 rg", true, new float[] {0, 0, 1}, "rg" },
                { "0.62745 0.62745 0.62745 RG", true, new float[] {0.62745f, 0.62745f, 0.62745f}, "RG" }
        });
    }

    private String syntaxToCheck;
    private boolean pass;
    private float[] operand;
    private String operator;

    public DeviceRgbTest(String syntaxToCheck, boolean pass, float[] operand, String operator) {
        this.syntaxToCheck = syntaxToCheck;
        this.pass = pass;
        this.operand = operand;
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

        PdfStreamParser.RgbContext context = streamParser.rgb();
        int childCount = context.getChildCount();

        TerminalNode operator = (TerminalNode) context.getChild(childCount-1);
        String actualOperator = operator.getSymbol().getText();
        Assert.assertEquals(this.operator, actualOperator);

        for ( int i = 0; i < this.operand.length; i++) {
            TerminalNode operand = (TerminalNode) context.getChild(i);
            String actual = operand.getSymbol().getText();
            Assert.assertEquals(this.operand[i], Float.parseFloat(actual), 0.0d);
        }
    }
}