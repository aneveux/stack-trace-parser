parser grammar StackTraceParser;

options {
    output = AST;
    tokenVocab = StackTraceLexer;
}

tokens {
ESTACK;
EXC;
MSG;
MSGSTR;
ATS;
CAUSE;
MORE;
CLS;
CNAME;
METH;
MNAME;
LOC;
THR;
}

@header {
package com.jmolly.stacktraceparser.guts;
}

estack: EIT threadname classname msg=message? atlines cause? EOF
-> ^(ESTACK ^(THR threadname) ^(EXC ^(CLS classname) ^(MSG message?)) atlines cause?);

threadname: QS;
atlines: atline* -> ^(ATS atline*);
atline: AT classname DOT methodname location -> ^(AT ^(CLS classname) ^(METH methodname) ^(LOC location));

location: LP sourcefile (COLON NUMBER)? RP;
sourcefile: (NMETH|UNSRC|identifier DOT JAVA);

cause: CB classname message? atlines moreline? cause?
-> ^(CAUSE ^(EXC ^(CLS classname) ^(MSG message?)) atlines ^(MORE moreline?) cause?);

moreline: ELLIPSIES NUMBER MORE;

message: COLON (options {greedy=false;}:.)*
 -> {new CommonTree(new CommonToken(MSGSTR,$message.text))};

classname: (identifier DOT)* identifier (DOLL identifier)?
 -> {new CommonTree(new CommonToken(CNAME,$classname.text))};
methodname: identifier
 -> {new CommonTree(new CommonToken(MNAME,$methodname.text))};

identifier: (IDENTIFIER|AT|IN|MORE|JAVA);