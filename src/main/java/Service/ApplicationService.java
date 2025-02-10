package Service;

import models.Applications;
import tools.MyDateBase;

import java.sql.*;
import java.util.List;

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
    public void supprimer(Applications a) {
        // Implémentation vide pour ne pas être obligé de l'implémenter pour l'instant
    }

    @Override
    public void modifier(Applications a) {
        // Implémentation vide pour ne pas être obligé de l'implémenter pour l'instant
    }

    @Override
    public List<Applications> recuperer() throws SQLException {
        // Implémentation vide pour ne pas implémenter cette méthode pour l'instant
        return null;
    }
}
