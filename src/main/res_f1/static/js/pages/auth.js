var authType = "login"; // Pode ser "login" ou "register"

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

    if (authType === "login") {
        callLogin(dados);
    }
    else if (authType === "register") {
        callRegister(dados);
    }
});

function changeToRegister() {
    loadComponent('authForm', '/components/registerForm.html');
    authType = "register";
}

function changeToLogin() {
    loadComponent('authForm', '/components/loginForm.html');
    authType = "login";
}

function callLogin(dados) {
    fetch("/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(dados)
    })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao logar");
            return response.json();
        })
        .then(data => {
            localStorage.setItem('token', data.token);
            console.log("Usuário logado com sucesso:", data);

            navigate('home');
        })
        .catch(error => console.error('Erro ao logar:', error));
}

function callRegister(dados) {
    fetch("/api/auth/register", {
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

            navigate('home');
        })
        .catch(error => console.error('Erro ao registrar:', error));
}