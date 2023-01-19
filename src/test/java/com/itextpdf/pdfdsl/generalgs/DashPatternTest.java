package com.itextpdf.pdfdsl.generalgs;

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
public class DashPatternTest {


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "[ 0 1 ] 2 d", true, new Object[] {2f, "d" } }
        });
    }

    private String syntaxToCheck;
    private boolean pass;
    private Object[] values;

    public DashPatternTest(String syntaxToCheck, boolean pass, Object[] values) {
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

        PdfStreamParser.DashPatternContext context = streamParser.dashPattern();
        int childCount = context.getChildCount();

        Assert.assertEquals(this.values.length, childCount - 1);

        Assert.assertTrue(context.getChild(0) instanceof PdfStreamParser.NumberArrayContext);

        TerminalNode number = (TerminalNode) context.getChild(1);
        String actual = number.getSymbol().getText();
        Assert.assertEquals((Float) this.values[0], Float.parseFloat(actual), 0.0d);

        TerminalNode operand = (TerminalNode) context.getChild(2);
        actual = operand.getSymbol().getText();
        Assert.assertEquals(String.valueOf(this.values[1]), actual);
    }
}