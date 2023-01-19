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

import com.itextpdf.antlr.PdfStreamParser;

import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.runners.Parameterized;

public class PageLevelObjectTest extends GroupingBaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { "/OC /Pr1 BDC\n" +
                        "BT\n" +
                        "100 780 Td\n" +
                        "(nested layer 1) Tj\n" +
                        "ET\n" +
                        "EMC", true, 2},
                { "BT\n" +
                        "() Tj\n" +
                        "ET\n", true, 2},
                { "q\n" +
                        "BT\n" +
                        "/F1 12 Tf\n" +
                        "36 789.33 Td\n" +
                        "(Hello World) Tj\n" +
                        "ET\n" +
                        "%Comment insertion \n" +
                        "Q", true, 5},
                { "/TouchUp_TextEdit MP\n" +
                        "BT\n" +
                        "/C2_8 7.019 Tf\n" +
                        "ET", true, 2},
                { "q\n" +
                        "35.75 806 m\n" +
                        "0.62745 0.62745 0.62745 RG\n" +
                        "0.5 w\n" +
                        "559.25 806 l\n" +
                        "S\n" +
                        "Q", false, 1},
                { "q\n" +
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
                        "Q\n" +
                        "\n", true, 14},
                { "q\n" +
                        "1 0 0 -1 133.58 42.69 cm\n" +
                        "/Fm86 Do\n" +
                        "Q\n" +
                        "\n", true, 4},
                { "q\n" +
                        "BT\n" +
                        "/F1 12 Tf\n" +
                        "36 789.33 Td\n" +
                        "(Hello World) Tj\n" +
                        "ET\n" +
                        "%Comment insertion \n" +
                        "BT\n" +
                        "/F1 12 Tf\n" +
                        "36 789.33 Td\n" +
                        "(Hello World) Tj\n" +
                        "ET\n" +
                        "Q", true, 6},
                {"0 0 0 rg\n" +
                        "[] 0 d\n" +
                        "1 w\n" +
                        "10 M\n" +
                        "0 i\n" +
                        "0 J\n" +
                        "0 j\n" +
                        "BT\n" +
                        "0 0 0 rg\n" +
                        "/F0 10 Tf\n" +
                        "1 0 0 1 72 710.69 Tm\n" +
                        "0 Tc\n" +
                        "0 Tw\n" +
                        "0 Tr\n" +
                        "100 Tz\n" +
                        "0 Ts\n" +
                        "(The full content of this file cannot be displayed with your current PDF viewer. Please update to the latest ) Tj\n" +
                        "0 0 0 rg\n" +
                        "1 0 0 1 72 699.13 Tm\n" +
                        "(possible version to view this document.) Tj\n" +
                        "ET", true, 4},
                { "BT\n" +
                        "0 0 0 rg\n" +
                        "/RelativeColorimetric ri\n" +
                        "/T1_0 1 Tf\n" +
                        "24 0 0 24 72 702.984 Tm\n" +
                        "(Please wait... ) Tj\n" +
                        "12 0 0 12 72 684.684 Tm\n" +
                        "(  ) Tj\n" +
                        "0 -1.109 TD\n" +
                        "(If this message is not eventually replaced by the proper contents of the document, your PDF ) Tj\n" +
                        "T*\n" +
                        "(viewer may not be able to display this type of document. ) Tj\n" +
                        "T*\n" +
                        "(  ) Tj\n" +
                        "T*\n" +
                        "(You can upgrade to the latest version of Adobe Reader for Windows®, Mac, or Linux® by ) Tj\n" +
                        "T*\n" +
                        "(visiting  http://www.adobe.com/products/acrobat/readstep2.html. ) Tj\n" +
                        "T*\n" +
                        "(  ) Tj\n" +
                        "T*\n" +
                        "(For more assistance with Adobe Reader visit  http://www.adobe.com/support/products/) Tj\n" +
                        "T*\n" +
                        "(acrreader.html. ) Tj\n" +
                        "T*\n" +
                        "(  ) Tj\n" +
                        "8 0 0 8 72 554.376 Tm\n" +
                        "(Windows is either a registered trademark or a trademark of Microsoft Corporation in the United States and/or other countries. Mac is a trademark ) Tj\n" +
                        "T*\n" +
                        "(of Apple Inc., registered in the United States and other countries. Linux is the registered trademark of Linus Torvalds in the U.S. and other ) Tj\n" +
                        "T*\n" +
                        "(countries.) Tj\n" +
                        "ET\n", true, 2}
        });
    }

    public PageLevelObjectTest(String syntaxToCheck, boolean pass, int childCount) {
        super(syntaxToCheck, pass, childCount);
    }

    @Override
    public ParserRuleContext getContext(PdfStreamParser streamParser) {
        return streamParser.pageLevelObject();
    }
}