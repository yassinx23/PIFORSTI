package com.esprit.Main.controllers;

import com.esprit.Main.models.Forum;
import com.esprit.Main.Service.ForumService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EditForumController {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField imageField;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    private Forum forum;
    private ForumService forumService = new ForumService();

    // Callback pour rafraîchir la ListView
    private Runnable refreshCallback;

    public void setRefreshCallback(Runnable refreshCallback) {
        this.refreshCallback = refreshCallback;
    }

    /**
     * Initialise les champs avec les valeurs du forum à éditer.
     */
    public void setForum(Forum forum) {
        this.forum = forum;
        titleField.setText(forum.getTitre());
        descriptionArea.setText(forum.getDescription());
        imageField.setText(forum.getImage());
    }

    /**
     * Met à jour le forum et rafraîchit la ListView.
     */
    @FXML
    private void handleUpdate(ActionEvent event) {
        forum.setTitre(titleField.getText());
        forum.setDescription(descriptionArea.getText());
        forum.setImage(imageField.getText());

        try {
            forumService.modifier(forum);
            // Rafraîchissement de la ListView via le callback
            if (refreshCallback != null) {
                refreshCallback.run();
            }

            // Charger forumAcc.fxml dans la même fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/forumAcc.fxml"));
            Parent forumAccRoot = loader.load();
            Stage stage = (Stage) updateButton.getScene().getWindow();
            stage.setScene(new Scene(forumAccRoot));
            stage.show();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Supprime le forum et rafraîchit la ListView.
     */
    @FXML
    private void handleDelete(ActionEvent event) {
        try {
            forumService.supprimer(forum);
            // Rafraîchissement de la ListView via le callback
            if (refreshCallback != null) {
                refreshCallback.run();
            }

            // Charger forumAcc.fxml dans la même fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/forumAcc.fxml"));
            Parent forumAccRoot = loader.load();
            Stage stage = (Stage) deleteButton.getScene().getWindow();
            stage.setScene(new Scene(forumAccRoot));
            stage.show();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


}
