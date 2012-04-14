package com.jmolly.stacktraceparser;

public final class NStackTrace {

    public static NStackTrace withNameAndStack(String threadName, NTrace stack) {
        return new NStackTrace(threadName, stack);
    }

    private final String threadName;
    private final NTrace trace;

    private NStackTrace(String threadName, NTrace stack) {
        this.threadName = threadName;
        this.trace = stack;
    }

    public String getThreadName() {
        return threadName;
    }

    public NTrace getTrace() {
        return trace;
    }

    @Override
    public String toString() {
        return "Thread(" + threadName + "," + trace + ")";
    }

}
