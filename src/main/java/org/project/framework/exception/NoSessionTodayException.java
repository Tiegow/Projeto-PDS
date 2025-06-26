package org.project.framework.exception;

public class NoSessionTodayException extends RuntimeException {
    public NoSessionTodayException() {
        super("Não há sessão programada para hoje.");
    }
}
