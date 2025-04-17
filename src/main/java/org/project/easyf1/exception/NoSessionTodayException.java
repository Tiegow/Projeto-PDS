package org.project.easyf1.exception;

public class NoSessionTodayException extends RuntimeException {
    public NoSessionTodayException() {
        super("Não há sessão programada para hoje.");
    }
}
