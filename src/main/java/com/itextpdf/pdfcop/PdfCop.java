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
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class PdfCop {

    public boolean isDocumentFollowingTheRules(final String pathToFile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(pathToFile);
        boolean output = this.isDocumentFollowingTheRules(fileInputStream);
        fileInputStream.close();

        return output;
    }

    public boolean isDocumentFollowingTheRules(final File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        boolean output = this.isDocumentFollowingTheRules(fileInputStream);
        fileInputStream.close();

        return output;
    }

    public boolean isDocumentFollowingTheRules(final InputStream inputStream) throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(inputStream));
        boolean output = this.isDocumentFollowingTheRules(pdfDocument);
        pdfDocument.close();

        return output;
    }

    public boolean isDocumentFollowingTheRules(final PdfDocument pdfDocument) {
        for ( int pageNumber = 1; pageNumber <= pdfDocument.getNumberOfPages(); pageNumber++ ) {
            PdfPage page = pdfDocument.getPage(pageNumber);

            StringBuilder stringBuilder = new StringBuilder();

            for (int streamIndx = 0; streamIndx < page.getContentStreamCount(); streamIndx++) {
                PdfStream contentStream = page.getContentStream(streamIndx);
                byte[] bytes = contentStream.getBytes(true);
                String syntax = new String(bytes);

                stringBuilder.append(syntax);
            }

            PdfStreamLexer streamLexer = new PdfStreamLexer(CharStreams.fromString(stringBuilder.toString()));
            streamLexer.removeErrorListeners();
            streamLexer.addErrorListener(ThrowingErrorListener.INSTANCE);

            CommonTokenStream tokens = new CommonTokenStream(streamLexer);
            PdfStreamParser streamParser = new PdfStreamParser(tokens);
            streamParser.removeErrorListeners();
            streamParser.addErrorListener(ThrowingErrorListener.INSTANCE);

            streamParser.pageLevelObject();
        }

        return true;
    }

    public boolean doesSnippetFollowTheRules(final String snippet) {
        PdfStreamLexer streamLexer = new PdfStreamLexer(CharStreams.fromString(snippet));
        streamLexer.removeErrorListeners();
        streamLexer.addErrorListener(ThrowingErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(streamLexer);
        PdfStreamParser streamParser = new PdfStreamParser(tokens);
        streamParser.removeErrorListeners();
        streamParser.addErrorListener(ThrowingErrorListener.INSTANCE);

        streamParser.pageLevelObject();

        return true;
    }
}