package com.jmolly.stacktraceparser;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

final class AntlrUtils {

    private AntlrUtils() {}

    static void printTokenStream(CommonTokenStream tokens, Map tokenToName) {
        TokenSource source = tokens.getTokenSource();
        Token curr;
        do {
            curr = source.nextToken();
            System.out.println(curr.getText() + " [" + tokenToName.get(String.valueOf(curr.getType())) + "]");
        } while (-1 != curr.getType() ); // eof
    }

    static Map loadAntlrTokens(String resource) {
        InputStream in = AntlrUtils.class.getResourceAsStream("/" + resource + ".tokens");
        Properties p = new Properties();
        try {
            p.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return transpose(p);
    }

    private static Map transpose(Properties p) {
        Map map = new HashMap();
        for (Object k : p.keySet()) {
            map.put(p.get(k), k);
        }
        return map;
    }

}
