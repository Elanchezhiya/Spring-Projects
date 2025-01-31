document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");
    form.addEventListener("submit", function(event) {
        const emailField = document.getElementById("email");
        if (!emailField.value.includes("@")) {
            alert("Please enter a valid email address.");
            event.preventDefault();
        }
    });
});
