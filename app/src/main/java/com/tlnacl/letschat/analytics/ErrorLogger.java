package com.tlnacl.letschat.analytics;

public interface ErrorLogger {

    void reportError(Throwable throwable, Object... args);

}
