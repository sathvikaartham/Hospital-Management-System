import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApiServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080), 0
        );

        server.createContext("/api/patients", ApiServer::patients);
        server.createContext("/api/doctors", ApiServer::doctors);
        server.createContext("/api/appointments", ApiServer::appointments);

        server.setExecutor(null);

        server.start();

        System.out.println("=================================");
        System.out.println("Hospital API Server Started");
        System.out.println("http://localhost:8080");
        System.out.println("=================================");
    }


    // =====================================================
    // PATIENTS
    // =====================================================

    private static void patients(HttpExchange exchange)
            throws IOException {

        addCors(exchange);

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            send(exchange, 200, "");
            return;
        }

        try {

            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            if (method.equalsIgnoreCase("GET")) {

                getPatients(exchange);

            } else if (method.equalsIgnoreCase("POST")) {

                addPatient(exchange);

            } else if (method.equalsIgnoreCase("DELETE")) {

                String[] parts = path.split("/");

                if (parts.length >= 4) {

                    int id = Integer.parseInt(parts[3]);

                    deletePatient(exchange, id);

                } else {

                    send(exchange, 400,
                            "{\"error\":\"Patient ID required\"}");
                }

            } else {

                send(exchange, 405,
                        "{\"error\":\"Method not allowed\"}");
            }

        } catch (Exception e) {

            e.printStackTrace();

            send(exchange, 500,
                    "{\"error\":\"Server error\"}");
        }
    }


    private static void getPatients(HttpExchange exchange)
            throws Exception {

        String sql = "SELECT * FROM patients";

        StringBuilder json = new StringBuilder();

        json.append("[");

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean first = true;

            while (rs.next()) {

                if (!first) {
                    json.append(",");
                }

                first = false;

                json.append("{");

                json.append("\"patient_id\":")
                        .append(rs.getInt("patient_id"))
                        .append(",");

                json.append("\"name\":\"")
                        .append(escape(rs.getString("name")))
                        .append("\",");

                json.append("\"age\":")
                        .append(rs.getInt("age"))
                        .append(",");

                json.append("\"gender\":\"")
                        .append(escape(rs.getString("gender")))
                        .append("\",");

                json.append("\"phone\":\"")
                        .append(escape(rs.getString("phone")))
                        .append("\",");

                json.append("\"disease\":\"")
                        .append(escape(rs.getString("disease")))
                        .append("\"");

                json.append("}");
            }
        }

        json.append("]");

        send(exchange, 200, json.toString());
    }


    private static void addPatient(HttpExchange exchange)
            throws Exception {

        String body = readBody(exchange);

        String name = getValue(body, "name");
        String ageText = getValue(body, "age");
        String gender = getValue(body, "gender");
        String phone = getValue(body, "phone");
        String disease = getValue(body, "disease");

        String sql =
                "INSERT INTO patients " +
                "(name, age, gender, phone, disease) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, Integer.parseInt(ageText));
            ps.setString(3, gender);
            ps.setString(4, phone);
            ps.setString(5, disease);

            ps.executeUpdate();
        }

        send(exchange, 200,
                "{\"message\":\"Patient added successfully\"}");
    }


    private static void deletePatient(
            HttpExchange exchange,
            int id) throws Exception {

        String sql =
                "DELETE FROM patients WHERE patient_id = ?";

        int rows;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            rows = ps.executeUpdate();
        }

        if (rows > 0) {

            send(exchange, 200,
                    "{\"message\":\"Patient deleted successfully\"}");

        } else {

            send(exchange, 404,
                    "{\"error\":\"Patient not found\"}");
        }
    }


    // =====================================================
    // DOCTORS
    // =====================================================

    private static void doctors(HttpExchange exchange)
            throws IOException {

        addCors(exchange);

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            send(exchange, 200, "");
            return;
        }

        try {

            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            if (method.equalsIgnoreCase("GET")) {

                getDoctors(exchange);

            } else if (method.equalsIgnoreCase("POST")) {

                addDoctor(exchange);

            } else if (method.equalsIgnoreCase("DELETE")) {

                String[] parts = path.split("/");

                if (parts.length >= 4) {

                    int id = Integer.parseInt(parts[3]);

                    deleteDoctor(exchange, id);

                } else {

                    send(exchange, 400,
                            "{\"error\":\"Doctor ID required\"}");
                }

            } else {

                send(exchange, 405,
                        "{\"error\":\"Method not allowed\"}");
            }

        } catch (Exception e) {

            e.printStackTrace();

            send(exchange, 500,
                    "{\"error\":\"Server error\"}");
        }
    }


    private static void getDoctors(HttpExchange exchange)
            throws Exception {

        String sql = "SELECT * FROM doctors";

        StringBuilder json = new StringBuilder();

        json.append("[");

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean first = true;

            while (rs.next()) {

                if (!first) {
                    json.append(",");
                }

                first = false;

                json.append("{");

                json.append("\"doctor_id\":")
                        .append(rs.getInt("doctor_id"))
                        .append(",");

                json.append("\"name\":\"")
                        .append(escape(rs.getString("name")))
                        .append("\",");

                json.append("\"specialization\":\"")
                        .append(escape(
                                rs.getString("specialization")))
                        .append("\",");

                json.append("\"phone\":\"")
                        .append(escape(rs.getString("phone")))
                        .append("\"");

                json.append("}");
            }
        }

        json.append("]");

        send(exchange, 200, json.toString());
    }


    private static void addDoctor(HttpExchange exchange)
            throws Exception {

        String body = readBody(exchange);

        String name = getValue(body, "name");
        String specialization =
                getValue(body, "specialization");
        String phone = getValue(body, "phone");

        String sql =
                "INSERT INTO doctors " +
                "(name, specialization, phone) " +
                "VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, specialization);
            ps.setString(3, phone);

            ps.executeUpdate();
        }

        send(exchange, 200,
                "{\"message\":\"Doctor added successfully\"}");
    }


    private static void deleteDoctor(
            HttpExchange exchange,
            int id) throws Exception {

        String sql =
                "DELETE FROM doctors WHERE doctor_id = ?";

        int rows;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            rows = ps.executeUpdate();
        }

        if (rows > 0) {

            send(exchange, 200,
                    "{\"message\":\"Doctor deleted successfully\"}");

        } else {

            send(exchange, 404,
                    "{\"error\":\"Doctor not found\"}");
        }
    }


    // =====================================================
    // APPOINTMENTS
    // =====================================================

    private static void appointments(HttpExchange exchange)
            throws IOException {

        addCors(exchange);

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            send(exchange, 200, "");
            return;
        }

        try {

            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            if (method.equalsIgnoreCase("GET")) {

                getAppointments(exchange);

            } else if (method.equalsIgnoreCase("POST")) {

                addAppointment(exchange);

            } else if (method.equalsIgnoreCase("DELETE")) {

                String[] parts = path.split("/");

                if (parts.length >= 4) {

                    int id = Integer.parseInt(parts[3]);

                    deleteAppointment(exchange, id);

                } else {

                    send(exchange, 400,
                            "{\"error\":\"Appointment ID required\"}");
                }

            } else {

                send(exchange, 405,
                        "{\"error\":\"Method not allowed\"}");
            }

        } catch (Exception e) {

            e.printStackTrace();

            send(exchange, 500,
                    "{\"error\":\"Server error\"}");
        }
    }


    private static void getAppointments(HttpExchange exchange)
            throws Exception {

        String sql = "SELECT * FROM appointments";

        StringBuilder json = new StringBuilder();

        json.append("[");

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean first = true;

            while (rs.next()) {

                if (!first) {
                    json.append(",");
                }

                first = false;

                json.append("{");

                json.append("\"appointment_id\":")
                        .append(rs.getInt("appointment_id"))
                        .append(",");

                json.append("\"patient_id\":")
                        .append(rs.getInt("patient_id"))
                        .append(",");

                json.append("\"doctor_id\":")
                        .append(rs.getInt("doctor_id"))
                        .append(",");

                json.append("\"appointment_date\":\"")
                        .append(rs.getDate("appointment_date"))
                        .append("\",");

                json.append("\"appointment_time\":\"")
                        .append(rs.getTime("appointment_time"))
                        .append("\",");

                json.append("\"reason\":\"")
                        .append(escape(rs.getString("reason")))
                        .append("\"");

                json.append("}");
            }
        }

        json.append("]");

        send(exchange, 200, json.toString());
    }


    private static void addAppointment(
            HttpExchange exchange) throws Exception {

        String body = readBody(exchange);

        String patientIdText =
                getValue(body, "patient_id");

        String doctorIdText =
                getValue(body, "doctor_id");

        String date =
                getValue(body, "appointment_date");

        String time =
                getValue(body, "appointment_time");

        String reason =
                getValue(body, "reason");

        if (time.length() == 5) {
            time = time + ":00";
        }

        String sql =
                "INSERT INTO appointments " +
                "(patient_id, doctor_id, appointment_date, " +
                "appointment_time, reason) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(patientIdText));
            ps.setInt(2, Integer.parseInt(doctorIdText));
            ps.setDate(3, java.sql.Date.valueOf(date));
            ps.setTime(4, java.sql.Time.valueOf(time));
            ps.setString(5, reason);

            ps.executeUpdate();
        }

        send(exchange, 200,
                "{\"message\":\"Appointment booked successfully\"}");
    }


    private static void deleteAppointment(
            HttpExchange exchange,
            int id) throws Exception {

        String sql =
                "DELETE FROM appointments " +
                "WHERE appointment_id = ?";

        int rows;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            rows = ps.executeUpdate();
        }

        if (rows > 0) {

            send(exchange, 200,
                    "{\"message\":\"Appointment deleted successfully\"}");

        } else {

            send(exchange, 404,
                    "{\"error\":\"Appointment not found\"}");
        }
    }


    // =====================================================
    // HELPER METHODS
    // =====================================================

    private static String readBody(
            HttpExchange exchange) throws IOException {

        InputStream input =
                exchange.getRequestBody();

        return new String(
                input.readAllBytes(),
                StandardCharsets.UTF_8
        );
    }


    private static String getValue(
            String json,
            String key) {

        String search = "\"" + key + "\":";

        int start = json.indexOf(search);

        if (start == -1) {
            return "";
        }

        start += search.length();

        while (start < json.length()
                && Character.isWhitespace(json.charAt(start))) {

            start++;
        }

        if (start < json.length()
                && json.charAt(start) == '"') {

            start++;

            int end = start;

            while (end < json.length()) {

                if (json.charAt(end) == '"'
                        && json.charAt(end - 1) != '\\') {

                    break;
                }

                end++;
            }

            return json.substring(start, end);
        }

        int end = start;

        while (end < json.length()
                && json.charAt(end) != ','
                && json.charAt(end) != '}') {

            end++;
        }

        return json.substring(start, end).trim();
    }


    private static String escape(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }


    private static void addCors(
            HttpExchange exchange) {

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Origin",
                "*"
        );

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Methods",
                "GET, POST, DELETE, OPTIONS"
        );

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Headers",
                "Content-Type"
        );
    }


    private static void send(
            HttpExchange exchange,
            int status,
            String response) throws IOException {

        byte[] bytes =
                response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set(
                "Content-Type",
                "application/json"
        );

        exchange.sendResponseHeaders(
                status,
                bytes.length
        );

        try (OutputStream os =
                     exchange.getResponseBody()) {

            os.write(bytes);
        }
    }
}