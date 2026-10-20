package ui;

import dao.ScenarioDao;
import model.ScenarioEntity;
import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {
    private final ScenarioDao dao = new ScenarioDao();
    private final Scanner scanner = new Scanner(System.in);

    public void runMenuLoop() {
        boolean loopRunning = true;

        while (loopRunning) {
            // Renders structural screen layout matching required specification wireframes
            System.out.println("\n========================================================");
            System.out.println("\t\tCLINIC PATIENT APPOINTMENT REGISTRY");
            System.out.println("========================================================");
            System.out.println("[1] Add New Appointment");
            System.out.println("[2] View Scheduled Appointments");
            System.out.println("[3] Exit System");
            System.out.println("--------------------------------------------------------");
            System.out.print("Enter choice: ");

            String inputChoice = scanner.nextLine().trim();

            switch (inputChoice) {
                case "1" -> processInsertRecord();
                case "2" -> processDisplayRecords();
                case "3" -> {
                    loopRunning = false;
                    System.out.println("\nSystem setup closing... Thank you!");
                }
                default -> System.out.println("⚠️ Choice mismatch error. Enter a valid menu option [1-3].");
            }
        }
    }

    private void processInsertRecord() {
        System.out.println("\n--- BOOK APPOINTMENT ---");

        System.out.print("Enter Patient Name: ");
        String patient = scanner.nextLine().trim();

        System.out.print("Enter Doctor Assigned: ");
        String doc = scanner.nextLine().trim();

        System.out.print("Enter Consultation Fee: ");
        String baseFee = scanner.nextLine().trim();
        double parsedFee;

        try {
            parsedFee = Double.parseDouble(baseFee);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Processing error: Invalid digit conversion value. Transaction halted.");
            return;
        }

        System.out.print("Enter Status (SCHEDULED / COMPLETED): ");
        String bookingStatus = scanner.nextLine().trim().toUpperCase();

        // Enforce application validation criteria requirements
        if (!bookingStatus.equals("SCHEDULED") && !bookingStatus.equals("COMPLETED")) {
            System.out.println("⚠️ Validation Error: Status field must read 'SCHEDULED' or 'COMPLETED'.");
            return;
        }

        if (patient.isEmpty() || doc.isEmpty()) {
            System.out.println("⚠️ Error entry: Required text data spaces cannot be left blank.");
            return;
        }

        ScenarioEntity newEntry = new ScenarioEntity(patient, doc, parsedFee, bookingStatus);
        if (dao.addRecord(newEntry)) {
            System.out.println("\nSUCCESS: Appointment successfully booked!");
        }
    }

    private void processDisplayRecords() {
        List<ScenarioEntity> entries = dao.getAllRecords();

        System.out.println("\n=========================== CLINIC SCHEDULE APPOINTMENT RECORDS ===========================");
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-20s | %-20s | %-15s | %-15s |\n", "ID", "Patient Name", "Doctor", "Consult Fee", "Status");
        System.out.println("-------------------------------------------------------------------------------------------");

        if (entries.isEmpty()) {
            System.out.printf("| %-85s |\n", "No appointment records displayed.");
        } else {
            for (ScenarioEntity item : entries) {
                System.out.printf("| %-4d | %-20s | %-20s | PHP %-11.2f | %-15s |\n",
                        item.id(),
                        item.patientName(),
                        item.doctor(),
                        item.consultFee(),
                        item.status());
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Total Records Displayed: " + entries.size());
    }
}
