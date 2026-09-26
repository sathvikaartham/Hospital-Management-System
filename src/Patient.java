import java.sql.Connection;
import java.sql.PreparedStatement;

public class Patient {

    private int patientId;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String disease;

    public Patient(String name, int age, String gender, String phone, String disease) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.disease = disease;
    }

    public void addPatient() {

        String sql = "INSERT INTO patients (name, age, gender, phone, disease) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, phone);
            ps.setString(5, disease);

            ps.executeUpdate();

            System.out.println("Patient added successfully!");

        } catch (Exception e) {
            System.out.println("Failed to add patient.");
            e.printStackTrace();
        }
    }
    public static void viewPatients() {

    String sql = "SELECT * FROM patients";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         java.sql.ResultSet rs = ps.executeQuery()) {

        System.out.println("\n========== PATIENT LIST ==========");

        while (rs.next()) {

            System.out.println("Patient ID: " + rs.getInt("patient_id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Age: " + rs.getInt("age"));
            System.out.println("Gender: " + rs.getString("gender"));
            System.out.println("Phone: " + rs.getString("phone"));
            System.out.println("Disease: " + rs.getString("disease"));
            System.out.println("----------------------------------");
        }

    } catch (Exception e) {
        System.out.println("Failed to fetch patients.");
        e.printStackTrace();
    }
}
public static void updatePatient(int patientId, String name, int age,
                                 String gender, String phone, String disease) {

    String sql = "UPDATE patients SET name = ?, age = ?, gender = ?, phone = ?, disease = ? WHERE patient_id = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setString(3, gender);
        ps.setString(4, phone);
        ps.setString(5, disease);
        ps.setInt(6, patientId);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Patient updated successfully!");
        } else {
            System.out.println("Patient not found!");
        }

    } catch (Exception e) {
        System.out.println("Failed to update patient.");
        e.printStackTrace();
    }
}
public static void deletePatient(int patientId) {

    String sql = "DELETE FROM patients WHERE patient_id = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, patientId);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Patient deleted successfully!");
        } else {
            System.out.println("Patient not found!");
        }

    } catch (Exception e) {
        System.out.println("Failed to delete patient.");
        e.printStackTrace();
    }
}
}