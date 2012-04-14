package com.jmolly.stacktraceparser;

public final class NException {

    public static NException create(String className, String message) {
        return new NException(className, message);
    }

    private final String className;
    private final String message;

    private NException(String className, String message) {
        this.className = className;
        this.message = message;
    }

    public String getClassName() {
        return className;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Exception(" + className + "," + message + ")";
    }

}
