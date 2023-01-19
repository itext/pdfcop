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
public class FlatnessToleranceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "0 i", true, 0 }
        });
    }

    private String syntaxToCheck;
    private boolean pass;
    private int value;

    public FlatnessToleranceTest(String syntaxToCheck, boolean pass, int value) {
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

        PdfStreamParser.FlatnessContext context = streamParser.flatness();
        int childCount = context.getChildCount();

        Assert.assertEquals(2, childCount);

        TerminalNode number = (TerminalNode) context.getChild(0);
        String actual = number.getSymbol().getText();
        Assert.assertEquals(value, Integer.parseInt(actual));
    }

}