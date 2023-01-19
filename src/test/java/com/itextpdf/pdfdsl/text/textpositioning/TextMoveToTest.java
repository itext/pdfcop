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
public class TextMoveToTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "0 0 Td", true, 0, 0, "Td" },
                { "0 0 TD", true, 0, 0, "TD" },
                { "1000 1000 Td", true, 1000, 1000, "Td" },
                { "0 Td", false, 0, 0, "Td" },
                { "0 TD", false, 0, 0, "Td" }
        });
    }


    private String syntaxToCheck;
    private boolean pass;
    private int operand;
    private int operand2;
    private String operator;

    public TextMoveToTest(String syntaxToCheck, boolean pass, int operand, int operand2, String operator) {
        this.syntaxToCheck = syntaxToCheck;
        this.pass = pass;
        this.operand = operand;
        this.operand2 = operand2;
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

        PdfStreamParser.TextMoveToNextLineWithOffsetContext  context = streamParser.textMoveToNextLineWithOffset();
        int childCount = context.getChildCount();

        Assert.assertEquals(3, childCount);

        TerminalNode operand = (TerminalNode) context.getChild(0);
        TerminalNode operand2 = (TerminalNode) context.getChild(1);
        TerminalNode operator = (TerminalNode) context.getChild(2);

        String actual = operand.getSymbol().getText();
        String actual2 = operand2.getSymbol().getText();
        String actualOperator = operator.getSymbol().getText();

        Assert.assertEquals(this.operand, Integer.parseInt(actual));
        Assert.assertEquals(this.operand2, Integer.parseInt(actual2));
        Assert.assertEquals(this.operator, actualOperator);
    }

}