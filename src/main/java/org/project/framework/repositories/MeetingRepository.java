package org.project.framework.repositories;

import java.util.List;

import org.project.easyf1.models.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório responsável pela interação com a tabela de eventos (Meetings) no banco de dados.
 * 
 * Extende a interface JpaRepository para fornecer operações básicas de persistência para a entidade 
 * "Meeting". Além disso, inclui métodos personalizados para buscar o evento mais recente e listar eventos por ano.
 */
@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer>{

    Meeting findFirstByOrderByStartDateDesc();

    List<Meeting> findAllByYear(Integer year);
}