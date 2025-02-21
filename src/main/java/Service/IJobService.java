package Service;

import models.Job;
import java.sql.SQLException;
import java.util.List;

public interface IJobService {

    void ajouter(Job job) throws SQLException;

    List<Job> recuperer() throws SQLException;

    // Ajouter la méthode recupererAll()
    List<Job> recupererAll() throws SQLException;

    void modifier(Job job) throws SQLException;

    void supprimer(int jobId) throws SQLException;

    Job recupererParId(int jobId) throws SQLException;  // Ajouter cette méthode
}
