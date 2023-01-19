package com.itextpdf.pdfdsl.text.textshowing;

import com.itextpdf.antlr.PdfStreamLexer;
import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.ThrowingErrorListener;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ShowTextWithOffsetTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "[(A) 120 (W) 120 (A) 95 (Y again)] TJ", true, 2 },
                { "[(AWAY again)] TJ", true, 2 }
        });
    }

    private String syntaxToCheck;
    private boolean pass;
    private int value;

    public ShowTextWithOffsetTest(String syntaxToCheck, boolean pass, int value) {
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

        PdfStreamParser.TextShowWithGlyphPositioningContext context = streamParser.textShowWithGlyphPositioning();
        int childCount = context.getChildCount();

        Assert.assertEquals(this.value, childCount);
    }

}