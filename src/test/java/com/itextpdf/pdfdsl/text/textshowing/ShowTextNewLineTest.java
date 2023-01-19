package com.itextpdf.pdfdsl.text.textshowing;

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
public class ShowTextNewLineTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "(Hello World) '", true, "(Hello World)", "'" },
                { "<AABBCC001122> '", true, "<AABBCC001122>", "'" },
                { "Hello World '", false, "Hello World", "'" }
        });
    }

    private String syntaxToCheck;
    private boolean pass;
    private String operand;
    private String operator;

    public ShowTextNewLineTest(String syntaxToCheck, boolean pass, String operand, String operator) {
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

        PdfStreamParser.TextShowWithNewLineContext context = streamParser.textShowWithNewLine();
        int childCount = context.getChildCount();

        Assert.assertEquals(2, childCount);

        TerminalNode operand = (TerminalNode) context.getChild(0);
        TerminalNode operator = (TerminalNode) context.getChild(1);

        String actual = operand.getSymbol().getText();
        String actualOperator = operator.getSymbol().getText();

        Assert.assertEquals(this.operand, actual);
        Assert.assertEquals(this.operator, actualOperator);
    }

}