package com.jmolly.stacktraceparser;

import java.lang.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class NTrace {

    public static NTrace start() {
        return new NTrace();
    }

    private NTrace nested;
    private NException exception;
    private final List<NFrame> frames = new ArrayList<NFrame>();

    private NTrace() {}

    public void push(NFrame frame) {
        frames.add(frame);
    }

    public NTrace getNested() {
        return nested;
    }

    public void setNested(NTrace nested) {
        this.nested = nested;
    }

    public List<NFrame> getFrames() {
        return Collections.unmodifiableList(frames);
    }

    public NException getException() {
        return exception;
    }

    public void setException(NException exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "Trace(" + exception + ",\n" + frames + ",\n" + nested + ")";
    }

}
