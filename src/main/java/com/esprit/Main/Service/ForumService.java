package com.esprit.Main.Service;

import com.esprit.Main.models.Forum;
import com.esprit.Main.tools.MyDateBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ForumService implements IService<Forum> {

    private Connection cnx;

    public ForumService() {
        cnx = MyDateBase.getMyDataBase().getCnx();
    }

    @Override
    public void ajouter(Forum f) throws SQLException {
        String sql = "INSERT INTO `forum`(`forum_titre`, `forum_description`, `forum_image`) VALUES (?,?,?)";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, f.getTitre());
            pst.setString(2, f.getDescription());
            pst.setString(3, f.getImage());

            // Exécuter la mise à jour
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Forum ajouté avec succès!");
            } else {
                System.out.println("Erreur lors de l'ajout du forum.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du forum : " + e.getMessage());
            throw e;  // Propager l'exception
        }
    }

    @Override
    public void supprimer(Forum f) throws SQLException {
        String sql = "DELETE FROM `forum` WHERE forum_id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, f.getForumId());
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Forum supprimé avec succès!");
            } else {
                System.out.println("Forum non trouvé");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du forum : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void modifier(Forum f) throws SQLException {
        String sql = "UPDATE `forum` SET forum_titre = ?, forum_description = ?, forum_image = ? WHERE forum_id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, f.getTitre());
            pst.setString(2, f.getDescription());
            pst.setString(3, f.getImage());
            pst.setInt(4, f.getForumId());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Forum modifié avec succès!");
            } else {
                System.out.println("Forum non trouvé");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du forum : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Forum> recuperer() throws SQLException {
        String sql = "SELECT * FROM forum";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            List<Forum> forums = new ArrayList<>();
            while (rs.next()) {
                Forum f = new Forum();
                f.setForumId(rs.getInt("forum_id"));
                f.setTitre(rs.getString("forum_titre"));
                f.setDescription(rs.getString("forum_description"));
                f.setImage(rs.getString("forum_image"));
                forums.add(f);
            }
            return forums;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des forums : " + e.getMessage());
            throw e;
        }
    }

    // Méthode ajoutée pour retourner un ObservableList de forums
    public ObservableList<Forum> getForums() {
        try {
            List<Forum> forumsList = recuperer();
            return FXCollections.observableArrayList(forumsList);
        } catch (SQLException e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }
}
