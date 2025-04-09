window.onload = function() {
    loadComponent('authForm', '/components/loginForm.html');
}

document.getElementById("authForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const formData = new FormData(this);
    const dados = Object.fromEntries(formData.entries());

    // Converter a data de nascimento no formato aceito pelo GregorianCalendar
    // if (dados.birthday) {
    //     const [ano, mes, dia] = dados.birthday.split("-");
    //     dados.birthday = {
    //         year: parseInt(ano),
    //         month: parseInt(mes) - 1,
    //         dayOfMonth: parseInt(dia)
    //     };
    // }

    fetch("/auth/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(dados)
    })
    .then(response => {
        if (!response.ok) throw new Error("Erro ao registrar");
        return response.json();
    })
    .then(data => {
        localStorage.setItem('token', data.token);
        console.log("Usuário registrado com sucesso:", data);
    })
    .catch(error => console.error('Erro ao registrar:', error));
});

function changeToRegister() {
    loadComponent('authForm', '/components/registerForm.html');
}

function changeToLogin() {
    loadComponent('authForm', '/components/loginForm.html');
}