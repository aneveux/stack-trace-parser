/**
 * Copyright (c) 2012, Aaron Dixon
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice, this list
 *       of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */
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
