window.onload = () => {
    // checkToken();
    loadMainComponents();
}

function checkToken() {
    const token = localStorage.getItem('token');

    if (!token && !window.location.pathname.includes('auth.html')) {
        window.location.href = '../../templates/pages/auth.html';
    }
}

// Função para carregar os componentes principais da página (cabeçalho, rodapé, etc.)
function loadMainComponents() {
    loadComponent('header', '../../templates/components/header.html');
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
    window.location.href = '../../templates/pages/' + destination + '.html';
}