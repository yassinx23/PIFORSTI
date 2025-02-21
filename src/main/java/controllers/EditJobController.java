package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Job;
import Service.JobService;

import java.io.IOException;

public class EditJobController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField typeField;

    private Job selectedJob;
    private JobService jobService = new JobService(); // Service pour gérer les jobs

    // Méthode pour initialiser les champs avec les données du job sélectionné
    public void setJob(Job job) {
        this.selectedJob = job;
        titleField.setText(job.getTitle());
        descriptionField.setText(job.getDescription());
        locationField.setText(job.getLocation());
        typeField.setText(job.getJobType());
    }

    // Méthode pour mettre à jour les informations du job
    @FXML
    public void handleUpdateJob() {
        // Mise à jour des informations du job
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();

        // Vérifier si tous les champs obligatoires sont remplis
        if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty()) {
            showErrorMessage("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier la longueur des champs
        if (title.length() > 100 || description.length() > 500 || location.length() > 100 || type.length() > 50) {
            showErrorMessage("Erreur", "Veuillez respecter les longueurs maximales pour chaque champ.");
            return;
        }

        // Vérifier que les champs ne contiennent pas de chiffres ou de caractères spéciaux (si cela est requis)
        if (!title.matches("[a-zA-Z\\s]+") || !location.matches("[a-zA-Z\\s]+")) {
            showErrorMessage("Erreur", "Les champs titre et localisation ne doivent contenir que des lettres et des espaces.");
            return;
        }

        // Vérifier que la description ne contient pas de chiffres ou caractères spéciaux non autorisés
        if (!description.matches("[a-zA-Z0-9\\s.,!?;:()-]*")) {
            showErrorMessage("Erreur", "La description contient des caractères non autorisés.");
            return;
        }

        // Vérifier le format du type d'emploi (par exemple, CDI, CDD, Freelance)
        if (!type.matches("CDI|CDD|Freelance")) {
            showErrorMessage("Erreur", "Veuillez entrer un type d'emploi valide (CDI, CDD, Freelance).");
            return;
        }

        // Mise à jour des informations du job
        selectedJob.setTitle(title);
        selectedJob.setDescription(description);
        selectedJob.setLocation(location);
        selectedJob.setJobType(type);

        try {
            // Appeler la méthode de mise à jour dans le service
            jobService.modifier(selectedJob);

            // Afficher un message de succès
            showSuccessMessage("Succès", "Le job a été mis à jour avec succès !");

            // Rediriger vers la liste des emplois
            redirectToJobList();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Erreur", "Erreur lors de la mise à jour du job.");
        }
    }


    // Méthode pour gérer le clic sur le bouton "Annuler"
    @FXML
    private void handleCancel() {
        // Fermez la fenêtre de modification
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    // Méthode pour rediriger vers la liste des emplois
    private void redirectToJobList() {
        try {
            // Charger le fichier FXML de la liste des emplois
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/job_list_rh.fxml")); // Mettez à jour le chemin
            Parent root = loader.load();

            // Obtenir la scène actuelle
            Stage stage = (Stage) titleField.getScene().getWindow();

            // Définir la nouvelle scène
            stage.setScene(new Scene(root));
            stage.setTitle("Liste des Emplois"); // Titre de la fenêtre
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Erreur", "Erreur lors de la redirection vers la liste des emplois.");
        }
    }

    // Méthode pour afficher un message d'erreur
    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour afficher un message de succès
    private void showSuccessMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
