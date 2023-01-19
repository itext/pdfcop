package com.itextpdf.pdfdsl.shading;

import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.SingleOperandBaseTest;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class ShadyTest extends SingleOperandBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "/Slim sh", true, "/Slim", "sh" },
                { "/Test /Test sh", false, "/Fail", "sh" },
                { "sh", false, "/Fail", "sh" },
                { "run sh", false, "/Fail", "sh" }
        });
    }

    public ShadyTest(String syntaxToCheck, boolean pass, String value, String operand) {
        super(syntaxToCheck, pass, value, operand);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.shading();
    }
}