package Service;

import models.Applications;
import tools.MyDateBase;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class ApplicationService implements IService<Applications>  {

    private Connection cnx ;

    public ApplicationService() {
        cnx = MyDateBase.getMyDataBase().getCnx();
    }

    @Override
    public void ajouter(Applications ap) throws SQLException {
        /*String sql = "INSERT INTO `applications`(`user_id`, `job_id`, `applied_date`, `status`, `cover_letter`) " +
                "VALUES (" + ap.getUserId() + ", " + ap.getJobId() + ", '" +
                ap.getAppliedDate() + "', '" + ap.getStatus() + "', '" +
                ap.getCoverLetter() + "')";

        Statement st = cnx.createStatement();
        st.executeUpdate(sql);
        System.out.println("Application ajoutée");*/
        String sql = "INSERT INTO `applications`(`user_id`, `job_id`, `applied_date`, `status`, `cover_letter`) " +
                "VALUES (?,?,?,?,?)";
        PreparedStatement pst = cnx.prepareStatement(sql);

        // Définition des valeurs des paramètres
        pst.setInt(1, ap.getUserId()); // user_id
        pst.setInt(2, ap.getJobId()); // job_id
        pst.setDate(3, (Date) ap.getAppliedDate()); // applied_date
        pst.setString(4, ap.getStatus()); // status
        pst.setString(5, ap.getCoverLetter()); // cover_letter

        // Exécution de la requête
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

        // Définition des valeurs des paramètres
        pst.setInt(1, a.getUserId());         // user_id
        pst.setInt(2, a.getJobId());          // job_id
        pst.setDate(3, (Date) a.getAppliedDate()); // applied_date
        pst.setString(4, a.getStatus());      // status
        pst.setString(5, a.getCoverLetter()); // cover_letter
        pst.setInt(6, a.getApplicationId());             // id (condition WHERE)
        pst.executeUpdate();
    }

    @Override
    public List<Applications> recuperer() throws SQLException {
        List<Applications> applications = new ArrayList<>();
        String sql = "SELECT * FROM `applications`";  // La requête SQL pour récupérer toutes les applications
        Statement statement = cnx.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        // Parcours des résultats et ajout des applications à la liste
        while (rs.next()) {
            applications.add(new Applications(
                    rs.getInt("user_id"),         // user_id
                    rs.getInt("job_id"),          // job_id
                    rs.getDate("applied_date"),   // applied_date
                    rs.getString("status"),       // status
                    rs.getString("cover_letter")  // cover_letter
            ));
        }

        // Retourne la liste des applications récupérées
        return applications;
    }

}
