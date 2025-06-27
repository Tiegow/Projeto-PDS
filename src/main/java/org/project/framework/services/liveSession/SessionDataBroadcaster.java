package org.project.framework.services.liveSession;

/**
 * Classe para qualquer provedor de dados que deseja transmitir informações
 * para a live session.
 */
public abstract class SessionDataBroadcaster {

    protected Integer sessionKey = null;

    /**
     * Método principal que executa a lógica de busca, processamento e envio.
     * Este método será tipicamente anotado com @Scheduled na implementação.
     */
    public abstract void broadcast();
}
