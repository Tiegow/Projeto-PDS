let stompClient = null;
let reconnectDelay = 1000; // Tempo de espera (segundos) antes da próxima tentativa de reconexão

let positions; // Mapa com as posições de cada piloto
let driversData; // Lista com os dados de cada piloto na sessão

function connect() {
  const socket = new SockJS('/api/live');
  stompClient = Stomp.over(socket);

  stompClient.connect({}, onConnected, onError);
}

function onConnected() {
  reconnectDelay = 1000; // Reseta o tempo após sucesso de conexão

  // Clima
  stompClient.subscribe('/topic/weather', function (message) {
    const weather = JSON.parse(message.body);

    updateWeatherInfo(weather);
  });

  // Posições
  stompClient.subscribe('/topic/positions', function (message) {
    positions = new Map(Object.entries(JSON.parse(message.body)));

    updatePositionsInfo();
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

// Atualiza as informações sobre o clima na página
function updateWeatherInfo(weather) {
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
}

// Atualiza as informações sobre as posições dos pilotos
function updatePositionsInfo() {
  if (!positions || !driversData) return;

  // Ordena os dados dos pilotos com base na posição atual
  const sortedDrivers = driversData.slice().sort((a, b) => {
    const posA = positions.get(String(a.driverNumber));
    const posB = positions.get(String(b.driverNumber));
    return posA - posB;
  });

  // Atualiza o DOM com os dados ordenados
  const container = document.getElementById('positions');
  container.innerHTML = ''; 

  sortedDrivers.forEach(driver => {
    const pos = positions.get(String(driver.driverNumber));

    const div = document.createElement('div');
    div.textContent = `P${pos}: ${driver.name} (#${driver.driverNumber})`;

    container.appendChild(div);
  });
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