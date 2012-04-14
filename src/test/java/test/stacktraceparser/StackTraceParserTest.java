package test.stacktraceparser;

import com.jmolly.stacktraceparser.NStackTrace;
import com.jmolly.stacktraceparser.StackTraceParser;
import org.antlr.runtime.RecognitionException;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

@Test
public class StackTraceParserTest {

    // for ides that don't support testng
    public static void main(String[] args) throws Exception {
        new StackTraceParserTest().testParser();
    }

    public void testParser() throws IOException, RecognitionException {
        NStackTrace result = StackTraceParser.parse(loadString("StackTrace.txt"));
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

    private static String loadString(String resource) {
        try {
            return IOUtils.toString(StackTraceParserTest.class.getResourceAsStream(resource));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
