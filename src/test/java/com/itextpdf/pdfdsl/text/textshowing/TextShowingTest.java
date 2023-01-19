package com.itextpdf.pdfdsl.text.textshowing;

import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.GroupingBaseTest;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class TextShowingTest extends GroupingBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "() Tj", true, 1},
                { "(Hello World) Tj", true, 1},
                { "(Hello World) Tj (Hello World) Tj", true, 2},
                { "(Hello World) Tj (Hello World) '", true, 2},
                { "(Hello World) Tj (Hello World) ' (Hello World) \"", true, 2},
                { "(Hello World) Tj [(A) 120 (W) 120 (A) 95 (Y again)] TJ [(AWAY again)] TJ", true, 3}
        });
    }

    public TextShowingTest(String syntaxToCheck, boolean pass, int childCount) {
        super(syntaxToCheck, pass, childCount);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.textShowing();
    }
}