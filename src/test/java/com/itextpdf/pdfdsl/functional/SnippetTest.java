package com.itextpdf.pdfdsl.functional;

import com.itextpdf.pdfdsl.PdfCop;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class SnippetTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "Q\n" +
                        "q\n" +
                        "1 0 0 1 310.57 679.89 cm\n" +
                        "/Fm1 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 310.57 638.1 cm\n" +
                        "/Fm2 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 38.19 738.65 cm\n" +
                        "/Fm3 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 154.05 726.71 cm\n" +
                        "/Fm4 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 37.13 691.31 cm\n" +
                        "/Fm5 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 70.31 681.38 cm\n" +
                        "/Fm6 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 102.56 660.19 cm\n" +
                        "/Fm7 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 227.25 660.19 cm\n" +
                        "/Fm8 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 102.56 638.06 cm\n" +
                        "/Fm9 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 227.37 638.21 cm\n" +
                        "/Fm10 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 34.95 599.4 cm\n" +
                        "/Fm11 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 175.18 414.45 cm\n" +
                        "/Fm12 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 175.79 401.54 cm\n" +
                        "/Fm13 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 213 384.32 cm\n" +
                        "/Fm14 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 213 371.32 cm\n" +
                        "/Fm15 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 213.61 358.68 cm\n" +
                        "/Fm16 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 213.61 345.66 cm\n" +
                        "/Fm17 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 214.22 319.65 cm\n" +
                        "/Fm18 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 213.61 307.24 cm\n" +
                        "/Fm19 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 214.83 294.94 cm\n" +
                        "/Fm20 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 213.61 282.01 cm\n" +
                        "/Fm21 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 216.65 259.57 cm\n" +
                        "/Fm22 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 213 229.41 cm\n" +
                        "/Fm23 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 213 240.62 cm\n" +
                        "/Fm24 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 483.23 440.82 cm\n" +
                        "/Fm25 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 484.45 430.65 cm\n" +
                        "/Fm26 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 483.84 419.54 cm\n" +
                        "/Fm27 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 318.05 355.08 cm\n" +
                        "/Fm28 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 318.05 343.55 cm\n" +
                        "/Fm29 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 318.05 334.88 cm\n" +
                        "/Fm30 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 348.61 355.32 cm\n" +
                        "/Fm31 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 347.46 345.04 cm\n" +
                        "/Fm32 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 348 334.65 cm\n" +
                        "/Fm33 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 496.32 347.26 cm\n" +
                        "/Fm34 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 496.31 334.88 cm\n" +
                        "/Fm35 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 545 301.26 cm\n" +
                        "/Fm36 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 220.11 208.65 cm\n" +
                        "/Fm37 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 300.5 208.65 cm\n" +
                        "/Fm38 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 44.07 126.45 cm\n" +
                        "/Fm39 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 77.45 88.54 cm\n" +
                        "/Fm40 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 407.92 65.99 cm\n" +
                        "/Fm41 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 54.93 54.36 cm\n" +
                        "/Fm42 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 407.92 54.19 cm\n" +
                        "/Fm43 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 54.93 42.86 cm\n" +
                        "/Fm44 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 407.92 42.69 cm\n" +
                        "/Fm45 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 273.63 432.99 cm\n" +
                        "/Fm46 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 125.58 283.23 cm\n" +
                        "/Fm47 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 310.51 724.87 cm\n" +
                        "/Fm48 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 311.94 753.38 cm\n" +
                        "/Fm49 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 310.51 737.9 cm\n" +
                        "/Fm50 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 127.2 735.61 cm\n" +
                        "/Fm51 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 197.26 683.77 cm\n" +
                        "/Fm52 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 309.28 542.45 cm\n" +
                        "/Fm53 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 37.28 541.23 cm\n" +
                        "/Fm54 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 36.06 475.82 cm\n" +
                        "/Fm55 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 309.28 468.52 cm\n" +
                        "/Fm56 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 483.84 408.9 cm\n" +
                        "/Fm57 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 557.5 89.12 cm\n" +
                        "/Fm58 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 300.95 89.02 cm\n" +
                        "/Fm59 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 431.16 88.41 cm\n" +
                        "/Fm60 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 193.62 89.07 cm\n" +
                        "/Fm61 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 511.07 66.05 cm\n" +
                        "/Fm62 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 511.07 54.25 cm\n" +
                        "/Fm63 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 511.07 42.75 cm\n" +
                        "/Fm64 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1.07 0 0 0.8162 454.97 356.49 cm\n" +
                        "/Fm65 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 496.31 356.54 cm\n" +
                        "/Fm66 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 171.41 735.61 cm\n" +
                        "/Fm67 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 214.22 331.8 cm\n" +
                        "/Fm68 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 484.45 397.88 cm\n" +
                        "/Fm69 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 305.03 42.63 cm\n" +
                        "/Fm70 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 305.03 54.13 cm\n" +
                        "/Fm71 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 305.03 65.93 cm\n" +
                        "/Fm72 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 454.97 345.99 cm\n" +
                        "/Fm73 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 454.97 345.99 cm\n" +
                        "/Fm74 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 454.36 334.65 cm\n" +
                        "/Fm75 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 454.36 334.04 cm\n" +
                        "/Fm75 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 155.53 113.7 cm\n" +
                        "/Fm76 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 43.84 112.93 cm\n" +
                        "/Fm77 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 155.28 101.36 cm\n" +
                        "/Fm78 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 36.74 465.26 cm\n" +
                        "/Fm79 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 239.02 65.99 cm\n" +
                        "/Fm80 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 239.02 54.19 cm\n" +
                        "/Fm81 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 239.02 42.69 cm\n" +
                        "/Fm82 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 54.93 66.33 cm\n" +
                        "/Fm83 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 1 133.58 64.9 cm\n" +
                        "/Fm84 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 -1 133.58 54.19 cm\n" +
                        "/Fm85 Do\n" +
                        "Q\n" +
                        "q\n" +
                        "1 0 0 -1 133.58 42.69 cm\n" +
                        "/Fm86 Do\n" +
                        "Q\n" }
        });
    }


    private String inputPdf;

    public SnippetTest(String inputPdf) {
        this.inputPdf = inputPdf;
    }

    @Test
    public void test() {
        Assert.assertTrue(new PdfCop().doesSnippetFollowTheRules(inputPdf));
    }
}