window.onload = () => {
    loadMainComponents();
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
 * @param {*} elementId ID do elemento onde o componente será carregado
 * @param {*} url Diretório do componente HTML a ser carregado
 */
function loadComponent(elementId, url) {
    const element = document.getElementById(elementId);
    if (element) {
        fetch(url)
            .then(response => response.text())
            .then(html => {
                element.innerHTML = html;
            })
            .catch(error => console.error('Erro ao carregar componente:', error));
    } else {
        console.error(`Componente com ID ${elementId} nao encontrado.`);
    }
}

function navigate(destination) {
    window.location.href = '../../templates/pages/' + destination + '.html';
}