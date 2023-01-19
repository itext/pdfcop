package com.itextpdf.pdfdsl;

import java.io.PrintWriter;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class ThrowingErrorListener extends BaseErrorListener {

    private boolean log = false;
    private PrintWriter writer;

    public static final ThrowingErrorListener INSTANCE = new ThrowingErrorListener();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
            throws ParseCancellationException {
        if ( this.log ) {
            this.writer.println("  ! line " + line + ":" + charPositionInLine + " " + msg);
        } else {
            throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg);
        }
    }

    public ThrowingErrorListener setLog(boolean log) {
        this.log = log;
        return this;
    }

    public ThrowingErrorListener setWriter(PrintWriter writer) {
        this.writer = writer;
        return this;
    }
}