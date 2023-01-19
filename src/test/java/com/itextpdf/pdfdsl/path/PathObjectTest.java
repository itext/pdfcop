package com.itextpdf.pdfdsl.path;

import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.GroupingBaseTest;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class PathObjectTest extends GroupingBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "0 0 m 0 0 l S", true, 2},
                { "270 180 150 30 re f", true, 2},
                { "379.70001 806.5 m\n" +
                        "565.70001 806.5 l\n" +
                        "565.70001 757. l\n" +
                        "379.70001 757. l\n" +
                        "379.70001 806.5 l\n" +
                        "h\n" +
                        "W\n" +
                        "n", true, 3}
        });
    }

    public PathObjectTest(String syntaxToCheck, boolean pass, int childCount) {
        super(syntaxToCheck, pass, childCount);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.pathObject();
    }
}