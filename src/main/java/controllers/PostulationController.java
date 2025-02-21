package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import models.Job;
import Service.ApplicationService;
import models.Applications;

import java.io.IOException;
import java.util.Date;

public class PostulationController {

    @FXML
    private TextField jobNameField;
    @FXML
    private TextArea jobDescriptionField;
    @FXML
    private TextField jobLocationField;
    @FXML
    private TextField jobTypeField;
    @FXML
    private TextArea coverLetterField;

    private Job selectedJob;
    @FXML
    private AnchorPane rootPane;

    // Méthode pour recevoir l'objet Job et désactiver les champs sauf "Lettre de motivation"
    public void setJob(Job job) {
        this.selectedJob = job;

        jobNameField.setText(job.getTitle());
        jobDescriptionField.setText(job.getDescription());
        jobLocationField.setText(job.getLocation());
        jobTypeField.setText(job.getJobType());

        // Désactiver tous les champs sauf la lettre de motivation
        jobNameField.setEditable(false);
        jobNameField.setDisable(true);
        jobDescriptionField.setEditable(false);
        jobDescriptionField.setDisable(true);
        jobLocationField.setEditable(false);
        jobLocationField.setDisable(true);
        jobTypeField.setEditable(false);
        jobTypeField.setDisable(true);
    }

    // Méthode pour soumettre la candidature
    @FXML
    private void submitApplication() {
        String coverLetter = coverLetterField.getText().trim();

        if (coverLetter.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir la lettre de motivation avant de soumettre votre candidature !");
            return;
        }

        Applications application = new Applications();
        application.setJobId(selectedJob.getJobId());
        application.setUserId(1);
        application.setAppliedDate(new Date());
        application.setStatus("En attente");
        application.setCoverLetter(coverLetter);

        ApplicationService applicationService = new ApplicationService();
        try {
            applicationService.ajouter(application);
            showAlert("Succès", "Votre candidature a été soumise avec succès !");
            redirectToCandidatureList();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur est survenue lors de la soumission de votre candidature.");
        }
    }

    // Rediriger vers la liste des candidatures
    private void redirectToCandidatureList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/candidature_list.fxml"));
            AnchorPane candidatureListView = loader.load();

            if (rootPane != null) {
                Scene currentScene = rootPane.getScene();
                currentScene.setRoot(candidatureListView);
            } else {
                Stage stage = (Stage) jobNameField.getScene().getWindow();
                stage.getScene().setRoot(candidatureListView);
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de rediriger vers la liste des candidatures.");
        }
    }

    // Affichage d'une boîte de dialogue d'alerte
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
