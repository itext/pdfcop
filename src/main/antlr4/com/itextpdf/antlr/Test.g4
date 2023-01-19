grammar Test;

comment : COMMENT;

COMMENT : '%' ('\u0000'..'\uffff' )*;

WS : [ \t\r\n]+ -> skip;