package com.esprit.Main.controllers;

import com.esprit.Main.models.Forum;
import com.esprit.Main.Service.ForumService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ForumController {

    private ForumService forumService;

    @FXML
    private TextField titreField;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button imageButton;

    @FXML
    private ImageView imageView;

    @FXML
    private Button confirmerButton;

    @FXML
    private Hyperlink forumAccHyperlink;

    // Label pour afficher le message d'erreur de saisie
    @FXML
    private Label errorLabel;

    private String imagePath;

    public ForumController() {
        this.forumService = new ForumService();
    }

    @FXML
    public void initialize() {
        imageButton.setOnAction(event -> choisirImage());
        confirmerButton.setOnAction(event -> ajouterForum());
        forumAccHyperlink.setOnAction(event -> redirectToForumAcc());
        System.out.println("ForumController initialisé et les boutons/hyperlink sont configurés.");
    }

    private void choisirImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath();
            System.out.println("Chemin de l'image : " + imagePath);
            imageView.setImage(new javafx.scene.image.Image("file:" + imagePath));
        }
    }

    private void ajouterForum() {
        String titre = titreField.getText();
        String description = descriptionField.getText();

        // Réinitialisation du message d'erreur
        errorLabel.setText("");

        if (titre.isEmpty() || description.isEmpty() || imagePath == null) {
            // Afficher le message d'erreur dans le label de la page
            errorLabel.setText("Veuillez remplir tous les champs et sélectionner une image.");
            return;
        }
        Forum forum = new Forum(titre, description, imagePath);
        try {
            forumService.ajouter(forum);
            System.out.println("Forum ajouté avec succès : " + forum);
            // Après l'ajout, redirection vers forumAcc.fxml
            redirectToForumAcc();
        } catch (SQLException e) {
            errorLabel.setText("Erreur lors de l'ajout du forum : " + e.getMessage());
            System.out.println("Erreur lors de l'ajout du forum : " + e.getMessage());
        }
    }

    private void redirectToForumAcc() {
        try {
            Parent forumAccRoot = FXMLLoader.load(getClass().getResource("/forumAcc.fxml"));
            Scene scene = new Scene(forumAccRoot);
            Stage stage = (Stage) confirmerButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
