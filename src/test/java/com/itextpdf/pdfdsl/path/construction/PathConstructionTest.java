package com.itextpdf.pdfdsl.path.construction;

import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.GroupingBaseTest;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class PathConstructionTest extends GroupingBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "h", true, 1},
                { "0 1 2 3 re h", true, 2},
                { "10 20 m 0 1 l", true, 2},
                { "10 20 m 10 20 m", true, 2},
                { "h h", true, 2},
                { "0 1 2 3 4 5 c 0 1 2 3 v 0 1 2 3 y", true, 3}
        });
    }

    public PathConstructionTest(String syntaxToCheck, boolean pass, int childCount) {
        super(syntaxToCheck, pass, childCount);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.pathConstruction();
    }
}