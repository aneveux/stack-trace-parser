lexer grammar StackTraceLexer;

@header {
package com.jmolly.stacktraceparser.guts;
}

ELLIPSIES:'...';
COLON:':';
DOT:'.';
LP:'(';
RP:')';
DOLL:'$';
EIT:'Exception in thread';
CB:'Caused by:';
NMETH:'Native Method';
UNSRC:'Unknown Source';
INIT:'<init>';
AT:'at';
IN:'in';
MORE:'more';
JAVA:'java';

QS:'"' .* '"' {setText(getText().substring(1, getText().length()-1));};

WSS:WS+ {$channel=HIDDEN;};
NUMBER:Digit+;
IDENTIFIER:Identifier;
CHAR:~WS;

fragment WS:(' '|'\t'|'\n'|'\r');
fragment Upper:'A'..'Z';
fragment Lower:'a'..'z';
fragment Digit:'0'..'9';

fragment Identifier:IdentifierStart IdentifierPart*;
fragment IdentifierStart:Upper|Lower|'_';
fragment IdentifierPart:Upper|Lower|Digit|'_';
