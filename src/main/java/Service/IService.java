package Service;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void ajouter(T t) throws SQLException; // La méthode peut lever SQLException
    void supprimer(T t);
    void modifier(T t);
    List<T> recuperer() throws SQLException; // La méthode peut aussi lever SQLException
}
