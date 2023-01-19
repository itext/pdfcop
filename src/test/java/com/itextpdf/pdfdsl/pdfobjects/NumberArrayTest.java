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
public class NumberArrayTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "[ 0 1 2 3 ]", true, new float[] {0f, 1f, 2f, 3f} }
//                { "[ ]", true, new float[] {0f, 1f, 2f, 3f} }
        });
    }

    private String syntaxToCheck;
    private boolean pass;
    private float[] values;

    public NumberArrayTest(String syntaxToCheck, boolean pass, float[] values) {
        this.syntaxToCheck = syntaxToCheck;
        this.pass = pass;
        this.values = values;
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

        PdfStreamParser.NumberArrayContext context = streamParser.numberArray();
        int childCount = context.getChildCount();

        Assert.assertEquals(this.values.length + 2, childCount);

        for ( int i = 1; i < this.values.length - 1; i++ ) {
            TerminalNode operand = (TerminalNode) context.getChild(i);

            String actual = operand.getSymbol().getText();

            Assert.assertEquals(this.values[i-1], Float.parseFloat(actual), 0.0d);
        }
    }
}