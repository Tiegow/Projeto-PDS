let stompClient = null;
let reconnectDelay = 1000; // Tempo de espera (segundos) antes da próxima tentativa de reconexão

function connect() {
    const socket = new SockJS('/api/live');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    reconnectDelay = 1000; // Reseta o tempo após sucesso de conexão

    stompClient.subscribe('/topic/weather', function (message) {
        const weather = JSON.parse(message.body);

        document.getElementById('airTemperature').textContent = weather.air_temperature;
        document.getElementById('trackTemperature').textContent = weather.track_temperature;
        document.getElementById('humidity').textContent = weather.humidity;
        document.getElementById('pressure').textContent = weather.pressure;
        document.getElementById('windSpeed').textContent = weather.wind_speed;

        const rainField = document.getElementById('rainfall');
        if (weather.rainfall == 0) {
            rainField.textContent = "Sem Chuva";
        } else {
            rainField.textContent = "Chovendo";
        }
    });

    // Requisição inicial dos dados
    fetch('/api/liveSession/sendLatest', {method: 'POST'});
}

function onError(error) {
  console.warn('Erro na conexão websocket:', error);
  scheduleReconnect();
}

// Agenda uma nova tentativa de conexão
function scheduleReconnect() {
  setTimeout(() => {
    console.log('Tentando reconectar WebSocket...');
    connect(); // Tenta conectar de novo

    // aumenta o tempo até o limite de 30s
    reconnectDelay = Math.min(reconnectDelay * 2, 30000);
  }, reconnectDelay);
}

// Detecta queda de rede
window.addEventListener('offline', () => {
  console.warn('Conexão de rede perdida. Tentando reconectar...');
  scheduleReconnect();
});

// Inicia a conexão com websocket
connect();

window.onload = () => {
    loadMainComponents();
}