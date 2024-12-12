document.getElementById("planTypeId").addEventListener("change", calculateEndDate);
document.getElementById("startDate").addEventListener("input", calculateEndDate);

function calculateEndDate() {
    const planTypeId = document.getElementById("planTypeId").value;
    const startDate = document.getElementById("startDate").value;

    if (planTypeId && startDate) {
        fetch(`/api/plan/calculateEndDate?planTypeId=${planTypeId}&startDate=${startDate}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to calculate end date");
                }
                return response.json();
            })
            .then(data => {
                document.getElementById("endDate").value = data;
            })
            .catch(error => console.error("Error:", error));
    }
}