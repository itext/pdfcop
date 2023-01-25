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
grammar PdfStream;

// PARSER RULES --------------------------------------------------------------

pageLevelObject : (
                    COMPATIBILITY | generalGraphicsState | specialGraphicsState | color | textState | shading | dobject | textObject | pathObject | comment | inlineImageObject | markedContent
                )* EOF;

textObject
    : BEGIN_TEXT ( COMPATIBILITY | generalGraphicsState | color | textState | textShowing | textPositioning | comment | markedContent )* END_TEXT
    ;

pathObject
    : ( moveTo | rectangle ) COMPATIBILITY | pathConstruction pathClipping? pathPainting
    ;

dobject
    : PDF_NAME XOBJECT
    ;

shading
    : PDF_NAME SHADING_PATTERN
    ;

generalGraphicsState
    : ( COMPATIBILITY | lineCap | lineJoin | lineWidth | miter | dashPattern | flatness | graphicsState | renderingIntent | comment )+
    ;

        lineWidth
            : NUMBER LINE_WIDTH
            ;

        lineCap
            : NUMBER 'J'
            ;

        lineJoin
            : NUMBER LINE_JOIN
            ;

        miter
            : NUMBER MITER_LIMIT
            ;

        dashPattern
            : numberArray NUMBER DASH_PATTERN
            ;

        flatness
            : NUMBER FLATNESS_TOLERANCE
            ;

        graphicsState
            : PDF_NAME GRAPHICS_STATE
            ;

        renderingIntent
            : PDF_NAME COLOUR_RENDERING_INTENT
            ;

specialGraphicsState
    : ( saveState | COMPATIBILITY | restoreState | currentMatrix | comment )+
    ;

        saveState
            : SAVE
            ;

        restoreState
            : RESTORE
            ;

        currentMatrix
            : NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER CURRENT_TRANSFORMATION_MATRIX
            ;

color
    : ( COMPATIBILITY | cs | sc | gray | rgb | cmyk | comment )+
    ;

        cs
            : PDF_NAME (NON_STROKE_COLOUR_SPACE | STROKE_COLOUR_SPACE)
            ;

        sc
            : ( NUMBER )+ (STROKE_COLOUR_DEVICE | STROKE_COLOUR_DEVICE_EXTRA | NON_STROKE_COLOUR_DEVICE | NON_STROKE_COLOUR_DEVICE_EXTRA )
            ;

        gray
            : NUMBER (STROKE_COLOUR_DEVICE_GRAY | NON_STROKE_COLOUR_DEVICE_GRAY)
            ;

        rgb
            : RGB_TOKEN
            ;

        cmyk
            : NUMBER NUMBER NUMBER NUMBER (STROKE_COLOUR_DEVICE_CMYK | NON_STROKE_COLOUR_DEVICE_CMYK)
            ;

textPositioning
    : ( COMPATIBILITY |textMoveToNextLineCurrentLeading | textMatrix | textMoveToNextLineWithOffset | comment )+
    ;

        textMoveToNextLineWithOffset
            : NUMBER NUMBER ( MOVE_TO_START_NEXT_LINE | MOVE_TO_START_NEXT_LINE_SET_LEADING )
            ;

        textMoveToNextLineCurrentLeading
            : MOVE_TO_START_NEXT_LINE_CURRENT_LEADING
            ;

        textMatrix
            : NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER TEXT_MATRIX
            ;

textShowing
    : ( COMPATIBILITY | textShow | textShowWithNewLine | textShowWithNewLineAndSpacing | textShowWithGlyphPositioning | comment )+
    ;

        textShow
            : STRING TEXT_SHOW
            ;

        textShowWithNewLine
            : STRING TEXT_NEW_LINE_AND_SHOW
            ;

        textShowWithNewLineAndSpacing
            : NUMBER NUMBER STRING TEXT_NEW_LINE_AND_SHOW_WITH_SPACING
            ;

        textShowWithGlyphPositioning
            : stringPosArray TEXT_SHOW_GLYPH_POSITIONING
            ;

textState
    : ( COMPATIBILITY | characterSpace | wordSpace | textScale | leading | fontSize | textRenderMode | textRise | comment )+
    ;

        characterSpace
            : NUMBER TEXT_CHAR_SPACE
            ;

        wordSpace
            : NUMBER TEXT_WORD_SPACE
            ;

        textScale
            : NUMBER TEXT_SCALE
            ;

        leading
            : NUMBER TEXT_LEADING
            ;

        fontSize
            : PDF_NAME NUMBER TEXT_FONT_AND_SIZE
            ;

        textRenderMode
            : NUMBER TEXT_RENDER_MODE
            ;

        textRise
            : NUMBER TEXT_RISE
            ;

markedContent
    : markedContentPoint | markedContentSequence
    ;

markedContentSequence
    : ( PDF_NAME ) ( BEGIN_MARKED_CONTENT | ( dictionary | PDF_NAME )  BEGIN_MARKED_CONTENT_WITH_PROPERTIES )
        (COMPATIBILITY | markedContent | generalGraphicsState | specialGraphicsState | color | textPositioning | textShowing | textState | textObject | pathObject | shading | dobject | inlineImageObject)*
      END_MARKED_CONTENT
    ;

markedContentPoint
    : PDF_NAME ((MARKED_CONTENT_POINT) | ( dictionary ( MARKED_CONTENT_POINT_WITH_PROPERTIES )))
             (COMPATIBILITY | markedContent | generalGraphicsState | specialGraphicsState | color | textState | textObject | pathObject | shading | dobject | inlineImageObject)
    ;

pathConstruction
    : ( COMPATIBILITY | moveTo | lineTo | curveTo | curveTo2 | curveTo3 | closePath | rectangle | comment )*
    ;

        moveTo
            : NUMBER NUMBER MOVE_TO
            ;

        lineTo
            : NUMBER NUMBER LINE_TO
            ;

        curveTo
            : NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER BEZIER_CURVE
            ;

        curveTo2
            : NUMBER NUMBER NUMBER NUMBER BEZIER_CURVE_2
            ;

        curveTo3
            : NUMBER NUMBER NUMBER NUMBER BEZIER_CURVE_3
            ;

        closePath
            : CLOSE_BY_LINE_TO_START
            ;

        rectangle
            : NUMBER NUMBER NUMBER NUMBER RECTANGLE
            ;

pathPainting
    : (stroke | strokeAndClose | fill | fillAndStroke | fillStrokeAndClose | endPath | comment)
    ;

        stroke
            : STROKE_PATH
            ;

        strokeAndClose
            : CLOSE_AND_STROKE_PATH
            ;

        fill
            : FILL_PATH_NON_ZERO | FILL_PATH_NON_ZERO_2 | FILL_PATH_EVEN_ODD
            ;

        fillAndStroke
            : FILL_STROKE_PATH_EVEN_ODD | FILL_STROKE_PATH_NON_ZERO
            ;

        fillStrokeAndClose
            : CLOSE_FILL_STROKE_PATH_NON_ZERO | CLOSE_FILL_STROKE_PATH_ODD_EVEN
            ;

        endPath
            : END_PATH_NO_STROKE_OR_FILL
            ;

pathClipping
    : CLIP_PATH_EVEN_ODD | CLIP_PATH_NON_ZERO
    ;

inlineImageObject
    : BEGIN_INLINE_IMAGE (PDF_NAME (PDF_NAME | NUMBER | STRING | dictionary | array | 'true' | 'false' | 'null'))* INLINE_DATA
    ;

number
    : NUMBER
    ;

array
    : '[' (NUMBER | STRING | PDF_NAME | dictionary | array | 'true' | 'false' | 'null' )* ']'
    ;

dictionary
    : '<<' (PDF_NAME (PDF_NAME | NUMBER | STRING | dictionary | array | 'true' | 'false' | 'null'))* '>>'
    ;

numberArray
    : '[' NUMBER* ']'
    ;

string
    : STRING
    ;

comment
    : COMMENT
    ;


stringPosArray
    : '[' ((STRING NUMBER) | STRING | NUMBER )*  ']'
    ;

RGB_TOKEN
    : NUMBER WS NUMBER WS NUMBER WS (STROKE_COLOUR_DEVICE_RGB | NON_STROKE_COLOUR_DEVICE_RGB)
    ;



// LEXER TOKENS --------------------------------------------------------------

// Keywords
INLINE_DATA    : BEGIN_INLINE_IMAGE_DATA WS .*? END_INLINE_IMAGE;

// General Graphics State Keywords
LINE_WIDTH                              : 'w';
LINE_JOIN                               : 'j';
MITER_LIMIT                             : 'M';
DASH_PATTERN                            : 'd';
COLOUR_RENDERING_INTENT                 : 'ri';
FLATNESS_TOLERANCE                      : 'i';
GRAPHICS_STATE                          : 'gs';

// Special Graphics State
SAVE                                    : 'q';
RESTORE                                 : 'Q';
CURRENT_TRANSFORMATION_MATRIX           : 'cm';

// Path Construction Keywords
MOVE_TO                                 : 'm';
LINE_TO                                 : 'l';
BEZIER_CURVE                            : 'c';
BEZIER_CURVE_2                          : 'v';
BEZIER_CURVE_3                          : 'y';
CLOSE_BY_LINE_TO_START                  : 'h';
RECTANGLE                               : 're';

// Path Painting Keywords
STROKE_PATH                             : 'S';
CLOSE_AND_STROKE_PATH                   : 's';
FILL_PATH_NON_ZERO                      : 'f';
FILL_PATH_NON_ZERO_2                    : 'F';
FILL_PATH_EVEN_ODD                      : 'f*';
FILL_STROKE_PATH_NON_ZERO               : 'B';
FILL_STROKE_PATH_EVEN_ODD               : 'B*';
CLOSE_FILL_STROKE_PATH_NON_ZERO         : 'b';
CLOSE_FILL_STROKE_PATH_ODD_EVEN         : 'b*';
END_PATH_NO_STROKE_OR_FILL              : 'n';

// Clipping Path Keywords
CLIP_PATH_NON_ZERO                      : 'W';
CLIP_PATH_EVEN_ODD                      : 'W*';

// Text Objects Keywords
BEGIN_TEXT                              : 'BT';
END_TEXT                                : 'ET';

// Text State Keywords
TEXT_CHAR_SPACE                         : 'Tc';
TEXT_WORD_SPACE                         : 'Tw';
TEXT_SCALE                              : 'Tz';
TEXT_LEADING                            : 'TL';
TEXT_FONT_AND_SIZE                      : 'Tf';
TEXT_RENDER_MODE                        : 'Tr';
TEXT_RISE                               : 'Ts';

// Text Positioning Keywords
MOVE_TO_START_NEXT_LINE                 : 'Td';
MOVE_TO_START_NEXT_LINE_SET_LEADING     : 'TD';
TEXT_MATRIX                             : 'Tm';
MOVE_TO_START_NEXT_LINE_CURRENT_LEADING : 'T*';

// Text Showing Keywords
TEXT_SHOW                               : 'Tj';
TEXT_SHOW_GLYPH_POSITIONING             : 'TJ';
TEXT_NEW_LINE_AND_SHOW                  : '\'';
TEXT_NEW_LINE_AND_SHOW_WITH_SPACING     : '"';

// Type 3 Fonts Keywords
TYPE3_SET_WIDTH_AND_SHAPE_AND_COLOUR    : 'd0';
TYPE3_SET_WIDTH_AND_SHAPE               : 'd1';

// Color Keywords
STROKE_COLOUR_SPACE                     : 'CS';
NON_STROKE_COLOUR_SPACE                 : 'cs';
STROKE_COLOUR_DEVICE                    : 'SC';
STROKE_COLOUR_DEVICE_EXTRA              : 'SCN';
NON_STROKE_COLOUR_DEVICE                : 'sc';
NON_STROKE_COLOUR_DEVICE_EXTRA          : 'scn';
STROKE_COLOUR_DEVICE_GRAY               : 'G';
NON_STROKE_COLOUR_DEVICE_GRAY           : 'g';
STROKE_COLOUR_DEVICE_RGB                : 'RG';
NON_STROKE_COLOUR_DEVICE_RGB            : 'rg';
STROKE_COLOUR_DEVICE_CMYK               : 'K';
NON_STROKE_COLOUR_DEVICE_CMYK           : 'k';

// Shading Patterns Keywords
SHADING_PATTERN                         : 'sh';

// Inline Images Keywords
BEGIN_INLINE_IMAGE                      : 'BI';
BEGIN_INLINE_IMAGE_DATA                 : 'ID';
END_INLINE_IMAGE                        : 'EI';

// XObject Keywords
XOBJECT                                 : 'Do';

// Marked Content Keywords
MARKED_CONTENT_POINT                    : 'MP';
MARKED_CONTENT_POINT_WITH_PROPERTIES    : 'DP';
BEGIN_MARKED_CONTENT                    : 'BMC';
BEGIN_MARKED_CONTENT_WITH_PROPERTIES    : 'BDC';
END_MARKED_CONTENT                      : 'EMC';

// Compatibility Keywords
BEGIN_COMPATIBILITY_SECTION             : 'BX';
END_COMPATIBILITY_SECTION               : 'EX';

// PDF Constructs
PDF_NAME     : '/' (('#' HEXDIGIT HEXDIGIT) | REGULAR_CHAR )+;

// Delimiters
LPAREN       : '(';
RPAREN       : ')';
LANGLE       : '<';
RANGLE       : '>';
LDOUBLEANGLE : '<<';
RDOUBLEANGLE : '>>';
DOT          : '.';

// Primitive Objects
STRING         : STRING_LITERAL | STRING_HEX;
NUMBER         : INTEGER_NUMBER | REAL_NUMBER;
INTEGER_NUMBER : ('-' | '+' )? DIGIT+;
fragment
REAL_NUMBER    : ('-' | '+' )? ( (DIGIT+'.'DIGIT*) | (DIGIT*'.'DIGIT+) );
fragment
HEXDIGIT       : 'a'..'f' | 'A'..'F' | DIGIT;
DIGIT          : '0'..'9';
COMMENT        : '%' ('\u0000'..'\u0009' | '\u000B' | '\u000C' | '\u000E'..'\uffff' )* -> skip;
STRING_LITERAL : '('
               (
               '\u0000'..'\u0027'
               | ('\u005c' '\u0028')
               | '\u0029'..'\uffff'
               )*
                 ')'
               ;
STRING_HEX     : '<' (HEXDIGIT)+ '>';
REGULAR_CHAR   : ('\u0001'..'\u0008'
               | '\u000B'
               | '\u000E'..'\u0019'
               | '\u0021' ..'\u0024'
               | '\u0026'..'\u0027'
               | '\u002A'..'\u002E'
               | '\u0030'..'\u003B'
               | '\u003D'
               | '\u003F'..'\u005A'
               | '\u005C'
               | '\u005D'..'\u007A'
               | '\u007C'
               | '\u007E'..'\u00ff'
               );
LETTER         : [A-Za-z];
COMPATIBILITY  : BEGIN_COMPATIBILITY_SECTION .*? END_COMPATIBILITY_SECTION;
WS : [ \t\r\n\u0000\u000C]+ -> skip;