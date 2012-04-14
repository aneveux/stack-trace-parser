package com.jmolly.stacktraceparser;

import com.jmolly.stacktraceparser.guts.StackTraceLexer;
import com.jmolly.stacktraceparser.guts.StackTraceTree;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

public final class StackTraceParser {

    public static NStackTrace parse(String sentence) throws RecognitionException {
        StackTraceLexer lexer = new StackTraceLexer(new ANTLRStringStream(sentence));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        com.jmolly.stacktraceparser.guts.StackTraceParser parser = new com.jmolly.stacktraceparser.guts.StackTraceParser(tokens);
        com.jmolly.stacktraceparser.guts.StackTraceParser.estack_return presult = parser.estack();
        CommonTree asTree = (CommonTree) presult.getTree();

        StackTraceTree tree = new StackTraceTree(new CommonTreeNodeStream(asTree));
        tree.estack();

        return tree.stackTrace;
    }

    private StackTraceParser() {}

}
