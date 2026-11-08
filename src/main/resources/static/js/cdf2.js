document.addEventListener("DOMContentLoaded", () => {
    const rows = document.getElementById("employmentRows");
    const previous = document.getElementById("previousExperience");

    // Searchable dropdown for the Indian IT company list.
    document.querySelectorAll(".employer-select").forEach(select => {
        if (typeof TomSelect !== "undefined" && !select.tomselect) {
            new TomSelect(select, {
                create: false,
                searchField: ["text"],
                placeholder: "Employer's Name",
                maxOptions: 100,
                closeAfterSelect: true,
                allowEmptyOption: true
            });
        }
    });

    // Show the employment row only when the candidate has prior experience.
    function toggleEmployment() {
        if (rows && previous) {
            rows.hidden = previous.value !== "Yes";
        }
    }

    if (previous) {
        previous.addEventListener("change", toggleEmployment);
        toggleEmployment();
    }
});
