let stompClient = null;
let reconnectDelay = 1000; // Tempo de espera (segundos) antes da próxima tentativa de reconexão

let driversPositions = null;

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
    driversPositions = JSON.parse(message.body);
    console.log(driversPositions);

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

  const rainField = document.getElementById('rainfallImg');
  if (weather.rainfall != 0) {
    rainField.src = "https://img.icons8.com/?size=200&id=91858&format=png"
  }
}

// Atualiza as informações sobre as posições dos pilotos
function updatePositionsInfo() {

  const container = document.getElementById('positions');
  container.innerHTML = '';
  let pos = 1;
  driversPositions.forEach(driver => {
    const div = document.createElement('div');
    div.textContent = `P${pos}: ${driver.first_name} ${driver.last_name} (#${driver.driver_number})`;

    container.appendChild(div);
    pos += 1;
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