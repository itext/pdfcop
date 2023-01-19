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
public class StringPosArrayTest {
    private static final int NO_OUTPUT = -1337;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "[ (AWAY again) ]", true, new String[] {"(AWAY again)"}, 3 },
                { "[ (A) 120 (W) 120 (A) 95 (Y again) ]", true, new String[] {"(A)", "120", "(W)", "120", "(A)", "95", "(Y again)"}, 9 }
        });
    }

    private String syntaxToCheck;
    private boolean pass;
    private String[] value;
    private int childCount;

    public StringPosArrayTest(String syntaxToCheck, boolean pass, String[] value, int childCount) {
        this.syntaxToCheck = syntaxToCheck;
        this.pass = pass;
        this.value = value;
        this.childCount = childCount;
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

        PdfStreamParser.StringPosArrayContext context = streamParser.stringPosArray();
        int childCount = context.getChildCount();

        Assert.assertEquals(this.childCount, childCount);

        for ( int i = 0; i < this.value.length; i++ ) {
           TerminalNode operand = (TerminalNode) context.getChild(i+1);
            String actual = operand.getSymbol().getText();

            Assert.assertEquals(this.value[i], actual);
        }
    }

}