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
tree grammar STTree;

options {
    ASTLabelType = CommonTree;
    tokenVocab = STParser;
}

@header {
package com.jmolly.stacktraceparser.internal;
import java.util.Stack;
import com.jmolly.stacktraceparser.*;
}

@members {
public NStackTrace stackTrace;
Stack<NTrace> traces = new Stack<NTrace>();
}

estack
@init {traces.push(NTrace.start());}
: ^(ESTACK ^(PRELIM ^(THR t=.*) ^(EXC ^(CLS CNAME?) ^(MSG MSGSTR?))) atlines cause?)
{NTrace curr = traces.pop();
curr.setException(NException.create($CNAME.text,$MSGSTR.text));
stackTrace=NStackTrace.withNameAndStack($t==null?"":$t.getText(), curr);};

atlines: ^(ATS atline*);
atline: ^(AT ^(CLS CNAME) ^(METH MNAME) ^(LOC .*))
{traces.peek().push(new NFrame($CNAME.text,$MNAME.text));};

cause
@init {traces.push(NTrace.start());}
: ^(CAUSE ^(EXC ^(CLS CNAME) ^(MSG MSGSTR?)) atlines ^(MORE .*) cause?)
{NTrace curr = traces.pop();
curr.setException(NException.create($CNAME.text,$MSGSTR.text));
traces.peek().setNested(curr);};