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
