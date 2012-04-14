Stack Trace Parser
==================

Parses Java stack traces and produces a simple Java object model representing the stack trace.

Use
---

import com.jmolly.stacktraceparser.StackTraceParser;
...
String stackTrace = "...";
NStackTrace result = StackTraceParser.parse(stackTrace);

Build
-----

mvn clean package