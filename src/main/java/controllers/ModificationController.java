package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Service.ApplicationService;
import models.Applications;
import java.sql.SQLException;

public class ModificationController {

    @FXML
    private TextField statusField;       // Statut de la candidature (lecture seule)
    @FXML
    private TextArea coverLetterField;   // Lettre de motivation mise à jour

    private Applications application;    // L'objet Applications à modifier
    private final ApplicationService applicationService; // Service pour mettre à jour la base de données

    public ModificationController() {
        applicationService = new ApplicationService(); // Initialisation du service
    }

    /**
     * Initialise les champs avec les données de la candidature sélectionnée.
     * Le statut est verrouillé pour éviter toute modification.
     */
    public void setApplication(Applications application) {
        this.application = application;

        statusField.setText(application.getStatus());
        coverLetterField.setText(application.getCoverLetter());

        // Rendre le champ "Statut" intouchable
        statusField.setEditable(false);
        statusField.setDisable(true);
    }

    /**
     * Met à jour la lettre de motivation de la candidature.
     */
    @FXML
    private void updateApplication() {
        String coverLetter = coverLetterField.getText().trim();

        // Vérification : La lettre de motivation ne doit pas être vide
        if (coverLetter.isEmpty()) {
            showErrorAlert("Erreur", "La lettre de motivation ne peut pas être vide.");
            return;
        }

        // Mise à jour des données
        application.setCoverLetter(coverLetter);

        try {
            applicationService.modifier(application); // Mise à jour dans la base de données
            showSuccessAlert("Succès", "Votre candidature a été mise à jour avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Erreur lors de la mise à jour", e.getMessage());
        }
    }

    /**
     * Affiche une alerte d'erreur.
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Affiche une alerte de succès.
     */
    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
