package org.project.framework.exception;

public class WeatherNotFoundException extends RuntimeException{
    public WeatherNotFoundException() {
        super("Erro ao buscar informações climáticas nesta corrida.");
    }
}
