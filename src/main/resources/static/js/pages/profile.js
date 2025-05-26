window.onload = () => {
    loadMainComponents();
    fetchUserData();
};

let userName = "";

function fetchUserData() {
    fetch("/api/user/get", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
        .then(async response => {
            if (!response.ok) {
                await handleResponseException(response);
                return;
            }

            const responseText = await response.text();
            try {
                const user = JSON.parse(responseText);

                userName = user.userName;
                document.getElementById("userName").textContent = userName.toUpperCase();
                document.getElementById("fullName").textContent = user.firstName + " " + user.lastName;
                document.getElementById("email").textContent = user.email;
            } catch (error) {
                console.error("Erro ao parsear JSON:", error);
            }
        })
        .catch(error => {
            const msg = "Erro ao buscar dados do usuário: " + error.message;
            console.error("Erro inesperado:", error);
            sessionStorage.setItem("errorMessage", msg);
            navigate('error');
        });
}

async function handleResponseException(response) {
    const message = await response.text();
    sessionStorage.setItem("errorMessage", message);
}