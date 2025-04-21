package org.project.easyf1.repositories;

import java.time.OffsetDateTime;
import java.util.List;

import org.project.easyf1.models.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import feign.Param;

/**
 * Repositório responsável pela interação com a tabela de sessões (Sessions) no banco de dados.
 * 
 * Extende a interface JpaRepository para fornecer operações básicas de persistência para a entidade 
 * "Session". Além disso, inclui métodos personalizados para:
 * - Buscar a sessão mais recente.
 * - Listar todas as sessões de um evento específico (identificada pela chave do evento).
 * - Buscar a sessão mais recente de um evento específico.
 * - Buscar a sessão do dia, levando em consideração um intervalo de datas.
 */
@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findFirstByOrderByEndDateDesc();

    List<Session> findAllByMeetingKey(Integer meetingKey);

    Session findFirstByMeetingKeyOrderByEndDateDesc(Integer meetingKey);

    @Query("""
      SELECT s FROM Session s
      WHERE s.startDate >= :startOfDay
        AND s.startDate < :startOfNextDay
        AND s.endDate >= :now
      ORDER BY s.startDate ASC
      """)
      Session findTodaySession(
          @Param("startOfDay") OffsetDateTime startOfDay,
          @Param("startOfNextDay") OffsetDateTime startOfNextDay,
          @Param("now") OffsetDateTime now
      );
}