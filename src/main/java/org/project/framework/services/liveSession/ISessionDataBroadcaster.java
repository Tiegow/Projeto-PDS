package org.project.framework.services.liveSession;

/**
 * Interface para qualquer provedor de dados que deseja transmitir informações
 * para a live session.
 */
public interface ISessionDataBroadcaster {

    /**
     * Método principal que executa a lógica de busca, processamento e envio.
     * Este método será tipicamente anotado com @Scheduled na implementação.
     */
    void broadcast(Integer sessionKey);
}
