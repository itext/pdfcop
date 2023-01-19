package com.itextpdf.pdfdsl.text.textstate;

import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.GroupingBaseTest;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class TextStateTest extends GroupingBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "0 Tc", true, 1},
                { "0 Tc 0 Tr", true, 2},
                { "0 TL 0 Tr", true, 2},
                { "/Name 0 Tf 0 Ts", true, 2}
        });
    }

    public TextStateTest(String syntaxToCheck, boolean pass, int childCount) {
        super(syntaxToCheck, pass, childCount);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.textState();
    }
}