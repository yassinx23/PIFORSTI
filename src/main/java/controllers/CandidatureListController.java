package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import models.Applications; // Utilisation de la classe Applications
import Service.ApplicationService; // Utilisation du service ApplicationService

import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

public class CandidatureListController {

    @FXML
    private ListView<Applications> candidatureListView; // Liste des candidatures

    private ApplicationService applicationService; // Instance du service

    // Méthode appelée à l'initialisation pour charger les candidatures
    @FXML
    public void initialize() {
        applicationService = new ApplicationService(); // Créer l'instance une fois
        chargerCandidatures(); // Charger les candidatures dès l'initialisation
    }

    // Méthode pour charger toutes les candidatures
    public void chargerCandidatures() {
        try {
            // Récupérer toutes les candidatures via la méthode recupererAll
            List<Applications> allApplications = applicationService.recupererAll();

            // Remplir la ListView avec les candidatures récupérées
            candidatureListView.getItems().clear(); // Clear any previous data
            if (allApplications != null && !allApplications.isEmpty()) {
                candidatureListView.getItems().addAll(allApplications); // Ajouter toutes les candidatures dans la ListView
            } else {
                showErrorAlert("Aucune donnée", "Aucune candidature disponible.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Erreur lors du chargement des candidatures.", e.getMessage());
        }
    }

    @FXML
    private void modifier() {
        Applications selectedCandidature = candidatureListView.getSelectionModel().getSelectedItem();
        if (selectedCandidature != null) {
            try {
                // Affichage des informations de la candidature sélectionnée
                System.out.println("Modification de la candidature : " + selectedCandidature);

                // Charger l'écran de modification de la candidature
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/modification.fxml"));
                AnchorPane modifyCandidatureView = loader.load();

                // Passer la candidature sélectionnée au contrôleur de modification
                ModificationController controller = loader.getController();
                controller.setApplication(selectedCandidature);  // Passez la candidature à modifier au contrôleur

                // Obtenir la scène actuelle et rediriger vers l'écran de modification
                Scene currentScene = candidatureListView.getScene();
                currentScene.setRoot(modifyCandidatureView);

            } catch (IOException e) {
                e.printStackTrace();
                showErrorAlert("Erreur lors de l'ouverture de l'écran de modification", e.getMessage());
            }
        } else {
            showErrorAlert("Aucune candidature sélectionnée", "Veuillez sélectionner une candidature à modifier.");
        }
    }

    // Méthode pour supprimer une candidature
    @FXML
    private void supprimer() {
        Applications selectedCandidature = candidatureListView.getSelectionModel().getSelectedItem();
        if (selectedCandidature != null) {
            try {
                applicationService.supprimer(selectedCandidature.getApplicationId());  // Supprimer la candidature
                System.out.println("Suppression de la candidature : " + selectedCandidature);
                chargerCandidatures();  // Rafraîchir la liste des candidatures après suppression
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorAlert("Erreur lors de la suppression", e.getMessage());
            }
        } else {
            showErrorAlert("Aucune candidature sélectionnée", "Veuillez sélectionner une candidature à supprimer.");
        }
    }

    // Méthode pour afficher une alerte d'erreur
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
