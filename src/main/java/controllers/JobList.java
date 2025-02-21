package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import models.Job;
import Service.JobService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class JobList {

    @FXML
    private ListView<Job> jobList;  // Liste des offres d'emploi

    @FXML
    public void initialize() {
        JobService jobService = new JobService();

        // Charger les données dans la ListView
        try {
            List<Job> jobs = jobService.recuperer();
            jobList.setItems(FXCollections.observableArrayList(jobs));
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des jobs : " + e.getMessage());
        }

        // Définir la façon dont chaque Job est affiché dans la ListView
        jobList.setCellFactory(lv -> new ListCell<Job>() {
            @Override
            protected void updateItem(Job job, boolean empty) {
                super.updateItem(job, empty);
                if (empty || job == null) {
                    setText(null);
                } else {
                    setText("ID: " + job.getJobId() + " - Titre: " + job.getTitle());
                }
            }
        });
    }

    // Méthode pour gérer l'action "Postuler"
    @FXML
    private void postuler() {
        try {
            // Récupérer l'emploi sélectionné
            Job selectedJob = jobList.getSelectionModel().getSelectedItem();
            if (selectedJob == null) {
                System.out.println("Veuillez sélectionner une offre d'emploi !");
                return;
            }

            // Vérifier si le fichier FXML est trouvé
            URL fxmlLocation = getClass().getResource("/Postulation.fxml");
            if (fxmlLocation == null) {
                System.err.println("ERREUR : Fichier Postulation.fxml introuvable !");
                return;
            }

            // Charger l'interface Postulation.fxml
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            // Passer l'objet Job à l'interface Postulation
            PostulationController postulerController = loader.getController();
            postulerController.setJob(selectedJob);

            // Créer une nouvelle fenêtre pour l'interface de postulation
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);  // Bloque l'interaction avec la fenêtre principale
            stage.setTitle("Postuler pour : " + selectedJob.getTitle());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
