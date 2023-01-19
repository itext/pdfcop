package com.itextpdf.pdfdsl.generalgs;

import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.GroupingBaseTest;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class GeneralGraphicsStateTest extends GroupingBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "/Test gs", true, 1},
                { "/Test gs /Test gs", true, 2},
                { "/Test gs 0 i", true, 2},
                { "0 M /Test gs 0 i", true, 3}
        });
    }

    public GeneralGraphicsStateTest(String syntaxToCheck, boolean pass, int childCount) {
        super(syntaxToCheck, pass, childCount);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.generalGraphicsState();
    }
}