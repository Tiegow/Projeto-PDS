let stompClient = null;
let reconnectDelay = 1000; // Tempo de espera (segundos) antes da próxima tentativa de reconexão

let driversPositions = null;
let raceEvents = null;

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

    updatePositionsInfo();
  });

  // Eventos na pista
  stompClient.subscribe('/topic/race_events', function (message) {
    raceEvents = JSON.parse(message.body);
    console.log(raceEvents);

    updateRaceEventsInfo();
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
    div.className = "position row";

    const divPos = document.createElement('div');
    divPos.className = "col-2";

    const divName = document.createElement('div');
    divName.className = "col";

    const divNumber = document.createElement('div');
    divNumber.className = "col driverNumber";

    divPos.textContent = `${pos}°:`;
    divName.textContent = `${driver.first_name} ${driver.last_name}`;
    divNumber.textContent = `(#${driver.driver_number})`;

    div.appendChild(divPos);
    div.appendChild(divName);
    div.appendChild(divNumber);
    container.appendChild(div);
    pos += 1;
  });
}

// Atualiza as informações sobre os eventos na pista
function updateRaceEventsInfo() {

  const container = document.getElementById('events');
  container.innerHTML = '';
  raceEvents.forEach(event => {
    const div = document.createElement('div');
    div.className = "event";

    const eventTitle = document.createElement('h5');

    const eventSubtitle = document.createElement('span');
    eventSubtitle.className = "subtitle";
    
    const eventTime = document.createElement('p');
    eventTime.className = "time";
    
    const eventMessage = document.createElement('p');
    eventMessage.className = "message";
  
    if (event.category == 'Flag') {
      eventSubtitle.textContent = ` (${event.subTitle})`;
      eventTitle.textContent = `Bandeira: ${event.flag}`;
    }
    else if (event.category == 'Other') {
      eventTitle.textContent = `Evento`;
    }
    else {
      eventTitle.textContent = event.category;
    }

    const date = new Date(event.date);
    eventTime.textContent = date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

    eventMessage.textContent = event.message;
    div.appendChild(eventTitle);
    div.appendChild(eventSubtitle);
    div.appendChild(eventTime);
    div.appendChild(eventMessage);

    container.appendChild(div);
  })
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