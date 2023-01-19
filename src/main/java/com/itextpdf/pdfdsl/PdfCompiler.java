package com.itextpdf.pdfdsl;

import com.itextpdf.antlr.PdfStreamLexer;
import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfStream;

import java.io.File;
import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class PdfCompiler {

    public boolean compiles(final File inputPdf) throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(inputPdf));
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

        pdfDocument.close();

        return true;
    }

    public boolean compiles(final String inputPdf, final int pageNumber) throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(inputPdf));
        PdfPage page = pdfDocument.getPage(pageNumber);

        StringBuilder stringBuilder = new StringBuilder();

        for ( int streamIndx = 0; streamIndx < page.getContentStreamCount(); streamIndx++ ) {
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

        return true;
    }

    public boolean compileSnippet(final String input) {
        PdfStreamLexer streamLexer = new PdfStreamLexer(CharStreams.fromString(input));
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