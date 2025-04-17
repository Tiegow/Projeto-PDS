window.onload = () => {
    loadMainComponents();
    loadYearMeetings();
    loadTodaySession();
}

async function loadYearMeetings() {
    const year = new Date().getFullYear(); //Ano atual

    document.getElementById("today_year").textContent = year;

    try {
        const response = await fetch(`/api/meetings/get?year=${year}`);
        if (!response.ok) {
            throw new Error(`Erro ao buscar meetings: ${response.status}`);
        }
        const data = await response.json();
        renderMeetings(data);
    } catch (error) {
        console.error('Erro na requisição:', error);
    }
}

async function renderMeetings(meetings) {
    const gpWrapper = document.getElementById("gp-wraper");
    gpWrapper.innerHTML = '';

    // Carrega o HTML do componente como string
    const templateHTML = await fetch('/components/gpContent.html')
        .then(res => res.text());

    meetings.forEach(meeting => {
        const temp = document.createElement('div');
        temp.innerHTML = templateHTML.trim();
        const meetingElement = temp.firstChild;

        // Preenche os dados do meeting
        if (meeting.meeting_name.includes("Grand Prix")) {
            meetingElement.querySelector('.session-name').textContent = "GP " + meeting.country_name;
        } else {
            meetingElement.querySelector('.session-name').textContent = meeting.meeting_name;
        }
        meetingElement.querySelector('.circuit-name').textContent = meeting.location;

        const startDate = new Date(meeting.date_start);
        const endDate = new Date(meeting.date_end);

        // Pega dia inicial e final
        const startDay = startDate.getDate();
        const endDay = endDate.getDate();

        // Pega o mês (abreviado e maiúsculo)
        const month = startDate.toLocaleString('default', { month: 'short' }).toUpperCase();

        meetingElement.querySelector('.session-days').textContent = `${startDay} - ${endDay}`;
        meetingElement.querySelector('.session-month').textContent = month;

        // converter alpha-3 para alpha-2
        const alpha2Code = convertIso3Code(meeting.country_code)?.toLowerCase()

        // carregar bandeira
        const flagUrl = `https://flagcdn.com/h80/${alpha2Code}.png`;

        meetingElement.querySelector('.flag-container').innerHTML = `
            <img src="${flagUrl}" alt="Bandeira de ${meeting.country_name}">
        `;

        const gpDetails = meetingElement.querySelector('.gp-details');
        gpDetails.innerHTML = ''; 

        // Adiciona evento de clique no container
        meetingElement.querySelector('.gp-content').addEventListener('click', async () => {
            // Evita múltiplas chamadas
            if (gpDetails.childElementCount > 0) {
                gpDetails.innerHTML = '';
                return;
            }

            try {
                const sessions = await fetch(`/api/sessions/get?meetingKey=${meeting.meeting_key}`)
                    .then(res => res.json());

                sessions.forEach(session => {
                    const sessionDiv = document.createElement('div');
                    sessionDiv.className = 'gp-day py-3';
                    const startDate = new Date(session.date_start);
                    const endDate = new Date(session.date_end);

                    sessionDiv.innerHTML = `
                        <span class="day-name">${session.session_name}</span>
                        <span class="day-date">
                            Dia ${startDate.getDate()} (${startDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })} - ${endDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })})
                        </span>
                    `;

                    gpDetails.appendChild(sessionDiv);
                });

            } catch (error) {
                console.error("Erro ao buscar sessões:", error);
                gpDetails.innerHTML = '<p>Erro ao carregar sessões</p>';
            }
        });

        gpWrapper.appendChild(meetingElement);
    });
}

async function loadTodaySession() {
    try {
        const response = await fetch(`/api/sessions/get/today`);
        
        if (!response.ok) {
            document.getElementById('no-session-message').style.display = 'block';

            document.getElementById('circuit-name').style.display = 'none';
            document.getElementById('circuit-type').style.display = 'none';
            document.getElementById('session-time').style.display = 'none';
            document.getElementById('flag-container').style.display = 'none';
            document.getElementById('session-button').style.display = 'none';

            return;
        }

        const data = await response.json();

        // Atualiza os detalhes da sessão no card
        document.getElementById('circuit-name').textContent = data.circuit_short_name;
        document.getElementById('circuit-type').textContent = data.session_name;

        const start = new Date(data.date_start);
        const end = new Date(data.date_end);

        const formatTime = (date) =>
            date.toLocaleTimeString('pt-BR', {
                hour: '2-digit',
                minute: '2-digit',
                hour12: false,
            });

        document.getElementById('session-time').textContent = `${formatTime(start)} - ${formatTime(end)}`;

        // Esconde a mensagem de erro, caso haja sessão
        document.getElementById('no-session-message').style.display = 'none';
        
    } catch (error) {
        console.error('Erro na requisição:', error);
        document.getElementById('no-session-message').style.display = 'block';
    }
}