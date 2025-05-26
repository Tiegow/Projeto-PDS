window.onload = async () => {
    loadMainComponents();
    await fetchUserData();
};

let userName = "";
let userFavorites = [];

async function fetchUserData() {
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
                userFavorites = user.favoriteDrivers;

                userName = user.userName;
                document.getElementById("userName").textContent = userName.toUpperCase();
                document.getElementById("fullName").textContent = user.firstName + " " + user.lastName;
                document.getElementById("email").textContent = user.email;
            } catch (error) {
                console.error("Erro ao parsear JSON:", error);
            }

            await loadFavoriteDrivers();
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

async function loadFavoriteDrivers() {
    console.log("Carregando favoritos:", userFavorites);
    const slider = document.getElementById("pilotos-slider");
    slider.innerHTML = ''; 

    fetch('/components/pilotoCard.html')
        .then(res => res.text())
        .then(html => {
            userFavorites.forEach(driver => {
                const temp = document.createElement('div');
                temp.innerHTML = html;
                const card = temp.firstElementChild;
                card.className = 'piloto-card';

                buildStarIcon(driver, card);

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

                slider.appendChild(card);
            });
        })
        .catch(err => console.error('Erro ao carregar pilotoCard:', err));
}

function buildStarIcon(driver, card) {
    const favoriteStar = card.querySelector('.favorite-star');
    let isFavoriteDriver;

    if (userFavorites.some(fav => fav.driver_number === driver.driver_number)) {
        favoriteStar.src = '/images/star.png';
        isFavoriteDriver = true;
    } else {
        favoriteStar.src = '/images/star_g.png';
        isFavoriteDriver = false;
    }
    favoriteStar.onclick = async () => {
        await toggleFavoriteDriver(driver.driver_number, isFavoriteDriver);
        await fetchUserData();
        
        buildStarIcon(driver, card);
    };
}