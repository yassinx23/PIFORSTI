package com.esprit.Main.models;
import com.esprit.Main.User;


public class Message {
    private int messageId;
    private String contenu;
    private User auteur;
    private Forum forum;


    public Message() {}

    public Message(String contenu, Forum forum) {
        this.contenu = contenu;

        this.forum = forum;
    }

    public int getMessageId() { return messageId;}
    public void setMessageId(int messageId) { this.messageId = messageId;}


    public String getContenu() {return contenu;}
    public void setContenu(String contenu) { this.contenu = contenu;}

    public User getAuteur() {return auteur;}
    public void setAuteur(User auteur) { this.auteur = auteur;}

    public Forum getForum() {return forum;}
    public void setForum(Forum forum) {this.forum = forum;}

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", contenu='" + contenu + '\'' +

                ", forum=" + forum +
                '}';
    }

}
