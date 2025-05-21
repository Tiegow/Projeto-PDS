package org.project.easyf1.exception;

public class EventsNotFountException extends RuntimeException{
    public EventsNotFountException() {
        super("Erro ao buscar por eventos de pista.");
    }
}
