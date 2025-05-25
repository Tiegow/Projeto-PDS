package org.project.easyf1.exception;

public class DriversNotFoundException extends RuntimeException{
    public DriversNotFoundException() {
        super("Erro ao buscar pilotos para esta corrida.");
    }
}
