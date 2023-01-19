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
package com.itextpdf.pdfcop;

import com.itextpdf.antlr.PdfStreamLexer;
import com.itextpdf.antlr.PdfStreamParser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public abstract class AbstractTest implements IPdfDslTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    protected boolean pass;
    protected String syntaxToCheck;

    protected PdfStreamLexer streamLexer;
    protected PdfStreamParser streamParser;
    protected ParserRuleContext context;

    AbstractTest(boolean pass, String syntaxToCheck) {
        this.pass = pass;
        this.syntaxToCheck = syntaxToCheck;
    }

    @Before
    public void setUp() {
        if (! this.pass) {
            this.expectedException.expect(ParseCancellationException.class);
        }

        this.streamLexer = new PdfStreamLexer(CharStreams.fromString(this.syntaxToCheck));
        this.streamLexer.removeErrorListeners();
        this.streamLexer.addErrorListener(ThrowingErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(this.streamLexer);
        this.streamParser = new PdfStreamParser(tokens);
        this.streamParser.removeErrorListeners();
        this.streamParser.addErrorListener(ThrowingErrorListener.INSTANCE);

        this.context = getContext(streamParser);
    }

    protected String getValue(final int childIndex) {
        TerminalNode name = (TerminalNode) this.context.getChild(childIndex);
        return name.getSymbol().getText();
    }
}