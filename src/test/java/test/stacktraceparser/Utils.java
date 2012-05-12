package test.stacktraceparser;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

final class Utils {

    private Utils() {}

    static String loadString(String resource) {
        try {
            return IOUtils.toString(LongestTest.class.getResourceAsStream(resource));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
