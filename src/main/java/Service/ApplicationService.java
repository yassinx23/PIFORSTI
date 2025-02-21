package Service;

import models.Applications;
import tools.MyDateBase;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ApplicationService implements IService<Applications> {

    private Connection cnx;

    public ApplicationService() {
        cnx = MyDateBase.getMyDataBase().getCnx();
    }

    @Override
    public void ajouter(Applications ap) throws SQLException {
        String sql = "INSERT INTO `applications`(`user_id`, `job_id`, `applied_date`, `status`, `cover_letter`) VALUES (?,?,?,?,?)";
        PreparedStatement pst = cnx.prepareStatement(sql);

        pst.setInt(1, ap.getUserId()); // user_id
        pst.setInt(2, ap.getJobId()); // job_id

        // Conversion de java.util.Date en java.sql.Date
        java.sql.Date sqlDate = (ap.getAppliedDate() != null) ? new java.sql.Date(ap.getAppliedDate().getTime()) : null;
        pst.setDate(3, sqlDate); // applied_date

        pst.setString(4, ap.getStatus()); // status
        pst.setString(5, ap.getCoverLetter()); // cover_letter

        pst.executeUpdate();
        System.out.println("Application ajoutée");
    }

    @Override
    public void supprimer(int applicationId) throws SQLException {
        String sql = "DELETE FROM `applications` WHERE application_id =?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, applicationId);
        ps.executeUpdate();
    }

    @Override
    public void modifier(Applications a) throws SQLException {
        String sql = "UPDATE `applications` SET `user_id` = ?, `job_id` = ?, `applied_date` = ?, `status` = ?, `cover_letter` = ? WHERE `application_id` = ?";
        PreparedStatement pst = cnx.prepareStatement(sql);

        pst.setInt(1, a.getUserId());         // user_id
        pst.setInt(2, a.getJobId());          // job_id

        // Conversion de java.util.Date en java.sql.Date
        java.sql.Date sqlDate = (a.getAppliedDate() != null) ? new java.sql.Date(a.getAppliedDate().getTime()) : null;
        pst.setDate(3, sqlDate); // applied_date

        pst.setString(4, a.getStatus());      // status
        pst.setString(5, a.getCoverLetter()); // cover_letter
        pst.setInt(6, a.getApplicationId());  // application_id (condition WHERE)

        pst.executeUpdate();
    }

    @Override
    public List<Applications> recuperer() throws SQLException {
        List<Applications> applications = new ArrayList<>();
        String sql = "SELECT * FROM `applications`";
        Statement statement = cnx.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            Applications app = new Applications(
                    rs.getInt("user_id"),
                    rs.getInt("job_id"),
                    rs.getDate("applied_date"),
                    rs.getString("status"),
                    rs.getString("cover_letter")
            );
            applications.add(app);
            System.out.println("Application récupérée : " + app.getUserId() + " " + app.getJobId());
        }
        System.out.println("Total applications récupérées : " + applications.size());
        return applications;
    }

    // Implémentation de recupererAll() pour correspondre à l'interface
    @Override
    public List<Applications> recupererAll() throws SQLException {
        return recuperer(); // Retourne la liste des applications obtenues par la méthode recuperer
    }
}
