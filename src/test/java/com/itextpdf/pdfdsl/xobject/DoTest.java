package com.itextpdf.pdfdsl.xobject;

import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.SingleOperandBaseTest;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class DoTest extends SingleOperandBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "/Test Do", true, "/Test", "Do" },
                { "/Test /Test Do", false, "/Fail", "Do" },
                { "Do", false, "/Fail", "Do" },
                { "Scooby Do", false, "/Fail", "Do" }
        });
    }

    public DoTest(String syntaxToCheck, boolean pass, String value, String operand) {
        super(syntaxToCheck, pass, value, operand);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.dobject();
    }
}