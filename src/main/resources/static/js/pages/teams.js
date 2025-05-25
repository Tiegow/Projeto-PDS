window.onload = () => {
    loadMainComponents();
    loadAllTeams();
}

async function loadAllTeams(){
    const container = document.getElementById('times-container');
    try {
        const response = await fetch(`/api/team/all`);

        if (!response.ok) { // Considera status 200-299 como ok
            throw new Error(`Erro ao buscar equipes: ${response.status} ${response.statusText}`);
        }

        if (response.status === 204) { // HTTP 204 No Content
            container.innerHTML = '<p class="text-center w-100 mt-4">Nenhuma equipe encontrada no momento.</p>';
            console.log('Nenhuma equipe retornada pelo servidor (204 No Content).');
            return; // Interrompe a execução pois não há dados para renderizar
        }

        const data = await response.json();

        if (data && data.length > 0) {
            renderTeams(data);
        } else {
            container.innerHTML = '<p class="text-center w-100 mt-4">Nenhuma equipe encontrada.</p>';
            console.log('Dados de equipes vazios ou não encontrados.');
        }

    } catch (error) {
        console.error('Erro na requisição ou ao processar equipes:', error);
        container.innerHTML = '<p class="text-center w-100 mt-4 text-danger">Ocorreu um erro ao carregar as equipes. Por favor, tente novamente mais tarde.</p>';
    }
}

function renderTeams(response) {
    const container = document.getElementById('times-container');
    // Limpa o container antes de adicionar novos cards para evitar duplicatas se a função for chamada múltiplas vezes
    container.innerHTML = '';

    fetch('/components/teamCard.html')
        .then(res => {
            if (!res.ok) {
                throw new Error(`Erro ao carregar teamCard.html: ${res.status}`);
            }
            return res.text();
        })
        .then(html => {
            if (teamsData.length === 0) {
                 container.innerHTML = '<p class="text-center w-100 mt-4">Nenhuma equipe para exibir.</p>';
                 return;
            }
            teamsData.forEach(team => {
                const temp = document.createElement('div');
                temp.innerHTML = html;
                const card = temp.firstElementChild;
                // É uma boa prática clonar o nó do template se ele for complexo, 
                // mas para innerHTML em um elemento temporário, isso funciona.
                card.className = 'time-card mx-1 col-md-4 col-lg-3 mb-4'; // Adicionando classes Bootstrap para layout responsivo

                const teamNameForDisplay = team.teamName || team.name || "Nome da Equipe Indisponível";

                card.style.backgroundColor = '#CCCCCC';

                const nomeTimeElement = card.querySelector('.time-info h5');
                if (nomeTimeElement) {
                    nomeTimeElement.textContent = teamNameForDisplay;
                }

                card.addEventListener('click', () => {

                    mostrarDetalhesTime(team.id || teamNameForDisplay);
                });

                container.appendChild(card);
            });
        })
        .catch(err => {
            console.error('Erro ao carregar ou renderizar teamCard.html:', err);
            container.innerHTML = '<p class="text-center w-100 mt-4 text-danger">Erro ao exibir os cards das equipes.</p>';
        });
}
