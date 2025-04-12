window.onload = () => {
    checkToken();
    loadMainComponents();
    fetchUserData();
}

function fetchUserData() {
    fetch("/user/get?username=Biu", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
    .then(response => response.text())  
    .then(responseText => {
        try {
            const user = JSON.parse(responseText);
            console.log("Achou o usuário:", user.username);
        } catch (error) {
            console.error("Erro ao parsear JSON:", error);
        }
    })
    .catch(error => {
        console.error("Erro ao buscar usuário:", error);
    });
}