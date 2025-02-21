package Main;

import Service.JobService;
import models.Job;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JobService jobService = new JobService();

        try {
            // Tester la récupération des données
            List<Job> jobs = jobService.recupererAll();  // Utiliser recupererAll() au lieu de recuperer()

            // Afficher les données récupérées
            for (Job job : jobs) {
                System.out.println("Job ID: " + job.getJobId());
                System.out.println("Title: " + job.getTitle());
                System.out.println("Description: " + job.getDescription());
                System.out.println("Location: " + job.getLocation());
                System.out.println("Job Type: " + job.getJobType());
                System.out.println("-------------------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des jobs : " + e.getMessage());
        }
    }
}
