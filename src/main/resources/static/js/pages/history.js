let selectedYear = 2023

window.onload = () => {
    loadMainComponents();
    loadDropDownItems();
    loadHistoryMeetings(selectedYear);
}

async function loadHistoryMeetings(year) {
    document.getElementById("year-selection").textContent = "Histórico de eventos: " + year;

    try {
        const response = await fetch(`/api/meetings/get?year=${year}`);
        if (!response.ok) {
            throw new Error(`Erro ao buscar meetings: ${response.status}`);
        }
        const data = await response.json();
        renderMeetingGroups(data);
    } catch (error) {
        console.error('Erro na requisição:', error);
    }
}

function loadDropDownItems() {
    const startYear = 2023;
    const currentYear = new Date().getFullYear();
    const dropdown = document.getElementById('years-menu');
  
    for (let year = startYear; year <= currentYear; year++) {
      const li = document.createElement('li');
      li.innerHTML = `<a class="dropdown-item" href="#" onclick="handleYearClick(${year})">${year}</a>`;
      dropdown.appendChild(li);
    }
}

function handleYearClick(year) {
    if (year !== selectedYear) {
        selectedYear = year;
        console.log("Ano selecionado:", year);
        loadHistoryMeetings(year);
    }
}

async function renderMeetingGroups(meetings) {
    const wrapper = document.querySelector(".meetings-wrapper");
    wrapper.innerHTML = '';

    const templateHTML = await fetch('/components/historyMeeting.html')
        .then(res => res.text());

    const groupedMeetings = meetings.reduce((acc, meeting) => {
        const date = new Date(meeting.date_start);
        const monthKey = `${date.getFullYear()}-${date.getMonth()}`;

        if (!acc[monthKey]) acc[monthKey] = [];
        acc[monthKey].push(meeting);
        return acc;
    }, {});

    const sortedMonthKeys = Object.keys(groupedMeetings).sort();

    for (const key of sortedMonthKeys) {
        const groupMeetings = groupedMeetings[key];

        const temp = document.createElement('div');
        temp.innerHTML = templateHTML.trim();
        const groupElement = temp.firstChild;

        const fullMonth = new Date(groupMeetings[0].date_start).toLocaleDateString('pt-BR', {
            month: 'long',
        }).toUpperCase();
        groupElement.querySelector('#full-month').textContent = fullMonth;

        const meetingsCol = groupElement.querySelector('.meetings-col');
        const dateCol = groupElement.querySelector('.date-col');

        for (const meeting of groupMeetings) {
            const alpha2Code = convertIso3Code(meeting.country_code)?.toLowerCase();
            const flagUrl = `https://flagcdn.com/h80/${alpha2Code}.png`;

            // CRIA COLUNA DO MEETING ITEM
            const meetingItem = document.createElement('div');
            meetingItem.className = 'row meeting-item align-items-center mb-2';
            meetingItem.innerHTML = `
                <div class="flag-container">
                    <img src="${flagUrl}">
                </div>
                <div class="meeting-info ms-3">
                    <p class="mb-0" id="session-name">
                        ${meeting.meeting_name.includes("Grand Prix") ? "GP " + meeting.country_name.toUpperCase() : meeting.meeting_name}
                    </p>
                    <p class="mb-0" id="circuit-name">${meeting.location}</p>
                </div>
            `;

            // CRIA CONTAINER DE DETALHES DA GP
            const gpDetails = document.createElement('div');
            gpDetails.className = 'gp-details';

            let isLoaded = false;

            // Adiciona evento assíncrono no clique do item
            meetingItem.addEventListener('click', async () => {
                const isVisible = gpDetails.classList.contains('show');

                if (isVisible) {
                    gpDetails.classList.remove('show');
                    return;
                }

                // Fecha outros detalhes abertos
                const allDetails = document.querySelectorAll('.gp-details.show');
                allDetails.forEach(d => d.classList.remove('show'));

                if (!isLoaded) {
                    gpDetails.innerHTML = ''; // limpa antes de carregar
                    try {
                        const sessions = await fetch(`/api/sessions/get?meetingKey=${meeting.meeting_key}`)
                            .then(res => res.json());

                        sessions.forEach(session => {
                            const sessionDiv = document.createElement('div');
                            sessionDiv.className = 'gp-day py-1';
                            const startDate = new Date(session.date_start);
                            const endDate = new Date(session.date_end);

                            sessionDiv.innerHTML = `
                                <span class="day-name">${session.session_name}</span>
                                <span class="day-date">
                                   - ${startDate.getDate().toString().padStart(2, '0')}/${(startDate.getMonth() + 1).toString().padStart(2, '0')} (${startDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })} - ${endDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })})
                                </span>
                            `;
                            gpDetails.appendChild(sessionDiv);
                        });

                        isLoaded = true;
                    } catch (error) {
                        console.error("Erro ao carregar sessões:", error);
                        gpDetails.innerHTML = '<p>Erro ao carregar sessões</p>';
                    }
                }

                gpDetails.classList.add('show');
            });

            // Agrupa o item e os detalhes juntos
            const meetingWrapper = document.createElement('div');
            meetingWrapper.appendChild(meetingItem);
            meetingWrapper.appendChild(gpDetails);
            meetingsCol.appendChild(meetingWrapper);

            // COLUNA DE DATA
            const startDate = new Date(meeting.date_start);
            const shortMonth = startDate.toLocaleDateString('pt-BR', { month: 'short' }).toUpperCase();
            const day = startDate.getDate();

            const dateItem = document.createElement('div');
            dateItem.className = 'row date-item text-center mb-2';
            dateItem.innerHTML = `
                <p class="mb-0 mt-0" id="day">Dia ${day}</p>
                <p class="fw-bold" id="short-month">${shortMonth}</p>
            `;
            dateCol.appendChild(dateItem);
        }

        wrapper.appendChild(groupElement);
    }
}


function formatToFullMonth(dateStr) {
    const date = new Date(dateStr);
    return date.toLocaleDateString('pt-BR', { month: 'long' }).toUpperCase();
}