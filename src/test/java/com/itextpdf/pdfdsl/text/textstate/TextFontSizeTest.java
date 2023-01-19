package com.itextpdf.pdfdsl.text.textstate;

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
public class TextFontSizeTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "/Name 0 Tf", true, 0, "/Name", "Tf" },
                { "/Name 1 Tf", true, 1, "/Name", "Tf" },
                { "/Name 20 Tf", true, 20, "/Name", "Tf" },
                { "Abcde Tf", false, 0, "Abcde", "Tf" }
        });
    }


    private String syntaxToCheck;
    private boolean pass;
    private int operand;
    private String name;
    private String operator;

    public TextFontSizeTest(String syntaxToCheck, boolean pass, int operand, String name, String operator) {
        this.syntaxToCheck = syntaxToCheck;
        this.pass = pass;
        this.operand = operand;
        this.operator = operator;
        this.name = name;
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

        PdfStreamParser.FontSizeContext  context = streamParser.fontSize();
        int childCount = context.getChildCount();

        Assert.assertEquals(3, childCount);

        TerminalNode name = (TerminalNode) context.getChild(0);
        TerminalNode operand = (TerminalNode) context.getChild(1);
        TerminalNode operator = (TerminalNode) context.getChild(2);

        String nameActual = name.getSymbol().getText();
        String actual = operand.getSymbol().getText();
        String actualOperator = operator.getSymbol().getText();

        Assert.assertEquals(this.name, nameActual);
        Assert.assertEquals(this.operand, Integer.parseInt(actual));
        Assert.assertEquals(this.operator, actualOperator);
    }
}
