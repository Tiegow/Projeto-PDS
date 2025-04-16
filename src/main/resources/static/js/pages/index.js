window.onload = () => {
    loadMainComponents();
    loadYearMeetings();
    console.log(new Date("2025-04-11T11:30:00Z"));
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
        console.log('Meetings do ano:', data);
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
        }
        meetingElement.querySelector('.circuit-name').textContent = meeting.location;

        const formattedDate = formatToMonthDay(meeting.date_start);
        meetingElement.querySelector('.session-days').textContent = formattedDate;

        const gpDetails = meetingElement.querySelector('.gp-details');
        gpDetails.innerHTML = ''; // limpa antes de adicionar sessions
        // meeting.sessions.forEach(session => {
        //     const sessionDiv = document.createElement('div');
        //     sessionDiv.className = 'gp-day';
        //     const sessionDate = new Date(session.startDate);
        //     sessionDiv.innerHTML = `
        //         <span class="day-name">${session.name}</span>
        //         <span class="day-date">Dia ${sessionDate.getDate()} (${session.startTime} - ${session.endTime})</span>
        //     `;
        //     gpDetails.appendChild(sessionDiv);
        // });

        gpWrapper.appendChild(meetingElement);
    });
}

function formatToMonthDay(dateString) {
    if (!dateString) return "Data inválida";

    const date = new Date(dateString);
    if (isNaN(date)) return "Data inválida";

    const day = date.getUTCDate().toString().padStart(2, '0');
    const month = date.toLocaleString('default', { month: 'short' }).toUpperCase();
    const formattedMonth = month.replace('.', '');

    return `${day} de ${formattedMonth}`;
}