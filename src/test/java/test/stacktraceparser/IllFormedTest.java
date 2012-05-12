package test.stacktraceparser;

import com.jmolly.stacktraceparser.NStackTrace;
import com.jmolly.stacktraceparser.StackTraceParser;
import org.antlr.runtime.RecognitionException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class IllformedTest {

    public static void testParser() throws RecognitionException {
        NStackTrace result = StackTraceParser.parse(Utils.loadString("Illformed.txt"));
        assertEquals(result.getThreadName(), "");
        assertEquals(result.getTrace().getException().getClassName(), "ERROR");
        assertEquals(result.getTrace().getException().getMessage(), null);
        assertEquals(result.getTrace().getFrames().get(0).getClassName(), "org.random.Pillariterator.RRailDiceIterator");
        assertEquals(result.getTrace().getFrames().get(0).getMethodName(), "<init>");
        assertEquals(result.getTrace().getFrames().get(8).getClassName(), "org.random.Table");
        assertEquals(result.getTrace().getFrames().get(8).getMethodName(), "getRow");
        assertEquals(result.getTrace().getFrames().size(), 15);
    }

}
