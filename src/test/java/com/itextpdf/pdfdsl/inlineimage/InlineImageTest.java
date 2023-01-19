package com.itextpdf.pdfdsl.inlineimage;

import com.itextpdf.antlr.PdfStreamLexer;
import com.itextpdf.antlr.PdfStreamParser;
import com.itextpdf.pdfdsl.ThrowingErrorListener;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class InlineImageTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "BI\n" +
                        "/BitsPerComponent 8\n" +
                        "/ColorSpace /DeviceRGB\n" +
                        "/Filter /DCTDecode\n" +
                        "/Height 16\n" +
                        "/Width 16\n" +
                        "ID\n" +
                        "ÿØÿà \u0010JFIF \u0001\u0001\u0001 ` `  ÿÛ C \u0002\u0001\u0001\u0002\u0001\u0001\u0002\u0002\u0002\u0002\u0002\u0002\u0002\u0002\u0003\u0005\u0003\u0003\u0003\u0003\u0003\u0006\u0004\u0004\u0003\u0005\u0007\u0006\u0007\u0007\u0007\u0006\u0007\u0007\b\t\u000B\t\b\b\n" +
                        "\b\u0007\u0007\n" +
                        "\n" +
                        "\n" +
                        "\u000B\f\f\f\f\u0007\t\u000E\u000F\n" +
                        "\f\u000E\u000B\f\f\fÿÛ C\u0001\u0002\u0002\u0002\u0003\u0003\u0003\u0006\u0003\u0003\u0006\f\b\u0007\b\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\f\fÿÀ \u0011\b \u0010 \u0010\u0003\u0001\" \u0002\u0011\u0001\u0003\u0011\u0001ÿÄ \u001F  \u0001\u0005\u0001\u0001\u0001\u0001\u0001\u0001        \u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n" +
                        "\u000BÿÄ µ\u0010 \u0002\u0001\u0003\u0003\u0002\u0004\u0003\u0005\u0005\u0004\u0004  \u0001}\u0001\u0002\u0003 \u0004\u0011\u0005\u0012!1A\u0006\u0013Qa\u0007\"q\u00142\u0081‘¡\b#B±Á\u0015RÑð$3br‚\t\n" +
                        "\u0016\u0017\u0018\u0019\u001A%&'()*456789:CDEFGHIJSTUVWXYZcdefghijstuvwxyzƒ„…†‡ˆ‰Š’“”•–—˜™š¢£¤¥¦§¨©ª²³´µ¶·¸¹ºÂÃÄÅÆÇÈÉÊÒÓÔÕÖ×ØÙÚáâãäåæçèéêñòóôõö÷øùúÿÄ \u001F\u0001 \u0003\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001      \u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n" +
                        "\u000BÿÄ µ\u0011 \u0002\u0001\u0002\u0004\u0004\u0003\u0004\u0007\u0005\u0004\u0004 \u0001\u0002w \u0001\u0002\u0003\u0011\u0004\u0005!1\u0006\u0012AQ\u0007aq\u0013\"2\u0081\b\u0014B‘¡±Á\t#3Rð\u0015brÑ\n" +
                        "\u0016$4á%ñ\u0017\u0018\u0019\u001A&'()*56789:CDEFGHIJSTUVWXYZcdefghijstuvwxyz‚ƒ„…†‡ˆ‰Š’“”•–—˜™š¢£¤¥¦§¨©ª²³´µ¶·¸¹ºÂÃÄÅÆÇÈÉÊÒÓÔÕÖ×ØÙÚâãäåæçèéêòóôõö÷øùúÿÚ \f\u0003\u0001 \u0002\u0011\u0003\u0011 ? þ\u007Fè¢Š ÿÙ\n" +
                        "EI", true, 12 }

        });
    }

    private int childCount;
    private String syntaxToCheck;
    private boolean pass;

    public InlineImageTest(String syntaxToCheck, boolean pass, int childCount) {
        this.syntaxToCheck = syntaxToCheck;
        this.pass = pass;
        this.childCount = childCount;
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

        ParserRuleContext context = streamParser.inlineImageObject();

        Assert.assertEquals(this.childCount, context.getChildCount());
    }

}