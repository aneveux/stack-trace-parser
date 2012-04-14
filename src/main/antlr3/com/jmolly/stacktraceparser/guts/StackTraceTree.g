tree grammar StackTraceTree;

options {
    ASTLabelType = CommonTree;
    tokenVocab = StackTraceParser;
}

@header {
package com.jmolly.stacktraceparser.guts;
import java.util.Stack;
import com.jmolly.stacktraceparser.*;
}

@members {
public NStackTrace stackTrace;
Stack<NTrace> traces = new Stack<NTrace>();
}

estack
@init {traces.push(NTrace.start());}
: ^(ESTACK ^(THR t=.*) ^(EXC ^(CLS CNAME) ^(MSG MSGSTR?)) atlines cause?)
{NTrace curr = traces.pop();
curr.setException(NException.create($CNAME.text,$MSGSTR.text));
stackTrace=NStackTrace.withNameAndStack($t.getText(), curr);};

atlines: ^(ATS atline*);
atline: ^(AT ^(CLS CNAME) ^(METH MNAME) ^(LOC .*))
{traces.peek().push(new NFrame($CNAME.text,$MNAME.text));};

cause
@init {traces.push(NTrace.start());}
: ^(CAUSE ^(EXC ^(CLS CNAME) ^(MSG MSGSTR?)) atlines ^(MORE .*) cause?)
{NTrace curr = traces.pop();
curr.setException(NException.create($CNAME.text,$MSGSTR.text));
traces.peek().setNested(curr);};