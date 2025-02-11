package Main;

import models.Job;
import Service.JobService;
import tools.MyDateBase;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JobService jobService = new JobService();

        try {
            // Ajouter un job
            Job job = new Job(0, "Développeur Java", "Développement d'applications", 1, "Paris", "CDI",
                    new java.util.Date(), new java.util.Date(), true);
            jobService.ajouter(job);
            System.out.println("Job ajouté");

            // Modifier un job
            job.setTitle("Développeur Full Stack");
            jobService.modifier(job);
            System.out.println("Job modifié");

            // Récupérer tous les jobs
            List<Job> jobs = jobService.recuperer();
            jobs.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
