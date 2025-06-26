package org.project.framework.exception;

public class PositionsUpdateException extends RuntimeException{
    public PositionsUpdateException() {
        super("Erro ao buscar pelas posições dos pilotos");
    }
}
