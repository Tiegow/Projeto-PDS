const msg = sessionStorage.getItem("errorMessage");

if (msg) {
    document.getElementById("errorMsg").textContent = msg;
    sessionStorage.removeItem("errorMessage");
}