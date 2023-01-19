package com.itextpdf.pdfdsl.color;

import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.GroupingBaseTest;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class ColorTest extends GroupingBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "0 g", true, 1},
                { "0 g 0 G", true, 2},
                { "0 g 0 g", true, 2},
                { "0 G 1 0 0 RG", true, 2},
                { "1 0 0 RG 0 G", true, 2},
                { "0 G 1 0 0 RG 0 0 0 0 K", true, 3},
                { "0 G 1 0 0 RG 0 0 0 0 K % Comment", true, 4}
        });
    }

    public ColorTest(String syntaxToCheck, boolean pass, int childCount) {
        super(syntaxToCheck, pass, childCount);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.color();
    }
}