package Service;

import models.Job;
import tools.MyDateBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobService implements IJobService {

    private Connection cnx;

    public JobService() {
        cnx = MyDateBase.getMyDataBase().getCnx();
    }

    @Override
    public void ajouter(Job job) throws SQLException {
        String sql = "INSERT INTO jobs (title, description, location, job_type) " +
                "VALUES (?, ?, ?, ?)";

        PreparedStatement pst = cnx.prepareStatement(sql);
        pst.setString(1, job.getTitle());
        pst.setString(2, job.getDescription());
        pst.setString(3, job.getLocation());
        pst.setString(4, job.getJobType());

        System.out.println("Tentative d'ajout du job : " + job.toString()); // Affiche les détails du job
        System.out.println("Requête SQL : " + pst.toString()); // Affiche la requête SQL avec les valeurs

        pst.executeUpdate();
        System.out.println("Job ajouté avec succès !");
    }


    @Override
    public List<Job> recuperer() throws SQLException {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM `jobs`";
        Statement statement = cnx.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            Job job = new Job(
                    rs.getInt("job_id"),  // Correspond à `job_id`
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("job_type")
            );
            jobs.add(job);
            System.out.println("Job ajouté : " + job.getTitle());  // Affiche le titre du job ajouté
        }
        System.out.println("Total jobs récupérés : " + jobs.size());  // Affiche le nombre total de jobs récupérés
        return jobs;
    }

    @Override
    public void modifier(Job job) throws SQLException {
        String sql = "UPDATE `jobs` SET `title` = ?, `description` = ?, `location` = ?, `job_type` = ? WHERE `job_id` = ?";

        PreparedStatement pst = cnx.prepareStatement(sql);

        // Définition des paramètres
        pst.setString(1, job.getTitle());
        pst.setString(2, job.getDescription());
        pst.setString(3, job.getLocation());
        pst.setString(4, job.getJobType());
        pst.setInt(5, job.getJobId());  // Correspond à `job_id`

        // Exécution de la requête
        pst.executeUpdate();
        System.out.println("Job modifié");
    }

    @Override
    public void supprimer(int jobId) throws SQLException {
        String sql = "DELETE FROM `jobs` WHERE `job_id` = ?";
        PreparedStatement pst = cnx.prepareStatement(sql);
        pst.setInt(1, jobId);
        pst.executeUpdate();
        System.out.println("Job supprimé");
    }

    @Override
    public List<Job> recupererAll() throws SQLException {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM `jobs`"; // La requête pour récupérer tous les jobs
        Statement statement = cnx.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            Job job = new Job(
                    rs.getInt("job_id"),  // Correspond à `job_id`
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("job_type")
            );
            jobs.add(job);
            System.out.println("Job récupéré : " + job.getTitle());  // Affiche le titre du job récupéré
        }
        return jobs;
    }

    @Override
    public Job recupererParId(int jobId) throws SQLException {
        String sql = "SELECT * FROM `jobs` WHERE `job_id` = ?";
        PreparedStatement pst = cnx.prepareStatement(sql);
        pst.setInt(1, jobId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return new Job(
                    rs.getInt("job_id"),  // Correspond à `job_id`
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("job_type")
            );
        }
        return null; // Si aucun job n'est trouvé avec cet ID
    }
}
