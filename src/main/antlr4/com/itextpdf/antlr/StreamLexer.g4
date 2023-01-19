lexer grammar StreamLexer;

// Keywords
// General Graphics State Keywords
LINE_WIDTH                              : 'w';
LINCE_CAP                               : 'J';
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
FILL_PATH_NON_ZERO                      : 'F';
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




















// Literals

DECIMAL_LITERAL:    ('0' | [1-9] (Digits? | '_'+ Digits)) [lL]?;
HEX_LITERAL:        '0' [xX] [0-9a-fA-F] ([0-9a-fA-F_]* [0-9a-fA-F])? [lL]?;
OCT_LITERAL:        '0' '_'* [0-7] ([0-7_]* [0-7])? [lL]?;
BINARY_LITERAL:     '0' [bB] [01] ([01_]* [01])? [lL]?;

FLOAT_LITERAL:      (Digits '.' Digits? | '.' Digits) ExponentPart? [fFdD]?
             |       Digits (ExponentPart [fFdD]? | [fFdD])
             ;

HEX_FLOAT_LITERAL:  '0' [xX] (HexDigits '.'? | HexDigits? '.' HexDigits) [pP] [+-]? Digits [fFdD]?;

BOOL_LITERAL:       'true'
            |       'false'
            ;

CHAR_LITERAL:       '\'' (~['\\\r\n] | EscapeSequence) '\'';

STRING_LITERAL:     '"' (~["\\\r\n] | EscapeSequence)* '"';

NULL_LITERAL:       'null';


// Separators
LPAREN       : '(';
RPAREN       : ')';
LANGLE       : '<';
RANGLE       : '>';
LDOUBLEANGLE : '<<';
RDOUBLEANGLE : '>>';
LBRACK       : '[';
RBRACK       : ']';
DOT          : '.';


// Whitespace and comments

WS:                 [ \t\r\n\u000C]+ -> channel(HIDDEN);
COMMENT:            '%' .*? '*/'    -> channel(HIDDEN);

// Identifiers

IDENTIFIER:         Letter LetterOrDigit*;

// Fragment rules

fragment ExponentPart
    : [eE] [+-]? Digits
    ;

fragment EscapeSequence
    : '\\' [btnfr"'\\]
    | '\\' ([0-3]? [0-7])? [0-7]
    | '\\' 'u'+ HexDigit HexDigit HexDigit HexDigit
    ;

fragment HexDigits
    : HexDigit ((HexDigit | '_')* HexDigit)?
    ;

fragment HexDigit
    : [0-9a-fA-F]
    ;

fragment Digits
    : [0-9] ([0-9_]* [0-9])?
    ;

fragment LetterOrDigit
    : Letter
    | [0-9]
    ;

fragment Letter
    : [a-zA-Z$_] // these are the "java letters" below 0x7F
    | ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
    | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
    ;
