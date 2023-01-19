package com.itextpdf.pdfdsl.specialgs;

import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.GroupingBaseTest;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class SpecialGraphicsStateTest extends GroupingBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "q", true, 1},
                { "q Q", true, 2},
                { "q Q 0 1 2 3 4 5 cm", true, 3}
        });
    }

    public SpecialGraphicsStateTest(String syntaxToCheck, boolean pass, int childCount) {
        super(syntaxToCheck, pass, childCount);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.specialGraphicsState();
    }
}