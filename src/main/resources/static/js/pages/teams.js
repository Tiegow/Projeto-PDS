window.onload = async () => {
    loadMainComponents();
    await loadUserFavorites();
    loadAllTeams();
};

// let userFavorites = [];

let userFavoriteTeams = [];

async function loadAllTeams() {
    const container = document.getElementById('pilotos-container');
    if (!container) {
        console.error('Elemento com ID "pilotos-container" não encontrado no DOM.');
        return;
    }
    container.innerHTML = '<p class="text-white text-center">Carregando times...</p>';

    try {
        const response = await fetch('/api/teams/all');
        if (!response.ok) {
            throw new Error(`Erro HTTP ${response.status}: ${response.statusText}`);
        }
        const teamsData = await response.json();

        if (!teamsData || teamsData.length === 0) {
            container.innerHTML = '<p class="text-white text-center">Nenhum time encontrado.</p>';
            return;
        }
        renderTeams(teamsData, container);
    } catch (error) {
        console.error('Erro ao carregar os times:', error);
        container.innerHTML = `<p class="text-danger text-center">Falha ao carregar times. Verifique o console para detalhes.<br>${error.message}</p>`;
    }
}

function renderTeams(teamsData, container) {
    const detalhesContainer = document.getElementById('times-detalhes');
    if (!detalhesContainer) {
        console.warn('Elemento com ID "times-detalhes" para mostrar detalhes não encontrado.');
    }

    fetch('/components/teamCard.html')
        .then(res => {
            if (!res.ok) {
                throw new Error(`Erro ao carregar teamCard.html: ${res.status} ${res.statusText}`);
            }
            return res.text();
        })
        .then(htmlTemplate => {
            container.innerHTML = '';
            teamsData.forEach(team => {
                const tempDiv = document.createElement('div');
                tempDiv.innerHTML = htmlTemplate;
                const card = tempDiv.firstElementChild;

                if (!card) {
                    console.error('Template teamCard.html não gerou um elemento válido para o time:', team.teamName);
                    return;
                }

                card.className = 'time-card mx-1';

                const baseColor = team.teamColor || "#222";
                const darkerGradientColor = createDarkerColor(baseColor, -25);

                // card.style.background = `linear-gradient(to right, ${baseColor}, ${darkerGradientColor})`;
                card.style.backgroundColor = baseColor;

                buildStarIcon(team, card);

                const nomeTimeEl = card.querySelector('.time-info h5');
                if (nomeTimeEl) nomeTimeEl.textContent = team.teamName || 'Nome Indisponível';

                const infoParagraphs = card.querySelectorAll('.time-info p');
                if (infoParagraphs[0]) infoParagraphs[0].textContent = `Piloto 1: #${team.firstDriverNumber ?? 'N/A'}`;
                if (infoParagraphs[1]) infoParagraphs[1].textContent = `Piloto 2: #${team.secondDriverNumber ?? 'N/A'}`;

                if (detalhesContainer && team.teamName) {
                    card.addEventListener('click', () => {
                        fetchTeamDetails(team.teamName, baseColor);
                        loadDrivers()
                    });
                } else if (!team.teamName) {
                    console.warn('Time sem nome, não é possível adicionar listener para detalhes:', team);
                }

                container.appendChild(card);
            });
        })
        .catch(error => {
            console.error('Erro:', error);
            if (typeof detalhes !== "undefined") {
                detalhes.innerHTML = `
                <div class="alert alert-danger p-4">
                    <h4>Erro ao carregar detalhes do time</h4>
                    <p>Não foi possível carregar os detalhes do time. Tente novamente mais tarde.</p>
                    <button class="btn btn-outline-danger" onclick="location.reload()">Tentar novamente</button>
                </div>
            `;
            }
        });
}

function fetchTeamDetails(teamName, teamColor) {
    fetch(`/api/teams/details?team_name=${teamName}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Falha ao buscar detalhes do time');
            }
            return response.json();
        })
        .then(data => {
            const drivers = [data.driver1, data.driver2];
            return loadDrivers(drivers).then(driverCardsHtml => {
                document.getElementById('times-detalhes').innerHTML = `
                    <div class="card shadow rounded-4 border-0 overflow-hidden">
                        <div class="card-header p-4" style="background: ${teamColor};">
                            <h2 class="mb-0 fw-bold text-white">${data.team_name || 'Nome não disponível'}</h2>
                        </div>
                        <div class="card-body p-4 bg-light">
                            <div class="mb-3"><strong>Base:</strong> ${data.team_base || 'N/D'}</div>
                            <div class="mb-3"><strong>Chefe de Equipe:</strong> ${data.team_principal || 'N/D'}</div>
                            <div class="mb-3"><strong>Posição no Campeonato:</strong> ${data.team_championship_position !== undefined ? data.team_championship_position + 'º' : 'N/D'}</div>
                            <div class="mb-3"><strong>Vitórias na Temporada:</strong> ${data.victories !== null ? data.victories : 'N/D'}</div>
                            <div id="pilotos-container" class="d-flex flex-wrap gap-3 mt-4 justify-content-center">
                                ${driverCardsHtml}
                            </div>
                        </div>
                    </div>
                `;
            });
        })
        .catch(error => {
            console.error('Erro:', error);
        });
}

function loadDrivers(drivers) {
    return fetch('/components/pilotoCard.html')
        .then(res => res.text())
        .then(html => {
            let cardsHtml = '';
            drivers.forEach(driver => {
                const temp = document.createElement('div');
                temp.innerHTML = html;
                const card = temp.firstElementChild;
                card.className = 'piloto-card mx-1';

                const teamColor = driver.team_colour;
                const darkerColor = createDarkerColor(teamColor);

                const favIcon = card.querySelector('.favorite-star');
                if (favIcon) {
                    favIcon.remove();
                }

                card.style.background = `linear-gradient(to right, #${teamColor}, ${darkerColor})`;

                const img = card.querySelector('.piloto-img');
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
                    window.location.href = `/pilotos/${pilotoEquipe}`;
                });

                cardsHtml += card.outerHTML;
            });

            return cardsHtml;
        });
}



function createDarkerColor(hexColor) {

    const r = parseInt(hexColor.substring(0, 2), 16);
    const g = parseInt(hexColor.substring(2, 4), 16);
    const b = parseInt(hexColor.substring(4, 6), 16);

    const darkerR = Math.floor(r * 0.85);
    const darkerG = Math.floor(g * 0.85);
    const darkerB = Math.floor(b * 0.85);

    return `rgb(${darkerR}, ${darkerG}, ${darkerB})`;
}

function buildStarIcon(team, card) {
    const favoriteStar = card.querySelector('.favorite-star');
    let isFavoriteTeam;

    if (userFavoriteTeams.some(fav => fav.id === team.id)) {
        favoriteStar.src = '/images/star.png';
        isFavoriteTeam = true;
    } else {
        favoriteStar.src = '/images/star_g.png';
        isFavoriteTeam = false;
    }
    favoriteStar.onclick = async () => {
        await toggleFavoriteTeam(team.id, isFavoriteTeam);
        await loadUserFavorites();
        
        buildStarIcon(team, card);
    };
}