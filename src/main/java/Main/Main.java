package Main;

import models.Applications;
import tools.MyDateBase;
import Service.ApplicationService;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        // Convert LocalDate to java.sql.Date
        LocalDate localDate = LocalDate.now();
        Date appliedDate = Date.valueOf(localDate); // Conversion de LocalDate en java.sql.Date

        // Create an Application instance
        Applications ap = new Applications(2, 102, appliedDate, "refusé", "This is a sample cover letter.");

        // Initialize ApplicationService
        ApplicationService applicationService = new ApplicationService();

        try {
            // Try adding the application to the database
            applicationService.ajouter(ap);
            System.out.println("Application added successfully.");
        } catch (SQLException e) {
            System.err.println("An error occurred while adding the application: " + e.getMessage());
        }
    }
}
