import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n=================================");
            System.out.println("   HOSPITAL MANAGEMENT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Patient Management");
            System.out.println("2. Doctor Management");
            System.out.println("3. Appointment Management");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            // ================= PATIENT =================

            if (choice == 1) {

                while (true) {

                    System.out.println("\n========== PATIENT MANAGEMENT ==========");
                    System.out.println("1. Add Patient");
                    System.out.println("2. View Patients");
                    System.out.println("3. Update Patient");
                    System.out.println("4. Delete Patient");
                    System.out.println("5. Back");
                    System.out.print("Enter your choice: ");

                    int patientChoice = Integer.parseInt(sc.nextLine());

                    if (patientChoice == 1) {

                        System.out.println("\n========== ADD PATIENT ==========");

                        System.out.print("Enter patient name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter age: ");
                        int age = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter gender: ");
                        String gender = sc.nextLine();

                        System.out.print("Enter phone: ");
                        String phone = sc.nextLine();

                        System.out.print("Enter disease: ");
                        String disease = sc.nextLine();

                        Patient patient =
                                new Patient(name, age, gender, phone, disease);

                        patient.addPatient();

                    } else if (patientChoice == 2) {

                        Patient.viewPatients();

                    } else if (patientChoice == 3) {

                        System.out.print("Enter patient ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter new name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter new age: ");
                        int age = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter new gender: ");
                        String gender = sc.nextLine();

                        System.out.print("Enter new phone: ");
                        String phone = sc.nextLine();

                        System.out.print("Enter new disease: ");
                        String disease = sc.nextLine();

                        Patient.updatePatient(
                                id, name, age, gender, phone, disease
                        );

                    } else if (patientChoice == 4) {

                        System.out.print("Enter patient ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        Patient.deletePatient(id);

                    } else if (patientChoice == 5) {

                        break;

                    } else {

                        System.out.println("Invalid choice!");
                    }
                }

            // ================= DOCTOR =================

            } else if (choice == 2) {

                while (true) {

                    System.out.println("\n========== DOCTOR MANAGEMENT ==========");
                    System.out.println("1. Add Doctor");
                    System.out.println("2. View Doctors");
                    System.out.println("3. Update Doctor");
                    System.out.println("4. Delete Doctor");
                    System.out.println("5. Back");
                    System.out.print("Enter your choice: ");

                    int doctorChoice = Integer.parseInt(sc.nextLine());

                    if (doctorChoice == 1) {

                        System.out.println("\n========== ADD DOCTOR ==========");

                        System.out.print("Enter doctor name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter specialization: ");
                        String specialization = sc.nextLine();

                        System.out.print("Enter phone: ");
                        String phone = sc.nextLine();

                        Doctor doctor =
                                new Doctor(name, specialization, phone);

                        doctor.addDoctor();

                    } else if (doctorChoice == 2) {

                        Doctor.viewDoctors();

                    } else if (doctorChoice == 3) {

                        System.out.print("Enter doctor ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter new name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter new specialization: ");
                        String specialization = sc.nextLine();

                        System.out.print("Enter new phone: ");
                        String phone = sc.nextLine();

                        Doctor.updateDoctor(
                                id, name, specialization, phone
                        );

                    } else if (doctorChoice == 4) {

                        System.out.print("Enter doctor ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        Doctor.deleteDoctor(id);

                    } else if (doctorChoice == 5) {

                        break;

                    } else {

                        System.out.println("Invalid choice!");
                    }
                }

            // ================= APPOINTMENT =================

            } else if (choice == 3) {

                while (true) {

                    System.out.println("\n========== APPOINTMENT MANAGEMENT ==========");
                    System.out.println("1. Book Appointment");
                    System.out.println("2. View Appointments");
                    System.out.println("3. Update Appointment");
                    System.out.println("4. Delete Appointment");
                    System.out.println("5. Back");
                    System.out.print("Enter your choice: ");

                    int appointmentChoice =
                            Integer.parseInt(sc.nextLine());

                    if (appointmentChoice == 1) {

                        System.out.println("\n========== BOOK APPOINTMENT ==========");

                        System.out.print("Enter patient ID: ");
                        int patientId =
                                Integer.parseInt(sc.nextLine());

                        System.out.print("Enter doctor ID: ");
                        int doctorId =
                                Integer.parseInt(sc.nextLine());

                        System.out.print("Enter appointment date (YYYY-MM-DD): ");
                        String date = sc.nextLine();

                        System.out.print("Enter appointment time (HH:MM:SS): ");
                        String time = sc.nextLine();

                        System.out.print("Enter reason: ");
                        String reason = sc.nextLine();

                        Appointment appointment =
                                new Appointment(
                                        patientId,
                                        doctorId,
                                        date,
                                        time,
                                        reason
                                );

                        appointment.bookAppointment();

                    } else if (appointmentChoice == 2) {

                        Appointment.viewAppointments();

                    } else if (appointmentChoice == 3) {

                        System.out.print("Enter appointment ID: ");
                        int appointmentId =
                                Integer.parseInt(sc.nextLine());

                        System.out.print("Enter patient ID: ");
                        int patientId =
                                Integer.parseInt(sc.nextLine());

                        System.out.print("Enter doctor ID: ");
                        int doctorId =
                                Integer.parseInt(sc.nextLine());

                        System.out.print("Enter new date (YYYY-MM-DD): ");
                        String date = sc.nextLine();

                        System.out.print("Enter new time (HH:MM:SS): ");
                        String time = sc.nextLine();

                        System.out.print("Enter new reason: ");
                        String reason = sc.nextLine();

                        Appointment.updateAppointment(
                                appointmentId,
                                patientId,
                                doctorId,
                                date,
                                time,
                                reason
                        );

                    } else if (appointmentChoice == 4) {

                        System.out.print("Enter appointment ID: ");
                        int appointmentId =
                                Integer.parseInt(sc.nextLine());

                        Appointment.deleteAppointment(appointmentId);

                    } else if (appointmentChoice == 5) {

                        break;

                    } else {

                        System.out.println("Invalid choice!");
                    }
                }

            // ================= EXIT =================

            } else if (choice == 4) {

                System.out.println(
                        "Thank you for using Hospital Management System!"
                );

                sc.close();
                return;

            } else {

                System.out.println("Invalid choice!");
            }
        }
    }
}