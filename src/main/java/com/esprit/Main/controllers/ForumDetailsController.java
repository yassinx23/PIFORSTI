package com.esprit.Main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.esprit.Main.Service.MessageService;
import com.esprit.Main.models.Forum;
import com.esprit.Main.models.Message;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ForumDetailsController {

    @FXML
    private ListView<Message> messageListView;

    @FXML
    private TextArea messageField;

    @FXML
    private Button sendButton;

    @FXML
    private Button updateButton;  // Bouton pour mettre à jour un message

    @FXML
    private Button deleteButton;  // Bouton pour supprimer un message

    @FXML
    private Hyperlink backToForumAccLink;

    private MessageService messageService = new MessageService();
    private Forum currentForum;
    private Message selectedMessage;

    @FXML
    public void initialize() {
        // Action sur le lien de retour
        backToForumAccLink.setOnAction(this::handleBackToForumAcc);

        // Ajouter l'action du bouton pour envoyer le message
        sendButton.setOnAction(this::handleSendMessage);

        // Ajouter l'action pour mettre à jour un message
        updateButton.setOnAction(this::handleUpdateMessage);

        // Ajouter l'action pour supprimer un message
        deleteButton.setOnAction(this::handleDeleteMessage);

        // Double-clic sur un message pour le modifier
        messageListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {  // Vérifier le double-clic
                Message message = messageListView.getSelectionModel().getSelectedItem();
                if (message != null) {
                    selectedMessage = message;
                    messageField.setText(message.getContenu());  // Mettre le contenu dans le champ de texte
                    updateButton.setVisible(true);  // Afficher le bouton de mise à jour
                    deleteButton.setVisible(true);  // Afficher le bouton de suppression
                    sendButton.setVisible(false);  // Cacher le bouton "Envoyer"
                }
            }
        });
    }

    public void setForum(Forum forum) {
        this.currentForum = forum;
        loadMessages();
    }

    private void loadMessages() {
        try {
            // Récupération des messages pour le forum actuel
            List<Message> messages = messageService.recupererMessagesParForum(currentForum.getForumId());
            ObservableList<Message> observableMessages = FXCollections.observableArrayList(messages);
            messageListView.setItems(observableMessages);

            // Personnalisation de la cellule pour ajouter un lien "Réclamation" et le nom de l'auteur
            messageListView.setCellFactory(listView -> new ListCell<Message>() {
                @Override
                protected void updateItem(Message message, boolean empty) {
                    super.updateItem(message, empty);
                    if (empty || message == null) {
                        setText(null);
                        setGraphic(null); // Si la cellule est vide, aucun texte ni graphique
                    } else {
                        String content = message.getContenu();
                        String authorName = message.getAuteur() != null ? message.getAuteur().getName() : "Anonyme";
                        setText(authorName + ": " + content); // Afficher le nom de l'auteur et le contenu du message

                        // Créer un lien pour effectuer une réclamation, pour tous les messages
                        Hyperlink link = new Hyperlink("Signaler");
                        link.setOnAction(event -> handleReclamationClick(message));  // Action pour le lien
                        setGraphic(link);  // Affecter le lien à la cellule
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void handleSendMessage(ActionEvent event) {
        String content = messageField.getText().trim();

        if (!content.isEmpty()) {
            Message newMessage = new Message();
            newMessage.setContenu(content);
            newMessage.setForum(currentForum); // Associer le message au forum actuel

            try {
                // Utiliser la méthode 'ajouter' et non 'ajouterMessage'
                messageService.ajouter(newMessage);

                // Rafraîchir la liste des messages pour inclure celui nouvellement ajouté
                loadMessages();  // Mettre à jour la vue avec les nouveaux messages
                messageField.clear();  // Vider le champ de texte
            } catch (SQLException e) {
                e.printStackTrace();
                // Afficher un message d'erreur
                System.out.println("Erreur lors de l'envoi du message !");
            }
        } else {
            // Afficher une alerte ou un message si le contenu est vide
            System.out.println("Veuillez entrer un contenu pour votre message.");
        }
    }

    private void handleBackToForumAcc(ActionEvent event) {
        try {
            // Charger la scène forumAcc.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/forumAcc.fxml"));
            Parent forumAccRoot = loader.load();
            Stage stage = (Stage) backToForumAccLink.getScene().getWindow();
            stage.setScene(new Scene(forumAccRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Action quand le lien de réclamation est cliqué
    private void handleReclamationClick(Message message) {
        System.out.println("Réclamation pour le message: " + message.getContenu());
        // Ajouter ici l'action à effectuer lors du clic sur le lien
        // Par exemple, ouvrir une fenêtre de réclamation ou envoyer un email, etc.
    }

    // Mettre à jour un message
    private void handleUpdateMessage(ActionEvent event) {
        if (selectedMessage != null) {
            String updatedContent = messageField.getText().trim();
            if (!updatedContent.isEmpty()) {
                selectedMessage.setContenu(updatedContent);  // Mettre à jour le contenu du message
                try {
                    messageService.modifier(selectedMessage);  // Appeler la méthode pour modifier le message
                    loadMessages();  // Rafraîchir la liste des messages
                    messageField.clear();  // Vider le champ de texte
                    updateButton.setVisible(false);  // Masquer le bouton de mise à jour après la modification
                    deleteButton.setVisible(false);  // Masquer le bouton de suppression après la modification
                    sendButton.setVisible(true);  // Rendre visible à nouveau "Envoyer"
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Le message ne peut pas être vide.");
            }
        }
    }

    // Supprimer un message
    private void handleDeleteMessage(ActionEvent event) {
        if (selectedMessage != null) {
            try {
                messageService.supprimer(selectedMessage);  // Appeler la méthode pour supprimer le message
                loadMessages();  // Rafraîchir la liste des messages
                messageField.clear();  // Vider le champ de texte
                updateButton.setVisible(false);  // Masquer le bouton de mise à jour après la suppression
                deleteButton.setVisible(false);  // Masquer le bouton de suppression après la suppression
                sendButton.setVisible(true);  // Rendre visible à nouveau "Envoyer"
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
