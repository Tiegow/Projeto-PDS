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
        console.log(response)
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
                        mostrarDetalhes(team.teamName, detalhesContainer, baseColor);
                    });
                } else if (!team.teamName) {
                    console.warn('Time sem nome, não é possível adicionar listener para detalhes:', team);
                }

                container.appendChild(card);
            });
        })
        .catch(err => {
            console.error('Erro ao carregar ou processar teamCard.html:', err);
            container.innerHTML = `<p class="text-danger text-center">Erro ao carregar o template dos cards.<br>${err.message}</p>`;
        });
}

function mostrarDetalhes(teamName, detalhesContainer, teamColorForGradient) {
    if (!detalhesContainer) {
        console.error('Contêiner de detalhes não disponível.');
        return;
    }
    detalhesContainer.innerHTML = `
        <div class="text-center p-5">
            <div class="spinner-border text-danger" role="status">
                <span class="visually-hidden">Carregando detalhes...</span>
            </div>
        </div>`;

    fetch(`/api/teams/details?team_name=${encodeURIComponent(teamName)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erro HTTP ${response.status} ao buscar detalhes: ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            const detailTeamColor = data.teamColor;
            const baseColor = `#${detailTeamColor}`;
            const lighterGradientColor = createDarkerColor(baseColor, 20);

            detalhesContainer.innerHTML = `
                <div class="card shadow rounded-4 border-0 overflow-hidden">
                    <div class="card-header p-4" style="background: linear-gradient(to right, ${baseColor}, ${lighterGradientColor});">
                        <div class="d-flex align-items-center gap-4">
                            <h1 class="text-white mb-0">${data.teamName || 'Detalhes Indisponíveis'}</h1>
                        </div>
                    </div>
                    <div class="card-body p-4">
                        <h4 class="mb-3">Informações da Equipe</h4>
                        <p><strong>Pontos:</strong> ${data.teamPoints ?? 'N/A'}</p>
                        <hr class="my-4">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="card mb-3">
                                    <div class="card-body">
                                        <h5 class="card-title">Primeiro Piloto</h5>
                                        <p class="card-text">Número: ${data.firstDriverNumber ?? 'N/A'}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="card mb-3">
                                    <div class="card-body">
                                        <h5 class="card-title">Segundo Piloto</h5>
                                        <p class="card-text">Número: ${data.secondDriverNumber ?? 'N/A'}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>`;
        })
        .catch(error => {
            console.error('Erro ao buscar ou renderizar detalhes do time:', error);
            detalhesContainer.innerHTML = `<p class="text-danger p-4">Falha ao carregar detalhes do time.<br>${error.message}</p>`;
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