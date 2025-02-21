package Service;

import models.Applications;
import models.Job;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void ajouter(T t) throws SQLException; // La méthode peut lever SQLException
    void supprimer(int applicationId) throws SQLException;
    void modifier(T t) throws SQLException;
    List<T> recuperer() throws SQLException; // La méthode peut aussi lever SQLException
    List<Applications> recupererAll() throws SQLException;
}
