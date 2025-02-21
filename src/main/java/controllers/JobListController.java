package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import models.Job;
import Service.JobService;

import java.sql.SQLException;
import java.util.List;

public class JobListController {

    @FXML
    private ListView<Job> jobListView;

    private final JobService jobService = new JobService(); // Service pour gérer les jobs

    @FXML
    public void initialize() {
        loadJobs(); // Charger les jobs au démarrage
    }

    // Charger les emplois depuis la base de données
    public void loadJobs() {
        try {
            List<Job> jobs = jobService.recuperer();
            jobListView.setItems(FXCollections.observableArrayList(jobs));

            // Personnaliser l'affichage des jobs dans la ListView
            jobListView.setCellFactory(lv -> new ListCell<Job>() {
                @Override
                protected void updateItem(Job job, boolean empty) {
                    super.updateItem(job, empty);
                    setText(empty || job == null ? null : job.getTitle() + " - " + job.getLocation());
                }
            });
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de récupérer les emplois: " + e.getMessage());
        }
    }

    // Ajouter un emploi
    @FXML
    private void handleAddJob() {
        try {
            // Charger l'FXML de la page add_job.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add_job.fxml")); // Mettez à jour le chemin

            // Créer la nouvelle scène avec l'FXML chargé
            Scene scene = new Scene(loader.load());

            // Changer de scène sans manipuler le Stage directement
            Stage currentStage = (Stage) jobListView.getScene().getWindow(); // Récupérer le stage actuel
            currentStage.setScene(scene);
            currentStage.show(); // Afficher la nouvelle scène
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir la page d'ajout de job.");
        }
    }

    // Modifier un emploi sélectionné
    @FXML
    private void handleEditJob() {
        Job selectedJob = jobListView.getSelectionModel().getSelectedItem();
        if (selectedJob == null) {
            showAlert("Aucune sélection", "Veuillez sélectionner un emploi à modifier.");
            return;
        }

        try {
            // Charger l'FXML de la page edit_job.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edit_job.fxml")); // Mettez à jour le chemin
            Parent root = loader.load();

            // Créer la nouvelle scène avec l'FXML chargé
            Stage stage = new Stage();
            stage.setTitle("Modifier un emploi");
            stage.setScene(new Scene(root));
            stage.show();

            // Passer l'emploi sélectionné au contrôleur de la nouvelle scène
            EditJobController controller = loader.getController();
            controller.setJob(selectedJob);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir la page de modification de job.");
        }
    }

    // Supprimer un emploi sélectionné
    @FXML
    private void handleDeleteJob() {
        Job selectedJob = jobListView.getSelectionModel().getSelectedItem();
        if (selectedJob == null) {
            showAlert("Aucune sélection", "Veuillez sélectionner un emploi à supprimer.");
            return;
        }

        try {
            jobService.supprimer(selectedJob.getJobId());  // Supprimer de la base
            jobListView.getItems().remove(selectedJob); // Mettre à jour l'affichage
            showAlert("Suppression", "Le job a été supprimé !");
        } catch (SQLException e) {
            showAlert("Erreur", "Échec de la suppression: " + e.getMessage());
        }
    }

    // Afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
