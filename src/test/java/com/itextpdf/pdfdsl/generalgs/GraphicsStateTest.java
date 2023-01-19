package com.itextpdf.pdfdsl.generalgs;

import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.SingleOperandBaseTest;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class GraphicsStateTest extends SingleOperandBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "/Test gs", true, "/Test", "gs" }
        });
    }

    public GraphicsStateTest(String syntaxToCheck, boolean pass, String value, String operand) {
        super(syntaxToCheck, pass, value, operand);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.graphicsState();
    }
}