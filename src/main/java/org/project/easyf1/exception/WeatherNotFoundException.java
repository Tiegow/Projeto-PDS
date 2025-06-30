package org.project.easyf1.exception;

public class WeatherNotFoundException extends RuntimeException{
    public WeatherNotFoundException() {
        super("Erro ao buscar informações climáticas nesta corrida.");
    }
}
