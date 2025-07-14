window.onload = async () => {
    loadMainComponents();
    await fetchUserData();
};

let userName = "";
let userFavoriteDrivers = [];
let userFavoriteTeams = [];

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
                userFavoriteDrivers = user.favoriteDrivers;
                userFavoriteTeams = user.favoriteTeams;

                userName = user.userName;
                document.getElementById("userName").textContent = userName.toUpperCase();
                document.getElementById("fullName").textContent = user.firstName + " " + user.lastName;
                document.getElementById("email").textContent = user.email;
            } catch (error) {
                console.error("Erro ao parsear JSON:", error);
            }

            await loadFavoriteDrivers();
            await loadFavoriteTeams();
        })
        .catch(error => {
            const msg = "Erro ao buscar dados do usuário. Tente logar novamente";
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
    const slider = document.getElementById("pilotos-slider");
    slider.innerHTML = ''; 

    fetch('/components/pilotoCard.html')
        .then(res => res.text())
        .then(html => {
            userFavoriteDrivers.forEach(driver => {
                const temp = document.createElement('div');
                temp.innerHTML = html;
                const card = temp.firstElementChild;
                card.className = 'piloto-card';
                const collor = "#" + driver.team_colour;
                card.style.borderBottom = `20px solid ${collor || 'red'}`;

                buildStarIconDriver(driver, card);

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

async function loadFavoriteTeams() {
    const slider = document.getElementById("times-slider");
    slider.innerHTML = ''; 

    fetch('/components/timeCard.html')
        .then(res => res.text())
        .then(html => {
            userFavoriteTeams.forEach(team => {
                const temp = document.createElement('div');
                temp.innerHTML = html;
                const card = temp.firstElementChild;
                card.className = 'piloto-card';
                const collor = team.team_color;
                card.style.borderBottom = `20px solid ${collor || 'red'}`;

                buildStarIconTeam(team, card);

                // const img = card.querySelector('.piloto-img');
                // img.src = team.headshot_url;
                // img.alt = team.broadcast_name;

                const nomePiloto = card.querySelector('.piloto-info h5');
                nomePiloto.textContent = team.team_name;

                const pilotoEquipe = card.querySelectorAll('.piloto-info p')[0];
                pilotoEquipe.textContent = `Primeiro piloto: #${team.first_driver_number}`;

                const pilotoNumero = card.querySelectorAll('.piloto-info p')[1];
                pilotoNumero.textContent = `Segundo piloto: #${team.second_driver_number}`;

                const pilotoPais = card.querySelectorAll('.piloto-info p')[2];
                pilotoPais.textContent = `Pontos na temporada: ${team.team_points}`;

                slider.appendChild(card);
            });
        })
        .catch(err => console.error('Erro ao carregar pilotoCard:', err));
}

function buildStarIconDriver(driver, card) {
    const favoriteStar = card.querySelector('.favorite-star');
    let isFavoriteDriver;

    if (userFavoriteDrivers.some(fav => fav.driver_number === driver.driver_number)) {
        favoriteStar.src = '/images/star.png';
        isFavoriteDriver = true;
    } else {
        favoriteStar.src = '/images/star_g.png';
        isFavoriteDriver = false;
    }
    favoriteStar.onclick = async () => {
        await toggleFavoriteDriver(driver.driver_number, isFavoriteDriver);
        await fetchUserData();
        
        buildStarIconDriver(driver, card);
    };
}

function buildStarIconTeam(team, card) {
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
        await fetchUserData();
        
        buildStarIconTeam(team, card);
    };
}