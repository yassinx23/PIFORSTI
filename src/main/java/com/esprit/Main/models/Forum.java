package com.esprit.Main.models;

import com.esprit.Main.User;

import java.util.List;

public class Forum {
    private int forumId;
    private String titre;
    private String description;
    private String image;
    private List<User> utilisateurs;
    private List<Message> messages;
   // private List<Reclamation> reclamations;

    public Forum() {}

    public Forum(String titre, String description, String image) {
        this.titre = titre;
        this.description = description;
        setImage(image);
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) { this.image = image;}

    public List<User> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<User> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

   /* public List<Reclamation> getReclamations() {
        return reclamations;
    }

    public void setReclamations(List<Reclamation> reclamations) {
        this.reclamations = reclamations;
    }
*/
    @Override
    public String toString() {
        return "Forum{" +
                "forumId=" + forumId +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", utilisateurs=" + utilisateurs +
                ", messages=" + messages +
                //", reclamations=" + reclamations +
                '}';
    }
}
