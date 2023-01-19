package com.itextpdf.pdfdsl.markedcontent;

import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.GroupingBaseTest;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class MarkedPointTest extends GroupingBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "/TouchUp_TextEdit MP\n" +
                        "BT\n" +
                        "/C2_8 7.019 Tf\n" +
                        "ET\n", true, 2 }
        });
    }

    public MarkedPointTest(String syntaxToCheck, boolean pass, int childCount) {
        super(syntaxToCheck, pass, childCount);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.markedContentPoint();
    }
}