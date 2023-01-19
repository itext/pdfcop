package com.itextpdf.pdfdsl;

import com.itextpdf.antlr.PdfStreamParser;

import org.antlr.v4.runtime.ParserRuleContext;

public interface IPdfDslTest {
    ParserRuleContext getContext(PdfStreamParser streamParser);
}