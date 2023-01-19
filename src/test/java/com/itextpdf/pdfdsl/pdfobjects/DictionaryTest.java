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
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class DictionaryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "<</Key /Value>>", true, 4 },
                { "<</Key/Value>>", true, 4 },
                { "<</Key 1>>", true, 4 },
                { "<</Key (Abcde)>>", true, 4 },
                { "<</Key <AABBCC001122>>>", true, 4 },
                { "<</Key 1 0 R>>", true, 4 },
                { "<</Key <</Key /Value>>>>", true, 4 },
                { "<</Key [0 1 2 3 4]>>", true, 4 },
                { "<</Key /Value [0 1 2 3 4]>>", false, 0 },
                { "<<>>", true, 2 }
        });
    }

    private int childCount;
    private String syntaxToCheck;
    private boolean pass;

    public DictionaryTest(String syntaxToCheck, boolean pass, int childCount) {
        this.syntaxToCheck = syntaxToCheck;
        this.pass = pass;
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

        ParserRuleContext context = streamParser.dictionary();

        Assert.assertEquals(this.childCount, context.getChildCount());
    }
}