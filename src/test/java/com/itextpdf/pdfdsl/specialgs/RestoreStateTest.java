package com.itextpdf.pdfdsl.specialgs;

import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.NoOperandBaseTest;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class RestoreStateTest extends NoOperandBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "Q", true },
                { "q", false }
        });
    }

    public RestoreStateTest(String syntaxToCheck, boolean pass) {
        super(syntaxToCheck, pass);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.restoreState();
    }

}