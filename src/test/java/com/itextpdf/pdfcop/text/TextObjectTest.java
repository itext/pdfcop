/*
    Copyright (c) 2023 iText Group NV

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.
 */
package com.itextpdf.pdfcop.text;

import com.itextpdf.antlr.PdfStreamLexer;
import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfcop.ThrowingErrorListener;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith( Parameterized.class )
public class TextObjectTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "BT\n" +
                        "/Content <</MCID 1 >> BDC\n" +
                        "0 0 0 rg\n" +
                        "/RelativeColorimetric ri\n" +
                        "/TT0 1 Tf\n" +
                        "11 0 0 11 261.285 715.438 Tm\n" +
                        "(Our Privacy Notice) Tj\n" +
                        "EMC\n" +
                        "ET", true, 3},
                { "BT\n" +
                        "10 0 0 10 64.417 693.424 Tm\n" +
                        "ET", true, 3},
                { "BT (Hello World) Tj ET", true, 3 },
                { "BT\n" +
                        "0 0 0 rg\n" +
                        "/GS0 gs\n" +
                        "/F1 -12 Tf\n" +
                        "270 142.0645 Td\n" +
                        "(KERKSTRAAT) Tj\n" +
                        "109.8164 0 Td\n" +
                        "(108) Tj\n" +
                        "-109.8164 14.584 Td\n" +
                        "(9050) Tj\n" +
                        "34.7344 0 Td\n" +
                        "(Gentbrugge) Tj\n" +
                        "ET", true, 13 },
                { "BT\n" +
                        "0.1019607 0.3803921 0.6627450 RG\n" +
                        "0.75 w\n" +
                        "[] 0. d\n" +
                        "2 J\n" +
                        "0 j\n" +
                        "7.5 M\n" +
                        "0.1019607 0.3803921 0.6627450 rg\n" +
                        "1. 0. 0. 1. 390.25 -442.600 Tm\n" +
                        "/F2 10.5 Tf\n" +
                        "1. 0. 0. 1. 412.949 366.149 Tm\n" +
                        "(FREQUENT) Tj\n" +
                        "ET", true, 9 },
                { "BT\n" +
                        "/F1 8 Tf\n" +
                        "1. 0. 0. 1. 399.25 810.25 Tm\n" +
                        "(Security nb: 2 - Ticket: 603267495996201) Tj\n" +
                        "ET", true, 5 },
                { "BT ET", true, 2 }
        });
    }

    private String syntaxToCheck;
    private boolean pass;
    private int expectedChildCount;

    public TextObjectTest(String syntaxToCheck, boolean pass, int childCount) {
        this.syntaxToCheck = syntaxToCheck;
        this.pass = pass;
        this.expectedChildCount = childCount;
    }

    @Test
    public void test() {
        if (! this.pass) {
            this.expectedException.expect(ParseCancellationException.class);
        }

        PdfStreamLexer streamLexer = new PdfStreamLexer(CharStreams.fromString(this.syntaxToCheck));
        streamLexer.removeErrorListeners();
        streamLexer.addErrorListener(ThrowingErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(streamLexer);
        PdfStreamParser streamParser = new PdfStreamParser(tokens);
        streamParser.removeErrorListeners();
        streamParser.addErrorListener(ThrowingErrorListener.INSTANCE);

        PdfStreamParser.TextObjectContext context = streamParser.textObject();
        int actualChildCount = context.getChildCount();

        Assert.assertEquals(this.expectedChildCount, actualChildCount);
    }


}