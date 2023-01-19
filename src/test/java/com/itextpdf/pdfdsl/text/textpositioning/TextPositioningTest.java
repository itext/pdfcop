package com.itextpdf.pdfdsl.text.textpositioning;

import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.GroupingBaseTest;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class TextPositioningTest extends GroupingBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "0 0 Td", true, 1},
                { "0 0 Td 0 0 TD", true, 2},
                { "0 0 Td 0 1 2 3 4 5 Tm", true, 2},
                { "T* 0 0 Td 0 1 2 3 4 5 Tm", true, 3}
        });
    }

    public TextPositioningTest(String syntaxToCheck, boolean pass, int childCount) {
        super(syntaxToCheck, pass, childCount);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.textPositioning();
    }
}