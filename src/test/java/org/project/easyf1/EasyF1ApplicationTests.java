package org.project.easyf1;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.project.framework.models.entity.Session;
import org.project.framework.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EasyF1ApplicationTests {

	@Autowired
    private SessionRepository sessionRepository;

    @Test
    void testFindLastSession() {
        Session session = sessionRepository.findFirstByOrderByEndDateDesc();
        System.out.println("Sessão retornada no teste: " + session.getSessionKey());
        assertNotNull(session);
    }
}
