window.onload = () => {
    loadMainComponents();
    loadAllDrivers();
}

async function loadAllDrivers(){

    const container = document.getElementById('pilotos-container');

    try {
        const response = await fetch(`/api/driver/lastSession`);
        if (!response.ok) {
            throw new Error(`Erro ao buscar pilotos: ${response.status}`);
        }
        const data = await response.json();
        renderDrivers(data);
    } catch (error) {
        console.error('Erro na requisição:', error);
    }
}

function renderDrivers(data) {
    const container = document.getElementById('pilotos-container');
    const detalhes = document.getElementById('piloto-detalhes');

    fetch('/components/pilotoCard.html')
        .then(res => res.text())
        .then(html => {
            data.forEach(driver => {
                const temp = document.createElement('div');
                temp.innerHTML = html;
                const card = temp.firstElementChild;
                card.className = 'piloto-card mx-1';

                const teamColor = driver.team_colour;

                const darkerColor = createDarkerColor(teamColor);

                card.style.background = `linear-gradient(to right, #${teamColor}, ${darkerColor})`;

                const img = card.querySelector('img');
                img.src = driver.headshot_url;
                img.alt = driver.broadcast_name;

                const nomePiloto = card.querySelector('.piloto-info h5');
                nomePiloto.textContent = driver.first_name + ' ' + driver.last_name;

                const pilotoEquipe = card.querySelectorAll('.piloto-info p')[0];
                pilotoEquipe.textContent = `${driver.broadcast_name} - ${driver.team_name}`;

                const pilotoNumero = card.querySelectorAll('.piloto-info p')[1];
                pilotoNumero.textContent = `n°: ${driver.driver_number}`;

                const pilotoPais = card.querySelectorAll('.piloto-info p')[2];
                pilotoPais.textContent = `País: ${driver.country_code}`;

                card.addEventListener('click', () => {
                    mostrarDetalhes(driver.driver_number);
                });

                container.appendChild(card);
            });
        })
        .catch(err => console.error('Erro ao carregar pilotoCard:', err));
}

function mostrarDetalhes(driver_number) {
    // Referência ao elemento de detalhes
    const detalhes = document.getElementById('piloto-detalhes');

    // Mostrar um estado de carregamento
    detalhes.innerHTML = `
        <div class="text-center p-5">
            <div class="spinner-border text-danger" role="status">
                <span class="visually-hidden">Carregando...</span>
            </div>
            <p class="mt-3">Buscando detalhes do piloto...</p>
        </div>
    `;

    fetch(`/api/driver/details?driver_number=${driver_number}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Falha ao buscar detalhes do piloto');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            const teamColor = data.team_colour;
            const lighterColor = createDarkerColor(teamColor);
            detalhes.innerHTML = `
                <div class="card shadow rounded-4 border-0 overflow-hidden">
                    <div class="card-header p-4" style="background: linear-gradient(to right, #${teamColor}, ${lighterColor});">
                        <div class="d-flex align-items-center gap-4">
                            <img src="${data.headshot_url}" class="rounded-circle" style="width:120px; height:120px; object-fit:cover; border:3px solid white; box-shadow: 0 3px 10px rgba(0,0,0,0.2);">
                            <div>
                                <h2 class="mb-0 fw-bold">${data.first_name} ${data.last_name}</h2>
                                <p class="fs-5 mb-1">${data.team_name}</p>
                                <span class="badge bg-dark fs-6">Nº ${data.driver_number}</span>
                                <span class="badge bg-secondary fs-6">${data.country_code}</span>
                            </div>
                        </div>
                    </div>
                    
                    <div class="card-body p-4">
                        <div class="row mb-4">
                            <div class="col-md-6">
                                <div class="card mb-3 bg-light">
                                    <div class="card-body">
                                        <h4 class="mb-3">Desempenho na Temporada</h4>
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Posição Atual:</span>
                                            <strong>${data.championship_position}º</strong>
                                        </div>
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Pontuação:</span>
                                            <strong>${data.points} pts</strong>
                                        </div>
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Melhor Posição:</span>
                                            <strong>${data.best_position}º</strong>
                                        </div>
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Pior Posição:</span>
                                            <strong>${data.worst_position}º</strong>
                                        </div>
                                        <div class="d-flex justify-content-between">
                                            <span>Vitórias:</span>
                                            <strong>${data.victories}</strong>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="col-md-6">
                                <div class="card mb-3 bg-light">
                                    <div class="card-body">
                                        <h4 class="mb-3">Dados do Carro</h4>
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Modelo:</span>
                                            <strong>${data.car_model}</strong>
                                        </div>
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Motor:</span>
                                            <strong>${data.engine}</strong>
                                        </div>
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Potência:</span>
                                            <strong>${data.power_hp} HP</strong>
                                        </div>
                                        <div class="d-flex justify-content-between">
                                            <span>Peso:</span>
                                            <strong>${data.weight_kg} kg</strong>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="card bg-light">
                            <div class="card-body">
                                <h4 class="mb-3">Sobre a Equipe</h4>
                                <div class="d-flex justify-content-between mb-2">
                                    <span>Nome Completo:</span>
                                    <strong>${data.team_full_name}</strong>
                                </div>
                                <div class="d-flex justify-content-between mb-2">
                                    <span>Base:</span>
                                    <strong>${data.team_base}</strong>
                                </div>
                                <div class="d-flex justify-content-between mb-2">
                                    <span>Chefe de Equipe:</span>
                                    <strong>${data.team_principal}</strong>
                                </div>
                                <div class="d-flex justify-content-between mb-2">
                                    <span>Posição no Campeonato de Construtores:</span>
                                    <strong>${data.team_championship_position}º</strong>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            `;
        })
        .catch(error => {
            console.error('Erro:', error);
            detalhes.innerHTML = `
                <div class="alert alert-danger p-4">
                    <h4>Erro ao carregar detalhes</h4>
                    <p>Não foi possível carregar os detalhes do piloto. Por favor, tente novamente mais tarde.</p>
                    <button class="btn btn-outline-danger" onclick="location.reload()">Tentar novamente</button>
                </div>
            `;
        });
}

function createDarkerColor(hexColor) {
    // Converter hex para RGB
    const r = parseInt(hexColor.substring(0, 2), 16);
    const g = parseInt(hexColor.substring(2, 4), 16);
    const b = parseInt(hexColor.substring(4, 6), 16);

    // Calcular versão mais escura (reduzindo cada componente em 15%)
    const darkerR = Math.floor(r * 0.85);
    const darkerG = Math.floor(g * 0.85);
    const darkerB = Math.floor(b * 0.85);

    // Retornar a cor em formato RGB
    return `rgb(${darkerR}, ${darkerG}, ${darkerB})`;
}