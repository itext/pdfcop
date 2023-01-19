package com.itextpdf.pdfdsl.path.construction;

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
public class MoveToTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "0 0 m", true, 0, 0, "m" },
                { "-100 100 m", true, -100, 100, "m" },
                { "0 m", false, 0, 0, "m" }
        });
    }

    private String syntaxToCheck;
    private boolean pass;
    private int x, y;
    private String operator;

    public MoveToTest(String syntaxToCheck, boolean pass, int x, int y, String operator) {
        this.syntaxToCheck = syntaxToCheck;
        this.pass = pass;
        this.x = x;
        this.y = y;
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

        PdfStreamParser.MoveToContext context = streamParser.moveTo();

        TerminalNode operator = (TerminalNode) context.getChild(2);
        String actualOperator = operator.getSymbol().getText();
        Assert.assertEquals(this.operator, actualOperator);

        TerminalNode xOperand = (TerminalNode) context.getChild(0);
        String xActual = xOperand.getSymbol().getText();
        Assert.assertEquals(this.x, Integer.parseInt(xActual));

        TerminalNode yOperand = (TerminalNode) context.getChild(1);
        String yActual = yOperand.getSymbol().getText();
        Assert.assertEquals(this.y, Integer.parseInt(yActual));
    }

}