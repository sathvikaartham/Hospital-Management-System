import java.sql.Connection;
import java.sql.PreparedStatement;

public class Doctor {

    private int doctorId;
    private String name;
    private String specialization;
    private String phone;

    // Constructor
    public Doctor(String name, String specialization, String phone) {
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
    }

    // Add doctor
    public void addDoctor() {

        String sql = "INSERT INTO doctors (name, specialization, phone) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, specialization);
            ps.setString(3, phone);

            ps.executeUpdate();

            System.out.println("Doctor added successfully!");

        } catch (Exception e) {
            System.out.println("Failed to add doctor.");
            e.printStackTrace();
        }
    }
    public static void viewDoctors() {

    String sql = "SELECT * FROM doctors";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         java.sql.ResultSet rs = ps.executeQuery()) {

        System.out.println("\n========== DOCTOR LIST ==========");

        while (rs.next()) {

            System.out.println("Doctor ID: " + rs.getInt("doctor_id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Specialization: " + rs.getString("specialization"));
            System.out.println("Phone: " + rs.getString("phone"));
            System.out.println("----------------------------------");
        }

    } catch (Exception e) {
        System.out.println("Failed to fetch doctors.");
        e.printStackTrace();
    }
}
public static void updateDoctor(int doctorId, String name,
                                String specialization, String phone) {

    String sql = "UPDATE doctors SET name = ?, specialization = ?, phone = ? WHERE doctor_id = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, name);
        ps.setString(2, specialization);
        ps.setString(3, phone);
        ps.setInt(4, doctorId);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Doctor updated successfully!");
        } else {
            System.out.println("Doctor not found!");
        }

    } catch (Exception e) {
        System.out.println("Failed to update doctor.");
        e.printStackTrace();
    }
}
public static void deleteDoctor(int doctorId) {

    String sql = "DELETE FROM doctors WHERE doctor_id = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, doctorId);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Doctor deleted successfully!");
        } else {
            System.out.println("Doctor not found!");
        }

    } catch (Exception e) {
        System.out.println("Failed to delete doctor.");
        e.printStackTrace();
    }
}
}