package com.esprit.Main.controllers;

import com.esprit.Main.models.Message;
import com.esprit.Main.models.Forum;
import com.esprit.Main.Service.MessageService;

import java.sql.SQLException;
import java.util.List;

public class MessageController {
    private MessageService messageService;

    public MessageController() {
        this.messageService = new MessageService();
    }

    // Ajouter un message
    public void ajouterMessage(String contenu, Forum forum) {
        try {
            Message message = new Message(contenu, forum);
            messageService.ajouter(message);
            System.out.println("Message ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du message : " + e.getMessage());
        }
    }

    // Supprimer un message par son ID
    public void supprimerMessage(int messageId) {
        try {
            Message message = new Message();
            message.setMessageId(messageId);
            messageService.supprimer(message);
            System.out.println("Message supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du message : " + e.getMessage());
        }
    }

    // Modifier un message
    public void modifierMessage(int messageId, String nouveauContenu) {
        try {
            Message message = new Message();
            message.setMessageId(messageId);
            message.setContenu(nouveauContenu);
            messageService.modifier(message);
            System.out.println("Message modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du message : " + e.getMessage());
        }
    }

    // Récupérer tous les messages
    public void recupererMessages() {
        try {
            List<Message> messages = messageService.recuperer();
            for (Message m : messages) {
                System.out.println(m);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des messages : " + e.getMessage());
        }
    }
}
