package com.itextpdf.pdfdsl.functional;

import com.itextpdf.pdfdsl.PdfCompiler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class FunctionalTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "src/test/resources/pdfs/input.pdf", 1, true },
                { "src/test/resources/pdfs/outlook.pdf", 1, true },
                { "src/test/resources/pdfs/flowcad.pdf", 1, true },
                { "src/test/resources/pdfs/opensource.pdf", 1, true },
                { "src/test/resources/pdfs/spunbysorcery.pdf", 3, true },
                { "src/test/resources/pdfs/inlineimage.pdf", 1, true },
                { "src/test/resources/pdfs/cmp_fieldsJustificationTest02.pdf", 1, true }
        });
    }


    private int pageNumber;
    private String inputPdf;
    private boolean pass;

    public FunctionalTest(String inputPdf, int pageNumber, boolean pass) {
        this.inputPdf = inputPdf;
        this.pageNumber = pageNumber;
        this.pass = pass;
    }

    @Test
    public void test() throws IOException {
        if (! this.pass) {
            this.expectedException.expect(ParseCancellationException.class);
        }

        Assert.assertTrue(new PdfCompiler().compiles(inputPdf, this.pageNumber));
    }

}