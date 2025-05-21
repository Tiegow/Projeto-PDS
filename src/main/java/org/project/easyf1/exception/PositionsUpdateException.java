package org.project.easyf1.exception;

public class PositionsUpdateException extends RuntimeException{
    public PositionsUpdateException() {
        super("Erro ao buscar pelas posições dos pilotos");
    }
}
