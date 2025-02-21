package com.esprit.Main.controllers;

import com.esprit.Main.models.Forum;
import com.esprit.Main.Service.ForumService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import java.io.IOException;

public class ForumAccController {

    @FXML
    private ListView<Forum> forumListView;

    @FXML
    private Button createForumButton;

    private ForumService forumService = new ForumService();

    @FXML
    public void initialize() {
        // Charger la liste des forums
        ObservableList<Forum> forums = forumService.getForums();
        forumListView.setItems(forums);

        // Personnalisation de la ListView pour afficher le titre du forum et un Hyperlink pour les détails
        forumListView.setCellFactory(listView -> new ListCell<Forum>() {
            @Override
            protected void updateItem(Forum forum, boolean empty) {
                super.updateItem(forum, empty);
                if (empty || forum == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(forum.getTitre());

                    // Créer un Hyperlink pour accéder aux détails
                    Hyperlink detailsLink = new Hyperlink("Rejoindre");
                    detailsLink.setOnAction(event -> openForumDetails(forum));
                    setGraphic(detailsLink); // Afficher le lien dans la cellule
                }
            }
        });

        // Détecter le clic simple sur un forum pour modification
        forumListView.setOnMouseClicked(this::handleForumClick);

        // Clic sur le bouton "Créer un forum"
        createForumButton.setOnAction(event -> openCreateForum());
    }

    // Ouvrir les détails du forum et afficher les messages
    private void openForumDetails(Forum forum) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/forumDetails.fxml"));
            Parent detailsRoot = loader.load();
            ForumDetailsController detailsController = loader.getController();
            detailsController.setForum(forum);

            Stage stage = (Stage) forumListView.getScene().getWindow();
            stage.setScene(new Scene(detailsRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ouvrir l'éditeur du forum pour modification
    private void handleForumClick(MouseEvent event) {
        Forum selectedForum = forumListView.getSelectionModel().getSelectedItem();

        if (selectedForum != null) {
            // Clic simple : ouvrir l'éditeur de forum pour modification
            openEditForum(selectedForum);
        }
    }

    private void openEditForum(Forum forum) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editForum.fxml"));
            Parent editRoot = loader.load();
            EditForumController editController = loader.getController();
            editController.setForum(forum);
            editController.setRefreshCallback(this::refreshListView);

            // Obtenir le stage actuel et changer la scène
            Stage stage = (Stage) forumListView.getScene().getWindow();  // Récupérer le stage actuel
            stage.setScene(new Scene(editRoot));  // Remplacer la scène actuelle par l'éditeur de forum
            stage.show();  // Afficher la nouvelle scène
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Ouvrir la page de création de forum (forum.fxml)
    private void openCreateForum() {
        try {
            // Charger le fichier FXML de création de forum
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum.fxml"));
            Parent createForumRoot = loader.load();

            // Obtenir la scène actuelle et la changer
            Stage stage = (Stage) createForumButton.getScene().getWindow();  // Récupérer le stage actuel
            stage.setScene(new Scene(createForumRoot));  // Remplacer la scène actuelle par celle de création
            stage.show();  // Afficher la scène mise à jour

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Rafraîchir la liste des forums après modification
    private void refreshListView() {
        forumListView.setItems(forumService.getForums());
    }
}
