package com.itextpdf.pdfdsl.color;

import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.SingleOperandBaseTest;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class ColourSpaceTest extends SingleOperandBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "/CS1 CS", true, "/CS1", "CS" },
                { "/CS1 cs", true, "/CS1", "cs" },
                { "Abcde cs", false, "Fail", "Fail" }
        });
    }

    public ColourSpaceTest(String syntaxToCheck, boolean pass, String value, String operand) {
        super(syntaxToCheck, pass, value, operand);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.cs();
    }
}