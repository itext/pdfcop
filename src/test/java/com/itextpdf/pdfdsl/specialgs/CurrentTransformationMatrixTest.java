package com.itextpdf.pdfdsl.specialgs;

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
public class CurrentTransformationMatrixTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "0 1 2 3 4 5 cm", true, new int[] {0, 1, 2, 3, 4, 5}, "cm" },
                { "0 1 2 cm", false, new int[] {0, 1, 2, 3, 4, 5}, "cm" }
        });
    }


    private String syntaxToCheck;
    private boolean pass;
    private int[] operands;
    private String operator;

    public CurrentTransformationMatrixTest(String syntaxToCheck, boolean pass, int[] operands, String operator) {
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

        PdfStreamParser.CurrentMatrixContext  context = streamParser.currentMatrix();
        int childCount = context.getChildCount();

        Assert.assertEquals(7, childCount);

        for ( int i = 0; i < 6; i++ ) {
            TerminalNode operand = (TerminalNode) context.getChild(i);
            String actual = operand.getSymbol().getText();
            Assert.assertEquals(this.operands[i], Integer.parseInt(actual));
        }

        TerminalNode operator = (TerminalNode) context.getChild(6);
        String actualOperator = operator.getSymbol().getText();
        Assert.assertEquals(this.operator, actualOperator);
    }

}