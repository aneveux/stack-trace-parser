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
