package com.itextpdf.pdfdsl;

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