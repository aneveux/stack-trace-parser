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
package test.stacktraceparser;

import com.jmolly.stacktraceparser.NStackTrace;
import com.jmolly.stacktraceparser.StackTraceParser;
import org.antlr.runtime.RecognitionException;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

@Test
public class ParserTest {

    public void testBasic() throws IOException, RecognitionException {
        NStackTrace result = StackTraceParser.parse(loadStack("Basic.txt"));
        assertEquals(result.getThreadName(), "main");
        assertEquals(result.getTrace().getException().getClassName(), "java.lang.RuntimeException");
        assertEquals(result.getTrace().getException().getMessage(), ": java.lang.reflect.InvocationTargetException");
        assertEquals(result.getTrace().getFrames().get(0).getClassName(), "test.a.pkg.Dog");
        assertEquals(result.getTrace().getFrames().get(0).getMethodName(), "bark");
        assertEquals(result.getTrace().getFrames().get(4).getClassName(), "sun.reflect.NativeMethodAccessorImpl");
        assertEquals(result.getTrace().getFrames().get(4).getMethodName(), "invoke0");
        assertEquals(result.getTrace().getFrames().get(8).getClassName(), "com.intellij.rt.execution.application.AppMain");
        assertEquals(result.getTrace().getFrames().get(8).getMethodName(), "main");
        assertEquals(result.getTrace().getFrames().size(), 9);
        assertEquals(result.getTrace().getNested().getException().getClassName(), "java.lang.reflect.InvocationTargetException");
        assertEquals(result.getTrace().getNested().getException().getMessage(), null);
        assertEquals(result.getTrace().getNested().getFrames().size(), 6);
        assertEquals(result.getTrace().getNested().getNested().getException().getClassName(), "java.lang.IllegalStateException");
        assertEquals(result.getTrace().getNested().getNested().getException().getMessage(), ": You tried to spin.");
        assertEquals(result.getTrace().getNested().getNested().getFrames().get(0).getClassName(), "Cobby");
        assertEquals(result.getTrace().getNested().getNested().getFrames().get(0).getMethodName(), "spin");
        assertEquals(result.getTrace().getNested().getNested().getFrames().size(), 1);
        assertEquals(result.getTrace().getNested().getNested().getNested().getException().getClassName(), "java.lang.RuntimeException");
        assertEquals(result.getTrace().getNested().getNested().getNested().getException().getMessage(), ": can't zap right now");
        assertEquals(result.getTrace().getNested().getNested().getNested().getFrames().size(), 3);
        assertEquals(result.getTrace().getNested().getNested().getNested().getNested(), null);
    }

    public void testIllformed() throws RecognitionException {
        NStackTrace result = StackTraceParser.parse(loadStack("Illformed.txt"));
        assertEquals(result.getThreadName(), "");
        assertEquals(result.getTrace().getException().getClassName(), "ERROR");
        assertEquals(result.getTrace().getException().getMessage(), null);
        assertEquals(result.getTrace().getFrames().get(0).getClassName(), "org.random.Pillariterator.RRailDiceIterator");
        assertEquals(result.getTrace().getFrames().get(0).getMethodName(), "<init>");
        assertEquals(result.getTrace().getFrames().get(8).getClassName(), "org.random.Table");
        assertEquals(result.getTrace().getFrames().get(8).getMethodName(), "getRow");
        assertEquals(result.getTrace().getFrames().size(), 15);
    }

    public void testLongest() throws IOException, RecognitionException {
        NStackTrace result = StackTraceParser.parse(loadStack("Longest.txt"));
        assertEquals(result.getThreadName(), "AWT-EventQueue-0");
        assertEquals(result.getTrace().getException().getClassName(), "java.lang.StackOverflowError");
        assertEquals(result.getTrace().getException().getMessage(), null);
        assertEquals(result.getTrace().getFrames().get(0).getClassName(), "java.util.IdentityHashMap");
        assertEquals(result.getTrace().getFrames().get(0).getMethodName(), "get");
        int last = result.getTrace().getFrames().size() - 1;
        assertEquals(result.getTrace().getFrames().get(last).getClassName(), "clojure.lang.Reflector");
        assertEquals(result.getTrace().getFrames().get(last).getMethodName(), "invokeMatchingMethod");
    }

    public void testSparse() throws IOException, RecognitionException {
        NStackTrace result = StackTraceParser.parse(loadStack("Sparse.txt"));
        assertEquals(result.getThreadName(), "");
        assertEquals(result.getTrace().getException().getClassName(), "java.lang.RuntimeException");
        assertEquals(result.getTrace().getException().getMessage(), null);
        assertEquals(result.getTrace().getFrames().get(0).getClassName(), "test.a.pkg.Dog");
        assertEquals(result.getTrace().getFrames().get(0).getMethodName(), "bark");
        assertEquals(result.getTrace().getFrames().get(4).getClassName(), "sun.reflect.NativeMethodAccessorImpl");
        assertEquals(result.getTrace().getFrames().get(4).getMethodName(), "invoke0");
        assertEquals(result.getTrace().getFrames().get(8).getClassName(), "com.intellij.rt.execution.application.AppMain");
        assertEquals(result.getTrace().getFrames().get(8).getMethodName(), "main");
        assertEquals(result.getTrace().getFrames().size(), 9);
        assertEquals(result.getTrace().getNested().getException().getClassName(), "java.lang.reflect.InvocationTargetException");
        assertEquals(result.getTrace().getNested().getException().getMessage(), null);
        assertEquals(result.getTrace().getNested().getFrames().size(), 6);
        assertEquals(result.getTrace().getNested().getNested().getException().getClassName(), "java.lang.IllegalStateException");
        assertEquals(result.getTrace().getNested().getNested().getException().getMessage(), ": You tried to spin.");
        assertEquals(result.getTrace().getNested().getNested().getFrames().get(0).getClassName(), "Cobby");
        assertEquals(result.getTrace().getNested().getNested().getFrames().get(0).getMethodName(), "spin");
        assertEquals(result.getTrace().getNested().getNested().getFrames().size(), 1);
        assertEquals(result.getTrace().getNested().getNested().getNested().getException().getClassName(), "java.lang.RuntimeException");
        assertEquals(result.getTrace().getNested().getNested().getNested().getException().getMessage(), ": can't zap right now");
        assertEquals(result.getTrace().getNested().getNested().getNested().getFrames().size(), 3);
        assertEquals(result.getTrace().getNested().getNested().getNested().getNested(), null);
    }

    public void testWithInitializers() throws IOException, RecognitionException {
        NStackTrace result = StackTraceParser.parse(loadStack("WithInitializers.txt"));
        assertEquals(result.getThreadName(), "");
        assertEquals(result.getTrace().getException().getClassName(), "java.lang.ClassCircularityError");
        assertEquals(result.getTrace().getException().getMessage(), ": jmolly/editorimpl/response2/consumer/ExceptionResponse");
        assertEquals(result.getTrace().getFrames().get(0).getClassName(), "jmolly.editorimpl.response2.consumer.ResponseManagerImpl");
        assertEquals(result.getTrace().getFrames().get(0).getMethodName(), "reportExceptionImpl");
        int last = result.getTrace().getFrames().size() - 1;
        assertEquals(result.getTrace().getFrames().get(last).getClassName(), "java.awt.EventDispatchThread");
        assertEquals(result.getTrace().getFrames().get(last).getMethodName(), "run");
    }
    
    private String loadStack(String resource) {
        try {
            return IOUtils.toString(getClass().getResourceAsStream("stacks/" + resource));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
