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
package com.itextpdf.pdfcop.text.textpositioning;

import com.itextpdf.antlr.PdfStreamLexer;
import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfcop.ThrowingErrorListener;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TextMoveToTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "0 0 Td", true, 0, 0, "Td" },
                { "0 0 TD", true, 0, 0, "TD" },
                { "1000 1000 Td", true, 1000, 1000, "Td" },
                { "0 Td", false, 0, 0, "Td" },
                { "0 TD", false, 0, 0, "Td" }
        });
    }


    private String syntaxToCheck;
    private boolean pass;
    private int operand;
    private int operand2;
    private String operator;

    public TextMoveToTest(String syntaxToCheck, boolean pass, int operand, int operand2, String operator) {
        this.syntaxToCheck = syntaxToCheck;
        this.pass = pass;
        this.operand = operand;
        this.operand2 = operand2;
        this.operator = operator;
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

        PdfStreamParser.TextMoveToNextLineWithOffsetContext  context = streamParser.textMoveToNextLineWithOffset();
        int childCount = context.getChildCount();

        Assert.assertEquals(3, childCount);

        TerminalNode operand = (TerminalNode) context.getChild(0);
        TerminalNode operand2 = (TerminalNode) context.getChild(1);
        TerminalNode operator = (TerminalNode) context.getChild(2);

        String actual = operand.getSymbol().getText();
        String actual2 = operand2.getSymbol().getText();
        String actualOperator = operator.getSymbol().getText();

        Assert.assertEquals(this.operand, Integer.parseInt(actual));
        Assert.assertEquals(this.operand2, Integer.parseInt(actual2));
        Assert.assertEquals(this.operator, actualOperator);
    }

}