package com.esprit.Main.Service;

import com.esprit.Main.models.Message;
import com.esprit.Main.models.Forum;
import com.esprit.Main.tools.MyDateBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageService implements IService<Message> {

    private Connection cnx;

    public MessageService() {
        cnx = MyDateBase.getMyDataBase().getCnx();
    }

    @Override
    public void ajouter(Message m) throws SQLException {
        if (m.getForum() == null || m.getForum().getForumId() == 0) {
            System.out.println("Erreur : Le forum associé est NULL ou son ID est invalide !");
            return;
        }

        String sql = "INSERT INTO `message`(`message_contenu`, `message_forum_id`) VALUES (?, ?)";
        PreparedStatement pst = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pst.setString(1, m.getContenu());
        pst.setInt(2, m.getForum().getForumId());

        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                m.setMessageId(rs.getInt(1));
            }
            System.out.println("Message ajouté avec succès !");
        } else {
            System.out.println("Erreur lors de l'ajout du message.");
        }
    }

    @Override
    public void supprimer(Message m) throws SQLException {
        String sql = "DELETE FROM `message` WHERE message_id = ?";
        PreparedStatement pst = cnx.prepareStatement(sql);
        pst.setInt(1, m.getMessageId());
        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Message supprimé");
        } else {
            System.out.println("Message non trouvé");
        }
    }

    @Override
    public void modifier(Message m) throws SQLException {
        String sql = "UPDATE `message` SET message_contenu = ? WHERE message_id = ?";
        PreparedStatement pst = cnx.prepareStatement(sql);
        pst.setString(1, m.getContenu());
        pst.setInt(2, m.getMessageId());
        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Message modifié");
        } else {
            System.out.println("Message non trouvé");
        }
    }

    @Override
    public List<Message> recuperer() throws SQLException {
        String sql = "SELECT * FROM message";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Message> messages = new ArrayList<>();

        while (rs.next()) {
            Message m = new Message();
            m.setMessageId(rs.getInt("message_id"));
            m.setContenu(rs.getString("message_contenu"));

            int forumId = rs.getInt("message_forum_id");
            Forum forum = new Forum();
            forum.setForumId(forumId);
            m.setForum(forum);

            messages.add(m);
        }
        return messages;
    }

    // ✅ Ajout de la méthode pour récupérer les messages d'un forum spécifique
    public List<Message> recupererMessagesParForum(int forumId) throws SQLException {
        String sql = "SELECT * FROM message WHERE message_forum_id = ?";
        PreparedStatement pst = cnx.prepareStatement(sql);
        pst.setInt(1, forumId);
        ResultSet rs = pst.executeQuery();

        List<Message> messages = new ArrayList<>();
        while (rs.next()) {
            Message m = new Message();
            m.setMessageId(rs.getInt("message_id"));
            m.setContenu(rs.getString("message_contenu"));

            Forum forum = new Forum();
            forum.setForumId(forumId);
            m.setForum(forum);

            messages.add(m);
        }
        return messages;
    }
}
