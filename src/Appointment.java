import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Time;

public class Appointment {

    private int patientId;
    private int doctorId;
    private String appointmentDate;
    private String appointmentTime;
    private String reason;

    public Appointment(int patientId, int doctorId,
                       String appointmentDate,
                       String appointmentTime,
                       String reason) {

        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
    }

    // ================= BOOK APPOINTMENT =================

    public void bookAppointment() {

        String sql = "INSERT INTO appointments " +
                "(patient_id, doctor_id, appointment_date, appointment_time, reason) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {

            Date date = Date.valueOf(appointmentDate);
            Time time = Time.valueOf(appointmentTime);

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, patientId);
                ps.setInt(2, doctorId);
                ps.setDate(3, date);
                ps.setTime(4, time);
                ps.setString(5, reason);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Appointment booked successfully!");
                }

            }

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Invalid date or time format!"
            );

            System.out.println(
                    "Date format: YYYY-MM-DD"
            );

            System.out.println(
                    "Time format: HH:MM"
            );

        } catch (Exception e) {

            System.out.println("Failed to book appointment.");
            e.printStackTrace();
        }
    }

    // ================= VIEW APPOINTMENTS =================

    public static void viewAppointments() {

        String sql = "SELECT * FROM appointments";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== APPOINTMENT LIST ==========");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        "Appointment ID: "
                                + rs.getInt("appointment_id")
                );

                System.out.println(
                        "Patient ID: "
                                + rs.getInt("patient_id")
                );

                System.out.println(
                        "Doctor ID: "
                                + rs.getInt("doctor_id")
                );

                System.out.println(
                        "Date: "
                                + rs.getDate("appointment_date")
                );

                System.out.println(
                        "Time: "
                                + rs.getTime("appointment_time")
                );

                System.out.println(
                        "Reason: "
                                + rs.getString("reason")
                );

                System.out.println("--------------------------------------");
            }

            if (!found) {
                System.out.println("No appointments found.");
            }

        } catch (Exception e) {

            System.out.println("Failed to fetch appointments.");
            e.printStackTrace();
        }
    }

    // ================= UPDATE APPOINTMENT =================

    public static void updateAppointment(
            int appointmentId,
            int patientId,
            int doctorId,
            String appointmentDate,
            String appointmentTime,
            String reason) {

        String sql = "UPDATE appointments SET " +
                "patient_id = ?, " +
                "doctor_id = ?, " +
                "appointment_date = ?, " +
                "appointment_time = ?, " +
                "reason = ? " +
                "WHERE appointment_id = ?";

        try {

            Date date = Date.valueOf(appointmentDate);
            Time time = Time.valueOf(appointmentTime);

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, patientId);
                ps.setInt(2, doctorId);
                ps.setDate(3, date);
                ps.setTime(4, time);
                ps.setString(5, reason);
                ps.setInt(6, appointmentId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println(
                            "Appointment updated successfully!"
                    );
                } else {
                    System.out.println(
                            "Appointment not found!"
                    );
                }
            }

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Invalid date or time format!"
            );

        } catch (Exception e) {

            System.out.println(
                    "Failed to update appointment."
            );

            e.printStackTrace();
        }
    }

    // ================= DELETE APPOINTMENT =================

    public static void deleteAppointment(int appointmentId) {

        String sql =
                "DELETE FROM appointments WHERE appointment_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, appointmentId);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Appointment deleted successfully!"
                );

            } else {

                System.out.println(
                        "Appointment not found!"
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Failed to delete appointment."
            );

            e.printStackTrace();
        }
    }
}