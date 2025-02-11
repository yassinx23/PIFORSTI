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
        String sql = "INSERT INTO `jobs` (`title`, `description`, `posted_by`, `location`, `job_type`, `posted_date`, `expiry_date`, `is_active`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = cnx.prepareStatement(sql);

        // Définition des paramètres
        pst.setString(1, job.getTitle());
        pst.setString(2, job.getDescription());
        pst.setInt(3, job.getPostedBy());  // Correspond à `posted_by`
        pst.setString(4, job.getLocation());
        pst.setString(5, job.getJobType());
        pst.setDate(6, new java.sql.Date(job.getPostedDate().getTime()));
        pst.setDate(7, new java.sql.Date(job.getExpiryDate().getTime()));
        pst.setBoolean(8, job.isActive());

        // Exécution de la requête
        pst.executeUpdate();
        System.out.println("Job ajouté");
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
                    rs.getInt("posted_by"),  // Correspond à `posted_by`
                    rs.getString("location"),
                    rs.getString("job_type"),
                    rs.getDate("posted_date"),
                    rs.getDate("expiry_date"),
                    rs.getBoolean("is_active")
            );
            jobs.add(job);
        }
        return jobs;
    }

    @Override
    public void modifier(Job job) throws SQLException {
        String sql = "UPDATE `jobs` SET `title` = ?, `description` = ?, `posted_by` = ?, `location` = ?, `job_type` = ?, " +
                "`posted_date` = ?, `expiry_date` = ?, `is_active` = ? WHERE `job_id` = ?";

        PreparedStatement pst = cnx.prepareStatement(sql);

        // Définition des paramètres
        pst.setString(1, job.getTitle());
        pst.setString(2, job.getDescription());
        pst.setInt(3, job.getPostedBy());  // Correspond à `posted_by`
        pst.setString(4, job.getLocation());
        pst.setString(5, job.getJobType());
        pst.setDate(6, new java.sql.Date(job.getPostedDate().getTime()));
        pst.setDate(7, new java.sql.Date(job.getExpiryDate().getTime()));
        pst.setBoolean(8, job.isActive());
        pst.setInt(9, job.getJobId());  // Correspond à `job_id`

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
    public Job recupererParId(int jobId) throws SQLException {  // Implémentation de la méthode
        String sql = "SELECT * FROM `jobs` WHERE `job_id` = ?";
        PreparedStatement pst = cnx.prepareStatement(sql);
        pst.setInt(1, jobId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return new Job(
                    rs.getInt("job_id"),  // Correspond à `job_id`
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("posted_by"),  // Correspond à `posted_by`
                    rs.getString("location"),
                    rs.getString("job_type"),
                    rs.getDate("posted_date"),
                    rs.getDate("expiry_date"),
                    rs.getBoolean("is_active")
            );
        }
        return null; // Si aucun job n'est trouvé avec cet ID
    }
}
