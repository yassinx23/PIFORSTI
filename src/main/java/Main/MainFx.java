/*package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Charger le fichier FXML de l'interface de la liste des offres d'emploi (job_list.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/job_list.fxml"));
            Parent root = loader.load();

            // Créer une scène avec la racine de l'interface
            Scene scene = new Scene(root);

            // Définir le titre de la fenêtre
            primaryStage.setTitle("Application de Gestion des Candidatures");

            // Ajouter la scène à la fenêtre principale (Stage)
            primaryStage.setScene(scene);

            // Afficher la fenêtre principale
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Lancer l'application JavaFX
        launch(args);
    }

    // Méthode pour tester le chargement du fichier FXML
    public Parent loadFXML(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        return loader.load();
    }
}//
*/
package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application { // ✅ Hérite correctement de Application

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger le fichier FXML de l'interface de la liste des offres d'emploi pour RH (job_list_rh.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/job_list_rh.fxml"));
            Parent root = loader.load();

            // Créer une scène avec la racine de l'interface
            Scene scene = new Scene(root);

            // Définir le titre de la fenêtre
            primaryStage.setTitle("Gestion des Offres d'Emploi - RH");

            // Ajouter la scène à la fenêtre principale (Stage)
            primaryStage.setScene(scene);

            // Afficher la fenêtre principale
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Lancer l'application JavaFX
        launch(args);
    }

    // Méthode pour tester le chargement du fichier FXML
    public Parent loadFXML(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        return loader.load();
    }
}
