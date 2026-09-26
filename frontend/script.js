const API = "http://localhost:8080/api";


// =============================
// SECTION NAVIGATION
// =============================

function showSection(sectionId) {

    const sections =
        document.querySelectorAll(".section");

    sections.forEach(section => {
        section.classList.remove("active");
    });

    document
        .getElementById(sectionId)
        .classList.add("active");

    if (sectionId === "patients") {
        loadPatients();
    }

    if (sectionId === "doctors") {
        loadDoctors();
    }

    if (sectionId === "appointments") {
        loadAppointments();
    }
}


// =============================
// PATIENTS
// =============================

async function loadPatients() {

    try {

        const response =
            await fetch(`${API}/patients`);

        const patients =
            await response.json();

        const table =
            document.getElementById("patientTable");

        table.innerHTML = "";

        patients.forEach(patient => {

            table.innerHTML += `
                <tr>

                    <td>${patient.patient_id}</td>

                    <td>${patient.name}</td>

                    <td>${patient.age}</td>

                    <td>${patient.gender}</td>

                    <td>${patient.phone}</td>

                    <td>${patient.disease}</td>

                    <td>
                        <button
                            class="delete-btn"
                            onclick="deletePatient(${patient.patient_id})">
                            Delete
                        </button>
                    </td>

                </tr>
            `;
        });

        document.getElementById("patientCount").innerText =
            patients.length;

    } catch (error) {

        console.error(error);

        alert("Could not connect to Java backend.");
    }
}


async function addPatient() {

    const patient = {

        name:
            document.getElementById("patientName").value,

        age:
            document.getElementById("patientAge").value,

        gender:
            document.getElementById("patientGender").value,

        phone:
            document.getElementById("patientPhone").value,

        disease:
            document.getElementById("patientDisease").value
    };

    try {

        const response =
            await fetch(`${API}/patients`, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(patient)
            });

        if (response.ok) {

            alert("Patient added successfully!");

            loadPatients();

        } else {

            alert("Failed to add patient.");
        }

    } catch (error) {

        console.error(error);

        alert("Could not connect to Java backend.");
    }
}


async function deletePatient(id) {

    if (!confirm("Delete this patient?")) {
        return;
    }

    try {

        const response =
            await fetch(`${API}/patients/${id}`, {
                method: "DELETE"
            });

        if (response.ok) {

            alert("Patient deleted successfully!");

            loadPatients();

        } else {

            alert("Failed to delete patient.");
        }

    } catch (error) {

        console.error(error);

        alert("Could not connect to Java backend.");
    }
}


// =============================
// DOCTORS
// =============================

async function loadDoctors() {

    try {

        const response =
            await fetch(`${API}/doctors`);

        const doctors =
            await response.json();

        const table =
            document.getElementById("doctorTable");

        table.innerHTML = "";

        doctors.forEach(doctor => {

            table.innerHTML += `
                <tr>

                    <td>${doctor.doctor_id}</td>

                    <td>${doctor.name}</td>

                    <td>${doctor.specialization}</td>

                    <td>${doctor.phone}</td>

                    <td>
                        <button
                            class="delete-btn"
                            onclick="deleteDoctor(${doctor.doctor_id})">
                            Delete
                        </button>
                    </td>

                </tr>
            `;
        });

        document.getElementById("doctorCount").innerText =
            doctors.length;

    } catch (error) {

        console.error(error);

        alert("Could not connect to Java backend.");
    }
}


async function addDoctor() {

    const doctor = {

        name:
            document.getElementById("doctorName").value,

        specialization:
            document.getElementById("doctorSpecialization").value,

        phone:
            document.getElementById("doctorPhone").value
    };

    try {

        const response =
            await fetch(`${API}/doctors`, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(doctor)
            });

        if (response.ok) {

            alert("Doctor added successfully!");

            loadDoctors();

        } else {

            alert("Failed to add doctor.");
        }

    } catch (error) {

        console.error(error);

        alert("Could not connect to Java backend.");
    }
}


async function deleteDoctor(id) {

    if (!confirm("Delete this doctor?")) {
        return;
    }

    try {

        const response =
            await fetch(`${API}/doctors/${id}`, {
                method: "DELETE"
            });

        if (response.ok) {

            alert("Doctor deleted successfully!");

            loadDoctors();

        } else {

            alert("Failed to delete doctor.");
        }

    } catch (error) {

        console.error(error);

        alert("Could not connect to Java backend.");
    }
}


// =============================
// APPOINTMENTS
// =============================

async function loadAppointments() {

    try {

        const response =
            await fetch(`${API}/appointments`);

        const appointments =
            await response.json();

        const table =
            document.getElementById("appointmentTable");

        table.innerHTML = "";

        appointments.forEach(appointment => {

            table.innerHTML += `
                <tr>

                    <td>${appointment.appointment_id}</td>

                    <td>${appointment.patient_id}</td>

                    <td>${appointment.doctor_id}</td>

                    <td>${appointment.appointment_date}</td>

                    <td>${appointment.appointment_time}</td>

                    <td>${appointment.reason}</td>

                    <td>
                        <button
                            class="delete-btn"
                            onclick="deleteAppointment(${appointment.appointment_id})">
                            Delete
                        </button>
                    </td>

                </tr>
            `;
        });

        document.getElementById("appointmentCount").innerText =
            appointments.length;

    } catch (error) {

        console.error(error);

        alert("Could not connect to Java backend.");
    }
}


async function addAppointment() {

    const appointment = {

        patient_id:
            document.getElementById(
                "appointmentPatientId"
            ).value,

        doctor_id:
            document.getElementById(
                "appointmentDoctorId"
            ).value,

        appointment_date:
            document.getElementById(
                "appointmentDate"
            ).value,

        appointment_time:
            document.getElementById(
                "appointmentTime"
            ).value,

        reason:
            document.getElementById(
                "appointmentReason"
            ).value
    };

    try {

        const response =
            await fetch(`${API}/appointments`, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(appointment)
            });

        if (response.ok) {

            alert("Appointment booked successfully!");

            loadAppointments();

        } else {

            alert("Failed to book appointment.");
        }

    } catch (error) {

        console.error(error);

        alert("Could not connect to Java backend.");
    }
}


async function deleteAppointment(id) {

    if (!confirm("Delete this appointment?")) {
        return;
    }

    try {

        const response =
            await fetch(`${API}/appointments/${id}`, {
                method: "DELETE"
            });

        if (response.ok) {

            alert("Appointment deleted successfully!");

            loadAppointments();

        } else {

            alert("Failed to delete appointment.");
        }

    } catch (error) {

        console.error(error);

        alert("Could not connect to Java backend.");
    }
}