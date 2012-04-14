package com.jmolly.stacktraceparser;

public final class NFrame {

    private final String className;
    private final String methodName;

    public NFrame(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public String toString() {
        return "Frame(" + className + "#" + methodName + ")";
    }

}
