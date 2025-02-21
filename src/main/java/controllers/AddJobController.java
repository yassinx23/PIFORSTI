package controllers;

import Service.JobService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import models.Job;

import java.io.IOException;
import java.sql.SQLException;

public class AddJobController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField typeField;

    private final JobService jobService = new JobService();

    // Méthode pour ajouter un emploi
    @FXML
    private void handleAddJob(javafx.event.ActionEvent event) {
        // Récupérer les champs de texte
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();

        // Vérifier si tous les champs obligatoires sont remplis
        if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier la longueur des champs
        if (title.length() > 100 || description.length() > 500 || location.length() > 100 || type.length() > 50) {
            showAlert("Erreur", "Veuillez respecter les longueurs maximales pour chaque champ.");
            return;
        }

        // Vérifier le format du type d'emploi (par exemple, CDI, CDD, Freelance)
        if (!type.matches("CDI|CDD|Freelance")) {
            showAlert("Erreur", "Veuillez entrer un type d'emploi valide (CDI, CDD, Freelance).");
            return;
        }

        // Vérifier qu'aucun champ ne contient de chiffres
        if (containsNumbers(title) || containsNumbers(description) || containsNumbers(location) || containsNumbers(type)) {
            showAlert("Erreur", "Les champs ne doivent pas contenir de chiffres.");
            return;
        }

        // Créer un nouvel objet Job avec les valeurs récupérées
        Job newJob = new Job(title, description, location, type);

        try {
            // Ajouter le job dans la base de données
            jobService.ajouter(newJob);
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible d'ajouter le job dans la base de données. Veuillez réessayer.");
            return;
        }

        // Afficher une alerte de succès
        showAlert("Succès", "Job ajouté avec succès !");

        // Fermer la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // Recharger la liste des jobs
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/job_list_rh.fxml"));
            Parent root = loader.load();

            // Mettre à jour la liste des jobs dans le contrôleur
            JobListController jobListController = loader.getController();
            jobListController.loadJobs();

            // Afficher la nouvelle fenêtre avec la liste des jobs
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("Liste des Jobs");
            newStage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de retourner à la liste des jobs.");
        }
    }

    // Méthode pour vérifier la présence de chiffres dans une chaîne
    private boolean containsNumbers(String input) {
        return input.matches(".*\\d.*");  // Vérifie si la chaîne contient un chiffre
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
