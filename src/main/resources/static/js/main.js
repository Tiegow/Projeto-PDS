// Função para carregar os componentes principais da página (cabeçalho, rodapé, etc.)
function loadMainComponents() {
    loadComponent('header', '/components/header.html');
}

/**
 * Função para carregar um componente HTML em um elemento específico da página.
 *
 * A função utiliza o método fetch para obter o conteúdo do componente HTML e, em seguida,
 * insere esse conteúdo no elemento especificado pelo ID.
 *
 * @param {*} targetElementId ID do elemento onde o componente será carregado
 * @param {*} componentUrl Diretório do componente HTML a ser carregado
 */
function loadComponent(targetElementId, componentUrl) {
    const element = document.getElementById(targetElementId);
    if (element) {
        fetch(componentUrl)
            .then(response => response.text())
            .then(html => {
                element.innerHTML = html;
            })
            .catch(error => console.error('Erro ao carregar componente:', error));
    } else {
        console.error(`Componente com ID ${targetElementId} nao encontrado.`);
    }
}

function navigate(destination) {
    window.location.href = '/easyF1/' + destination;
}

function toggleDetails(element) {
    const container = element.parentElement;
    container.classList.toggle("expanded");
}

async function toggleFavoriteDriver(driverNumber, isFavoriteDriver) {
    const response = await fetch(`/api/user/favorite-driver/${driverNumber}`, {
        method: isFavoriteDriver ? "DELETE" : "POST",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    });
}

async function loadUserFavorites() {
    try {
        const response = await fetch(`/api/user/get`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        });

        if (!response.ok) {
            throw new Error(`Erro ao buscar pilotos favoritos: ${response.status}`);
        }

        const data = await response.json();
        userFavorites = data.favoriteDrivers;
    } catch (error) {
        console.error('Erro na requisição:', error);
    }
}