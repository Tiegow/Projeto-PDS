package org.project.framework.services.liveSession;

import java.util.List;

import org.project.framework.exception.DriversNotFoundException;
import org.project.framework.exception.NoSessionTodayException;
import org.project.framework.models.dto.DriverDTO;
import org.project.framework.providers.DriverProvider;
import org.project.framework.services.TodaySessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class LiveSessionService {
    private final TodaySessionHelper todaySessionHelper;
    private final DriverProvider driverProvider;
    
    // Lista de todos os broadcasters "plugados" no framework.
    private final List<SessionDataBroadcaster> broadcasters;

    // Estado da sessão
    private Integer sessionKey = null;
    private List<DriverDTO> sessionDrivers = null;    

    @Autowired
    public LiveSessionService(TodaySessionHelper todaySessionHelper, DriverProvider driverProvider, List<SessionDataBroadcaster> broadcasters) {
        this.todaySessionHelper = todaySessionHelper;
        this.driverProvider = driverProvider;
        this.broadcasters = broadcasters;
    }    

    @PostConstruct
    public void init() {
        try {
            ensureLive(); 
        } catch (Exception e) {
            sessionKey = null;
            sessionDrivers = null;
        }
    }    

    public void ensureLive() {
        if (isSessionReady()) {
            return;
        }

        try {
            sessionKey = todaySessionHelper.getTodaySession().getSessionKey();
        } catch (Exception e) {
            throw new NoSessionTodayException();
        }

        try {
            sessionDrivers = driverProvider.getDrivers(sessionKey);
        } catch (Exception e) {
            throw new DriversNotFoundException();
        }
    }
    
    public boolean isSessionReady() {
        return sessionKey != null && sessionDrivers != null && !sessionDrivers.isEmpty();
    }
    
    public void broadcastAll() {
        if (!isSessionReady()) {
            init(); // Tenta reiniciar a sessão se não estiver pronta
        }
        if (isSessionReady()) {
            broadcasters.forEach(broadcaster -> broadcaster.broadcast());
        }
    }    
}
